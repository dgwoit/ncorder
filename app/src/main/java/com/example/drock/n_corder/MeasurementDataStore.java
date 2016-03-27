package com.example.drock.n_corder;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
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
    List<Measurement> mData = new ArrayList<>();
    IMeasurementSink mSink;


    public static MeasurementDataStore getInstance() {
        return mInstance;
    }

    private MeasurementDataStore(String streamName) {
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
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
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

            Object[] data;
            synchronized (mData) {
                data = getData().toArray();
            }
            if(data.length > 0) {
                long baseTime = ((Measurement)data[0]).getTimestamp();
                for (Object o : data) {
                    ///hard-code measurement serializer for now
                    Measurement m = (Measurement)o;
                    double time = (m.getTimestamp() - baseTime) / 1000000000.;
                    line = String.format("%s,%f\n", time, m.getValue());
                    osw.write(line);
                }
            }
            osw.flush();
            osw.close();

            //notify user data was successfully saved

            CharSequence text = String.format("Saved to %s", file.getPath());


            AlertDialog.Builder builder = new AlertDialog.Builder(context, android.support.v7.appcompat.R.style.Theme_AppCompat_Dialog_Alert);
            builder.setTitle(R.string.dialog_saved_data)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public List<Measurement> getData() { return mData; }
}
