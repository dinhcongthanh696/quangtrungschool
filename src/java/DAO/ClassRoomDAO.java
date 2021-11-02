/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ClassRoom;
import Model.Department;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author My Computer
 */
public class ClassRoomDAO extends AbstractClassRoomDAO{

    @Override
    public List<ClassRoom> getALL() {
        List<ClassRoom> classes = new ArrayList<>();
        String sql = "SELECT * FROM class left join department on class.department_code = department.department_code";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            ResultSet rs = prepare_stmt.executeQuery();
            ClassRoom classroom;
            Department department;
            while(rs.next()){
                classroom = new ClassRoom();
                classroom.setClassCode(rs.getString("class_code"));
                department = new Department();
                department.setDepartmentCode(rs.getString("department_code"));
                department.setDepartmentName(rs.getString("department_name"));
                classroom.setDepartment(department);
                classes.add(classroom);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ClassRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }

    @Override
    public void insert(ClassRoom classroom) {
        String sql = "INSERT INTO class VALUES(?,?) ";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classroom.getClassCode());
            prepare_stmt.setString(2, classroom.getDepartment().getDepartmentCode());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
