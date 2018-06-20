package com.i_am_kern.Class;

import android.content.Intent;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class Myuser extends BmobUser {
    private String qq="未填写";
    private Integer touxiang=1;
    private Boolean IS_STUDENT=false;
    private String unit = "没有数据0_0!";
    private String nickname = "用户名";
    private Integer importantnum = 1;
    private BmobRelation friends;
    private BmobRelation blacklist;
    private String location="未填写所在地";
    private String birthday="未填写生日";
    private String hobbies="兴趣爱好未填写";
    private String backupsd;

    public String getBackupsd() {
        return backupsd;
    }

    public void setBackupsd(String backupsd) {
        this.backupsd = backupsd;
    }

    public Integer getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(Integer touxiang) {
        this.touxiang = touxiang;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String realname;
    private String sex;

    public BmobRelation getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(BmobRelation blacklist) {
        this.blacklist = blacklist;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public BmobRelation getFriends() {
        return friends;
    }

    public void setFriends(BmobRelation friends) {
        this.friends = friends;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Boolean getIS_STUDENT() {
        return IS_STUDENT;
    }

    public void setIS_STUDENT(Boolean IS_STUDENT) {
        this.IS_STUDENT = IS_STUDENT;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getImportantnum() {
        return importantnum;
    }

    public void setImportantnum(Integer importantnum) {
        this.importantnum = importantnum;
    }
}
