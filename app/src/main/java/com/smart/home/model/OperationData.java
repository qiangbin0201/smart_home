package com.smart.home.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lenovo on 2017/6/7.
 */
@Entity
public class OperationData {
    @Id(autoincrement = true)
    private Long id;

    private String operationTime;

    private String operationEquip;

    private String operationType;

    public String getOperationType() {
        return this.operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationEquip() {
        return this.operationEquip;
    }

    public void setOperationEquip(String operationEquip) {
        this.operationEquip = operationEquip;
    }

    public String getOperationTime() {
        return this.operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 806028963)
    public OperationData(Long id, String operationTime, String operationEquip,
            String operationType) {
        this.id = id;
        this.operationTime = operationTime;
        this.operationEquip = operationEquip;
        this.operationType = operationType;
    }

    @Generated(hash = 1545447959)
    public OperationData() {
    }
}
