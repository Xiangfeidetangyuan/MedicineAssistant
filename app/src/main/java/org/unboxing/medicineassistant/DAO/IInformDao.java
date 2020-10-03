package org.unboxing.medicineassistant.DAO;


import org.unboxing.medicineassistant.entity.Inform;

import java.util.List;

public abstract class IInformDao {
    /**
     * 增加提醒
     * @param inform
     * @return
     */
    abstract long addInform(Inform inform);

    /**
     * 删除提醒
     *
     * @param id
     * @return
     */
    abstract int delInformById(long id);

  abstract void updateInform(Inform inform);


    /**
     * 列出提醒
     *
     * @return
     */
    abstract List<Inform> listInform();

    /**
     *设置提醒状态
     * @return
     */

    abstract int setInfromStatus(long id,int status);

}
