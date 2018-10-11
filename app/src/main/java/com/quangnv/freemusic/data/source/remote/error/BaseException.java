package com.quangnv.freemusic.data.source.remote.error;

import android.support.annotation.Nullable;

import com.quangnv.freemusic.data.source.remote.response.ErrorResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;

/**
 * Created by quangnv on 11/10/2018
 */

public final class BaseException extends RuntimeException {

    @Type
    private final String mType;
    @Nullable
    private Response mResponse;
    @Nullable
    private ErrorResponse mErrorResponse;

    private BaseException(@Type String type, Throwable cause) {
        super(cause.getMessage(), cause);
        mType = type;
    }

    private BaseException(@Type String type, @Nullable Response response) {
        mType = type;
        mResponse = response;
    }

    public BaseException(@Type String type, @Nullable ErrorResponse response) {
        mType = type;
        mErrorResponse = response;
    }

    public static BaseException toNetworkError(Throwable cause) {
        return new BaseException(Type.NETWORK, cause);
    }

    public static BaseException toHttpError(Response response) {
        return new BaseException(Type.HTTP, response);
    }

    public static BaseException toUnexpectedError(Throwable cause) {
        return new BaseException(Type.UNEXPECTED, cause);
    }

    public static BaseException toServerError(ErrorResponse response) {
        return new BaseException(Type.SERVER, response);
    }

    @Type
    public String getErrorType() {
        return mType;
    }

    public String getMessage() {
        switch (mType) {
            case Type.SERVER:
                if (mErrorResponse != null) {
                    return mErrorResponse.getMessage();
                }
                return "";
            case Type.NETWORK:
                return getNetworkErrorMessage(getCause());
            case Type.HTTP:
                if (mResponse != null) {
                    return getHttpErrorMessage(mResponse.code());
                }
                return "Error";
            default:
                return "Error";
        }
    }

    public String getType() {
        return mType;
    }

    public int getHttpCode() {
        return mResponse == null ? 0 : mResponse.code();
    }

    private String getNetworkErrorMessage(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return throwable.getMessage();
        }

        if (throwable instanceof UnknownHostException) {
            return "Internet connection is not available. Please connect and try again!";
        }

        if (throwable instanceof IOException) {
            return throwable.getMessage();
        }

        return throwable.getMessage();
    }

    private String getHttpErrorMessage(int httpCode) {
        if (httpCode >= 300 && httpCode <= 308) {
            // Redirection
            return "It was transferred to a different URL. I'm sorry for causing you trouble";
        }
        if (httpCode >= 400 && httpCode <= 451) {
            // Client error
            return "An error occurred on the application side. Please try again later!";
        }
        if (httpCode >= 500 && httpCode <= 511) {
            // Server error
            return "A server error occurred. Please try again later!";
        }

        // Unofficial error
        return "An error occurred. Please try again later!";
    }
}
