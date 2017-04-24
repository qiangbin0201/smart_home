package com.smart.home.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.smart.home.R;
import com.smart.home.model.Equip;
import com.smart.home.model.EquipData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/23.
 */

public class EquipListAdapter extends BaseAdapter{

    private Context mContext;

    private List<Equip> mEquipList = new ArrayList<Equip>();

    private List<Equip> mSelectToDeleteList = new ArrayList<>();

    private boolean isManageStatus = false;

    public EquipListAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        if(mEquipList != null){
            return mEquipList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mEquipList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        EquipViewHolder mEquipViewHolder = null;
        if(convertView == null) {
            mEquipViewHolder = new EquipViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_equip_item, null);
            mEquipViewHolder.nomalView = convertView;
            mEquipViewHolder.mIvDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            mEquipViewHolder.mTvEquipName = (TextView) convertView.findViewById(R.id.tv_equip_name);
            mEquipViewHolder.mTvEquipPosition = (TextView) convertView.findViewById(R.id.tv_equip_position);

            convertView.setTag(mEquipViewHolder);

        }else{
            mEquipViewHolder = (EquipViewHolder) convertView.getTag();

        }
        mEquipViewHolder.mTvEquipName.setText(mEquipList.get(position).equipName);
        mEquipViewHolder.mTvEquipPosition.setText(mEquipList.get(position).equipPosition);


        final Equip model = (Equip) getItem(position);
        if(isManageStatus){
            mEquipViewHolder.mIvDelete.setVisibility(View.VISIBLE);
            if(model.isSelectDel){
                mEquipViewHolder.mIvDelete.setImageResource(R.drawable.checkbox_checked);
            }else {
                mEquipViewHolder.mIvDelete.setImageResource(R.drawable.checkbox_checked_disable);
            }
        }else {
            mEquipViewHolder.mIvDelete.setVisibility(View.GONE);
        }

        mEquipViewHolder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.isSelectDel = !model.isSelectDel;
                if(model.isSelectDel){
                    model.itemId = position + 1;
                    mSelectToDeleteList.add(model);
                }else {
                    mSelectToDeleteList.remove(model);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public List<Equip> getEquipList(){
        return mEquipList;
    }

    public List<Equip> getSelectToDeleteList(){
        return mSelectToDeleteList;
    }

    public void setIsManageStatus(boolean isManageStatus) {
        this.isManageStatus = isManageStatus;
        notifyDataSetChanged();
    }

    class EquipViewHolder{
        View nomalView;
        ImageView mIvDelete;

        TextView mTvEquipName;
        TextView mTvEquipPosition;


    }
}
