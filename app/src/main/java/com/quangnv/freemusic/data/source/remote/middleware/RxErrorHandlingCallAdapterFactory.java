package com.quangnv.freemusic.data.source.remote.middleware;

/**
 * Created by Tuanlvt on 13/03/2018.
 */

import android.text.TextUtils;

import com.google.gson.Gson;
import com.quangnv.freemusic.data.source.remote.error.BaseException;
import com.quangnv.freemusic.data.source.remote.response.ErrorResponse;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import okhttp3.internal.Util;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by quangnv on 11/10/2018
 */

public final class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private static final String TAG = RxErrorHandlingCallAdapterFactory.class.getName();

    private final RxJava2CallAdapterFactory mOriginal;

    private RxErrorHandlingCallAdapterFactory() {
        mOriginal = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(returnType,
                mOriginal.get(returnType, annotations, retrofit));
    }

    /**
     * RxCallAdapterWrapper, @param <R> RxCallAdapterWrapper </>
     */
    class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Type mReturnType;
        private final CallAdapter<R, Object> mWrapped;

        RxCallAdapterWrapper(Type type, CallAdapter<R, Object> wrapped) {
            mReturnType = type;
            mWrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return mWrapped.responseType();
        }

        @Override
        public Object adapt(Call<R> call) {
            Class<?> rawType = getRawType(mReturnType);

            boolean isFlowable = rawType == Flowable.class;
            boolean isSingle = rawType == Single.class;
            boolean isMaybe = rawType == Maybe.class;
            boolean isCompletable = rawType == Completable.class;
            if (rawType != Observable.class && !isFlowable && !isSingle && !isMaybe) {
                return null;
            }
            if (!(mReturnType instanceof ParameterizedType)) {
                String name = isFlowable ? "Flowable"
                        : isSingle ? "Single" : isMaybe ? "Maybe" : "Observable";
                throw new IllegalStateException(name + " return type must be parameterized");
            }

            if (isFlowable) {
                return ((Flowable) mWrapped.adapt(call)).onErrorResumeNext(
                        new Function<Throwable, Publisher>() {
                            @Override
                            public Publisher apply(Throwable throwable) throws Exception {
                                return Flowable.error(convertToBaseException(throwable));
                            }
                        });
            }
            if (isSingle) {
                return ((Single) mWrapped.adapt(call)).onErrorResumeNext(
                        new Function<Throwable, SingleSource>() {
                            @Override
                            public SingleSource apply(Throwable throwable) throws Exception {
                                return Single.error(convertToBaseException(throwable));
                            }
                        });
            }
            if (isMaybe) {
                return ((Maybe) mWrapped.adapt(call)).onErrorResumeNext(
                        new Function<Throwable, MaybeSource>() {
                            @Override
                            public MaybeSource apply(Throwable throwable) throws Exception {
                                return Maybe.error(convertToBaseException(throwable));
                            }
                        });
            }
            if (isCompletable) {
                return ((Completable) mWrapped.adapt(call)).onErrorResumeNext(
                        new Function<Throwable, CompletableSource>() {
                            @Override
                            public CompletableSource apply(Throwable throwable) throws Exception {
                                return Completable.error(convertToBaseException(throwable));
                            }
                        });
            }
            return ((Observable) mWrapped.adapt(call)).onErrorResumeNext(
                    new Function<Throwable, ObservableSource>() {
                        @Override
                        public ObservableSource apply(Throwable throwable) throws Exception {
                            return Observable.error(convertToBaseException(throwable));
                        }
                    });
        }

        private BaseException convertToBaseException(Throwable throwable) {
            if (throwable instanceof BaseException) {
                return (BaseException) throwable;
            }

            if (throwable instanceof IOException) {
                return BaseException.toNetworkError(throwable);
            }

            if (throwable instanceof HttpException) {
                BufferedSource source = null;
                try {
                    HttpException httpException = (HttpException) throwable;
                    Response response = httpException.response();
                    source = response.errorBody().source();
                    String errrorMsg = getErrorString(source);
                    ErrorResponse errorResponse =
                            new Gson().fromJson(errrorMsg, ErrorResponse.class);
                    if (errorResponse != null && !TextUtils.isEmpty(errorResponse.getMessage())) {
                        return BaseException.toServerError(errorResponse);
                    } else {
                        return BaseException.toHttpError(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Util.closeQuietly(source);
                }
            }
            return BaseException.toUnexpectedError(throwable);
        }
    }

    private String getErrorString(BufferedSource source) throws IOException {
        Charset charset = Util.bomAwareCharset(source, UTF_8);
        return source.readString(charset);
    }
}
