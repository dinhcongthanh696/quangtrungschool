/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ClassRoom;
import Model.Department;
import Model.Teacher;
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
public class DepartmentDAO extends AbstractDepartmentDAO {

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department left join [class] as c on department.department_code = c.department_code "
                + "left join teacher on department.dean = teacher.teacher_code ";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            ResultSet rs = prepare_stmt.executeQuery();
            Department department = new Department();
            department.setDepartmentCode("");
            Teacher teacher;
            ClassRoom classroom;
            while (rs.next()) {
                if ( ! rs.getString("department_code").equals(department.getDepartmentCode())) {
                    department = new Department();
                    department.setClassrooms(new ArrayList<>());
                    teacher = new Teacher();
                    teacher.setTeacherCode(rs.getString("dean"));
                    teacher.setFullname(rs.getString("teacher_fullname"));
                    department.setDepartmentCode(rs.getString("department_code"));
                    department.setDepartmentName(rs.getString("department_name"));
                    department.setDean(teacher);
                    departments.add(department);
                }
                if(rs.getString("class_code") != null){
                    classroom = new ClassRoom();
                    classroom.setClassCode(rs.getString("class_code"));
                    department.getClassrooms().add(classroom);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return departments;
    }

    @Override
    public void update(Department department) {
        String sql = "UPDATE department SET dean = ? WHERE department_code = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, department.getDean().getTeacherCode());
            prepare_stmt.setString(2, department.getDepartmentCode());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
