package org.unboxing.medicineassistant.entity;

import java.io.Serializable;

public class medicine implements Serializable {


    public medicine(String name,  String description,int dose, int repeation) {
        this.name = name;
        this.dose = dose;
        this.description = description;
        this.repeation = repeation;
    }
    private String name;//药品名



    private int dose; //用量 1天几次
    private String description;//疗效，一次几片

    private int repeation;//重复的天数

  public  medicine(){};//无参构造函数



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRepeation() {
        return repeation;
    }

    public void setRepeation(int repeation) {
        this.repeation = repeation;
    }



//把类的所有信息打印出来
@Override
public String toString() {
    return "name='" + name + '\'' +
            ", dose=" + dose +
            ", description='" + description + '\'' +
            ", repeation=" + repeation +
            '}';
}

}
