package com.example.murali.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.BackingStoreException;

/**
 * Created by murali on 11/20/2016.
 */
public class MyAdapter extends BaseAdapter {

    ArrayList<HashMap<String,String>> dataSource = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater = null;

    public MyAdapter(Context context, ArrayList<HashMap<String,String>> dataSource){
        this.context=context;
        this.dataSource=dataSource;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        MyHolder holder;
        if(row==null) {

            row = layoutInflater.inflate(R.layout.row_item, viewGroup, false);
            holder = new MyHolder(row);
            row.setTag(holder);

        }else{

            holder= (MyHolder) row.getTag();
        }

        HashMap<String,String> currentMap = dataSource.get(i);
        holder.title.setText(currentMap.get("title"));
        holder.date.setText(currentMap.get("pubDate"));
        //holder.imageView.setImageURI(Uri.parse(currentMap.get("imageURL")));
        Glide.with(context).load(Uri.parse(currentMap.get("imageURL"))).override(600,400).centerCrop().into(holder.imageView);
        holder.description.setText(currentMap.get("description"));
        return row;
    }





}

class MyHolder{

    TextView title;
    TextView date;
    ImageView imageView;
    TextView description;

    public MyHolder(View view){

        title= (TextView) view.findViewById(R.id.title_row);
        date= (TextView) view.findViewById(R.id.date);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        description= (TextView) view.findViewById(R.id.description);
    }

}
