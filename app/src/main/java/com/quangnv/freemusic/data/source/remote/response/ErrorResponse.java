package com.quangnv.freemusic.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by quangnv on 11/10/2018
 */

public class ErrorResponse {
    @Expose
    private int mCode;
    @Expose
    @SerializedName("description")
    private List<String> mErrorMessage;

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        if (mErrorMessage != null
                && mErrorMessage != null
                && mErrorMessage.size() != 0) {
            return getMessageFromError();
        }
        return "";
    }

    public String getMessageFromError() {
        String listMessage = "";
        for (String message : mErrorMessage) {
            listMessage += message + "\n";
        }
        return listMessage;
    }
}
