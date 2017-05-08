package com.smart.home.presenter;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.DaoSession;
import com.afa.tourism.greendao.gen.EquipDataDao;
import com.smart.home.model.EquipData;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiangbin on 2017/4/23.
 */

public class EquipDataPresenter {

    public EquipDataDao mEquipDataDao;

    public static EquipDataPresenter mEquipDataPresenter;

    public static EquipDataPresenter getInstance(){
        if( mEquipDataPresenter == null)
            mEquipDataPresenter = new EquipDataPresenter();
        return mEquipDataPresenter;
    }

    //初始化数据库
    public void initDbHelp(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "equip-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        mEquipDataDao = daoSession.getEquipDataDao();
    }

    //根据设备名称查询
    public List<EquipData> queryUserList(String equipName) {

        QueryBuilder<EquipData> qb = mEquipDataDao.queryBuilder();
        ArrayList<EquipData> list = (ArrayList<EquipData>) qb.where(EquipDataDao.Properties.EquipName.eq(equipName)).list();
        return list;
    }

    //根据设备位置查询
    public List<EquipData> queryEquipList(String equipPosition) {

        QueryBuilder<EquipData> qb = mEquipDataDao.queryBuilder();
        ArrayList<EquipData> list = (ArrayList<EquipData>) qb.where(EquipDataDao.Properties.EquipPosition.eq(equipPosition)).list();
        return list;
    }


    //查询数据库中所有数据
    public List<EquipData> queryAllData(){
        List<EquipData> list = mEquipDataDao.loadAll();
        return list;
    }

    public void deleteDataByEquipData(EquipData equipData){
        mEquipDataDao.delete(equipData);
    }

    //根据id删除数据库中的信息
    public void deleteData(Long id){

        mEquipDataDao.deleteByKey(id);
    }

    public void deleteByEquipName(String equipPosition){
        EquipData findUser = mEquipDataDao.queryBuilder().where(EquipDataDao.Properties.EquipPosition.eq("equipPosition")).build().unique();
        Long qw = findUser.getId();
        deleteData(findUser.getId());
    }

    //删除数据库中的所有信息
    public void deleteAllData(){
        mEquipDataDao.deleteAll();
    }

    //根据equip对象删除数据库中的信息
    public void deleteByEquipData(EquipData equipData){
        mEquipDataDao.delete(equipData);
    }

    //向数据库插入信息
    public void insertData(String equipName, String equipPosition, String equipCode){
        EquipData insertData = new EquipData(null, equipName, equipPosition, equipCode);
        mEquipDataDao.insert(insertData);
    }
}
