package com.honeste.honest_e;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class commonComment extends AppCompatActivity {

    Bitmap bitmap1;
    String name,content,time;

    public int getRid_user() {
        return rid_user;
    }

    public void setRid_user(int rid_user) {
        this.rid_user = rid_user;
    }

    int rid_user;

    public int getComplaintid() {
        return complaintid;
    }

    public void setComplaintid(int complaintid) {
        this.complaintid = complaintid;
    }

    int complaintid;

    public int getRid_comment() {
        return rid_comment;
    }

    public void setRid_comment(int rid_comment) {
        this.rid_comment = rid_comment;
    }

    int rid_comment;

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    int commentid;

    public Bitmap getBitmap1() {
        return bitmap1;
    }

    public void setBitmap1(Bitmap bitmap1) {
        this.bitmap1 = bitmap1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
