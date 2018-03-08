package com.ztt.stockinhome.libs;

import android.widget.ImageView;


public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object listener);
}
