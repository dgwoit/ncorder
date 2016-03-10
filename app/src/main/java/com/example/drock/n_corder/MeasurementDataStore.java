package com.example.drock.n_corder;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MeasurementDataStore extends Observable {
    static MeasurementDataStore mInstance;
    String mStreamName;
    List<Measurement> mData;
    IMeasurementSink mSink;


    public static MeasurementDataStore getInstance() {
        return mInstance;
    }

    private MeasurementDataStore(String streamName) {
        mData = new ArrayList<>();
        mStreamName = streamName;
        mSink = new IMeasurementSink() {
            @Override
            public boolean update(Measurement m) {
                synchronized(mData) {
                    mData.add(m);
                    setChanged();
                    notifyObservers(mData);
                }

                return true;
            }
        };
        SensorStreamBroker.getInstance().AttachToStream(mSink, streamName);
    }

    public void finalize() {
        SensorStreamBroker.getInstance().DetachFromStream(mSink, mStreamName);
    }

    public static void recordStream(String streamName) {
        mInstance = new MeasurementDataStore(streamName);
    }

    public void save(Context context) {
        try {
            String TestString="";

            String filename = "recording.csv";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            String line = "time,value\n";
            osw.write(line);
            List<Measurement> data = getData();
            if(data.size() > 0) {
                long baseTime = data.get(0).getTimestamp();
                for (Measurement m : data) {
                    ///hard-code measurement serializer for now
                    double time = (m.getTimestamp() - baseTime) / 1000000000.;
                    line = String.format("%s,%f\n", time, m.getValue());
                    osw.write(line);
                }
            }
            osw.flush();
            osw.close();

            //notify user data was successfully saved

            CharSequence text = String.format("Saved %s", file.getPath());
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public List<Measurement> getData() { return mData; }
}
