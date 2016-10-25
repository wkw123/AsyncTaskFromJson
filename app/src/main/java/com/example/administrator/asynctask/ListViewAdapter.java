package com.example.administrator.asynctask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<NewsBeans> newsBeansList;

    public ListViewAdapter(Context context, List<NewsBeans> newsBeansList) {
        this.context = context;
        this.newsBeansList = newsBeansList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsBeansList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsBeansList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        viewHolder mViewHolder;
        NewsBeans newsBeans = newsBeansList.get(position);
        if(view == null){
            mViewHolder = new viewHolder();
            view = inflater.inflate(R.layout.item, null);
            mViewHolder.imageView = (ImageView) view.findViewById(R.id.item_image);
            mViewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            mViewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (viewHolder) view.getTag();
        }
        mViewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        Log.i("image",newsBeans.getNewsIconUrl());
        new ImageLoad().showImageByThread(mViewHolder.imageView,newsBeans.getNewsIconUrl());
        mViewHolder.tv_title.setText(newsBeans.getNewsTitle());
        mViewHolder.tv_content.setText(newsBeans.getNewsContent());
        return view;
    }
    class viewHolder{
        public ImageView imageView;
        public TextView tv_title;
        public TextView tv_content;
    }
}

