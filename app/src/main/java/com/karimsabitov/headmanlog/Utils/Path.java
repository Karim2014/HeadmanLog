package com.karimsabitov.headmanlog.Utils;

import android.support.annotation.NonNull;

/**
 * Created by User on 15.12.2018.
 */

public class Path {
    private String mFullPath;
    private String mFolderPath;
    private String mFileName;

    static class Builder{
        private String mFullPath;
        private String mFolderPath;
        private String mFileName;

        Path.Builder setFullPath(@NonNull String path) {
            mFullPath = path;
            return this;
        }

        Path.Builder setFolderPath(@NonNull String path){
            mFolderPath = path;
            return this;
        }

        Path.Builder setFileName(@NonNull String path){
            mFileName = path;
            return this;
        }

        Path build(){
            Path path = new Path();
            path.setFileName(mFileName);
            path.setFolderPath(mFolderPath);
            path.setFullPath(mFullPath);
            return path;
        }
    }

    public String getFullPath() {
        return mFullPath;
    }

    public void setFullPath(String fullPath) {
        mFullPath = fullPath;
    }

    public String getFolderPath() {
        return mFolderPath;
    }

    public void setFolderPath(String folderPath) {
        mFolderPath = folderPath;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }
}