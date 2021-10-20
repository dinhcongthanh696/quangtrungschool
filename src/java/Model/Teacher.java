/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author My Computer
 */
public class Teacher extends Person{
    private String teacherCode;
    private List<Schedule> schedules = new ArrayList<>();

    public Teacher(String teacherCode, String fullname, String address, Date dob, String email, String phone, Account account) {
        super(fullname, address, dob, email, phone, account);
        this.teacherCode = teacherCode;
    }

    public Teacher() {
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }
    
    
    
    
}
