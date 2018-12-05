package com.calebco.caleb.prototype1;

import android.graphics.Bitmap;
import android.net.Uri;

public class VideoListClass {

    private Bitmap bitmap;
    //private String title;
    private Uri videoUri;


    public VideoListClass(){

    }
    public VideoListClass(Bitmap bitmap, Uri videoUri) {
        //this.title = title;
        this.bitmap = bitmap;
        this.videoUri = videoUri;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


   /// public String getTitle() {
      //  return title;
    //}

    //public void setTitle(String title) {
      //  this.title = title;
    //}

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }





}
