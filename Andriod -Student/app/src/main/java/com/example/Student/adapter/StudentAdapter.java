package com.example.Student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Student.bean.StudentBean;
import com.example.Student.R;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private List<StudentBean> list;

    public void setList(List<StudentBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public StudentAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 :list.size();
    }

    @Override
    public StudentBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.student_item_layout,null);
            viewHolder  = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();
        }
        StudentBean bookInfo = getItem(i);
        viewHolder.tvBookName.setText(bookInfo.getName());
        viewHolder.tvBookAuthor.setText(bookInfo.getAge());
        return view;
    }
    class ViewHolder{
        TextView tvBookName;
        TextView tvBookAuthor;
        public ViewHolder(View view){

            tvBookName=(TextView) view.findViewById(R.id.item_name);
            tvBookAuthor=(TextView) view.findViewById(R.id.item_age);
        }
    }
}
