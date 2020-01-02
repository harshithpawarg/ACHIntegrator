package com.mca.model;

public class ScheduleType {

    private String scheduleType;

    private String scheduleScript;


    public ScheduleType() {
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleScript() {
        return scheduleScript;
    }

    public void setScheduleScript(String scheduleScript) {
        this.scheduleScript = scheduleScript;
    }

    @Override
    public String toString() {
        return "ScheduleTypes{" +
                "scheduleType='" + scheduleType + '\'' +
                ", scheduleScript='" + scheduleScript + '\'' +
                '}';
    }
}
