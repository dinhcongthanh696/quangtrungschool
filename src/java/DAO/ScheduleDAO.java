/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ClassRoom;
import Model.Course;
import Model.Schedule;
import Model.Student;
import Model.StudentAttendance;
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
public class ScheduleDAO extends AbstractScheduleDAO {

    @Override
    public List<Schedule> getSchedulesOfClassInWeek(String classCode, Week week) {
        String sql = "SELECT * FROM schedule "
                + "left join course on schedule.course_code = course.course_code "
                + "WHERE class_code = ? ";
                
        List<Date> days = new ArrayList<>();
        List<Schedule> schedules = new ArrayList<>();
        Date day;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Object[] params : week.getDays()) {
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
            while (rs.next()) {
                schedule = new Schedule();
                classroom = new ClassRoom();
                classroom.setClassCode(rs.getString("class_code"));
                course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                course.setType(rs.getInt("type"));
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
    public void updateTeacherAndActive(Schedule schedule) {
        String sql = "UPDATE schedule SET teacher_code = ?,active = ? WHERE "
                + "class_code = ? AND date = ? AND slot = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, schedule.getTeacher().getTeacherCode());
            prepare_stmt.setInt(2, schedule.getActive());
            prepare_stmt.setString(3, schedule.getClassroom().getClassCode());
            prepare_stmt.setDate(4, schedule.getDate());
            prepare_stmt.setInt(5, schedule.getSlot());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void getAttendances(Schedule schedule) {
        String sql = "SELECT * FROM schedule as sche left join learning as l on sche.class_code = l.class_code\n"
                + "AND sche.semester = l.semester AND DATEPART(year,sche.date) = l.year LEFT JOIN student as s ON "
                + "l.student_code = s.student_code "
                + " WHERE sche.class_code = ? AND sche.date = ? AND sche.slot = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, schedule.getClassroom().getClassCode());
            prepare_stmt.setDate(2, schedule.getDate());
            prepare_stmt.setInt(3, schedule.getSlot());
            ResultSet rs = prepare_stmt.executeQuery();
            Student student;
            StudentAttendance studentAttendance;
            Course course;
            schedule.setStudentattendances(new ArrayList<>());
            while (rs.next()) {
                schedule.setActive(rs.getInt("active"));
                schedule.setAttendance(rs.getString("attendance"));
                student = new Student();
                student.setStudentCode(rs.getString("student_code"));
                student.setFullname(rs.getString("student_fullname"));
                studentAttendance = new StudentAttendance();
                studentAttendance.setStudent(student);
                studentAttendance.setSchedule(schedule);
                if (schedule.getAttendance() == null) {
                    studentAttendance.setStatus(0);   //REPERSENT FOR NOT YET
                } else if (schedule.getAttendance().contains(student.getStudentCode())) {
                    studentAttendance.setStatus(-1);  // ABSENT
                } else {
                    studentAttendance.setStatus(1);   // ATTENDED
                }
                schedule.getStudentattendances().add(studentAttendance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateAttendance(Schedule schedule) {
        String sql = "UPDATE schedule SET attendance = ? WHERE "
                + "class_code = ? AND date = ? AND slot = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, schedule.getAttendance());
            prepare_stmt.setString(2, schedule.getClassroom().getClassCode());
            prepare_stmt.setDate(3, schedule.getDate());
            prepare_stmt.setInt(4, schedule.getSlot());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Schedule getById(String classCode, int slot, java.sql.Date date) {
        String sql = "SELECT * FROM schedule inner join course on schedule.course_code = course.course_code "
                + "WHERE class_code = ? AND date = ? AND slot = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classCode);
            prepare_stmt.setInt(3, slot);
            prepare_stmt.setDate(2, date);
            ResultSet rs = prepare_stmt.executeQuery();
            if(rs.next()){
                Schedule schedule = new Schedule();
                ClassRoom classroom = new ClassRoom();
                Course course = new Course();
                course.setType(rs.getInt("type"));
                course.setCourseCode(rs.getString("course_code"));
                classroom.setClassCode(rs.getString("class_code"));
                Teacher teacher = new Teacher();
                teacher.setTeacherCode(rs.getString("teacher_code"));
                schedule.setActive(rs.getInt("active"));
                schedule.setSlot(rs.getInt("slot"));
                schedule.setClassroom(classroom);
                schedule.setTeacher(teacher);
                schedule.setDate(rs.getDate("date"));
                schedule.setCourse(course);
                schedule.setSemester(rs.getInt("semester"));
                return schedule;
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
