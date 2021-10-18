/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Course;
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
public class CourseDAO extends AbstractCourseDAO {

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            ResultSet rs = prepare_stmt.executeQuery();
            Course course;
            while (rs.next()) {
                course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                courses.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    @Override
    public List<Course> getUntakenCourse(String classCode, int year, int semester) {
        List<Course> courses = new ArrayList<>();
        String sql = "select * from course as c \n"
                + "WHERE c.course_code NOT IN\n"
                + "(\n"
                + "	SELECT clc.course_code\n"
                + "	FROM classcourse as clc\n"
                + "	WHERE clc.class_code = ? AND clc.year = ? AND clc.semester = ?\n"
                + ")";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classCode);
            prepare_stmt.setInt(2, year);
            prepare_stmt.setInt(3, semester);
            ResultSet rs = prepare_stmt.executeQuery();
            Course course;
            while (rs.next()) {
                course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                courses.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

}
