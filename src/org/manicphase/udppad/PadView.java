package org.manicphase.udppad;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

//public class PadView extends View implements OnTouchListener {
public class PadView extends ImageView implements OnTouchListener {
    private static final String TAG = "PadView";
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;
    
    int xcentreleft;
    int xcentreright;
    int xcentre;
    int ycentre;
    
    int leftindex;
    int rightindex;
    
    float x;
    float y;
    
    private float lastx;
    private float lasty;

    
    Paint paint = new Paint();
    
    TCPClient udpc = new TCPClient();


    public PadView(Context context) {
        super(context);
    	
        try {
    		udpc.run();
    		//udpc.start();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	//set up screen
    	
    	setImageResource(R.drawable.pad2);
    	setScaleType(ScaleType.FIT_XY);
    	//setBackgroundColor(Color.WHITE);
        setFocusable(true);
        setFocusableInTouchMode(true);
        
        this.setOnTouchListener(this);

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        
        //invalidate();
        
        //set up values
        /*xcentreleft = getMeasuredWidth() / 4;
        xcentreright = (int) (getMeasuredWidth() * 0.75);
        xcentre = getMeasuredWidth() / 2;
        ycentre = getMeasuredHeight() / 2;*/
		
    }
/*
    @Override
    public void onDraw(Canvas canvas) {

        xcentreleft = this.getWidth() / 4;
        xcentreright = (int) (this.getWidth() * 0.75);
        xcentre = this.getWidth() / 2;
        ycentre = this.getHeight() / 2;
        
    }
*/  
    public boolean onTouch(View view, MotionEvent event) {
    	//TODO: make this not look embarrassing
        xcentreleft = this.getWidth() / 4;
        xcentreright = (int) (this.getWidth() * 0.75);
        xcentre = this.getWidth() / 2;
        ycentre = this.getHeight() / 2;
        
    	final int action = event.getAction();
    	final int fingercount = event.getPointerCount();
    	boolean leftside = false;
    	String fingerside;
    	
    	int i = 0;
    	String senddata = "";
    	
    	int eventmask = action & MotionEvent.ACTION_MASK;
    	
    	String leftinstruction = "";
    	String rightinstruction = "";

    	
    	while (i < fingercount) {
	        x = event.getX(i);
	        y = event.getY(i);
	        //points.add(point);
	        
	        if (x < xcentre){
	        	leftindex = event.getPointerId(i);
	        	leftside = true;
	        	fingerside = "l";
	        	leftinstruction = "l"+ Integer.toString(Math.round(x-xcentreleft)) + "," + Integer.toString(Math.round(y-ycentre)) + " ";
	        	//senddata = senddata + " x" + Integer.toString(Math.round(x-xcentreleft)) + " y" + Integer.toString(Math.round(y-ycentre));
	        }
	        else {
	        	leftindex = (0 == event.getPointerId(i)? 1:0);
	        	leftside = false;
	        	fingerside = "r";
	        	rightinstruction = "r" + Integer.toString(degrees(x, y, leftside));
	        	//senddata = senddata + " r" + Integer.toString(degrees(x, y, leftside));
	        }
	        i++;
	      	}
    	
    	if ((eventmask & event.ACTION_UP) == event.ACTION_UP) {
    		if ((action != 261) && (action != 5)) {
	    		if (leftindex == 0){
	    			leftinstruction = "lu ";
	    		}
	    		else {
	    			rightinstruction = "ru";
	    		}
    		}
    	}
    	
    	if ((eventmask & event.ACTION_POINTER_UP) == event.ACTION_POINTER_UP){
    		if (action != 261) {
	    		if (action == 6){
	    			if (leftindex == 0){
	    				leftinstruction = "lu ";
	    			}
	    			else {
	    				rightinstruction = "ru";
	    			}
	    		}
	    		else{
		    		if (leftindex == 1){
		    			leftinstruction = "lu ";
		    		}
		    		else {
		    			rightinstruction = "ru";
		    		}
	    		}
    		}
       	}

    	if (udpc.unblocked){
    		udpc.send(leftinstruction + rightinstruction);
    	}
    	invalidate();
    	
        return true;
    }    
   
    public int degrees(float x, float y, boolean left){
    	double touchx;
    	if (left){
    		touchx = xcentreleft - x;
    	}
    	else {
    		touchx = xcentreright - x;
    	}
       	double touchy = ycentre - y;
		return (int) ((57.29 * Math.atan2(touchx, touchy)) + 360) % 360;   	
    }
}
