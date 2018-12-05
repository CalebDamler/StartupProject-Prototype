package com.calebco.caleb.prototype1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


//data compression helper
public class MediaHelper extends AppCompatActivity{

    public String compressBitmap(Bitmap bitmap){
        String filename = "bitmap.png";
        FileOutputStream stream = null;
        try {
            stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.recycle();

        return filename;

    }

    public Bitmap decompressBimap(String file){

        Bitmap bmp = null;

        try {
            FileInputStream is = this.openFileInput(file);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bmp;

    }
    /*
    public --- compressVideo(){

    }

    public --- decompressVideo(){

    }
    */

}
