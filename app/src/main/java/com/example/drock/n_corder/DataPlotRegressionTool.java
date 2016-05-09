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

    public void onDraw(Canvas canvas, int layer) {
        if(mBounds != null) {
            if(layer == 1)
                canvas.drawRect(mBounds, mSelectionPaint);
        } else { //draw instructions
            if(layer == 2) {
                String instructionText = "SELECT DATA TO ANALYZE";
                float textWidth = mSelectionPaint.measureText(instructionText);
                canvas.drawText("SELECT DATA TO ANALYZE", (canvas.getWidth() - textWidth) / 2, canvas.getHeight() / 2, mSelectionPaint);
            }
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

        StringBuilder result = new StringBuilder();
        if(data.size() > 0) {
            RegressionServices regressor = new RegressionServices();

            Matrix x = null;
            x = regressor.regressLinear(data);
            result.append(String.format("y = %f*x + %f", x.get(1, 0), x.get(0, 0)));
            result.append("\n");
            x = regressor.regressQuadratic(data);
            result.append(String.format("y = %f*x^2 + %f*x + %f",
                    x.get(2, 0), x.get(1, 0), x.get(0, 0)));
            result.append("\n");
            x = regressor.regressCubic(data);
            result.append(String.format("y = %f*x^3 + %f*x^2 + %f*x + %f",
                    x.get(3, 0), x.get(2, 0), x.get(1, 0), x.get(0, 0)));
            result.append("\n");
            x = regressor.regressQuartic(data);
            result.append(String.format("y = %f*x^4 + %f*x^3 + %f*x^2 + %f*x + %f",
                    x.get(4, 0), x.get(3, 0), x.get(2, 0), x.get(1, 0), x.get(0, 0)));
            result.append("\n");
            double[] results = regressor.regressLogarithmic(data);
            result.append(String.format("y = %f + %f * ln(x), correlation=%f", results[0], results[1], results[2]));
            result.append("\n");
            results = regressor.regressEExponential(data);
            result.append(String.format("y = %f * e ^ (%f * x), correlation=%f", results[0], results[1], results[2]));
            result.append("\n");
            results = regressor.regressABExponential(data);
            result.append(String.format("y = %f * (%f ^ x), correlation=%f", results[0], results[1], results[2]));
            result.append("\n");
            results = regressor.regressPower(data);
            result.append(String.format("y = %f * (x ^ %f), correlation=%f", results[0], results[1], results[2]));
            result.append("\n");
            results = regressor.regressPower(data);
            result.append(String.format("y = %f + %f/x, correlation=%f", results[0], results[1], results[2]));
            result.append("\n");
            //not ready for use
            //results = regressor.regressEExponentialSin(data);
            //result.append(String.format("y = %f * e^(%f*x))*sin(%f*x+%f)", results[0], results[1], results[2], results[3]));

        } else {
            result.append("No data selected for analysis");
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext(), android.support.v7.appcompat.R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle("Regression Results")
                .setMessage(result)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
    }




}
