package com.zvbxrpl.flashlightfree;
import com.google.ads.*;



import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.view.Menu;

import android.widget.LinearLayout;
import android.widget.TextView;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;



public class MainActivity extends Activity {
	private AdView adView;
	private TextView batteryInfo;
	
	
	
	Camera cam;
	Parameters p;
	int time = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //
        // Create the adView
        adView = new AdView(this, AdSize.BANNER,  "ca-app-pub-1739340664259303/3224887676");

        // Lookup your LinearLayout assuming it's been given
        // the attribute android:id="@+id/mainLayout"
        LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);

        // Add the adView to it
        layout.addView(adView);
       
        // Initiate a generic request to load it with an ad
        adView.loadAd(new AdRequest());
        //
       
        
        batteryInfo=(TextView)findViewById(R.id.textViewBatteryInfo);
        this.registerReceiver(this.batteryInfoReceiver,	new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        
        
    }
         

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    
        cam = Camera.open();     
        p = cam.getParameters();
    	
       	p.setFlashMode(Parameters.FLASH_MODE_TORCH);
    	cam.setParameters(p);

    }

    
    @Override
    protected void onPause() {
        super.onPause();
                // Another activity is taking focus (this activity is about to be "paused").
        
        
       // cam.startPreview();
   	 //cam.stopPreview();
   	 cam.release();
   	 
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        //cam.startPreview();
   	 	//cam.stopPreview();
        
   	 cam.release();
    }
    
    @Override
    public void onDestroy() {
      if (adView != null) {
        adView.destroy();
      }
      super.onDestroy();
    }
    
   
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
 		@Override
 		public void onReceive(Context context, Intent intent) {
 			
 			int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
 			int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
 			int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
 			int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
 			boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT); 
 			int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
 			int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
 			String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
 			int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
 			int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
 			
  int percent = (int)(((double)level/(double)scale)*100);
  
 			batteryInfo.setText("Battery\n "+percent+"%");
 			
 			
// 			batteryInfo.setText(
// 					"Health: "+health+"\n"+
// 					"Icon Small:"+icon_small+"\n"+
// 					"Level: "+level+"\n"+
// 					"Plugged: "+plugged+"\n"+
// 					"Present: "+present+"\n"+
// 					"Scale: "+scale+"\n"+
// 					"Status: "+status+"\n"+
// 					"Technology: "+technology+"\n"+
// 					"Temperature: "+temperature+"\n"+
// 					"Voltage: "+voltage+"\n");
// 			//imageBatteryState.setImageResource(icon_small);
 		}
 	};

}












