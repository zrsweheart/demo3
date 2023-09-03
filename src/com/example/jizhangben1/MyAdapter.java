package com.example.jizhangben1;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    //ʹ��list<Nate>,list��洢���ݿ���note�����м�¼��
    private List<Zhangdan> list;
    //���ڽ�ĳ������ת��Ϊview�Ķ���
    private LayoutInflater layoutInflater;
    //������MyAdapter�����ʱ����Ҫlist������
    public MyAdapter(List<Zhangdan> list, Context context){
        this.list=list;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.zd_item,null,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //�����ݿ��е����ݼ��ص���Ӧ�Ŀؼ���
        Zhangdan zd =(Zhangdan) getItem(position);
        viewHolder.kongge1.setText(zd.getKongge1());
        viewHolder.tv_fenlei.setText(zd.getFenlei());
        viewHolder.kongge2.setText(zd.getKongge2());
        viewHolder.tv_num.setText(zd.getNum());
        viewHolder.kongge3.setText(zd.getKongge3());
        viewHolder.tv_data.setText(zd.getData());
        return convertView;
    }
    //���ڸ�item����ͼ�����������ݡ�
    class ViewHolder{
    	private TextView kongge1,kongge2,kongge3;
    	private TextView tv_num;
		private TextView tv_fenlei;
		private TextView tv_data;
        public ViewHolder(View view){
        	kongge1=(TextView) view.findViewById(R.id.kongge1);
            tv_fenlei=(TextView) view.findViewById(R.id.zd_fenlei);
            kongge2=(TextView) view.findViewById(R.id.kongge2);
            tv_num=(TextView) view.findViewById(R.id.zd_num);
            kongge3=(TextView) view.findViewById(R.id.kongge3);
            tv_data=(TextView) view.findViewById(R.id.zd_data);
        }
    }
}

