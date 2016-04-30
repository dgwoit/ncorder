package com.example.drock.n_corder;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.drock.n_corder.fileselector.FileOperation;
import com.example.drock.n_corder.fileselector.FileSelector;
import com.example.drock.n_corder.fileselector.OnHandleFileListener;
import com.example.drock.n_corder.units.TimeUnits;
import com.example.drock.n_corder.units.Units;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class MeasurementDataStore extends Observable {
    static MeasurementDataStore mInstance;
    String mStreamName;
    List<Measurement> mData = new ArrayList<>();
    IMeasurementSink mSink;
    Context mContext;

    public static MeasurementDataStore getInstance() {
        if(mInstance == null) {
            mInstance = new MeasurementDataStore();
        }

        return mInstance;
    }

    private MeasurementDataStore() {}

    public void attachToStream(String streamName) {
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
        mInstance = new MeasurementDataStore();
        mInstance.attachToStream(streamName);
    }

    public void save(Context context) {
        mContext = context;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String defaultFilename = String.format("ncorder%s.csv", dateFormat.format(date));

        OnHandleFileListener saveFileListener = new OnHandleFileListener() {
            @Override
            public void handleFile(final String filePath) {
                Toast.makeText(mContext,
                        ("Save:" + filePath),
                        Toast.LENGTH_SHORT).show();

                if(save(filePath)) {
                    //notify user data was successfully saved
                    CharSequence text = String.format("Saved to %s", filePath);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext, android.support.v7.appcompat.R.style.Theme_AppCompat_Dialog_Alert);
                    builder.setTitle(R.string.dialog_saved_data)
                            .setMessage(text)
                            .setPositiveButton(android.R.string.ok, null)
                            .create()
                            .show();
                }
            }
        };
        String[] fileFilter = {".csv"};

        new FileSelector(mContext, FileOperation.SAVE, saveFileListener, fileFilter)
                .setDefaultFileName(defaultFilename)
                .show();
    }

    public boolean save(String filePath) {
        try {
            File file = new File(filePath);
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            Object[] data;
            synchronized (mData) {
                data = getData().toArray();
            }

            if(data.length > 0) {
                // Write the string to the file
                Measurement firstMeasurement  =(Measurement)data[0];
                String line = (firstMeasurement.getLocation() == null) ?
                        "time,value\n" : "time,value,lat,lon\n";
                osw.write(line);

                long baseTime = firstMeasurement.getTimestamp();
                for (Object o : data) {
                    ///hard-code measurement serializer for now
                    Measurement m = (Measurement)o;
                    double time = (m.getTimestamp() - baseTime) / 1000000000.;
                    Location location = m.getLocation();
                    if(location == null) {
                        line = String.format("%s,%f\n", time, m.getValue());
                    } else {
                        line = String.format("%s,%f,%f,%f\n", time, m.getValue(),
                                location.getLatitude(), location.getLongitude());
                    }
                    osw.write(line);
                }
            }
            osw.flush();
            osw.close();
            return true;
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        return false;
    }

    public List<Measurement> getData() { return mData; }

    public void load(String filePath) throws IOException {
        mInstance = new MeasurementDataStore(); //TODO make separate class to contain the data, this will provide services on top of that
        MeasurementFactory measurementFactory = SystemFactoryBroker.getSystemFactory().getMeasurementFactory();
        try {
            File file = new File(filePath);
            //CSVParser parser = CSVParser.parse(file, Charset.defaultCharset(),CSVFormat.DEFAULT);
            //CSVParser parser = CSVParser.parse(file, Charset.defaultCharset(),CSVFormat.RFC4180);
            Charset charset = Charset.forName("US_ASCII");
            CSVFormat format = CSVFormat.DEFAULT.withHeader("time", "value").withSkipHeaderRecord();
            CSVParser parser = CSVParser.parse(file, charset,format);

            if(parser.getHeaderMap() == null || !parser.getHeaderMap().containsKey("time") || !parser.getHeaderMap().containsKey("value"))
                throw new IOException("CSV header missing values");

            int timeCol = parser.getHeaderMap().get("time");
            int valueCol = parser.getHeaderMap().get("value");

            synchronized (mInstance.mData) {
                for (CSVRecord record : parser) {
                    String timeString = record.get(timeCol);
                    String valueString = record.get(valueCol);

                    long timestamp = (long) (Double.parseDouble(timeString) * (double) TimeUnits.NSEC_PER_SEC);
                    float value = Float.parseFloat(valueString);
                    Measurement measurement = measurementFactory.createMeasurement(value, Units.UNKNOWN, timestamp);
                    mInstance.mData.add(measurement);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
