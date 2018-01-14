package com.bishe.nongcun.imageloader;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;
import com.bishe.nongcun.R;
import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import java.io.File;

/**
 * @ 创建时间: 2017/8/22 on 10:17.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605C:\Users\Administrator\.android\build-cache\895ed11e48e4f87615d07c7ca179d3576e37a9ab\output\res\mipmap-xxhdpi-v4\default_image.png
 */

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)//
                .load(Uri.fromFile(new File(path)))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}
