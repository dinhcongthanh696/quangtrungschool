/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ClassRoom;
import Model.Learning;
import Model.Student;
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
public class LearningDAO extends AbstractLearningDAO {

    @Override
    public void save(Learning learning) {
        String sql = "INSERT INTO learning VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, learning.getStudent().getStudentCode());
            prepare_stmt.setString(2, learning.getClassroom().getClassCode());
            prepare_stmt.setInt(3, learning.getYear());
            prepare_stmt.setDate(4, learning.getStartDate());
            prepare_stmt.setInt(5, learning.getSemester());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LearningDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Learning getStudentLearning(String studentCode, String classCode, int year, int semester) {
        String sql = "SELECT * FROM learning WHERE student_code = ? AND class_code = ? AND year = ? AND semester = ?";
        try {
            Learning learning;
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, studentCode);
            prepare_stmt.setString(2, classCode);
            prepare_stmt.setInt(3, year);
            prepare_stmt.setInt(4, semester);
            ResultSet rs = prepare_stmt.executeQuery();
            ClassRoom classroom;
            Student student;
            while (rs.next()) {
                learning = new Learning();
                classroom = new ClassRoom();
                student = new Student();
                classroom.setClassCode(rs.getString("class_code"));
                // set more attributes for class
                student.setStudentCode(rs.getString("student_code"));
                // set more attributes for student
                learning.setClassroom(classroom);
                learning.setStudent(student);
                learning.setYear(rs.getInt("year"));
                learning.setStartDate(rs.getDate("started_date"));
                learning.setYear(rs.getInt("semester"));
                return learning;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearningDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Learning> getAll() {
        String sql = "SELECT * FROM learning";
        List<Learning> learnings = new ArrayList<>();
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            ResultSet rs = prepare_stmt.executeQuery();
            Student student;
            ClassRoom classroom;
            Learning learning;
            while (rs.next()) {
                student = new Student();
                student.setStudentCode(rs.getString("student_code"));
                classroom = new ClassRoom();
                classroom.setClassCode(rs.getString("class_code"));
                learning = new Learning();
                learning.setStudent(student);
                learning.setClassroom(classroom);
                learning.setStartDate(rs.getDate("started_date"));
                learning.setYear(rs.getInt("year"));
                learning.setSemester(rs.getInt("semester"));
                learnings.add(learning);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearningDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return learnings;
    }


    @Override
    public void delete(String studentCode, String classCode, int year, int semester) {
        String sql = "DELETE FROM learning WHERE student_code = ? AND class_code = ? AND year = ? AND semester = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, studentCode);
            prepare_stmt.setString(2, classCode);
            prepare_stmt.setInt(3, year);
            prepare_stmt.setInt(4, semester);
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LearningDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
