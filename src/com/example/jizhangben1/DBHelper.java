package com.example.jizhangben1;

 


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
   

    ContentValues contentValues = new ContentValues();
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db=this.getWritableDatabase(); 
       // String dirPath="/data/data/"+"com.example.jizhangben1"+"/databases/";;
   // db=SQLiteDatabase.openOrCreateDatabase(dirPath+"data.db",null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table zhangdan(_id integer primary key autoincrement,kongge1 text,fenlei text,kongge2 text,num text,kongge3 text,data text)");
       
    }
    //添加数据
    public boolean insert(String fenlei,String num,String data) {
    
    	contentValues.put("kongge1", " ");
        contentValues.put("fenlei", fenlei);
        contentValues.put("kongge2", "          ");
        contentValues.put("num", num);
    	int mon=Integer.parseInt(num);
			if((mon>-10)){
        contentValues.put("kongge3","¥          ");
		}
			else if(mon<=-10&&mon>=-99){
				contentValues.put("kongge3","¥        ");
		}
			else if(mon<-99&&mon>=-999){
				contentValues.put("kongge3","¥      ");
		}
			else if(mon<-999&&mon>-9999){
				contentValues.put("kongge3","¥    ");
		}
			
			else if(mon<10&&mon>0){
				contentValues.put("kongge3","¥          ");
		}
		
		else if(mon>=10&&mon<=99){
			contentValues.put("kongge3","¥        ");
		}
		
		else if(mon>99&&mon<=999){
			contentValues.put("kongge3","¥      ");
	}
		
		else if(mon>999&&mon<9999){
			contentValues.put("kongge3","¥    ");
		}
        contentValues.put("data", data);
        long result = db.insert("zhangdan", null, contentValues);
        return result > 0 ? true : false;
    }
    public boolean delete(String id){
        int result=db.delete("zhangdan","_id=?",new String[]{id});
        return result>0?true:false;
    }
    //修改数据，根据id进行修改
    public boolean update(String id,String fenlei,String num,String data){
    	 contentValues.put("_id",id);
    	 contentValues.put("kongge1", " ");
        
         contentValues.put("kongge2", "          ");
	        contentValues.put("fenlei",fenlei);
	        contentValues.put("num",num);
	        int mon=Integer.parseInt(num);
			if((mon>-10)){
        contentValues.put("kongge3","¥          ");
		}
			else if(mon<=-10&&mon>=-99){
				contentValues.put("kongge3","¥        ");
		}
			else if(mon<-99&&mon>=-999){
				contentValues.put("kongge3","¥      ");
		}
			else if(mon<-999&&mon>-9999){
				contentValues.put("kongge3","¥    ");
		}
			
			else if(mon<10&&mon>0){
				contentValues.put("kongge3","¥          ");
		}
		
		else if(mon>=10&&mon<=99){
			contentValues.put("kongge3","¥        ");
		}
		
		else if(mon>99&&mon<=999){
			contentValues.put("kongge3","¥      ");
	}
		
		else if(mon>999&&mon<9999){
			contentValues.put("kongge3","¥    ");
		}
	        contentValues.put("data",data);
	        int result=db.update("zhangdan",contentValues,"_id=?",new String[]{id});
	        return result>0?true:false;
    }
    //查询数据,查询表中的所有内容，将查询的内容用note的对象属性进行存储，并将该对象存入集合中。
    //查询数据,查询表中的所有内容，将查询的内容用note的对象属性进行存储，并将该对象存入集合中。
    public List<Zhangdan> query(){//查询所有的信息
    	
    	
		List<Zhangdan> list = new ArrayList<Zhangdan>();
		list.clear();//清空数据
		//查询全部数据
	    Cursor cursor = db.rawQuery("select * from zhangdan" ,null);
	   
	    while(cursor.moveToNext()){
			
	      	int _id = cursor.getInt(cursor.getColumnIndex("_id"));
	      	String kongge1=cursor.getString(cursor.getColumnIndex("kongge1"));
	      	String fenlei = cursor.getString(cursor.getColumnIndex("fenlei"));//获取分类
	      	String kongge2=cursor.getString(cursor.getColumnIndex("kongge2"));
	      	String data = cursor.getString(cursor.getColumnIndex("data"));//获取日期
	      	String kongge3=cursor.getString(cursor.getColumnIndex("kongge3"));
			String num =  cursor.getString(cursor.getColumnIndex("num"));//获取金额
		String a=String.valueOf(_id)+kongge1+fenlei+kongge2+num+kongge3+data;
			System.out.print(a);
		
			Zhangdan zd = new Zhangdan(String.valueOf(_id),kongge1,fenlei,kongge2,num,kongge3,data);
			
			list.add(zd);//将新对象添加到list集合
			
		}
				
				
			
		
	    //System.out.println("数目："+list.size());
		// 最后会释放 资源 
		cursor.close();
		
		return list;//将所有信息返回
	}

    public Zhangdan get(String data) {
        Zhangdan zd=new Zhangdan();
        Cursor result=db.query("zhangdan",null,"data=?",new String[]{data},
                null,null,null,null);
        if (result.getCount()==1){
            result.moveToFirst();
            zd.setKongge1(result.getString(1));
            zd.setFenlei(result.getString(2));
            zd.setKongge2(result.getString(3));
            zd.setNum(result.getString(4));
            zd.setKongge3(result.getString(5));
            zd.setData(result.getString(6));
            return zd;
        }else {
        	zd.setKongge1(null);
        	zd.setKongge2(null);
        	zd.setKongge3(null);
            zd.setNum(null);
            zd.setFenlei(null);
            zd.setData(null);
            return zd;
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

	