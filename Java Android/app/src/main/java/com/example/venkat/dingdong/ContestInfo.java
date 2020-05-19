package com.example.venkat.dingdong;

/**
 * Created by venkat on 24/4/18.
 */

public class ContestInfo {

    String ContestName,StartDate,EndDate;

    public ContestInfo(String contestName, String startDate, String endDate) {
        ContestName = contestName;
        StartDate = startDate;
        EndDate = endDate;
    }

    public String getContestName() {
        return ContestName;
    }

    public void setContestName(String contestName) {
        ContestName = contestName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
