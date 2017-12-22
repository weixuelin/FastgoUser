package com.wt.fastgo_user.model;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class FlowTrace {
    /**
     * 描述
     */
    private String acceptStation;
    /**
     * 标题
     */
    private String title;
    /**
     * 标识
     */
    private String num;

    public FlowTrace() {
    }

    public FlowTrace(String num, String title, String acceptStation) {
        this.num = num;
        this.title = title;
        this.acceptStation = acceptStation;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }
}
