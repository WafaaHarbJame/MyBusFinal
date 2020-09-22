package com.elaj.patient.Model;

import java.io.Serializable;

public class NavModel implements Serializable {

    private int icon;
    private String title;
    private String counter;
    private int type;

    private boolean isGroupHeader = false;

    public NavModel(String title) {
        this(0, title, null, 0);
        isGroupHeader = true;
    }

    public NavModel(int icon, String title, String counter, int type) {
        super();
        this.icon = icon;
        this.title = title;
        this.counter = counter;
        this.type = type;
    }

    public NavModel(int icon, String title) {
        super();
        this.icon = icon;
        this.title = title;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public boolean isGroupHeader() {
        return isGroupHeader;
    }

    public void setGroupHeader(boolean isGroupHeader) {
        this.isGroupHeader = isGroupHeader;
    }
}