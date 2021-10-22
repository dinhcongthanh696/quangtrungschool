/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.Teacher;
import java.sql.Date;
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
public abstract class AbstractTeacherDAO extends BaseDAO{
    public abstract List<Teacher> getAll();
    public abstract Teacher getById(String teacherCode);
    public abstract Teacher getByUsername(Account account);
    public abstract void getSchedules(Teacher teacher);
    public abstract void insert(Teacher teacher);
    public abstract List<Teacher> search(String query,int offset,int fetch);
    public abstract List<Teacher> getFreeTeacher(Date date,int slot);
    public abstract void getMainClasses(Teacher teacher);
    public abstract void delete(Teacher teacher);
    public abstract void update(Teacher teacher);
    
    protected List<Teacher> select(String sql, Object... parameters) {
        List<Teacher> teachers = new ArrayList<>();
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            Object param;
            for (int i = 0; i < parameters.length; i++) {
                param = parameters[i];
                if (param instanceof String) {
                    prepare_stmt.setString(i + 1, (String) param);
                } else if (param instanceof Date) {
                    prepare_stmt.setDate(i + 1, (Date) param);
                } else if(param instanceof Integer){
                    prepare_stmt.setInt(i + 1,(Integer) param);
                }
            }
            ResultSet rs = prepare_stmt.executeQuery();
            Teacher teacher;
            Account account;
            while (rs.next()) {
                teacher = new Teacher();
                teacher.setTeacherCode(rs.getString("teacher_code"));
                teacher.setFullname(rs.getString("teacher_fullname"));
                teacher.setDob(rs.getDate("teacher_dob"));
                teacher.setAddress(rs.getString("teacher_address"));
                teacher.setEmail(rs.getString("teacher_email"));
                teacher.setPhone(rs.getString("teacher_phone"));
                account = new Account();
                account.setUsername(rs.getString("username"));
                teacher.setAccount(account);
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractStudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }
    
   
}
