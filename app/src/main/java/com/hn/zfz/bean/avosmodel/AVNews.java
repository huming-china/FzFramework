package com.hn.zfz.bean.avosmodel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.hn.zfz.ui.widget.quickadapter.ISelectable;

import java.io.Serializable;

/**
 * Created by huming on 2015/12/22.
 */
@AVClassName("News")
public class AVNews extends AVObject implements ISelectable,Serializable{
    public String getContent() {
        return getString("content");
    }
    public void setContent(String value) {
        put("content", value);
    }
    public String getFenlei1id() {
        return getString("fenlei1id");
    }
    public void setFenlei1id(String value) {
        put("fenlei1id", value);
    }
    public String getFenlei2id() {
        return getString("fenlei2id");
    }
    public void setFenlei2id(String value) {
        put("fenlei2id", value);
    }
    public String getTime() {
        return getString("time");
    }
    public void setTime(String value) {
        put("time", value);
    }
    public String getUsername() {
        return getString("username");
    }
    public void setUsername(String value) {
        put("username", value);
    }
    public String getTitle() {
        return getString("title");
    }
    public void setTitle(String value) {
        put("title", value);
    }
    public String getAuthor() {
        return getString("author");
    }
    public void setAuthor(String value) {
        put("author", value);
    }
    public String getSubtitle() {
        return getString("subtitle");
    }
    public void setSubtitle(String value) {
        put("subtitle", value);
    }
    public void setImagefile(AVFile value) {
        put("imagefile", value);
    }
    public AVFile getImagefile() {
        return getAVFile("imagefile");
    }

    @Override
    public void setSelected(boolean selected) {

    }

    @Override
    public boolean isSelected() {
        return false;
    }
}
