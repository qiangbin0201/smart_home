package com.smart.home.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qiangbin on 2017/4/22.
 */
@Entity
public class EquipData {

    @Id(autoincrement = true)
    private Long id;

    private String equipName;

    private String equipPosition;

    private String equipCode;

    public String getEquipCode() {
        return this.equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getEquipPosition() {
        return this.equipPosition;
    }

    public void setEquipPosition(String equipPosition) {
        this.equipPosition = equipPosition;
    }

    public String getEquipName() {
        return this.equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1388817259)
    public EquipData(Long id, String equipName, String equipPosition,
            String equipCode) {
        this.id = id;
        this.equipName = equipName;
        this.equipPosition = equipPosition;
        this.equipCode = equipCode;
    }

    @Generated(hash = 1358072407)
    public EquipData() {
    }
}
