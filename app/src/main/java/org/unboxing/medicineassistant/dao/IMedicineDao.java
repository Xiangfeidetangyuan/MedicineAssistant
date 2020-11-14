package org.unboxing.medicineassistant.dao;


import org.unboxing.medicineassistant.entity.medicine;

import java.util.List;

/**
 * 数据库接口层
 */
public abstract class IMedicineDao {


    /**
     * 添加药品
     *
     * @param drug
     * @return
     */
    abstract long addMedicine(medicine drug);


    /**
     * 删除药品
     *
     * @param name
     * @return
     */
    abstract int delMedicineByName(String name);


    /**
     * 查询数据
     *
     * @param name
     * @return
     */
    abstract List<medicine>  getUserByName(String name);

    /**
     * 列出数据
     *
     * @return
     */
    abstract List<medicine> listMedicine();
}


