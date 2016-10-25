package com.example.administrator.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.*;


/**
 * Created by Administrator on 2016/10/25.
 */

public class ImageLoad {

    private ImageView mImage;

   Handler mHandler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           mImage.setImageBitmap((Bitmap) msg.obj);
       }
   };

    public void showImageByThread(ImageView imageView, final String url){
        mImage = imageView;
        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitMapFromUrl(url);
                Message message = Message.obtain();
                message.obj = bitmap;
                mHandler.sendMessage(message);
            }
        }.start();
    }
    public Bitmap getBitMapFromUrl(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return  bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            try {
//  //              is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }
}
