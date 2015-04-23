package com.clicktheright;

import com.analytics.DisplayRecords;
import com.database.TestAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ClickTheRightActivity extends Activity {

	Button play, rules, exit, score, save, cancel;
	Dialog dialog;
	EditText name;
	String save_name="";
	TestAdapter mDbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_click_the_right);
       
	    play=(Button)findViewById(R.id.playBtn);
	    rules=(Button)findViewById(R.id.rulesBtn);
	    exit=(Button)findViewById(R.id.exitBtn);
	    score=(Button)findViewById(R.id.scoreCard);
	    
	    mDbHelper = new TestAdapter(this);
	    play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ClickTheRightActivity.this, SelectLevel.class);
			    startActivity(intent); 
			}
		});
	    
	    rules.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ClickTheRightActivity.this, Instructions.class);
			    startActivity(intent);

			}
		});
	    
	    score.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ClickTheRightActivity.this, DisplayRecords.class);
				startActivity(i);
			}
		});
	    
	    exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				System.exit(0);
			}
		});
     playerForm();
	}
	
	public void playerForm()
	{
		dialog = new Dialog(ClickTheRightActivity.this);
		dialog.setContentView(R.layout.player_detail);
		dialog.setTitle("Player Details");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);

        name=(EditText) dialog.findViewById(R.id.name);
        Button save= (Button)dialog.findViewById(R.id.save);
        Button cancel= (Button)dialog.findViewById(R.id.cancel);
		save.setOnClickListener(new OnClickListener() 
        {
        public void onClick(View v) 
        {
        save_name=name.getText().toString();
        if (name ==null|| name.length() == 0) 
      	{
		name.setError("Enter Your Name");
		name.requestFocus();
		}
      	else
      	{
         int userid;
         userid=Integer.parseInt(savePlayerName(save_name));//inserting name in database and fetching id
         savePlayerNameApplication(save_name, userid);//saving name and id to shared preference
      	 dialog.dismiss();
      	}
        }
        });
		cancel.setOnClickListener(new OnClickListener() 
        {
        public void onClick(View v) 
        {
        	savePlayerNameApplication("",0);//saving null in name and  0 in id to shared preference to ignore recording
        	dialog.dismiss();
        }
        });
		dialog.show();
	}

	public void savePlayerNameApplication(String name, int id){
	    SharedPreferences sharedPref= getSharedPreferences("mypref", MODE_PRIVATE);
	    SharedPreferences.Editor editor= sharedPref.edit();
	    editor.putString("playerName",name);
	    editor.putInt("playerID",id);
	    editor.commit();

	}
	
	public String savePlayerName(String name){
    	String id;
		mDbHelper.createDatabase();       
		mDbHelper.open(); 
		id=mDbHelper.insertStudent(name); 
		mDbHelper.close();
		return id;		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.click_the_right, menu);
		return true;
	}

}
