package com.example.android.sharingfiles_review;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public class ListItem {
    private File file;
    private Uri uri;
    private Bitmap image;
    private String title;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
