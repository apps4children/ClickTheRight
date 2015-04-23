package com.clicktheright;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.database.TestAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.transition.Scene;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Level3 extends Activity implements OnClickListener{
	String response=null;
	Integer[] letters;
	List<String> questions =new ArrayList<String>(52);
//	List<String> symbols = new ArrayList<String>(19);
	List<String> options = new ArrayList<String>(12);
	List<String> options_temp = new ArrayList<String>(6);
	List<Integer> ids = new ArrayList<Integer>(2);

	TestAdapter mDbHelper;
	StringBuilder sb = new StringBuilder();
	MediaPlayer mp = new MediaPlayer();
	Button option1,option2,option3,option4,option5,option6,option7,option8,option9,option10,option11,option12,question,source,target;
	int screenCounter=1, sequence=2, i;
	int result1,result2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.level1);

		mDbHelper = new TestAdapter(this);
        loadImage();
	    set_ans_choice();
	    
	    question = (Button)findViewById(R.id.question);
	    question.setId(result1);
	    question.setText(questions.get(0));
		  	 	    
	    option1 = (Button)findViewById(R.id.img1);
		option1.setId(12);
		option1.setText(options.get(0));
		
		option2 = (Button)findViewById(R.id.img2);
		option2.setId(13);
		option2.setText(options.get(1));
	
		option3 = (Button)findViewById(R.id.img3);
		option3.setId(14);
		option3.setText(options.get(2));
		
		option4 = (Button)findViewById(R.id.img4);
		option4.setId(15);
		option4.setText(options.get(3));
	
		option5 = (Button)findViewById(R.id.img5);
		option5.setId(16);
		option5.setText(options.get(4));
	
		option6 = (Button)findViewById(R.id.img6);
		option6.setId(17);
		option6.setText(options.get(5));
		
		option7 = (Button)findViewById(R.id.img7);
		option7.setId(18);
		option7.setText(options.get(6));
		
		option8 = (Button)findViewById(R.id.img8);
		option8.setId(19);
		option8.setText(options.get(7));
	
		option9 = (Button)findViewById(R.id.img9);
		option9.setId(20);
		option9.setText(options.get(8));
		
		option10 = (Button)findViewById(R.id.img10);
		option10.setId(21);
		option10.setText(options.get(9));
	
		option11 = (Button)findViewById(R.id.img11);
		option11.setId(22);
		option11.setText(options.get(10));
	
		option12 = (Button)findViewById(R.id.img12);
		option12.setId(23);
		option12.setText(options.get(11));
		
		
		option1.setOnClickListener(this) ;
		option2.setOnClickListener(this) ;
		option3.setOnClickListener(this) ;
		option4.setOnClickListener(this) ;
		option5.setOnClickListener(this) ;
		option6.setOnClickListener(this) ;
	
		option7.setOnClickListener(this) ;
		option8.setOnClickListener(this) ;
		option9.setOnClickListener(this) ;
		option10.setOnClickListener(this) ;
		option11.setOnClickListener(this) ;
		option12.setOnClickListener(this) ;
		
  
    }
    public void onClick(View v){
    	
		Button answer=(Button) v;
		
		if(response==null)
		{
			response = question.getText()+"-"+answer.getText();
			sb.append(response);
		}
		else
		{
			sb.append(","+answer.getText());
		}

		
    	 int id = v.getId();  
   	  if((id-12) == result1 || (id-12) == result2 ){
   		  sequence--;
   		  ids.add(id);
   		  System.out.println("num" + sequence);
   		Log.d("num", "num"+ sequence);
   		  if(sequence == 0 )
   		  {	
   		 
   			//View b = findViewById(R.id.next);
   			//b.setVisibility(View.VISIBLE);
   			option1.setOnClickListener(null) ;
   			option2.setOnClickListener(null) ;
   			option3.setOnClickListener(null) ;
   			option4.setOnClickListener(null) ;
   			option5.setOnClickListener(null) ;
   			option6.setOnClickListener(null) ;
   		
   			option7.setOnClickListener(null) ;
   			option8.setOnClickListener(null) ;
   			option9.setOnClickListener(null) ;
   			option10.setOnClickListener(null) ;
   			option11.setOnClickListener(null) ;
   			option12.setOnClickListener(null) ;
   			Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
   			vb.vibrate(500);
   			  mp = MediaPlayer.create(Level3.this, R.raw.succ2);
   		mp.start();
   		final Handler handler = new Handler();
   		handler.postDelayed(new Runnable() {
   		  @Override
   		  public void run() {
   			Log.d("delayed","delayed");
   			if(screenCounter>8){
   				Intent i = new Intent(Level3.this, SelectLevel.class);
   				startActivity(i);
   				finish();
   				savescore(sb.toString());
   			}
   			else{
   				nextFunction();
   				screenCounter = screenCounter+1;
   			  }
   		  }
   		}, 500);
   		  } 
   		  else 
   			  playmusic (1);
   		 v.setBackgroundColor(Color.GREEN);
   		 v.setOnClickListener(null);
   		//  v.setVisibility(View.INVISIBLE);
   	  }
   	  else {playmusic(0);
     	v.setBackgroundColor(Color.RED);
        v.setOnClickListener(null);
       		  }
    }
    
    private void playmusic(int i) {
		// i==0	error
		// i==1 success for a single fruit
		mp.reset();
		if(i==0)
		{
			mp = MediaPlayer.create(Level3.this, R.raw.err);
		}
		else 
		{
			mp = MediaPlayer.create(Level3.this, R.raw.succ1);
		}
			
		mp.start();
	}
    
    
public void nextFunction(){

	response = null;
	sb.append("\n");
			
	 if(sequence == 0){
		 sequence = 2;
		 View v1 = findViewById(ids.get(0));
		 v1.setVisibility(View.VISIBLE);
		 View v2 = findViewById(ids.get(1));
		 v2.setVisibility(View.VISIBLE);
		 
		 ids.clear();
		 options.clear();
		 Log.d("next","next  1");
         
		 Collections.shuffle(questions);
		 set_ans_choice();
		 
		 question.setId(result1);
		 question.setText(questions.get(0));
		 question.setBackgroundResource(R.drawable.img_bg);
			
			option1.setId(12);
 			option1.setText(options.get(0));
			option1.setBackgroundResource(R.drawable.img_bg);
			
			option2.setId(13);
			option2.setText(options.get(1));
			option2.setBackgroundResource(R.drawable.img_bg);
		
			
			option3.setId(14);
			option3.setText(options.get(2));
			option3.setBackgroundResource(R.drawable.img_bg);
			
			
			option4.setId(15);
			option4.setText(options.get(3));
			option4.setBackgroundResource(R.drawable.img_bg);
		
		
			option5.setId(16);
			option5.setText(options.get(4));
			option5.setBackgroundResource(R.drawable.img_bg);
		
			
			option6.setId(17);
			option6.setText(options.get(5));
			option6.setBackgroundResource(R.drawable.img_bg);
			
			
			option7.setId(18);
			option7.setText(options.get(6));
			option7.setBackgroundResource(R.drawable.img_bg);
			
			
			option8.setId(19);
			option8.setText(options.get(7));
			option8.setBackgroundResource(R.drawable.img_bg);
		
			
			option9.setId(20);
			option9.setText(options.get(8));
			option9.setBackgroundResource(R.drawable.img_bg);
			
			
			option10.setId(21);
			option10.setText(options.get(9));
			option10.setBackgroundResource(R.drawable.img_bg);
		
		
			option11.setId(22);
			option11.setText(options.get(10));
			option11.setBackgroundResource(R.drawable.img_bg);
			
			option12.setId(23);
			option12.setText(options.get(11));
			option12.setBackgroundResource(R.drawable.img_bg);
			
			
			option1.setOnClickListener(this) ;
			option2.setOnClickListener(this) ;
			option3.setOnClickListener(this) ;
			option4.setOnClickListener(this) ;
			option5.setOnClickListener(this) ;
			option6.setOnClickListener(this) ;
		
			option7.setOnClickListener(this) ;
			option8.setOnClickListener(this) ;
			option9.setOnClickListener(this) ;
			option10.setOnClickListener(this) ;
			option11.setOnClickListener(this) ;
			option12.setOnClickListener(this) ;
			
		 
		 
	 }
	
}
    
private void loadImage() {

	questions.add("A");
	questions.add("B");                
	questions.add("C");
	questions.add("D");
	questions.add("E");
	questions.add("F");
	questions.add("G");
	questions.add("H");
	questions.add("I");
	questions.add("J");
	questions.add("K");
	questions.add("L");
	questions.add("M");
	questions.add("N");
	questions.add("O");
	questions.add("P");                
	questions.add("Q");
	questions.add("R");
	questions.add("S");
	questions.add("T");
	questions.add("U");
	questions.add("V");
	questions.add("W");
	questions.add("X");
	questions.add("Y");
	questions.add("Z");

	questions.add("a");
	questions.add("b");                
	questions.add("c");
	questions.add("d");
	questions.add("e");
	questions.add("f");
	questions.add("g");
	questions.add("h");
	questions.add("i");
	questions.add("j");
	questions.add("k");
	questions.add("l");
	questions.add("m");
	questions.add("n");
	questions.add("o");
	questions.add("p");                
	questions.add("q");
	questions.add("r");
	questions.add("s");
	questions.add("t");
	questions.add("u");
	questions.add("v");
	questions.add("w");
	questions.add("x");
	questions.add("y");
	questions.add("z");

	Collections.shuffle(questions);
}

private void set_ans_choice() {
	options_temp.clear();

	options_temp.add(questions.get(0));
	options_temp.add(questions.get(1));
	options_temp.add(questions.get(2));
	options_temp.add(questions.get(3));
	options_temp.add(questions.get(4));
	options_temp.add(questions.get(5));
	
	
	String quesid1=options_temp.get(0);
	Collections.shuffle(options_temp);

	options.add(options_temp.get(0));
	options.add(options_temp.get(1));
	options.add(options_temp.get(2));
	options.add(options_temp.get(3));
	options.add(options_temp.get(4));
	options.add(options_temp.get(5));
	
	options_temp.clear();
	 
	options_temp.add(questions.get(0));
	options_temp.add(questions.get(6));
	options_temp.add(questions.get(7));
	options_temp.add(questions.get(8));
	options_temp.add(questions.get(9));
	options_temp.add(questions.get(10));
		
		
		String quesid2=options_temp.get(0);
		Collections.shuffle(options_temp);
		
		options.add(options_temp.get(0));
		options.add(options_temp.get(1));
		options.add(options_temp.get(2));
		options.add(options_temp.get(3));
		options.add(options_temp.get(4));
		options.add(options_temp.get(5));
	
	
	result1 = options.indexOf(quesid1);
	result2 = options.lastIndexOf(quesid2);
   }	

public void savescore(String data)
{
	int id;
	SharedPreferences sharedPref= getSharedPreferences("mypref", 0);
	String playerName= sharedPref.getString("playerName", null);
	id= sharedPref.getInt("playerID",0);
	//id=Integer.parseInt(playerID);

	System.out.println("Player Name at Level1:="+playerName);
	System.out.println("Player Id="+id);
	if(playerName!=null&&playerName.length()>0)
	{
		mDbHelper.createDatabase();       
		mDbHelper.open(); 
		mDbHelper.updateLevel(id, data,"level3"); 
		mDbHelper.close();
	}
}



}
