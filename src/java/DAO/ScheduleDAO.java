/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ClassRoom;
import Model.Course;
import Model.Schedule;
import Model.Teacher;
import Model.Week;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author My Computer
 */
public class ScheduleDAO extends AbstractScheduleDAO{

    @Override
    public List<Schedule> getSlotsOfClassInWeek(String classCode, Week week) {
        String sql = "SELECT * FROM schedule WHERE class_code = ? ";
        List<Date> days = new ArrayList<>();
        List<Schedule> schedules = new ArrayList<>();
        Date day;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for(Object[] params : week.getDays()){
            try {
                day = formatter.parse((String) params[0]);
                days.add(day);
            } catch (ParseException ex) {
                Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        sql += " AND date >= ? AND date <= ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classCode);
            prepare_stmt.setDate(2, new java.sql.Date(days.get(0).getTime()));
            prepare_stmt.setDate(3, new java.sql.Date(days.get(days.size() - 1).getTime()));
            ResultSet rs = prepare_stmt.executeQuery();
            Schedule schedule;
            ClassRoom classroom;
            Course course;
            Teacher teacher;
            while(rs.next()){
                schedule = new Schedule();
                classroom = new ClassRoom();
                classroom.setClassCode(rs.getString("class_code"));
                course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                teacher = new Teacher();
                teacher.setTeacherCode(rs.getString("teacher_code"));
                schedule.setClassroom(classroom);
                schedule.setCourse(course);
                schedule.setTeacher(teacher);
                schedule.setDate(rs.getDate("date"));
                schedule.setSemester(rs.getInt("semester"));
                schedule.setSlot(rs.getInt("slot"));
                schedule.setAttendance(rs.getString("attendance"));
                schedule.setActive(rs.getInt("active"));
                schedules.add(schedule);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return schedules;
    }

    @Override
    public void insert(Schedule schedule) {
        String sql = "INSERT INTO schedule (class_code,course_code,slot,[date],semester,teacher_code,attendance) "
                + " VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, schedule.getClassroom().getClassCode());
            prepare_stmt.setString(2, schedule.getCourse().getCourseCode());
            prepare_stmt.setInt(3, schedule.getSlot());
            prepare_stmt.setDate(4, schedule.getDate());
            prepare_stmt.setInt(5, schedule.getSemester());
            prepare_stmt.setString(6, schedule.getTeacher().getTeacherCode());
            prepare_stmt.setString(7, schedule.getAttendance());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Schedule schedule) {
        String sql = "DELETE FROM schedule WHERE class_code = ? "
                + "AND slot = ? AND date = ? ";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, schedule.getClassroom().getClassCode());
            prepare_stmt.setInt(2, schedule.getSlot());
            prepare_stmt.setDate(3, schedule.getDate());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Schedule schedule) {
        String sql = "UPDATE schedule SET teacher_code = ? WHERE "
                + "class_code = ? AND date = ? AND slot = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, schedule.getTeacher().getTeacherCode());
            prepare_stmt.setString(2, schedule.getClassroom().getClassCode());
            prepare_stmt.setDate(3, schedule.getDate());
            prepare_stmt.setInt(4, schedule.getSlot());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
