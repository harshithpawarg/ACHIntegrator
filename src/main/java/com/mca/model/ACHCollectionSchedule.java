package com.mca.model;

import java.util.Date;

public class ACHCollectionSchedule {

    private Date startDate;

    private Date endDate;

    private Date pauseDate;

    private Date resumeDate;

    private Date currentDate;

    private Date lastDebitInstructionDate;

    public ACHCollectionSchedule() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getPauseDate() {
        return pauseDate;
    }

    public void setPauseDate(Date pauseDate) {
        this.pauseDate = pauseDate;
    }

    public Date getResumeDate() {
        return resumeDate;
    }

    public void setResumeDate(Date resumeDate) {
        this.resumeDate = resumeDate;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getLastDebitInstructionDate() {
        return lastDebitInstructionDate;
    }

    public void setLastDebitInstructionDate(Date lastDebitInstructionDate) {
        this.lastDebitInstructionDate = lastDebitInstructionDate;
    }

    @Override
    public String toString() {
        return "ACHCollectionSchedule{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", pauseDate=" + pauseDate +
                ", resumeDate=" + resumeDate +
                ", currentDate=" + currentDate +
                ", lastDebitInstructionDate=" + lastDebitInstructionDate +
                '}';
    }
}
