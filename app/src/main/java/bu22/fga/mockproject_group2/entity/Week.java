package bu22.fga.mockproject_group2.entity;

import java.util.ArrayList;

/**
 * Created by vuson on 4/17/2018.
 */



public class Week {

    private  int id_Week;
    private String startDay;
    private String endDay;
    private ArrayList<DayOfWeek> arrayList;

    public Week(int id_Week, String startDay, String endDay, ArrayList<DayOfWeek> arrayList) {
        this.id_Week = id_Week;
        this.startDay = startDay;
        this.endDay = endDay;
        this.arrayList = arrayList;
    }

    public Week(String startDay, String endDay) {
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public Week(String startDay, String endDay, ArrayList<DayOfWeek> arrayList) {
        this.id_Week = id_Week;
        this.startDay = startDay;
        this.endDay = endDay;
        this.arrayList = arrayList;
    }

    public Week() {

    }
    public int getId_Week() {
        return id_Week;
    }

    public void setId_Week(int id_Week) {
        this.id_Week = id_Week;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
    public ArrayList<DayOfWeek> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<DayOfWeek> arrayList) {
        this.arrayList = arrayList;
    }
}

