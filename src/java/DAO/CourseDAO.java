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
                course.setType(rs.getInt("type"));
                courses.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    @Override
    public void insert(Course course) {
        String sql = "INSERT INTO course VALUES(?,?,?)";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, course.getCourseCode());
            prepare_stmt.setString(2, course.getCourseName());
            prepare_stmt.setInt(3, course.getType());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


}
