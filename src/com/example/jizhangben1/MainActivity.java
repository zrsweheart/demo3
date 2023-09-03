package com.example.jizhangben1;

 
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
 
public class MainActivity extends Activity implements OnClickListener{
	// String etnum,etdata,etfenlei;
	 private EditText et_num,et_data;
	 private Spinner et_fenlei;
	 private Button iv_add,iv_help;
	 private ListView lv;
	 private List<Zhangdan> zdList;
	 private MyAdapter myAdapter;
	 private DBHelper dbHelper;

	SQLiteDatabase db;
	
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    lv = (ListView) findViewById(R.id.main_lv);
	    iv_add = (Button) findViewById(R.id.btn_add);
	    iv_help=(Button) findViewById(R.id.btn_help);
	  
	    if (zdList != null) {
            zdList.clear();
        }
        dbHelper = new DBHelper(MainActivity.this, "zhangdan.db", null, 2);
        updateListView();
        iv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               help();
            }
        });
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
		
		  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                updateData(position);
	           }
	        });
	        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	            @Override
	            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
	               deleteData(position);
	                return true;
	            }
	        });

	}
	
	
 

	

	  private void help() {
	    	 // rg=(RadioGroup)findViewById(R.id.radioGroup1);
	  	 //   r0=(RadioButton)findViewById(R.id.radio0);
	  	//	r1=(RadioButton)findViewById(R.id.radio1);
	        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
	        final View helpView=View.inflate(MainActivity.this,R.layout.help,null);
	        builder.setIcon(R.drawable.icon)
	                .setTitle("帮助")
	                .setView(helpView);
	        builder
	       
	                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.dismiss();
	                    }
	                });
	        AlertDialog alertDialog=builder.create();
	        alertDialog.show();
	    }
	
	
    private void addData() {
    	 // rg=(RadioGroup)findViewById(R.id.radioGroup1);
  	 //   r0=(RadioButton)findViewById(R.id.radio0);
  	//	r1=(RadioButton)findViewById(R.id.radio1);
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        final View dialogView=View.inflate(MainActivity.this,R.layout.dialog,null);
        builder.setIcon(R.drawable.icon)
                .setTitle("添加账单")
                .setView(dialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_num=(EditText) dialogView.findViewById(R.id.et_num);
                et_fenlei=(Spinner) dialogView.findViewById(R.id.et_fenlei);
                et_data=(EditText) dialogView.findViewById(R.id.et_data);
                String num=et_num.getText().toString();
               
                String fenlei=(String) et_fenlei.getSelectedItem();;
                String data=et_data.getText().toString();
                if("收入".equals(fenlei)){
                	num="+"+num;
                }
                else{
                num="-"+num;}
                    Zhangdan zd=dbHelper.get(data);
                  
                        if (dbHelper.insert(fenlei,num,data)){
                            showToast("添加成功");
                            updateListView();
                        }else{
                            showToast("添加失败");
                        }
                    }
            }
        )
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

	 private void deleteData(final int position) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	        builder.setIcon(R.drawable.icon)
	                .setTitle("提示")
	                .setMessage("是否删除该账单？")
	                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        Zhangdan zd = (Zhangdan) myAdapter.getItem(position);
	                        String deleteId = zd.getId();
	                        System.out.println(deleteId);
	                        if (dbHelper.delete(deleteId)) {
	                            updateListView();
	                            showToast("删除成功");
	                        } else {
	                            showToast("删除失败");
	                        }
	                    }
	                })
	                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.dismiss();
	                    }
	                });
	        AlertDialog alertDialog = builder.create();
	        alertDialog.show();
	    }

	  public void showToast(String msg){
	        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
	    }

		private void updateData(int position) {
	        View dialogView = View.inflate(MainActivity.this, R.layout.dialog, null);
	        Zhangdan zd = (Zhangdan) myAdapter.getItem(position);
	        et_num = (EditText) dialogView.findViewById(R.id.et_num);
	        et_data = (EditText) dialogView.findViewById(R.id.et_data);
	        et_fenlei = (Spinner) dialogView.findViewById(R.id.et_fenlei);
	       // System.out.println(etnum);
	        String fenlei=et_fenlei.getContext().toString();
	        et_num.setText(zd.getNum());
	        et_data.setText(zd.getData());
	       // et_fenlei.setText(zd.getFenlei()); 
	        System.out.println(et_num+" "+ et_data+" "+et_fenlei);
	        final String findId = zd.getId();
	        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	        builder.setIcon(R.drawable.icon)
	                .setTitle("修改账单")
	                .setView(dialogView)
	                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	String fenlei = (String) et_fenlei.getSelectedItem();;
	                        String num = et_num.getText().toString();
	                        String data = et_data.getText().toString();
	                      
	                            Zhangdan zd=dbHelper.get(data);
	                          
	                                if (dbHelper.update(findId,fenlei,num,data)){
	                                    showToast("修改成功");
	                                    updateListView();
	                                }else{
	                                    showToast("修改失败");
	                                }
	                            }
	                  
	                })
	                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.dismiss();
	                    }
	                });
	        AlertDialog alertDialog = builder.create();
	        alertDialog.show();
	    }
 

    
    public void updateListView(){
    	 zdList=dbHelper.query();
         myAdapter=new MyAdapter(zdList,MainActivity.this);
         lv.setAdapter(myAdapter);
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
 
}