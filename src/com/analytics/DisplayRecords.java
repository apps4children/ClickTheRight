package com.analytics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.clicktheright.R;
import com.database.Student;
import com.database.TestAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

public class DisplayRecords extends Activity{
    FileWriter writer;
    ArrayList<Student> student=null;
    ArrayList<String>  studentByGroup=null;
    ArrayList<Student> studentByName=null;
    ArrayList<Student> studentDataCsv=null;

    TestAdapter mDbHelper; 
    ListView listView;
    CustomAdapter customAdapter;
    Animation animAlpha;
    ImageView saveRecord,exit;
    String totalTime;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.player_list);
        mDbHelper = new TestAdapter(this);
        displayRecords();
        customAdapter=new CustomAdapter(student, this);            
        listView=(ListView) findViewById(R.id.MessageList);
        listView.setAdapter(customAdapter);
        //click animation
        animAlpha=AnimationUtils.loadAnimation(this,R.anim.alpha);
        
        saveRecord= (ImageView)findViewById(R.id.save);
        exit= (ImageView)findViewById(R.id.exit);
        saveRecord.setOnClickListener(new ImageView.OnClickListener()
        {
			public void onClick(View v) 
			{
				alert();
		}});	
        
        exit.setOnClickListener(new ImageView.OnClickListener()
        {
			public void onClick(View v) 
			{
             finish();
		}});	
    }

    public void onClick(View v) 
	{
    	ImageView button = (ImageView) v;
		Student row = (Student) button.getTag();
		mDbHelper.createDatabase();       
		mDbHelper.open(); 
		//mDbHelper.deleteRecord(row.getId()); 
		mDbHelper.close();
		customAdapter.deleteRow(row);
		customAdapter.notifyDataSetChanged();
	}
    
    public void displayRecords() 
    {
    	student=new ArrayList<Student>();
		mDbHelper.createDatabase();       
		mDbHelper.open(); 
		student=mDbHelper.browseAllStudent(); 
		mDbHelper.close();
	}
    
    //generate csv file
    public void createCSV(ArrayList<Student> studentdata,String name)
    {
    	studentDataCsv=new ArrayList<Student>();
    	studentDataCsv=studentdata;
    	String[] headers = new String[]{"Name","Date","Level 1","Level 2","Level 3","Total Time","Total Mistakes"};
    	try 
        {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppsReport/ClickTheRight";
        File dir = new File(path);
        if(!dir.exists())
        dir.mkdirs();
                               
        File file = new File(dir,name+".csv");
        if(file.exists()) 
          file.delete();
        writer = new FileWriter(file);

        writeCsvHeader(headers[0],headers[1],headers[2],headers[3],headers[4],headers[5],headers[6]);

        for(int i=0;i<studentDataCsv.size();i++)
        {
        	int totalMistake=0;
        	StringBuilder level1= new StringBuilder();
        	StringBuilder level2= new StringBuilder();
        	StringBuilder level3= new StringBuilder();
        	
        	StringBuilder time1= new StringBuilder();
        	StringBuilder time2= new StringBuilder();
        	StringBuilder time3= new StringBuilder();
         	
        	StringBuilder mistake= new StringBuilder();
            Student st= studentDataCsv.get(i);
            
            if(!(st.getLevel1().equalsIgnoreCase("No mistakes")||st.getLevel1()==null||st.getLevel1().equals("")))
            {
            totalMistake=totalMistake+countMistake(st.getLevel1());
            
            level1.append("\"");
        	level1.append(st.getLevel1());
        	level1.append("\"");  
        	
        	time1.append("\"");
        	time1.append(st.getTotalTime());
        	time1.append("\""); 
            }
            
            if(!(st.getLevel2().equalsIgnoreCase("No mistakes")||st.getLevel2()==null||st.getLevel2().equals("")))
            {
            totalMistake=totalMistake+countMistake(st.getLevel2());
            
        	level2.append("\"");
        	level2.append(st.getLevel2());
        	level2.append("\""); 
        	
        	time2.append("\"");
        	time2.append(st.getTotalTime());
        	time2.append("\""); 
            }    
            
            if(!(st.getLevel3().equalsIgnoreCase("No mistakes")||st.getLevel3()==null||st.getLevel3().equals("")))
            {
            totalMistake=totalMistake+countMistake(st.getLevel3());
            
        	level3.append("\"");
        	level3.append(st.getLevel3());
        	level3.append("\""); 
        	
        	time3.append("\"");
        	time3.append(st.getTotalTime());
        	time3.append("\""); 
            }   
            
            totalTime=calculateTime(Long.parseLong(st.getTotalTime()));
            
            mistake.append(String.valueOf(totalMistake));
            mistake.append("\n");//adding new line after record
            
            writeCsvData(st.getName(), st.getDate(),level1.toString(),level2.toString(),level3.toString(),totalTime,mistake.toString());
        }
        writer.flush();
        writer.close(); 
        }catch(IOException e) 
        { 
            e.printStackTrace();
        }
    }//end of method create 
    private void writeCsvHeader(String h1, String h2, String h3,String h4,String h5,String h6,String h7) throws IOException
    {
    	   String line = String.format("%s,%s,%s,%s,%s,%s,%s\n",h1,h2,h3,h4,h5,h6,h7);
    	   writer.write(line);
    }
    @SuppressLint("DefaultLocale")
	private void writeCsvData(String name,String date,String level1,String level2,String level3,String time,String totalMistake) throws IOException 
    {  
    	  String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",name.toUpperCase(),date,level1,level2,level3,time,totalMistake);
    	  writer.write(line);
    }
    
    public int countMistake(String str)
    {
    	  int mistakes=0;
    	  for(int i = 0; i < str.length(); i++) 
    	  {
    		if(str.charAt(i)==',') 
    		mistakes=mistakes+1;
    	  }
       return mistakes;
    	
    }
    //alert dialog
    public void alert()
    {
    	    studentByGroup=new ArrayList<String>();
		    mDbHelper.createDatabase();       
		    mDbHelper.open(); 
		   // studentByGroup=mDbHelper.browseStudentByGroup(); 
		    mDbHelper.close();
		
		    final String[] items=studentByGroup.toArray(new String[studentByGroup.size()]);
		    
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Select Student Name");
	        builder.setItems(items, new DialogInterface.OnClickListener() 
	        {
	            public void onClick(DialogInterface dialog, int item) 
	            {
	               String name=items[item];
	               if(name.equalsIgnoreCase("All"))
	               {
	            	   createCSV(student,name);
	            	   
	            	   sdCardPath(name);//showing alert for path
	               }
	               else
	               {
	            	   studentByName=new ArrayList<Student>();
	           		   mDbHelper.createDatabase();       
	           		   mDbHelper.open(); 
	           		  // studentByName=mDbHelper.browseStudentByName(name); 
	           		   mDbHelper.close();
	           		   createCSV(studentByName,name);
	           		   
	           		   sdCardPath(name);//showing alert for path
	               }
	            }
	        });
	        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() 
	        {
	            public void onClick(DialogInterface dialog, int id) 
	            {
	                dialog.cancel();
                }
            });
	        AlertDialog alert = builder.create();
	        alert.show();
    }
    public void sdCardPath(String name)
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("File Path : sdcard/AppsReport/ClickTheRight \n");
    	sb.append("File Name : "+name+".csv");

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Saved File Location");
        builder.setMessage(sb.toString())
		.setCancelable(false)
		.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
        AlertDialog alert = builder.create();
        alert.show();
    }
    
    public String calculateTime(long time)
	{
		String result=null;
		int seconds=(int)(time/1000)%60;
		int minutes=(int)((time/(1000*60))%60);
		int hours=(int)((time/(1000*60*60))%24);

		if(hours>0&&hours<2)
			result=hours+" Hour";
		else if(hours>1)
			result=hours+" Hours";
		else if(minutes>0&&minutes<2)
			result=result+" "+minutes+" Minute";
		else if(minutes>1)
			result=result+" "+minutes+" Minutes";
		else if(seconds>0&&seconds<2)
			result=result+" "+seconds+" Second";
		else if(seconds>1)
			result=result+" "+seconds+" Seconds";

		return result;
	}


}
