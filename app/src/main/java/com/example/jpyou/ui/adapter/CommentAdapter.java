package com.example.jpyou.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jpyou.data.model.Comment;
import com.example.myapplication.R;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private Integer layout;
    private List<Comment> comments;


    public CommentAdapter(Context context, Integer layout, List<Comment> comments) {
        this.context = context;
        this.layout = layout;
        this.comments = comments;
    }


    private class ViewHolder{
        TextView txtName, txtComment;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Khai báo của hệ thống
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.txtName = view.findViewById(R.id.textViewName_RowComment);
            holder.txtComment = view.findViewById(R.id.textViewDescribe_RowComment);

            view.setTag(holder); //Truyền trạng thái ánh xạ
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Comment cmt = comments.get(i);
        holder.txtName.setText(cmt.getName());
        holder.txtComment.setText(cmt.getComment());
        return view;
    }
}
