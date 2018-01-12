package com.journaldev.searchview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    List<StoreModel> items;
    Context context;
    CallBackk callBackk;

    public ListAdapter(Context context, List<StoreModel> items){
        this.items=items;
        layoutInflater= LayoutInflater.from(context);
        this.context = context;
//        this.callBackk = callBackk;
    }

    @Override
    public int getCount() {
        if(items.size()!=0){
            return items.size();
        }
        return 0;
    }

    @Override
    public StoreModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
       int  link;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.row_item, parent, false);
        }
        callBackk = (CallBackk) context;
        TextView textTitle= (TextView) convertView.findViewById(R.id.tv_title);
        TextView textPrice= (TextView) convertView.findViewById(R.id.tv_price);
        TextView textType= (TextView) convertView.findViewById(R.id.tv_type);
        ImageView imageIcon=(ImageView)convertView.findViewById(R.id.img_icon);
        ImageView imageState=(ImageView)convertView.findViewById(R.id.img_stateDownload);
        StoreModel current=getItem(position);
        textTitle.setText(current.getTitle());
        textPrice.setText(current.getPrice());
        textType.setText(current.getType());

        textType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBackk.clickInItem(position);
            }
        });
//
        Glide.with(context)
                .load(current.getLinkIcon())
                .into(imageIcon);

        if(current.getStateDownload()==0){
           link = R.drawable.download32x32;
        }else {link = R.drawable.play32x32;}
        Glide.with(context)
                .load(link)
                .into(imageState);
        return convertView;
    }

}
