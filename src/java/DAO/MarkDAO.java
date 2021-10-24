/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Mark;
import Model.StudentCourse;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author My Computer
 */
public class MarkDAO extends AbstractMarkDAO {

    @Override
    public void insert(StudentCourse studentcourse,Mark mark) {
        String sql = "INSERT INTO [dbo].[mark]\n"
                + "           ([student_code]\n"
                + "           ,[exam_type]\n"
                + "           ,[mark]\n"
                + "           ,[class_code]\n"
                + "           ,[year]\n"
                + "           ,[semester]\n"
                + "           ,[course_code]) "
                + "  VALUES(?,?,?,?,?,?,?)  ";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, studentcourse.getStudent().getStudentCode());
            prepare_stmt.setInt(2, mark.getExam_type());
            prepare_stmt.setDouble(3, mark.getScore());
            prepare_stmt.setString(4, mark.getClassyearsemester().getClassroom().getClassCode());
            prepare_stmt.setInt(5, mark.getClassyearsemester().getYear());
            prepare_stmt.setInt(6, mark.getClassyearsemester().getSemester());
            prepare_stmt.setString(7, studentcourse.getCourse().getCourseCode());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Mark mark) {
        String sql = "UPDATE mark SET mark = ? WHERE no = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setDouble(1, mark.getScore());
            prepare_stmt.setInt(2, mark.getNo());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Mark mark) {
        String sql = "DELETE FROM mark WHERE no = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setInt(1, mark.getNo());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
