package org.manicphase.udppad;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	Thread thread = null;
	SurfaceHolder surfaceHolder;
	volatile boolean running = false;
    volatile boolean touched = false;
    volatile float touched_x;
    volatile float touched_y;

	public MySurfaceView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		// TODO Auto-generated constructor stub
	}
	
	public void onResumeMySurfaceView() {
		running = true;
		//thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	   //return true; //processed
	}

	public void onPauseMySurfaceView() {
		boolean retry = true;
		running = false;
		while(retry){
			try{
				thread.join();
				retry = false;
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
