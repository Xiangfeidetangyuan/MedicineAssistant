package org.unboxing.medicineassistant.entity;

import java.io.Serializable;
import java.util.Date;

public class Inform implements Serializable {
    private int status;//运行状态，0为不启动，1为启动
    private  long informid;//提醒ID
    private String username;//用户名

    public Inform(int status, long informid, String username, String content, int hour, int minute, String title, long begindate, long enddate) {
        this.status = status;
        this.informid = informid;
        this.username = username;
        this.content = content;
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.begindate = begindate;
        this.enddate = enddate;
    }

    private String content;//提醒内容
    private  int hour;//小时
    private  int minute;//分钟
    private String title;//标题
    private long begindate;//开始日期



    public long getBegindate() {
        return begindate;
    }

    public void setBegindate(long begindate) {
        this.begindate = begindate;
    }

    public long getEnddate() {
        return enddate;
    }

    public void setEnddate(long enddate) {
        this.enddate = enddate;
    }

    private long enddate;//结束日期

//    public Inform(int status, long informid, String username, String content, int hour, int minute, String title) {
//        this.status = status;
//        this.informid = informid;
//        this.username = username;
//        this.content = content;
//        this.hour = hour;
//        this.minute = minute;
//        this.title = title;
//    }





    public Inform(){}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public long getInformid() {
        return informid;
    }

    public void setInformid(long informid) {
        this.informid = informid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


}
