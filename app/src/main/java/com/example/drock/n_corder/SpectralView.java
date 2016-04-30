package com.example.drock.n_corder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class SpectralView extends View {
    private Paint mBackgroundPaint;
    private Paint[] mPaints = new Paint[256];
    private Bitmap mDataBitmap;
    private Canvas mDataCanvas;
    private int mActiveColumn = 0;
    private Paint mFrontPaint;
    private int mBlockSize = 512;
    private float mHzPerBin;
    private PointF mTouchPoint;
    private float mCrossHairInnerWidth;
    private float mCrossHairOuterWidth;
    private Paint mCrossHairInnerPaint;
    private Paint mCrossHairOuterPaint;
    private Paint mTextPaint;

    public SpectralView(Context context) {
        super(context);
        init(null, 0);
    }

    public SpectralView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SpectralView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void setBlockSize(int size ) {mBlockSize = size;}

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        synchronized (this) {
            mDataBitmap = Bitmap.createBitmap((int) w, (int) mBlockSize, Bitmap.Config.ARGB_8888);
            mDataCanvas = new Canvas(mDataBitmap);
            mDataCanvas.drawColor(getResources().getColor(android.R.color.black));
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        //paint to show intensity data
        float[] hsv = {0f, 1f, 1f};
        for(int i =0; i < mPaints.length; i++) {
            mPaints[i] = new Paint();
            hsv[0] = 240f-(float)i*240f/255f;
            mPaints[i].setColor(Color.HSVToColor(hsv));
            //mPaints[i].setARGB(0xFF, i, i, i);
        }
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(getResources().getColor(android.R.color.background_dark));
        mFrontPaint = new Paint();
        mFrontPaint.setARGB(0xFF, 0xFF, 0xFF, 0xFF);
        //float width =  getResources().getDimension(R.dimen.spectral_view_xhair_inner_size);
        mFrontPaint.setStrokeWidth(1);

        //cross-hair
        mCrossHairInnerWidth = getResources().getDimensionPixelSize(R.dimen.spectral_view_xhair_inner_size);
        mCrossHairOuterWidth = getResources().getDimensionPixelSize(R.dimen.spectral_view_xhair_outer_size);
        mCrossHairInnerPaint = new Paint();
        mCrossHairInnerPaint.setARGB(0x80, 0x00, 0x00, 0x00);
        mCrossHairInnerPaint.setStrokeWidth(mCrossHairInnerWidth);
        mCrossHairOuterPaint = new Paint();
        mCrossHairOuterPaint.setARGB(0x80, 0xFF, 0xFF, 0xFF);
        mCrossHairOuterPaint.setStrokeWidth(mCrossHairOuterWidth);
        mTextPaint = new Paint();
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.spectral_view_text_size));
        mTextPaint.setColor(Color.WHITE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (this) {
            Rect srcRect = new Rect(0, 0, mDataCanvas.getWidth(), mDataCanvas.getHeight()-1);
            Rect dstRect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
            canvas.drawBitmap(mDataBitmap, srcRect, dstRect, mBackgroundPaint);
            //canvas.drawBitmap(mDataBitmap, 0, 0, mBackgroundPaint);
            drawCrossHair(canvas);
        }
    }

    protected void drawCrossHair(Canvas canvas) {
        if(mTouchPoint != null) {
            for(int pass = 0; pass < 2; pass++) {
                Paint paint = pass == 0 ? mCrossHairOuterPaint : mCrossHairInnerPaint;
                canvas.drawLine(mTouchPoint.x, 0,
                        mTouchPoint.x, mTouchPoint.y - mCrossHairOuterWidth, paint);
                canvas.drawLine(mTouchPoint.x, mTouchPoint.y + mCrossHairOuterWidth,
                        mTouchPoint.x, canvas.getHeight(), paint);
                canvas.drawLine(0, mTouchPoint.y,
                        mTouchPoint.x - mCrossHairOuterWidth, mTouchPoint.y, paint);
                canvas.drawLine(mTouchPoint.x + mCrossHairOuterWidth,
                        mTouchPoint.y, canvas.getWidth(), mTouchPoint.y, paint);
            }

            float frequency = calcFrequencyFromPoint(mTouchPoint);
            Rect textBounds = new Rect();
            String text = String.format("%d Hz", (int) frequency);
            mTextPaint.getTextBounds(text, 0, text.length(), textBounds);
            canvas.drawText(text, 10, 10+textBounds.height(), mTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mTouchPoint = new PointF(event.getX(), event.getY());
        } else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
        } else if(event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            mTouchPoint = new PointF(event.getX(), event.getY());
        }

        return true;
    }

    public void addFFTData(double[] data, double hzPerBin) {
        synchronized (this) {
            mHzPerBin = (float)hzPerBin;
            if (mDataCanvas != null) {
                int height = mDataCanvas.getHeight();
                mDataCanvas.drawLine(mActiveColumn, 0, mActiveColumn, height, mBackgroundPaint);
                for (int i = 0; i < data.length && i / 2 <= height; ) {
                    //int y = height - i/2;
                    int y = height - i-2;
                    double real = data[i++];
                    double imag = data[i++];
                    int colorIndex = (int) (Math.sqrt(real * real + imag * imag) * 255.0);
                    //int colorIndex = (int) (Math.sqrt(real * real) * 255.0);
                    colorIndex = colorIndex < 0 ? 0 : (colorIndex <= 255 ? colorIndex : 255);
                    mDataCanvas.drawPoint(mActiveColumn, y, mPaints[colorIndex]);
                    //colorIndex = (int) (Math.sqrt(imag * imag) * 255.0);
                    //colorIndex = colorIndex < 0 ? 0 : (colorIndex <= 255 ? colorIndex : 255);
                    mDataCanvas.drawPoint(mActiveColumn, y+1, mPaints[colorIndex]);

                }
                mActiveColumn++;
                if (mActiveColumn >= mDataBitmap.getWidth())
                    mActiveColumn = 0;
                mDataCanvas.drawLine(mActiveColumn, 0, mActiveColumn, height, mFrontPaint);
            }
        }
        postInvalidate();
    }

    public float calcFrequencyFromPoint(PointF point) {
        float frequencyRange = mHzPerBin * mBlockSize;
        float height = getHeight();
        return frequencyRange * (height - point.y) /getHeight();
    }
}
