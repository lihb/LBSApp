package com.android.Nearby;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {

	 private HashMap<String, SoftReference<Drawable>> imageCache;
	  
	     public AsyncImageLoader() {
	    	 imageCache = new HashMap<String, SoftReference<Drawable>>();
	     }
	     // ���� SoftReference ��ʹ�ã��μ� http://www.androiddiscuss.com/1-android-discuess/77744.html
	     public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
	    	 // ���ͼ���Ѿ������ع�����ڴ��л�ȡͼ�����
	         if (imageCache.containsKey(imageUrl)) {
	             SoftReference<Drawable> softReference = imageCache.get(imageUrl);
	             Drawable drawable = softReference.get();
	             if (drawable != null) {
	                 return drawable;
	             }
	         }
	         final Handler handler = new Handler() {
	             public void handleMessage(Message message) {
	            	 // ���߳��л�ȡ��Ϣ��ݣ����ص�ͼ��
	                 imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
	             }
	         };
	         new Thread() {
	             @Override
	             public void run() {
	                 Drawable drawable = loadImageFromUrl(imageUrl);
	                 imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
	                 // ����Ϣ�ػ�ȡ��Ϣ���󣬲����� message �� what=0,obj=drawable
	                 Message message = handler.obtainMessage(0, drawable);                 
	                 // ͼ��������ϣ�������Ϊ��Ϣ��ݷ��͸����߳�
	                 handler.sendMessage(message);
	             }
	         }.start();
	         return null;
	     }
	  
		public static Drawable loadImageFromUrl(String url) {
			URL m;
			InputStream i = null;
			try {
				m = new URL(url);
				i = (InputStream) m.getContent();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Drawable d = Drawable.createFromStream(i, "src");
			return d;
		}
	  
	     public interface ImageCallback {
	         public void imageLoaded(Drawable imageDrawable, String imageUrl);
	     }

}
