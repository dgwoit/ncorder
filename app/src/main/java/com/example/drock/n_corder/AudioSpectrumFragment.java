package com.example.drock.n_corder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.uol.aig.fftpack.RealDoubleFFT;

public class AudioSpectrumFragment extends Fragment {
    public static AudioSpectrumFragment newInstance() {
        return new AudioSpectrumFragment();
    }

    SpectralView mSpectralView;
    AudioRecord mAudioSource;
    //Visualizer mAudioVisualTransformer;
    /*protected Visualizer.OnDataCaptureListener mDataCaptureListener = new Visualizer.OnDataCaptureListener() {
        @Override
        public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
            //do nothing for now
        }

        @Override
        public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
            if(mSpectralView != null) {
                mSpectralView.addFFTData(fft, samplingRate);
            }
        }
    };*/
    protected final int mSamplingRate = 8000;
    protected RealDoubleFFT mAudioTransform;
    protected AsyncTask mStreamProcessor;
    protected final int mTransformBlockSize = 256;
    protected final short[] mAudioStreamBuffer = new short[mTransformBlockSize];
    protected final double[] mTransformBlock = new double[mTransformBlockSize];
    protected final double mHzPerBin = (double)mSamplingRate / (double)mTransformBlockSize;

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStreamProcessor.cancel(false);
        mAudioSource.stop();
        mAudioSource.release();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_spectrum, container, false);
        mSpectralView = (SpectralView)view.findViewById(R.id.spectral_view);
        if(mSpectralView != null && mAudioSource == null) {
            mSpectralView.setBlockSize(mTransformBlockSize);
            int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;//AudioFormat.CHANNEL_IN_MONO;
            int audioFormat = AudioFormat.ENCODING_PCM_16BIT;//AudioFormat.ENCODING_PCM_8BIT;
            int bufSize = AudioRecord.getMinBufferSize(mSamplingRate, channelConfiguration, audioFormat);
            mAudioSource = new AudioRecord(MediaRecorder.AudioSource.MIC, mSamplingRate, channelConfiguration, audioFormat,
                    bufSize);
            mAudioSource.startRecording();

            mAudioTransform = new RealDoubleFFT(mTransformBlockSize);
            mStreamProcessor = new AsyncTask() {

                @Override
                protected Object doInBackground(Object[] params) {
                    while(!this.isCancelled()) {
                        try {
                            int readResult = mAudioSource.read(mAudioStreamBuffer, 0, mAudioStreamBuffer.length);
                            for (int i = 0; i < readResult && i < mTransformBlockSize; i++) {
                                mTransformBlock[i] = mAudioStreamBuffer[i] / 32768.0;
                            }
                            mAudioTransform.ft(mTransformBlock);
                            mSpectralView.addFFTData(mTransformBlock, mHzPerBin);
                        } catch (Exception ex) {
                            Log.e("AudioStreamProcessor", String.format("audio stream processing exception: %s", ex.toString()));
                        }
                    }
                    return null;
                }
            };
            mStreamProcessor.execute(null);
            // this may only work properly on newer versions of android, may need to improves some
            //sort of audio track for this to work fully
            //mAudioVisualTransformer = new Visualizer(mAudioSource.getAudioSessionId());
            //mAudioVisualTransformer.setDataCaptureListener(mDataCaptureListener,
            //        10000 /*sample rate in millihertz */,
            //        false, /*no wafeform capture*/
            //        true /*capture fft*/);
            //mAudioVisualTransformer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            //mAudioVisualTransformer.setMeasurementMode(Visualizer.MEASUREMENT_MODE_PEAK_RMS);
            //mAudioVisualTransformer.setScalingMode(Visualizer.SCALING_MODE_NORMALIZED);
            //mAudioVisualTransformer.setEnabled(true);
        }
        return view;
    }

}
