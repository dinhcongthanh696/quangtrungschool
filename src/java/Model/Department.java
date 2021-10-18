/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author My Computer
 */
public class Department {
    private int departmentCode;
    private String departmentName;
    private Teacher dean;

    public Department(int departmentCode, String departmentName, Teacher dean) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.dean = dean;
    }

    public Department() {
    }

    public int getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(int departmentCode) {
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
