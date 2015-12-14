package com.example.drock.n_corder;

import java.util.ArrayList;
import java.util.List;

interface IMeasurementSource {
    public void attach(IMeasurementSink sink);
    public void detach(IMeasurementSink sink);
    public void update(Measurement m);
}

public class MeasurementSource implements IMeasurementSource {
    private List<IMeasurementSink> sinks = new ArrayList<IMeasurementSink>();

    public void attach(IMeasurementSink subscriber) {
        this.sinks.add(subscriber);
    }

    public void detach(IMeasurementSink subscriber) {
        this.sinks.remove(subscriber);
    }

    public void update(Measurement m) {
        for(IMeasurementSink s : this.sinks) {
            if(!s.update(m))
                this.sinks.remove(s);
        }
    }
}