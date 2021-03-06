package com.example.drock.n_corder;


import android.util.Log;

import java.util.Iterator;
import java.util.List;

import Jama.Matrix;
import ca.uol.aig.fftpack.RealDoubleFFT;

public class RegressionServices {
    public Matrix regressLinear(List<Measurement> data) {
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

    //for y = a + b*ln(x)
    // r is correlation:
    public double[] regressLogarithmic(List<Measurement> data) {
        double lnXSum = 0;
        double ySum = 0;
        double lnXYSum = 0;
        double sumLnXSquared = 0;
        double sumYSquared = 0;
        double n = data.size();
        for(Measurement m:data) {
            double x = m.getTimestamp() / 1e9;
            if(0 == x) {
                n--;
                continue;
            }
            double lnX = Math.log(x); //convert to seconds
            lnXSum += lnX;
            sumLnXSquared += lnX * lnX;
            double y = m.getValue();
            ySum += y;
            sumYSquared += y*y;
            lnXYSum += lnX * y;
        }

        double lnXMean = lnXSum / n;
        double yMean = ySum / n;
        double lnXYMean = lnXYSum / n;
        double sXY = lnXYMean - lnXMean*yMean;
        double lnXSquareMean = sumLnXSquared / n;
        double ySquareMean = sumYSquared / n;
        double sXX = lnXSquareMean - lnXMean * lnXMean;
        double sYY = ySquareMean - yMean * yMean;
        double b = sXY / sXX;
        double a = yMean - b * lnXMean;
        double r = sXY / (Math.sqrt(sXX*sYY));
        double[] results = new double[3];
        results[0] = a;
        results[1] = b;
        results[2] = r;
        return results;
    }

   //for y = a*e^(b*x)
    // r is correlation:
    double[] regressEExponential(List<Measurement> data) {
        double sumX = 0;
        double sumXSquared = 0;
        double sumLnY = 0;
        double sumLnYSquared = 0;
        double sumXLnY = 0;
        double n = data.size();
        for(Measurement m:data) {
            double x = m.getTimestamp() / 1e9; //convert to seconds
            double y = m.getValue();
            if(y == Double.NEGATIVE_INFINITY) {
                n--;
                continue;
            }
            double lnY = Math.log(y);
            sumX += x;
            sumXSquared += x * x;
            sumLnY += lnY;
            sumLnYSquared += lnY * lnY;
            sumXLnY += x * lnY;
        }

        double meanX = sumX / n;
        double meanLnY = sumLnY / n;
        double meanSquareLnY = sumLnYSquared  / n;
        double meanSquareXLnY = sumXLnY / n;
        double sXX = sumXSquared / n - meanX * meanX;
        double sYY = meanSquareLnY - meanLnY * meanLnY;
        double sXY = meanSquareXLnY - meanX * meanLnY;
        double b = sXY / sXX;
        double a = Math.exp(meanLnY - b * meanX);
        double r = sXY / (Math.sqrt(sXX*sYY));
        double[] results = new double[3];
        results[0] = a;
        results[1] = b;
        results[2] = r;
        return results;
    }

    double[] regressEExponential(double[] xvals, double[] yvals) {
        double sumX = 0;
        double sumXSquared = 0;
        double sumLnY = 0;
        double sumLnYSquared = 0;
        double sumXLnY = 0;
        for(int i = 0; i < xvals.length; i++) {
            double x = xvals[i]; //convert to seconds
            double y = yvals[i];
            double lnY = Math.log(y);
            sumX += x;
            sumXSquared += x * x;
            sumLnY += lnY;
            sumLnYSquared += lnY * lnY;
            sumXLnY += x * lnY;
        }
        double n = xvals.length;
        double meanX = sumX / n;
        double meanLnY = sumLnY / n;
        double meanSquareLnY = sumLnYSquared  / n;
        double meanSquareXLnY = sumXLnY / n;
        double sXX = sumXSquared / n - meanX * meanX;
        double sYY = meanSquareLnY - meanLnY * meanLnY;
        double sXY = meanSquareXLnY - meanX * meanLnY;
        double b = sXY / sXX;
        double a = Math.exp(meanLnY - b * meanX);
        double r = sXY / (Math.sqrt(sXX*sYY));
        double[] results = new double[3];
        results[0] = a;
        results[1] = b;
        results[2] = r;
        return results;
    }

    //for y = a(b^x)
    // r is correlation:
    double[] regressABExponential(List<Measurement> data) {
        double sumX = 0;
        double sumXSquared = 0;
        double sumLnY = 0;
        double sumLnYSquared = 0;
        double sumXLnY = 0;
        double sign = 0;
        double n = data.size();
        for(Measurement m:data) {
            double x = m.getTimestamp() / 1e9; //convert to seconds
            double y = m.getValue();
            if(y == 0) {
                n--;
                continue;
            }
            if(sign == 0) {
                sign = Math.signum(y);
            }
            double lnY = Math.log(sign*y);
            sumX += x;
            sumXSquared += x * x;
            sumLnY += lnY;
            sumLnYSquared += lnY * lnY;
            sumXLnY += x * lnY;
        }
        double meanX = sumX / n;
        double meanLnY = sumLnY / n;
        double meanSquareX = sumXSquared / n;
        double meanSquareLnY = sumLnYSquared  / n;
        double meanSquareXLnY = sumXLnY / n;
        double sXX = meanSquareX - meanX * meanX;
        double sYY = meanSquareLnY - meanLnY * meanLnY;
        double sXY = meanSquareXLnY - meanX * meanLnY;
        double b = Math.exp(sXY / sXX);
        double a = sign * Math.exp(meanLnY - Math.log(b) * meanX);
        double r = sXY / (Math.sqrt(sXX*sYY));
        double[] results = new double[3];
        results[0] = a;
        results[1] = b;
        results[2] = r;
        return results;
    }

    //for y = a(x^b)
    // r is correlation:
    double[] regressPower(List<Measurement> data) {
        double sumLnX = 0;
        double sumLnXSquared = 0;
        double sumLnY = 0;
        double sumLnYSquared = 0;
        double sumLnXLnY = 0;
        double sign = 0;
        double n = data.size();
        for(Measurement m:data) {
            double x = m.getTimestamp() / 1e9; //convert to seconds
            double y = m.getValue();
            if(y == 0 || x == 0) {
                n--;
                continue;
            }
            if(sign == 0) {
                sign = Math.signum(y);
            }

            double lnY = Math.log(sign*y);
            double lnX = Math.log(x);
            assert(lnY != Double.NaN);
            assert(lnX != Double.NaN);
            sumLnX += lnX;
            sumLnXSquared += lnX * lnX;
            sumLnY += lnY;
            sumLnYSquared += lnY * lnY;
            sumLnXLnY += lnX * lnY;
        }
        double meanLnX = sumLnX / n;
        double meanLnY = sumLnY / n;
        double meanSquareLnX = sumLnXSquared / n;
        double meanSquareLnY = sumLnYSquared / n;
        double meanLnXLnY = sumLnXLnY / n;
        double sXX = meanSquareLnX - meanLnX * meanLnX;
        double sYY = meanSquareLnY - meanLnY * meanLnY;
        double sXY = meanLnXLnY - meanLnX * meanLnY;
        double b = sXY / sXX;
        assert(b != Double.NaN);
        double a = sign*Math.exp(meanLnY - b * meanLnX);
        assert(a != Double.NaN);
        double r = sXY / (Math.sqrt(sXX*sYY));
        double[] results = new double[3];
        results[0] = a;
        results[1] = b;
        results[2] = r;
        return results;
    }

    //for y = a+b/x)
    // r is correlation:
    Matrix regressInverse(List<Measurement> data) {
        Matrix x = null;
        int usableEntryCount = data.size();
        for(Measurement m: data) {
            if(m.getTimestamp() == 0)
                usableEntryCount--;
        }
        if(data.size() > 0) {
            //long timeBase = data.get(0).getTimestamp();
            double[][] aVals = new double[usableEntryCount][2];
            double[][] bVals = new double[usableEntryCount][1];

            for (int i = 0; i < data.size(); i++) {
                double t = (data.get(i).getTimestamp()) / 1e9;
                if(t == 0)
                    continue;

                aVals[i][1] = 1./t;
                aVals[i][0] = 1.;
                bVals[i][0] = data.get(i).getValue();
            }

            Matrix A = new Matrix(aVals);
            Matrix b = new Matrix(bVals);
            x = A.solve(b);
        }

        return x;
    }

    // For regressing damped sine waves
    // y = a (e^(b x)) sin(c x + d)
    public double[] regressEExponentialSin(List<Measurement> data) {
        double sumY = 0;
        for(Measurement m:data) {
            double y = m.getValue();
            sumY += y;
        }
        int n = data.size();
        double meanX = sumY / n;

        //count the zero crossings to get an idea of the frequency
        int zeroCrossings = 0;
        double testSign = Math.signum(data.get(0).getValue()-meanX);
        for(Measurement m: data) {
            double sign = Math.signum(m.getValue()-meanX);
            if(sign != testSign) {
                zeroCrossings++;
                testSign = sign;
            }
        }
        double timeBase = data.get(0).getTimestamp();
        double duration = (data.get(n-1).getTimestamp() - timeBase) * 1e-9;
        double frequency = (duration / zeroCrossings) / 2;
        double phaseShift = 0; //need to calculate phase shift

        /*int transformBlockSize = 256;
        RealDoubleFFT fft = new RealDoubleFFT(transformBlockSize);

        //process a block
        double[] block = new double[transformBlockSize];
        int i = 0, j = 0;
        for(i=0; j < transformBlockSize && i < n; i++, j++) {
            block[j] = data.get(i).getValue();
        }
        fft.ft(block);

        //find the highest magnitude in the block
        double magnitude = 0;
        double frequency = 0;
        double timeBase = data.get(0).getTimestamp();
        double duration = (data.get(i-1).getTimestamp() - timeBase) * 1e-9;
        double sumPhase = 0;
        for(j = 0; j < transformBlockSize; ) {
            double real = block[j++];
            double imag = block[j++];
            sumPhase += Math.atan2(imag, real);
            double testMagnitude = real*real + imag * imag;
            if(testMagnitude > magnitude) {
                magnitude = testMagnitude;
                frequency = ((double)(j/2) * (double)transformBlockSize / (double)duration) / 2.;
            }
        }
        double c = frequency;
        double d = sumPhase / transformBlockSize;*/

        //transform from decaying sinusoidal wave to a series of intensities
        double[] intensities = new double[data.size()];
        double[] times = new double[data.size()];
        int i = 0;
        for(Measurement m:data) {
            double t = (m.getTimestamp() - timeBase) * 1e-9;
            double phase = 2 * Math.PI * frequency * t + phaseShift;
            double y = m.getValue();
            double real = Math.cos(phase) * y;
            double imag = Math.sin(phase) * y;
            double intensity = Math.sqrt(real*real + imag*imag);
            times[i] = t;
            intensities[i++] = intensity;
        }

        double r = 0;
        double[] envelopeParams = regressEExponential(times, intensities);
        double[] results = new double[6];
        results[0] = meanX;
        results[2] = envelopeParams[0]; //amplitude
        results[2] = envelopeParams[1]; //decay
        results[3] = frequency;
        results[4] = phaseShift;
        return results;
    }

    // For regressing damped sine waves
    // y = a (e^(b x)) sin(c x + d)
    public double[] regressEExponentialSin_2(List<Measurement> data) {
        double sumY = 0;
        for(Measurement m:data) {
            double y = m.getValue();
            sumY += y;
        }
        double n = data.size();
        double meanX = sumY / n;

        //find peaks
        //calculate period of peaks
        //calculate phase
        //calculate exponential decay from normalized, unbiased peak data

        double[] results = new double[4];
        results[0] = meanX;
        results[1] = 0;
        results[2] = 0;
        results[3] = 0;
        return results;
    }
}
