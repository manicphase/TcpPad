package org.manicphase.udppad;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TCPSurfaceView extends SurfaceView implements Runnable{
	Thread thread = null;
	SurfaceHolder surfaceHolder;
	volatile boolean running = false;
    volatile boolean touched = false;
    volatile float touched_x;
    volatile float touched_y;
    TCPClient tcpc;


	public TCPSurfaceView(Context context) {
		super(context);
		surfaceHolder = getHolder();
		// TODO Auto-generated constructor stub
	}
	
	public void onResumeMySurfaceView() {
		running = true;
		thread = new Thread(this);
		thread.run();
		thread.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
	   touched_x = event.getX();
	   touched_y = event.getY();
	    
	   int action = event.getAction();
	   switch(action){
	   case MotionEvent.ACTION_DOWN:
		   touched = true;
		   tcpc.send("fire");
		   break;
	   case MotionEvent.ACTION_MOVE:
		   touched = true;
		   break;
	   case MotionEvent.ACTION_UP:
		   touched = false;
		   break;
	   case MotionEvent.ACTION_CANCEL:
		   touched = false;
		   break;
	   case MotionEvent.ACTION_OUTSIDE:
		   touched = false;
		   break;
	   default:
	   }
	   return true; //processed
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
	public void run() {
		// TODO Auto-generated method stub
		while(running){
			if(surfaceHolder.getSurface().isValid()){
			    Canvas canvas = surfaceHolder.lockCanvas();
			    surfaceHolder.unlockCanvasAndPost(canvas);
			    tcpc.run();
			    tcpc.start();
			}
		}
	}

}
