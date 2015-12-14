package com.example.drock.n_corder;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.app.NotificationManager;
import android.widget.Toast;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOService;

public class IOIOAccessService extends IOIOService {
    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.local_service_started;


    public IOIOAccessService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    protected IOIOLooper createIOIOLooper() {
        return new BaseIOIOLooper() {
            private DigitalOutput mLed;

            @Override
            protected void setup() throws ConnectionLostException,
                    InterruptedException {
                mLed = ioio_.openDigitalOutput(IOIO.LED_PIN);
                IOIODeviceDriverManager.getInstance().RealizeDrivers(ioio_);
            }

            @Override
            public void loop() throws ConnectionLostException,
                    InterruptedException {
                mLed.write(true);
                for(IOIODeviceDriver driver: IOIODeviceDriverManager.getInstance().getDrivers())
                    driver.Update();
                mLed.write(false);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        if (intent != null && intent.getAction() != null
                && intent.getAction().equals("stop")) {

            stopSelf();
        }

        return result;
    }

    public class LocalBinder extends Binder {
        IOIOAccessService getService() {
            return IOIOAccessService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }
}
