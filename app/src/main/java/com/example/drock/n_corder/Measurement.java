package com.example.drock.n_corder;

/**
 * Created by drock on 11/27/2015.
 */
public class Measurement {
    protected float value;

    public Measurement(float v) {
        this.value = v;
    }
    public void setValue(float v) {
        this.value = v;
    }
    public float getGalue() {
        return this.value;
    }
}
