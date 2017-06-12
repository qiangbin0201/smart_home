package com.smart.home.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.DaoSession;
import com.afa.tourism.greendao.gen.OperationDataDao;
import com.smart.home.model.OperationData;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/6/7.
 */

public class OperationDataPresenter {

    public OperationDataDao mOperationDataDao;

    public static OperationDataPresenter mOperationDataPresenter;

    public static OperationDataPresenter getInstance(){
        if( mOperationDataPresenter == null)
            mOperationDataPresenter = new OperationDataPresenter();
        return mOperationDataPresenter;
    }

    //初始化数据库
    public void initDbHelp(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "operation-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        mOperationDataDao = daoSession.getOperationDataDao();
    }

    //根据操作的对象设备名称查询
    public List<OperationData> queryUserList(String equipName) {

        QueryBuilder<OperationData> qb = mOperationDataDao.queryBuilder();
        ArrayList<OperationData> list = (ArrayList<OperationData>) qb.where(OperationDataDao.Properties.OperationEquip.eq(equipName)).list();
        return list;
    }

    //根据操作时间查询
    public List<OperationData> queryEquipList(String equipPosition) {

        QueryBuilder<OperationData> qb = mOperationDataDao.queryBuilder();
        ArrayList<OperationData> list = (ArrayList<OperationData>) qb.where(OperationDataDao.Properties.OperationTime.eq(equipPosition)).list();
        return list;
    }


    //查询数据库中所有数据
    public List<OperationData> queryAllData(){
        List<OperationData> list = mOperationDataDao.loadAll();
        return list;
    }

    public void deleteDataByOperationData(OperationData OperationData){
        mOperationDataDao.delete(OperationData);
    }

    //根据id删除数据库中的信息
    public void deleteData(Long id){

        mOperationDataDao.deleteByKey(id);
    }

    public void deleteByEquipName(String equipPosition){
        OperationData findUser = mOperationDataDao.queryBuilder().where(OperationDataDao.Properties.OperationTime.eq("equipPosition")).build().unique();
        Long qw = findUser.getId();
        deleteData(findUser.getId());
    }

    //删除数据库中的所有信息
    public void deleteAllData(){
        mOperationDataDao.deleteAll();
    }

    //根据operation对象删除数据库中的信息
    public void deleteByOperationData(OperationData OperationData){
        mOperationDataDao.delete(OperationData);
    }

    //向数据库插入信息
    public void insertData(String operationTime, String operationEquip, String operationType){
        OperationData insertData = new OperationData(null, operationTime, operationEquip, operationType);
        mOperationDataDao.insert(insertData);
    }
}
