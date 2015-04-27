package com.common.image;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache{

	private static  LruCache<String, Bitmap> lruCache;
	
	private static BitmapCache bitmapCache;
	
	private LinkedHashMap<String, SoftReference<Bitmap>> softMap = new LinkedHashMap<String, SoftReference<Bitmap>>();
	
	public  static BitmapCache getInstance(){
		if(bitmapCache==null){
			bitmapCache = new BitmapCache();
		}
		return bitmapCache;
	}
	private BitmapCache(){
		int memoryCache = (int)(Runtime.getRuntime().maxMemory()/8);
		lruCache = new LruCache<String, Bitmap>(memoryCache){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
				
			}
			
			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				if(evicted){
					SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(oldValue);
					softMap.put(key, softReference);
				}
			}
		};
	}
	
	
	@Override
	public Bitmap getBitmap(String url) {
		Bitmap bm = null;
		bm = lruCache.get(url);
		if(bm!=null)
		{
			return bm;
		}else {
			SoftReference<Bitmap> softReference = softMap.get(url);
			
			if(softReference!=null){
				bm = softReference.get();
				if(bm!=null){
					softMap.remove(url);
					lruCache.put(url, bm);
				}
			}
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		
	}

}
