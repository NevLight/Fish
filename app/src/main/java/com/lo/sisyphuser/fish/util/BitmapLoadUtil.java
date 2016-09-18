package com.lo.sisyphuser.fish.util;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

/**
 * Created by xx on 2016/9/14.
 */
public class BitmapLoadUtil {
    /**
     * 加载网络图片
     *
     * @param imageView
     *            容器
     * @param url
     *            图片路径
     * @param defult
     *            默认图片
     * @param fail
     *            加载失败图片
     */
    public static ImageLoader loadNetBitmap(ImageLoader imageLoader,ImageView imageView, String url, int defult) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defult) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(defult) // 设置图片加载或解码过程中发生错误显示的图片
                .showStubImage(defult)
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象
        imageLoader.displayImage(url, imageView,options);
        return imageLoader;
    }

    public static ImageLoader loadLocalBitmap(ImageLoader imageLoader, ImageView imageView, String url, int defult) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defult) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(defult) // 设置图片加载或解码过程中发生错误显示的图片
                .showStubImage(defult)
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象
        String fileUrl= ImageDownloader.Scheme.FILE.wrap(url);
        imageLoader.displayImage(fileUrl, imageView,options);
        return imageLoader;
    }
}
