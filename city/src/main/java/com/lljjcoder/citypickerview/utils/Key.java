package com.lljjcoder.citypickerview.utils;

import com.lljjcoder.citypickerview.model.DbInfo;

import java.util.List;


public class Key {
   private int key;
   private int oneId;
   private List<DbInfo> list;
   private int type;

    public int getOneId() {
        return oneId;
    }

    public void setOneId(int oneId) {
        this.oneId = oneId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<DbInfo> getList() {
        return list;
    }

    public void setList(List<DbInfo> list) {
        this.list = list;
    }
}
