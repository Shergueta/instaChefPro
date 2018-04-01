package com.example.sharongueta.instachefpro.Model;

import android.net.Uri;

/**
 * Created by sharongueta on 27/03/2018.
 */

public class ResourceUploadRequest {

    private boolean succeeded;
    private Uri downloadUri;
    private String message;

    public ResourceUploadRequest(boolean succeeded, Uri downloadUri, String message) {
        this.succeeded = succeeded;
        this.downloadUri = downloadUri;
        this.message = message;
    }


    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public Uri getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(Uri downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
