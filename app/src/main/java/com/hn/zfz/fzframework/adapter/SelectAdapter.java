package com.hn.zfz.fzframework.adapter;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hn.zfz.bean.FenLei;
import com.hn.zfz.fzframework.R;

import java.util.ArrayList;

/**
 * Created by huming on 2015/12/9.
 */
public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder>{
    private ArrayList<FenLei> fenLeis;
    private boolean isShowCheckBox;
    private ArrayMap<Integer, Boolean> arraySelected;


    public SelectAdapter(ArrayList<FenLei> fenLeis){
        this.fenLeis=fenLeis;
        initArrayMap();
    }
    public SelectAdapter(ArrayList<FenLei> fenLeis,boolean isShowCheckBox){
        this.fenLeis=fenLeis;
        this.isShowCheckBox=isShowCheckBox;
        initArrayMap();

    }
    private void initArrayMap(){
        if(fenLeis!=null){
            int size=fenLeis.size();
            arraySelected = new ArrayMap<Integer, Boolean>(size);
            for (int i=0;i<size;i++){
                arraySelected.put(i,false);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_select_recycler_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return  vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final FenLei fenlei=fenLeis.get(i);
        viewHolder.tvName.setText(fenlei.getName());
        if(isShowCheckBox){
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setChecked(arraySelected.get(i));
        }else{
            viewHolder.checkBox.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowCheckBox){
                    viewHolder.checkBox.setChecked(!arraySelected.get(i));
                    arraySelected.put(i,viewHolder.checkBox.isChecked());
                }
                if(listener!=null)
                    listener.ItemClickListener(v,fenlei);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fenLeis==null?0:fenLeis.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        CheckBox checkBox;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            tvName= (TextView) itemView.findViewById(R.id.tv_name);
            checkBox= (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        public void ItemClickListener(View v,FenLei fl);
    }
}
