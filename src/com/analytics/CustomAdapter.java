package com.analytics;

import java.util.ArrayList;

import com.clicktheright.R;
import com.database.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{

	private ArrayList<Student> student;
	Context context;
	
	public CustomAdapter(ArrayList<Student> data1, Context context) {
		// TODO Auto-generated constructor stub
		student = data1;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return student.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return student.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Student st = student.get(position);
		View v = convertView;
		
		if(v == null){
			LayoutInflater inflator = LayoutInflater.from(parent.getContext());
			v = inflator.inflate(R.layout.list_item, parent, false);
		}           TextView name = (TextView)v.findViewById(R.id.name);
        TextView date = (TextView)v.findViewById(R.id.date);
        TextView level1 = (TextView)v.findViewById(R.id.level1);
        TextView level2 = (TextView)v.findViewById(R.id.level2);
        TextView level3 = (TextView)v.findViewById(R.id.level3);
        
        TextView level1status = (TextView)v.findViewById(R.id.level1status);
        TextView level2status = (TextView)v.findViewById(R.id.level2status);
        TextView level3status = (TextView)v.findViewById(R.id.level3status);
         
        TextView time1 = (TextView)v.findViewById(R.id.time1);
/*        TextView time2 = (TextView)v.findViewById(R.id.time2);
        TextView time3 = (TextView)v.findViewById(R.id.time3);
*/        
        ImageView delete=(ImageView)v.findViewById(R.id.delete);
        delete.setTag(st);

        //image.setImageResource(msg.icon);
        name.setText(st.getName().toUpperCase());
        date.setText(st.getDate()); 
        level1.setText(st.getLevel1());
        level2.setText(st.getLevel2());
        level3.setText(st.getLevel3());
        if(st.getLevel1()==null||st.getLevel1().equals(""))
     	   level1status.setText("Not Played");
        else
     	   level1status.setText("Played");
        
        if(st.getLevel2()==null||st.getLevel2().equals(""))
     	   level2status.setText("Not Played");
        else
     	   level2status.setText("Played");
        
        if(st.getLevel3()==null||st.getLevel3().equals(""))
     	   level3status.setText("Not Played");
        else
     	   level3status.setText("Played");
                              
        time1.setText(st.getTotalTime());
/*        time2.setText(st.getTime2());
        time3.setText(st.getTime3());
*/                     
     return v;
    }
 
 public void deleteRow(Student st) 
	{
		if(this.student.contains(st)) 
		{
			this.student.remove(st);
		}
	}

}
