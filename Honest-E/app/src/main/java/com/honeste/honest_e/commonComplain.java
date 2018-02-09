package com.honeste.honest_e;

import android.graphics.Bitmap;

/**
 * Created by abhis on 10-Mar-17.
 */

public class commonComplain {

    Bitmap bitmapComp, bitmapProfile;

    public String getCompimgpath() {
        return compimgpath;
    }

    public void setCompimgpath(String compimgpath) {
        this.compimgpath = compimgpath;
    }

    String compimgpath;
    String name;
    String category;

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    String subcategory;
    String area;
    String hours;
    String desc;
    int like;

    int compid;
    int complaint_rid;

    public int getLogin_rid() {
        return login_rid;
    }

    public void setLogin_rid(int login_rid) {
        this.login_rid = login_rid;
    }

    int login_rid;

    public int getComplaint_rid() {
        return complaint_rid;
    }

    public void setComplaint_rid(int complaint_rid) {
        this.complaint_rid = complaint_rid;
    }


    public int getCompid() {
        return compid;
    }

    public void setCompid(int compid) {
        this.compid = compid;
    }



    public Bitmap getBitmapComp() {
        return bitmapComp;
    }

    public void setBitmapComp(Bitmap bitmapComp) {
        this.bitmapComp = bitmapComp;
    }

    public Bitmap getBitmapProfile() {
        return bitmapProfile;
    }

    public void setBitmapProfile(Bitmap bitmapProfile) {
        this.bitmapProfile = bitmapProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }


}
