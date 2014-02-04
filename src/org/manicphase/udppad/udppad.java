package org.manicphase.udppad;

import java.io.*;
import java.net.*;


import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;



public class udppad extends Activity{
	PadView padView;
	/** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
		Context context = getApplicationContext();

        padView = new PadView(this);
        setContentView(padView);
        //padView.setBackgroundColor(55555555);
        padView.requestFocus();
        
        
		CharSequence text = padView.udpc.connstat;
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();			
    }
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//padView.udpc.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//padView.udpc.close();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//padView.udpc.close();
	}

	/*private OnTouchListener listener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if((event.getActionMasked() & 6) == 6){
				udpc.send("stop");
			}
			if((event.getActionMasked() & 8) == 8){
				udpc.send("fire");
			}
			//udpc.send("fire" +  Boolean.toString((event.getActionIndex() & 6) == 6));
			return false;
		}
    };*/

}