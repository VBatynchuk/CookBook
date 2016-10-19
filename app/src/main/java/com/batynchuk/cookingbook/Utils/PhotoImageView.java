package com.batynchuk.cookingbook.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Батинчук on 09.10.2016.
 */

public class PhotoImageView extends ImageView {

    public PhotoImageView(Context context) {
        super(context);

    }

    public PhotoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = width * 3 / 4;
        setMeasuredDimension(width, height);
    }
}
