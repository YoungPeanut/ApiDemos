package com.example.android.graphics;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.android.apis.R;

/**
 * Created by chenshao on 17/1/12.
 */
public class AnimatedStateBorderImageView extends ImageView {

    AnimatedStateBorderDrawable mBorder;

    public AnimatedStateBorderImageView(Context context) {
        super(context);

        init(context, null, 0, 0);
    }

    public AnimatedStateBorderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0, 0);
    }

    public AnimatedStateBorderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimatedStateBorderImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        setWillNotDraw(false);
        int[][] states = new int[][]{
                {-android.R.attr.state_pressed},
                {android.R.attr.state_pressed}
        };
        int[] colors = new int[]{
                context.getResources().getColor(R.color.primary),
                context.getResources().getColor(R.color.accent)
        };
        ColorStateList colorStateList = new ColorStateList(states, colors);

        mBorder = new AnimatedStateBorderDrawable(colorStateList, getPaddingLeft(), getPaddingLeft() / 2, context.getResources().getInteger(android.R.integer.config_mediumAnimTime));
        mBorder.setCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBorder.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBorder.draw(canvas);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        mBorder.setState(getDrawableState());
    }

    @Override
    protected boolean verifyDrawable(Drawable dr) {
        return super.verifyDrawable(dr) || dr == mBorder;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        mBorder.jumpToCurrentState();
    }
}