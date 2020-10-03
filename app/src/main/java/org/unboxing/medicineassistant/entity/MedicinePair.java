package org.unboxing.medicineassistant.entity;

public class MedicinePair {
    private String MainMedicine;
    private String PairMedicine;
    private String level;

    public MedicinePair(String mainMedicine, String pairMedicine, String level) {
        MainMedicine = mainMedicine;
        PairMedicine = pairMedicine;
        this.level = level;
    }



    public String getMainMedicine() {
        return MainMedicine;
    }

    public void setMainMedicine(String mainMedicine) {
        MainMedicine = mainMedicine;
    }
    public String getPairMedicine() {
        return PairMedicine;
    }

    public void setPairMedicine(String pairMedicine) {
        PairMedicine = pairMedicine;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
