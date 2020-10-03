package org.unboxing.medicineassistant.DAO;


import org.unboxing.medicineassistant.entity.MedicinePair;

import java.util.List;

public interface MedicinePairDao {
    List<MedicinePair> findAllMainMedicine();
    List<MedicinePair> findByMainMedicine(String mainMedicine);
    void save(MedicinePair medicinePair);
    List<String> findAllName();
}
