package com.example.drock.n_corder;

public interface IMeasurementSource {
    public void attach(IMeasurementSink sink);
    public void detach(IMeasurementSink sink);
    public void update(Measurement m);
}
