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
public class ClassRoom {
    private String classCode;
    private Department department;

    public ClassRoom(String classCode, Department department) {
        this.classCode = classCode;
        this.department = department;
    }

    public ClassRoom(String classCode) {
        this.classCode = classCode;
    }
    

    public ClassRoom() {
    }
    
    
    

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    
}
