package com.example.drock.n_corder;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by drock on 11/29/2015.
 */
public class SensorStreamBroker {
    private static SensorStreamBroker mInstance = new SensorStreamBroker();

    public static SensorStreamBroker getInstance() {
        return mInstance;
    }

    Map<String, IMeasurementSource> mStreams = new TreeMap<String, IMeasurementSource>();

    public void RegisterStream(String moniker, IMeasurementSource source) {
        mStreams.put(moniker, source);
    }

    public void UnregisterStream(IMeasurementSource source) {
        for(Map.Entry<String, IMeasurementSource> entry : mStreams.entrySet()) {
            if(entry.getValue() == source)
                mStreams.remove(entry.getKey());
        }
    }

    public void AttachToStream(IMeasurementSink sink, String sourceName) {
        if(mStreams.containsKey(sourceName))
            mStreams.get(sourceName).attach(sink);
    }

    public void DetachFromStream(IMeasurementSink sink, String sourceName) {
        if(mStreams.containsKey(sourceName))
            mStreams.get(sourceName).detach(sink);
    }
}
