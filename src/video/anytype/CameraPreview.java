package firstsubtext.subtext;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "";
	private SurfaceHolder mHolder;
    private Camera mCamera;
     

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }
    
 

    /**
      * When this function returns, mCamera will be null.
      */
//    private void stopPreviewAndFreeCamera() {
//
//        if (mCamera != null) {
//            /*
//              Call stopPreview() to stop updating the preview surface.
//            */
//            mCamera.stopPreview();
//        
//            /*
//              Important: Call release() to release the camera for use by other applications. 
//              Applications should release the camera immediately in onPause() (and re-open() it in
//              onResume()).
//            */
//            mCamera.release();
//        
//            mCamera = null;
//        }
//    }
    
   public void surfaceDestroyed(SurfaceHolder holder) {
//        // Surface will be destroyed when we return, so stop the preview.
//        if (mCamera != null) {
//            /*
//              Call stopPreview() to stop updating the preview surface.
//            */
//            mCamera.stopPreview();
//        }
    }

    

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
		Parameters parameters = mCamera.getParameters();
    	parameters.setPreviewSize(1280, 720);
    	parameters.setPictureSize(1280, 720);
    	parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);

    	Log.d("Video","White Balance: "+parameters.getWhiteBalance());
    	Log.d("Video","Flash: "+parameters.getFlashMode());
    	Log.d("Video","Exposure: "+parameters.getExposureCompensation());
    	Log.d("Video","ColorEffect: "+parameters.getColorEffect());
    	Log.d("Video","SceneMode: "+parameters.getSceneMode());


   
		mCamera.setParameters(parameters);
		
		
        
        

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
