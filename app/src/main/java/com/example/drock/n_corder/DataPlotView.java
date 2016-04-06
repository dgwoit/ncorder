/*
* THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.example.drock.n_corder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.drock.n_corder.units.UnitConverter;
import com.example.drock.n_corder.units.UnitFormatter;
import com.example.drock.n_corder.units.Units;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class DataPlotView extends View {
    GestureDetector mScrollGestureDetector;
    ScaleGestureDetector mScaleGestureDetector;

    private String mExampleString; // TODO: use a default from R.string...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private Paint mGraticulePaint;
    private Paint mGraticuleTextBgPaint;
    private boolean mGraticuleDrawNumbers = true;
    private Paint mDataPaint;
    private float mDataPointSize = 1f;
    private boolean mDrawLines = false;

    private TextPaint mTextPaint;
    private UnitFormatter mRangeFormatter;
    private UnitFormatter mTimeFormatter = new UnitFormatter(Units.TIME);

    private List<Measurement> mVisibleDataSet;
    private float mRangeMin;
    private float mRangeMax;
    private float mRange;
    private long mStartTimeStamp = 0;
    private long mDataDomainMin = 0;
    private long mDataDomainMax = 0;
    private long mDomainMin;
    private long mDomainMax;
    private long mDomain;
    private long mTimePerPixel = 100000000;

    private boolean mAutoScroll = true;
    private boolean mGestureZooming = false;
    private DataPlotRegressionTool mTool;
    private UnitConverter mRangeConverter;
    private int mRangeSystemUnit = 0;
    private int mRangeDisplayUnit = 0;

    public DataPlotView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DataPlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DataPlotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mScrollGestureDetector = new GestureDetector(context, new GestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DataPlotView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.DataPlotView_exampleString);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.DataPlotView_exampleDimension,
                mExampleDimension);

//        if (a.hasValue(R.styleable.DataPlotView_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.DataPlotView_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }

        a.recycle();

        mGraticulePaint = new Paint();
        mGraticulePaint.setColor(Color.rgb(0xa0, 0xa0, 0xa0));
        float axisLabelSize = getResources().getDimension(R.dimen.axis_label_size);
        mGraticulePaint.setTextSize(axisLabelSize);
        mGraticuleTextBgPaint = new Paint();
        mGraticuleTextBgPaint.setColor(Color.rgb(0x00, 0x00, 0x00));


        mDataPaint = new Paint();
        mDataPaint.setColor(Color.rgb(0xcc, 0xcc, 0x00));
        mDataPointSize = getResources().getDimensionPixelSize(R.dimen.data_plot_point_size);

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);


    }

    List<Measurement> mData;
    protected synchronized void attachDataSource(List<Measurement> data) {
        synchronized (data) {
            mData = data;
        }
    }

    protected synchronized void detachDataSource() {
        synchronized (mData) {
            mData = null;
        }
    }

    protected void onDataChanged() {
        postInvalidate();
    }

    protected void setDrawLines(boolean b) {
        mDrawLines = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
//        int paddingLeft = getPaddingLeft();
//        int paddingTop = getPaddingTop();
//        int paddingRight = getPaddingRight();
//        int paddingBottom = getPaddingBottom();
//
//        int contentWidth = getWidth() - paddingLeft - paddingRight;
//        int contentHeight = getHeight() - paddingTop - paddingBottom;

        determineVisibleSet();
        if(mVisibleDataSet != null && mVisibleDataSet.size() > 0) {
            if(mTool != null)
                mTool.onDraw(canvas);
            drawGraticule(canvas);
            drawData(canvas);
            drawXAxisLabel(canvas);
            drawYAxisLabel(canvas);
        }

        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);


        // Draw the example drawable on top of the text.
//        if (mExampleDrawable != null) {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            mAutoScroll = true; //resume auto scroll
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            if(event.getPointerCount() == 1) {
                mAutoScroll = !mAutoScroll;
            }

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mAutoScroll = false;
            long domainChange = (long)(mDomain * (double)(distanceX / (float)getWidth()));
            float rangeChange = -mRange * (distanceY / (float)getHeight());
            adjustDomain(domainChange);
            adjustRange(rangeChange);
            return true;
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mAutoScroll = false;

            //x-scale
            if(Math.abs(detector.getPreviousSpanX() - detector.getCurrentSpanX()) >= 5f)
            {
                double xZoomFactor = detector.getPreviousSpanX() / detector.getCurrentSpanX();
                long originalDomain = mDomain;
                mDomain = (long)(mDomain * xZoomFactor);
                if(mDomain < getWidth())
                    mDomain = getWidth();
                if(xZoomFactor > 1f && mDomain < originalDomain) {
                    mDomain = originalDomain;
                }
                mDomainMin = (mDomainMin - mDomainMax) / 2 - mDomain / 2;
                mDomainMax = mDomainMin + mDomain;
                adjustDomain(0);
            }

            //y-scale
            if(Math.abs(detector.getPreviousSpanY() - detector.getCurrentSpanY()) >= 5f) {

                float yZoomFactor = detector.getPreviousSpanY() / detector.getCurrentSpanY();
                float originalRange = mRange;
                mRange = mRange * yZoomFactor;
                if(mRange < getHeight() * Float.MIN_NORMAL) {
                    mRange = getHeight() * Float.MIN_NORMAL;
                }
                if(mRange == Float.POSITIVE_INFINITY) {
                    mRange = originalRange;
                }
                mRangeMin = (mRangeMin + mRangeMax) / 2f - mRange / 2f;
                mRangeMax = mRangeMin + mRange;
                adjustRange(0);
            }
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mGestureZooming = true;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mGestureZooming = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mTool != null)
            return mTool.onTouchEvent(event);
        else {
            if (!mGestureZooming) {
                mScrollGestureDetector.onTouchEvent(event);
            }
            mScaleGestureDetector.onTouchEvent(event);
        }

        return true;
    }

    protected void drawGraticule(Canvas canvas) {
        //calculate range per division based on text height
        String yText = "YY.yyys";
        Rect yTextBounds = new Rect();
        Rect yTextBgBounds = new Rect();
        mGraticulePaint.getTextBounds(yText, 0, yText.length(), yTextBounds);
        float rangePerDivision = 4 * mRange * yTextBounds.height() / getHeight();
        float orderOfRangeMagnitude = (float)Math.ceil(Math.log10(rangePerDivision));
        rangePerDivision *= Math.pow(10, -orderOfRangeMagnitude);
        rangePerDivision = (float)Math.ceil(rangePerDivision);
        rangePerDivision *= Math.pow(10, orderOfRangeMagnitude);

       // draw horizontal lines
        float startValue = mRangeMin - mRangeMin % rangePerDivision;
        float xStart = 0; //getPaddingLeft();
        float xEnd = getWidth(); // - getPaddingRight();
        for(float value = startValue; value < mRangeMax; value += rangePerDivision) {
            float y = valueToY(value);
            canvas.drawLine(xStart, y, xEnd, y, mGraticulePaint);
        }

        //draw vertical lines
        float yStart = 0; //getPaddingTop();
        float yEnd = getHeight(); // - getPaddingBottom();

        // calculate time per division based on text width
        String xText = "XXeyys";
        Rect xTextBounds = new Rect();
        Rect xTextBgBounds = new Rect();
        mGraticulePaint.getTextBounds(xText, 0, xText.length(), xTextBounds);
        float xKern = 1.0f;
        double timePerDivision = 2* mDomain * (xTextBounds.width() + xKern) / getWidth();
        double orderOfMagnitude = (float)Math.floor(Math.log10(timePerDivision));
        timePerDivision *= Math.pow(10, -orderOfMagnitude);
        timePerDivision = Math.ceil(timePerDivision);
        timePerDivision *= Math.pow(10, orderOfMagnitude);
        long timePerDivisionL = (long)timePerDivision;
        long timeVal = mDomainMin - mDomainMin % timePerDivisionL;

        for(float x = xStart;x <= xEnd; timeVal += timePerDivisionL) {
            x = timeToX(timeVal);
            canvas.drawLine(x, yStart, x, yEnd, mGraticulePaint);
            xText = mTimeFormatter.format((timeVal - mStartTimeStamp) / 1000000000);
            mGraticuleTextBgPaint.getTextBounds(xText, 0, xText.length(), xTextBounds);
            xTextBounds.offset((int) (x - xTextBounds.width() / 2), (int) (yEnd - xTextBounds.height()));
            if(xTextBounds.left > yTextBounds.right) {
                xTextBgBounds.set(xTextBounds);
                xTextBgBounds.inset(-2, -2);
                canvas.drawRect(xTextBgBounds, mGraticuleTextBgPaint);
                canvas.drawText(xText, xTextBounds.left, xTextBounds.bottom, mGraticulePaint);
            }
        }

        if(mRangeFormatter != null) {
            for (float value = startValue; value < mRangeMax; value += rangePerDivision) {
                float y = valueToY(value);
                yText = mRangeFormatter.format(value);
                mGraticulePaint.getTextBounds(yText, 0, yText.length(), yTextBounds);
                yTextBounds.offsetTo(1, (int) (y - yTextBounds.height() / 2f));
                yTextBgBounds.set(yTextBounds);
                yTextBgBounds.inset(-2, -2);
                canvas.drawRect(yTextBgBounds, mGraticuleTextBgPaint);
                canvas.drawText(yText, yTextBounds.left, yTextBounds.bottom, mGraticulePaint);
            }
        }
    }

    protected void drawData(Canvas canvas) {
        if(mVisibleDataSet != null && mVisibleDataSet.size() > 0) {
           Measurement m0 = mVisibleDataSet.get(0);
            float x_ = timeToX(m0.getTimestamp());
            float y_ = valueToY(m0.getValue());
            for (Measurement m : mVisibleDataSet) {
                float x = timeToX(m.getTimestamp());
                if(x - x_ >= 1f) {
                    float y = valueToY(m.getValue());
                    //canvas.drawPoint(x, y, mDataPaint);
                    canvas.drawCircle(x, y, mDataPointSize, mDataPaint);
                    if(mDrawLines) {
                        canvas.drawLine(x_, y_, x, y, mDataPaint);
                    }
                    x_ = x;
                    y_ = y;
                }
            }
        }
    }

    protected void drawXAxisLabel(Canvas canvas) {
        Rect labelBounds = new Rect();
        String label = mTimeFormatter.getUnitSystemName();
        mGraticulePaint.getTextBounds(label, 0, label.length(), labelBounds);
        float labelTop = labelBounds.height() * 2f + getTopPaddingOffset();
        float labelRight = (getWidth() - labelBounds.width())/2;
        labelBounds.inset(-2, -2);
        canvas.drawRect(labelBounds, mGraticuleTextBgPaint);
        canvas.drawText(label, labelRight, labelTop, mGraticulePaint);
    }

    protected void drawYAxisLabel(Canvas canvas) {
        if(mRangeFormatter != null) {
            RectF labelBounds = new RectF();
            String label = mRangeFormatter.getUnitSystemName();
            Matrix m = new Matrix();
            m.setRotate(90f);
            Path labelPath = new Path();
            mGraticulePaint.getTextPath(label, 0, label.length(), 0, 0, labelPath);
            labelPath.transform(m);
            labelPath.computeBounds(labelBounds, false);
            float labelTop = (getHeight() - labelBounds.height()) / 2;
            float labelRight = getWidth() - labelBounds.width() * 1.5f;
            labelBounds.inset(-2, -2);
            labelBounds.offset(labelRight, labelTop);
            m.setTranslate(labelRight, labelTop);
            labelPath.transform(m);
            canvas.drawRect(labelBounds, mGraticuleTextBgPaint);
            canvas.drawPath(labelPath, mGraticulePaint);
        }
    }

    public float valueToY(float v) {
        int paddingTop = getPaddingTop();
        //int paddingBottom = getPaddingBottom();
        int contentHeight = getHeight(); // - paddingTop - paddingBottom;

        float y = contentHeight * ((mRange - (v - mRangeMin)) / mRange); // + paddingTop;
        return y;
    }

    public float timeToX(long timeVal) {
        //int paddingRight = getPaddingRight();
        float contentWidth = (float)getWidth(); // - paddingRight - getPaddingLeft();
        float x = (contentWidth * (float)(timeVal - mDomainMin)) / (float)mDomain; //+paddingRight;
        return x;
    }

    public long xToTime(float x) {
        float contentWidth = (float)getWidth(); // - paddingRight - getPaddingLeft();
        long timeVal = (long)(x * (float)mDomain / contentWidth) + mDomainMin; //+paddingRight;
        return timeVal;
    }

    void adjustDomain(long change) {
        synchronized (mData) {
            //calculate domain
            mDataDomainMin = 0;
            mDataDomainMax = 0;
            if (mData.size() > 0) {
                mDataDomainMin = mData.get(0).getTimestamp();
                mDataDomainMax = mData.get(mData.size() - 1).getTimestamp();
            }

            mDomainMin = mDomainMin + change;
            if(mDomainMin > mDataDomainMax) {
                mDomainMin = mDataDomainMax - mDomain;
            }
            if (mDomainMin < mDataDomainMin) {
                mDomainMin = mDataDomainMin;
            }

            mDomainMax = mDomainMin + mDomain;
        }
    }

    void adjustRange(float change) {
        //calculate range
        float dataRangeMin = Float.MAX_VALUE;
        float dataRangeMax = Float.MIN_VALUE;
        synchronized (mData) {
            for (Measurement m : mData) {
                float displayValue = convertRangeValueToDisplayUnits(m.getValue());
                if (mDomainMin <= m.getTimestamp() && m.getTimestamp() <= mDomainMax) {
                    dataRangeMin = Math.min(mRangeMin, displayValue);
                    dataRangeMax = Math.max(mRangeMax, displayValue);
                }
            }
        }
        float dataRange = dataRangeMax - dataRangeMin;

        float testRangeMin = mRangeMin + change;
        float testRangeMax = mRangeMax + change;
        if(testRangeMax > dataRangeMax) {
            testRangeMax = Math.min(dataRangeMax + dataRange / 2, testRangeMax);
            testRangeMin = testRangeMax - dataRange;
        }
        else if(testRangeMin < dataRangeMin) {
            testRangeMin = Math.max(dataRangeMin - dataRange / 2, testRangeMin);
            testRangeMax = testRangeMin + dataRange;
        }

        mRangeMin = testRangeMin;
        mRangeMax = testRangeMax;
    }

    //finds a data subset suitable for rendering and gets some details about the data
    void determineVisibleSet() {
        //final int paddingLeft = getPaddingLeft();
        //final int paddingRight = getPaddingRight();
        final int contentWidth = getWidth();// - paddingLeft - paddingRight;

        //calculate range, domain and select visible data set

        mVisibleDataSet = new LinkedList<Measurement>();
        synchronized (mData) {
            //calculate domain
            mDataDomainMin = 0;
            mDataDomainMax = 0;
            if(mData.size() > 0) {
                if(mStartTimeStamp == 0)
                    mStartTimeStamp = mData.get(0).getTimestamp();

                //range unit setup
                mRangeSystemUnit = mData.get(0).getUnit();
                ISystemFactory systemFactory = SystemFactoryBroker.getSystemFactory();
                mRangeConverter = systemFactory.getUnitConverterFactory().createUnitConverter(mRangeSystemUnit);
                DisplayUnitManager displayUnitManager = systemFactory.getDisplayUnitManager();
                mRangeDisplayUnit = displayUnitManager.getDisplayUnit(mRangeSystemUnit);
                if(mRangeFormatter == null) {
                    mRangeFormatter = displayUnitManager.getUnitFormatter(mRangeSystemUnit);
                }

                mDataDomainMin = mData.get(0).getTimestamp();
                mDataDomainMax = mData.get(mData.size()-1).getTimestamp();
            }

            if(mAutoScroll) {
                mDomainMin = mDataDomainMax - mDomain;
                if(mDomainMin < mDataDomainMin) {
                    mDomainMin = mDataDomainMin;
                    mDomainMax = mDomainMin + mDomain;
                }
                mDomain = contentWidth * mTimePerPixel;
            }
            else if(0 == mDomainMin && mData.size() > 0) {
                mDomainMin = mData.get(0).getTimestamp();
                mDomain = contentWidth * mTimePerPixel;
            }
            mDomainMax = mDomainMin + mDomain;

            //select data withing visible domain
            for (Measurement m : mData) {
                if (mDomainMin <= m.getTimestamp() && m.getTimestamp() <= mDomainMax) {
                    mVisibleDataSet.add(convertMeasurement(m));
                }
            }

            //calculate range if no range or auto-scrolling
            if (mRange == 0 || mAutoScroll) {
                mRangeMin = Float.POSITIVE_INFINITY;
                mRangeMax = Float.NEGATIVE_INFINITY;
                for (Measurement m : mVisibleDataSet) {
                    mRangeMin = Math.min(mRangeMin, m.getValue());
                    mRangeMax = Math.max(mRangeMax, m.getValue());
                }
            }
        }

        mRange = mRangeMax - mRangeMin;

        //adjust range if there is no variation, this allows us to draw data
        if (mRange <= Float.MIN_NORMAL) {
            mRange = 1.f;
            mRangeMin -= mRange / 2.0f;
            mRangeMax = mRangeMin + mRange;
        }
    }

    public List<Measurement> getMeasurementsOverDomain(long domainStart, long domainEnd) {
        List<Measurement> dataSet = new LinkedList<>();
        synchronized (mData) {
            //select data withing visible domain
            for (Measurement m : mData) {
                if (domainStart > m.getTimestamp())
                    continue;
                else if (domainEnd > m.getTimestamp())
                    dataSet.add(convertMeasurement(m));
                else
                    break;
            }
        }

        return dataSet;
    }

    public float convertRangeValueToDisplayUnits(float systemValue) {
        return mRangeConverter.convert(mRangeSystemUnit, systemValue, mRangeDisplayUnit);
    }

    public Measurement convertMeasurement(Measurement systemMeasurement) {
        float value = convertRangeValueToDisplayUnits(systemMeasurement.getValue());
        return new Measurement(value, systemMeasurement.getTimestamp(), mRangeDisplayUnit);
    }

    public void startRegressionTool() {
        mTool = new DataPlotRegressionTool();
        mTool.init(this);
    }

    public void endTool() {
        mTool = null;
    }

    public void endAutoScroll() {
        mAutoScroll = false;
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
