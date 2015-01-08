package com.maximos.mobile.challengeapp.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.maximos.mobile.challengeapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoCapture extends Activity implements View.OnClickListener,
        SurfaceHolder.Callback {

    private static final String TAG = "CAMERA_TUTORIAL";

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private boolean previewRunning;
    private MediaRecorder mMediaRecorder;
    private final int maxDurationInMs = 20000;
    private final long maxFileSizeInBytes = 500000;
    private final int videoFramesPerSecond = 20;
    Button btn_record;
    boolean mInitSuccesful = false;
    File file;
    ToggleButton mToggleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_capture);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mToggleButton = (ToggleButton) findViewById(R.id.toggleRecordingButton);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // toggle video recording
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked())
                    mMediaRecorder.start();
                else {
                    mMediaRecorder.stop();
                    mMediaRecorder.reset();
                    try {
                        initRecorder(mHolder.getSurface());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initRecorder(Surface surface) throws IOException {
        // It is very important to unlock the camera before doing setCamera
        // or it will results in a black preview
        if (mCamera == null)
        {
            mCamera = Camera.open();
            mCamera.unlock();
        }

        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();

        mMediaRecorder.setPreviewDisplay(surface);
        mMediaRecorder.setCamera(mCamera);

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);

        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);

        mMediaRecorder.setOutputFile(this.initFile().getAbsolutePath());

        // No limit. Don't forget to check the space on disk.
        mMediaRecorder.setMaxDuration(50000);
        mMediaRecorder.setVideoFrameRate(24);
        mMediaRecorder.setVideoSize(1280, 720);
        mMediaRecorder.setVideoEncodingBitRate(3000000);
        mMediaRecorder.setAudioEncodingBitRate(8000);

        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            // This is thrown if the previous calls are not called with the
            // proper order
            e.printStackTrace();
        }

        mInitSuccesful = true;
    }

    private File initFile() {
        // File dir = new
        // File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
        // this
        File dir = new File(Environment.getExternalStorageDirectory(), this
                .getClass().getPackage().getName());


        if (!dir.exists() && !dir.mkdirs()) {
            Log.wtf(TAG,
                    "Failed to create storage directory: "
                            + dir.getAbsolutePath());
            //Toast.makeText(MediaStore.Video, "not record", Toast.LENGTH_SHORT);
            file = null;
        } else {
            file = new File(dir.getAbsolutePath(), new SimpleDateFormat(
                    "'IMG_'yyyyMMddHHmmss'.mp4'").format(new Date()));
        }
        return file;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (!mInitSuccesful)
                initRecorder(mHolder.getSurface());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void shutdown() {
        // Release MediaRecorder and especially the Camera as it's a shared
        // object that can be used by other applications
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mCamera.release();

        // once the objects have been released they can't be reused
        mMediaRecorder = null;
        mCamera = null;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        shutdown();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

}