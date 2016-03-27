package com.example.drock.n_corder;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import Jama.*;

import java.util.List;

public class DataPlotRegressionTool {
    DataPlotView mView;
    Paint mSelectionPaint;
    RectF mBounds;
    float mStartX = Float.NaN;

    public void init(DataPlotView view) {
        mView = view;
        mView.endAutoScroll();

        // create drawing resources
        mSelectionPaint = new Paint();
        mSelectionPaint.setColor(Color.rgb(0x30, 0x30, 0x90));
    }

    public void onDraw(Canvas canvas) {
        if(mBounds != null) {
            canvas.drawRect(mBounds, mSelectionPaint);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mBounds = new RectF(event.getX(), mView.getTop(), event.getX(), mView.getBottom());
            mStartX = event.getX();
        } else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
            mBounds.left = Math.min(mBounds.left, event.getX());
            mBounds.right = Math.max(mBounds.right, event.getX());
            regressOverXRange(mBounds.left, mBounds.right);
            mView.endTool();
        } else if(event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            mBounds.left = Math.min(mStartX, event.getX());
            mBounds.right = Math.max(mStartX, event.getX());
            mView.postInvalidate();
        }

        return true;
    }

    public void regressOverXRange(float xStart, float xEnd) {
        long domainStart = mView.xToTime(mBounds.left);
        long domainEnd = mView.xToTime(xEnd);
        regressOverDomain(domainStart, domainEnd);
    }

    public void regressOverDomain(long domainStart, long domainEnd) {
        List<Measurement> data = mView.getMeasurementsOverDomain(domainStart, domainEnd);

        String result = "No data selected for analysis";
        if(data.size() > 0) {
            long timeBase = data.get(0).getTimestamp();
            double[][] aVals = new double[data.size()][2];
            double[][] bVals = new double[data.size()][1];
            for(int i = 0; i < data.size(); i++) {
                aVals[i][0] = (data.get(i).getTimestamp() - timeBase) / 1e9;
                aVals[i][1] = 1.;
                bVals[i][0] = data.get(i).getValue();
            }

            Matrix A = new Matrix(aVals);
            Matrix b = new Matrix(bVals);
            Matrix x = A.solve(b);

            result = String.format("y = m*x + b: m = %f, b = %f", x.get(0, 0), x.get(1, 0));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext(), android.support.v7.appcompat.R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Linear Regression Results")
                .setMessage(result)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
    }
}
