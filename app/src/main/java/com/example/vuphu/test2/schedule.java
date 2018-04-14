package com.example.vuphu.test2;

import java.io.Serializable;

public class schedule implements Serializable {

    private String name;
    private String timeStart;
    private String timeStop;
    private int avatar;
    private int id;

    public schedule() {
    }

    public schedule(String name, String timeStart, String timeStop, int avatar, int id) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.avatar =avatar;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(String timeStop) {
        this.timeStop = timeStop;
    }
}
