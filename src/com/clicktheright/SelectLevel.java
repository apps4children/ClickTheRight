package com.clicktheright;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SelectLevel extends Activity{
	
	Intent intent;
	Button level1, level2, level3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      //Remove notification bar
        setContentView(R.layout.select_level);
	    
        level1 = (Button)findViewById(R.id.firstLevel);
        level2 = (Button)findViewById(R.id.secondLevel);
        level3 = (Button)findViewById(R.id.thirdLevel);
        
        level1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent = new Intent(SelectLevel.this, Level1.class);
				startActivity(intent);
				finish();
			}
		});
        
        level2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(SelectLevel.this, Level2.class);
				startActivity(intent);
				finish();
			}
		});
        
        level3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(SelectLevel.this, Level3.class);
				startActivity(intent);
				finish();
			}
		});
    }
    
    public void onBackPressed(){
    	intent = new Intent(SelectLevel.this, ClickTheRightActivity.class);
    	startActivity(intent);
    	finish();
    }

}
