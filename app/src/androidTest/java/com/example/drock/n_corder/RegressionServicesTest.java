package com.example.drock.n_corder;

import android.test.ApplicationTestCase;

import com.example.drock.n_corder.units.TimeUnits;
import com.example.drock.n_corder.units.Units;

import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.closeTo;
import Jama.Matrix;

public class RegressionServicesTest extends TestCase {
    //TODO create tests that include additive and multiplicative noise
    RegressionServices mRegressor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mRegressor = new RegressionServices();
    }

    @Override
    protected void tearDown() throws Exception {
        mRegressor = null;
        super.tearDown();

    }

    public void testLinearRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = 2f, b = 3f;
        for(int x = 0; x < 10; x++) {
            float y = a+ b*(float)x;
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        Matrix results = mRegressor.regressLinear(data);
        assertThat(results.get(0, 0), is(closeTo(a, 0.001)));
        assertThat(results.get(1, 0), is(closeTo(b, b / 100.)));
    }

    public void testQuadraticRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = 2f, b = -3f, c = 5f;
        for(int x = 0; x < 10; x++) {
            float y = a + b*(float)x+c*x*x;
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        Matrix results = mRegressor.regressQuadratic(data);
        assertThat(results.get(0, 0), is(closeTo(a, 0.001)));
        assertThat(results.get(1, 0), is(closeTo(b, 0.001)));
        assertThat(results.get(2, 0), is(closeTo(c, 0.001)));
    }

    public void testCubicRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = 2f, b = -3f, c = 5f, d=7f;
        for(int x = 0; x < 10; x++) {
            float y = a + b*(float)x+c*x*x+d*x*x*x;
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        Matrix results = mRegressor.regressCubic(data);
        assertThat(results.get(0, 0), is(closeTo(a, 0.001)));
        assertThat(results.get(1, 0), is(closeTo(b, 0.001)));
        assertThat(results.get(2, 0), is(closeTo(c, 0.001)));
        assertThat(results.get(3, 0), is(closeTo(d, 0.001)));
    }

    public void testQuarticRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = 2f, b = -3f, c = 5f, d=7f, e=11f;
        for(int x = 0; x < 10; x++) {
            float y = a + b*(float)x+c*x*x+d*x*x*x+e*x*x*x*x;
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        Matrix results = mRegressor.regressQuartic(data);
        assertThat(results.get(0, 0), is(closeTo(a, 0.001)));
        assertThat(results.get(1, 0), is(closeTo(b, 0.001)));
        assertThat(results.get(2, 0), is(closeTo(c, 0.001)));
        assertThat(results.get(3, 0), is(closeTo(d, 0.001)));
        assertThat(results.get(4, 0), is(closeTo(e, 0.001)));
    }

    public void testLogarithmicRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = 1f, b = 3f;
        for(int x = 0; x < 100; x++) {
            float y = a + b*(float)Math.log(x);
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        double[] results = mRegressor.regressLogarithmic(data);
        assertThat(results[0], is(closeTo(a, 0.001)));
        assertThat(results[1], is(closeTo(b, 0.001)));
        assertThat(results[2], is(closeTo(0.9, 0.1)));
    }

    public void testEExponentialRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = 1f, b = 1f/13f;
        for(int x = 0; x < 100; x++) {
            float y = a *(float)Math.exp(b*x);
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        double[] results = mRegressor.regressEExponential(data);
        assertThat(results[0], is(closeTo(a, 0.001)));
        assertThat(results[1], is(closeTo(b, 1e-6)));
        assertThat(results[2], is(closeTo(0.9, 0.1)));
    }

    public void testABExponentialRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = -13f, b = 1.03125f;
        double x;
        for(x = 0; x < 50; x += 0.5) {
            float y = a*(float)Math.pow(b, x);
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        double[] results = mRegressor.regressABExponential(data);
        assertThat(results[0], is(closeTo(a, 0.13)));
        assertThat(results[1], is(closeTo(b, 0.001)));
        assertThat(results[2], is(closeTo(1, 0.1)));
    }

    public void testPowerRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = -13f, b = 3.0f;
        double x;
        for(x = 0; x < 100; x += 1) {
            float y = a*(float)Math.pow(x, b);
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        double[] results = mRegressor.regressPower(data);
        assertThat(results[1], is(closeTo(b, 0.001)));
        assertThat(results[0], is(closeTo(a, -a/60.)));
        assertThat(results[2], is(closeTo(0.9, 0.1)));
    }

    public void testInverseRegression() {
        List<Measurement> data = new LinkedList<>();
        float a = 23f, b = 17.0f;
        double x;
        for(x = 0.5; x < 100; x += 1) {
            float y = (float)(a + b / x);
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        Matrix results = mRegressor.regressInverse(data);
        assertThat(results.get(0, 0), is(closeTo(a, a/50.)));
        assertThat(results.get(1, 0), is(closeTo(b, b/100.)));
    }

    public void testEExponentialSinRegression() {
        double bias = 2;
        double amplitude = 5;
        double decay = -0.1;
        double frequency = 3*Math.PI;
        double phaseShift = Math.PI/7;
        List<Measurement> data = new LinkedList<>();
        double x;
        long t_ = -1;
        for(x = 1; x < 100; x += 0.01) {
            float y = (float)(bias + amplitude * Math.exp(decay * x) * Math.sin(frequency * x + phaseShift));
            long t = (long)(x * TimeUnits.NSEC_PER_SEC);
            assertTrue(t != t_);
            t_ = t;
            Measurement m = new Measurement(y, t, Units.UNKNOWN);
            data.add(m);
        }
        double[] results = mRegressor.regressEExponentialSin(data);
        assertThat(results[0], is(closeTo(bias, bias/100.)));
        assertThat(results[1], is(closeTo(amplitude, amplitude/100.)));
        assertThat(results[2], is(closeTo(decay, Math.abs(decay)/100.)));
        assertThat(results[3], is(closeTo(frequency, frequency/100.)));
        assertThat(results[4], is(closeTo(phaseShift, phaseShift/100.)));
    }
}
