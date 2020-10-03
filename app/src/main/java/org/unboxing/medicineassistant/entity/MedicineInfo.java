package org.unboxing.medicineassistant.entity;

public class MedicineInfo {

    private int ID;
    private String Name;
    private String level4;
    private String level3;
    private String level2;
    private String level1;

    public MedicineInfo(String name, String level4, String level3, String level2, String level1) {
        Name = name;
        this.level4 = level4;
        this.level3 = level3;
        this.level2 = level2;
        this.level1 = level1;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLevel4() {
        return level4;
    }

    public void setLevel4(String level4) {
        this.level4 = level4;
    }

    public String getLevel3() {
        return level3;
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }
}
