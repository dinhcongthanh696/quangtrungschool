/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;

/**
 *
 * @author My Computer
 */
public class Department {
    private String departmentCode;
    private String departmentName;
    private Teacher dean;
    private List<ClassRoom> classrooms;

    public Department(String departmentCode, String departmentName, Teacher dean) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.dean = dean;
    }

    public Department() {
    }

    public List<ClassRoom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<ClassRoom> classrooms) {
        this.classrooms = classrooms;
    }
    
    
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Teacher getDean() {
        return dean;
    }

    public void setDean(Teacher dean) {
        this.dean = dean;
    }
    
    
}
