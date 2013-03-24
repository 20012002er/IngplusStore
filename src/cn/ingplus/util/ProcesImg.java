package cn.ingplus.util;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ProcesImg extends AsyncTask<String, Void, Bitmap> {
	ImageView imageView;
	
	public ProcesImg(ImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		String url = urls[0];  
        Bitmap tmpBitmap = null;  
        try {  
            InputStream is = new java.net.URL(url).openStream();  
            tmpBitmap = BitmapFactory.decodeStream(is);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return tmpBitmap;  
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		imageView.setImageBitmap(result);
	}

}
