package com.example.cwjwj.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.cwjwj.myapplication.R;
import com.example.cwjwj.myapplication.entity.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressAdapter extends ArrayAdapter<Address>{
    private int resourceId;
    private List<Address> addressList;

    public AddressAdapter(Context context, int textResourceId, List<Address> objects){
        super(context,textResourceId,objects);
        resourceId=textResourceId;
        addressList=objects;


    }


  


    @Override
    public int getCount() {
        return addressList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Address address=getItem(position);
        View view;
        addressViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new addressViewHolder();
            viewHolder.addressImg=view.findViewById(R.id.address_img);
            viewHolder.emailAdress=view.findViewById(R.id.free_address);
            viewHolder.displayName=view.findViewById(R.id.display_name);
            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(addressViewHolder) view.getTag();
        }
        viewHolder.checkButton=view.findViewById(R.id.select_address);
        viewHolder.addressImg.setImageResource(R.drawable.address);
        viewHolder.emailAdress.setText(address.getEmail());
        viewHolder.displayName.setText(address.getDisplay_name());

        return view;
    }
    class addressViewHolder{
        ImageView addressImg;
        TextView emailAdress;
        TextView displayName;
        CheckBox checkButton;
    }
}
