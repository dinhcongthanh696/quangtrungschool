/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author My Computer
 */
public class Week {
    private int weekNumber;
    private String weekName;
    private ArrayList<Object[]> days = new ArrayList<>();
    private int totalDays;

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public ArrayList<Object[]> getDays() {
        return days;
    }

    public void setDays(ArrayList<Object[]> days) {
        this.days = days;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }
    
    
    
    
}
