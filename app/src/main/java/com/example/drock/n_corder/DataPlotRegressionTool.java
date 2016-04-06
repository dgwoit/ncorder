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

        StringBuilder result = new StringBuilder();
        if(data.size() > 0) {
            Matrix x = null;
            x = regressLinear(data);
            result.append(String.format("y = %f*x + %f", x.get(0, 0), x.get(1, 0)));
            result.append("\n");
            x = regressQuadratic(data);
            result.append(String.format("y = %f*x^2 + %f*x + %f",
                    x.get(2, 0), x.get(1, 0), x.get(0, 0)));
            result.append("\n");
            x = regressCubic(data);
            result.append(String.format("y = %f*x^3 + %f*x^2 + %f*x + %f",
                    x.get(3, 0), x.get(2, 0), x.get(1, 0), x.get(0, 0)));
            result.append("\n");
            x = regressQuartic(data);
            result.append(String.format("y = %f*x^4 + %f*x^3 + %f*x^2 + %f*x + %f",
                    x.get(4, 0), x.get(3, 0), x.get(2, 0), x.get(1, 0), x.get(0, 0)));
            result.append("\n");
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

    Matrix regressLinear(List<Measurement> data) {
        Matrix x = null;
        if(data.size() > 0) {
            long timeBase = data.get(0).getTimestamp();
            double[][] aVals = new double[data.size()][2];
            double[][] bVals = new double[data.size()][1];
            for (int i = 0; i < data.size(); i++) {
                aVals[i][1] = (data.get(i).getTimestamp() - timeBase) / 1e9;
                aVals[i][0] = 1.;
                bVals[i][0] = data.get(i).getValue();
            }

            Matrix A = new Matrix(aVals);
            Matrix b = new Matrix(bVals);
            x = A.solve(b);
        }
        return x;
    }


    Matrix regressQuadratic(List<Measurement> data) {
        Matrix x = null;
        if(data.size() > 0) {
            long timeBase = data.get(0).getTimestamp();
            double[][] aVals = new double[data.size()][3];
            double[][] bVals = new double[data.size()][1];
            for (int i = 0; i < data.size(); i++) {
                double t = (data.get(i).getTimestamp() - timeBase) / 1e9;
                aVals[i][2] = t*t;
                aVals[i][1] = t;
                aVals[i][0] = 1.;
                bVals[i][0] = data.get(i).getValue();
            }

            Matrix A = new Matrix(aVals);
            Matrix b = new Matrix(bVals);
            x = A.solve(b);
        }
        return x;
    }

    Matrix regressCubic(List<Measurement> data) {
        Matrix x = null;
        if(data.size() > 0) {
            long timeBase = data.get(0).getTimestamp();
            double[][] aVals = new double[data.size()][4];
            double[][] bVals = new double[data.size()][1];
            for (int i = 0; i < data.size(); i++) {
                double t = (data.get(i).getTimestamp() - timeBase) / 1e9;
                aVals[i][3] = t*t*t;
                aVals[i][2] = t*t;
                aVals[i][1] = t;
                aVals[i][0] = 1.;
                bVals[i][0] = data.get(i).getValue();
            }

            Matrix A = new Matrix(aVals);
            Matrix b = new Matrix(bVals);
            x = A.solve(b);
        }
        return x;
    }

    Matrix regressQuartic(List<Measurement> data) {
        Matrix x = null;
        if(data.size() > 0) {
            long timeBase = data.get(0).getTimestamp();
            double[][] aVals = new double[data.size()][5];
            double[][] bVals = new double[data.size()][1];
            for (int i = 0; i < data.size(); i++) {
                double t = (data.get(i).getTimestamp() - timeBase) / 1e9;
                aVals[i][4] = t*t*t*t;
                aVals[i][3] = t*t*t;
                aVals[i][2] = t*t;
                aVals[i][1] = t;
                aVals[i][0] = 1.;
                bVals[i][0] = data.get(i).getValue();
            }

            Matrix A = new Matrix(aVals);
            Matrix b = new Matrix(bVals);
            x = A.solve(b);
        }
        return x;
    }
}
