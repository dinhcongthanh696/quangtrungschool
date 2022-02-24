/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.ClassRoom;
import Model.ClassYearSemester;
import Model.Course;
import Model.Group;
import Model.Schedule;
import Model.Teacher;
import Model.Week;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author My Computer
 */
public class TeacherDAO extends AbstractTeacherDAO {

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher INNER JOIN account ON teacher.username = account.username";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            Teacher teacher;
            Account account;
            ResultSet rs = prepare_stmt.executeQuery();
            while (rs.next()) {
                teacher = new Teacher();
                account = new Account();
                teacher.setTeacherCode(rs.getString("teacher_code"));
                teacher.setFullname(rs.getString("teacher_fullname"));
                teacher.setAddress(rs.getString("teacher_address"));
                teacher.setDob(rs.getDate("teacher_dob"));
                teacher.setEmail(rs.getString("teacher_email"));
                teacher.setPhone(rs.getString("teacher_phone"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                teacher.setAccount(account);
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }

    @Override
    public Teacher getById(String teacherCode) {
        String sql = "SELECT * FROM teacher INNER JOIN account ON teacher.username = account.username "
                + "left join groupaccount as gc on account.username = gc.username "
                + "left join [group] as g on gc.gid = g.gid "
                + "WHERE teacher.teacher_code = ?";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        Teacher teacher = null;
        Account account = null;
        Group group;
        try {
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacherCode);
            rs = prepare_stmt.executeQuery();
            while (rs.next()) {
                if (teacher == null) {
                    teacher = new Teacher();
                    account = new Account();
                    teacher.setTeacherCode(rs.getString("teacher_code"));
                    teacher.setFullname(rs.getString("teacher_fullname"));
                    teacher.setAddress(rs.getString("teacher_address"));
                    teacher.setDob(rs.getDate("teacher_dob"));
                    teacher.setEmail(rs.getString("teacher_email"));
                    teacher.setPhone(rs.getString("teacher_phone"));
                    account.setUsername(rs.getString("username"));
                    account.setPassword(rs.getString("password"));
                    teacher.setAccount(account);
                }
                if (rs.getInt("gid") != 0) {
                    group = new Group();
                    group.setGid(rs.getInt("gid"));
                    account.getGroups().add(group);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacher;
    }

    @Override
    public void insert(Teacher teacher) {
        String sql = "INSERT INTO account VALUES(?,?)";
        PreparedStatement prepare_stmt;
        try {
            connection.setAutoCommit(false);
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getAccount().getUsername());
            prepare_stmt.setString(2, teacher.getAccount().getPassword());
            prepare_stmt.executeUpdate();
            sql = "INSERT INTO groupaccount VALUES(?,?)";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setInt(1, teacher.getAccount().getGroups().get(0).getGid());
            prepare_stmt.setString(2, teacher.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            sql = "INSERT INTO teacher VALUES(?,?,?,?,?,?,?)";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getTeacherCode());
            prepare_stmt.setString(2, teacher.getFullname());
            if (teacher.getAddress().isEmpty()) {
                prepare_stmt.setNull(3, java.sql.Types.NULL);
            } else {
                prepare_stmt.setString(3, teacher.getAddress());
            }
            prepare_stmt.setDate(4, teacher.getDob());
            if (teacher.getEmail().isEmpty()) {
                prepare_stmt.setNull(5, java.sql.Types.NULL);
            } else {
                prepare_stmt.setString(5, teacher.getEmail());
            }
            if (teacher.getPhone().isEmpty()) {
                prepare_stmt.setNull(6, java.sql.Types.NULL);
            } else {
                prepare_stmt.setString(6, teacher.getPhone());
            }
            prepare_stmt.setString(7, teacher.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @Override
    public List<Teacher> search(String query, int offset, int fetch) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher INNER JOIN account ON teacher.username = account.username WHERE ";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        Teacher teacher;
        Account account;
        try {
            Date date = Date.valueOf(query);
            sql += " teacher_dob = ? ORDER BY teacher_code OFFSET ? rows FETCH next ? rows only";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setDate(1, date);
                prepare_stmt.setInt(2, offset);
                prepare_stmt.setInt(3, fetch);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {  // CODE TO ADD NEW TEACHER WITH THEIR ACCOUNT
                    teacher = new Teacher();
                    account = new Account();
                    teacher.setTeacherCode(rs.getString("teacher_code"));
                    teacher.setFullname(rs.getString("teacher_fullname"));
                    teacher.setAddress(rs.getString("teacher_address"));
                    teacher.setDob(rs.getDate("teacher_dob"));
                    teacher.setEmail(rs.getString("teacher_email"));
                    teacher.setPhone(rs.getString("teacher_phone"));
                    account.setUsername(rs.getString("username"));
                    account.setPassword(rs.getString("password"));
                    teacher.setAccount(account);
                    teachers.add(teacher);
                }
            } catch (SQLException ex) {
                Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IllegalArgumentException ex) {
            sql += " teacher_code LIKE ? OR teacher_address LIKE ? OR teacher_email LIKE ? OR "
                    + "teacher_fullname LIKE ? OR teacher_phone LIKE ? OR teacher.username LIKE ? "
                    + "ORDER BY teacher_code OFFSET ? rows FETCH next ? rows only";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                for (int i = 1; i <= 6; i++) {
                    prepare_stmt.setString(i, "%" + query + "%");
                }
                prepare_stmt.setInt(7, offset);
                prepare_stmt.setInt(8, fetch);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {   // CODE TO ADD NEW TEACHER WITH THEIR ACCOUNT
                    teacher = new Teacher();
                    account = new Account();
                    teacher.setTeacherCode(rs.getString("teacher_code"));
                    teacher.setFullname(rs.getString("teacher_fullname"));
                    teacher.setAddress(rs.getString("teacher_address"));
                    teacher.setDob(rs.getDate("teacher_dob"));
                    teacher.setEmail(rs.getString("teacher_email"));
                    teacher.setPhone(rs.getString("teacher_phone"));
                    account.setUsername(rs.getString("username"));
                    account.setPassword(rs.getString("password"));
                    teacher.setAccount(account);
                    teachers.add(teacher);
                }
            } catch (SQLException ex1) {
                Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return teachers;
    }

    @Override
    public void delete(Teacher teacher) {
        String sql;
        PreparedStatement prepare_stmt;
        try {
            connection.setAutoCommit(false);
            sql = "DELETE FROM teacher WHERE teacher_code = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getTeacherCode());
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM account WHERE username = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM groupaccount WHERE username = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            sql = "UPDATE schedule SET teacher_code = NULL "
                    + "WHERE teacher_code = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getTeacherCode());
            prepare_stmt.executeUpdate();
            sql = "UPDATE classyearsemester SET homeroom_teacher = NULL WHERE teacher_code = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getTeacherCode());
            prepare_stmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void update(Teacher teacher, String isadmin, int group) {
        String sql = "UPDATE teacher SET teacher_address = ?,teacher_phone = ?,teacher_email = ? WHERE teacher_code = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getAddress());
            prepare_stmt.setString(2, teacher.getPhone());
            prepare_stmt.setString(3, teacher.getEmail());
            prepare_stmt.setString(4, teacher.getTeacherCode());
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM groupaccount WHERE username = ? AND gid = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getAccount().getUsername());
            prepare_stmt.setInt(2, group);
            prepare_stmt.executeUpdate();
            if (isadmin != null) {
                sql = "INSERT INTO groupaccount VALUES (?,?)";
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setInt(1, group);
                prepare_stmt.setString(2, teacher.getAccount().getUsername());
                prepare_stmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public List<Teacher> getFreeTeacher(Date date, int slot) {
        String sql = "select * from teacher \n"
                + "   WHERE teacher_code NOT IN (\n"
                + "          select teacher_code from teacher INNER JOIN account ON teacher.username = account.username \n"
                + "          WHERE teacher_code IN \n"
                + "	     (\n"
                + "			SELECT sch.teacher_code FROM schedule sch\n"
                + "                	WHERE sch.slot = ?  AND sch.date = ?\n"
                + "          )\n"
                + "			         )";
        List<Teacher> teachers = new ArrayList<>();
        Account account;
        Teacher teacher;
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setInt(1, slot);
            prepare_stmt.setDate(2, date);
            ResultSet rs = prepare_stmt.executeQuery();
            while (rs.next()) {  // CODE TO ADD NEW TEACHER WITH THEIR ACCOUNT
                teacher = new Teacher();
                account = new Account();
                teacher.setTeacherCode(rs.getString("teacher_code"));
                teacher.setFullname(rs.getString("teacher_fullname"));
                teacher.setAddress(rs.getString("teacher_address"));
                teacher.setDob(rs.getDate("teacher_dob"));
                teacher.setEmail(rs.getString("teacher_email"));
                teacher.setPhone(rs.getString("teacher_phone"));
                account.setUsername(rs.getString("username"));
                teacher.setAccount(account);
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }

    @Override
    public Teacher getByUsername(Account account) {
        String sql = "SELECT * FROM teacher INNER JOIN account ON teacher.username = account.username "
                + "WHERE teacher.username = ?";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        Teacher teacher = null;
        try {
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, account.getUsername());
            rs = prepare_stmt.executeQuery();
            if (rs.next()) {
                teacher = new Teacher();
                teacher.setTeacherCode(rs.getString("teacher_code"));
                teacher.setFullname(rs.getString("teacher_fullname"));
                teacher.setAddress(rs.getString("teacher_address"));
                teacher.setDob(rs.getDate("teacher_dob"));
                teacher.setEmail(rs.getString("teacher_email"));
                teacher.setPhone(rs.getString("teacher_phone"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                teacher.setAccount(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teacher;
    }

    @Override
    public void getMainClasses(Teacher teacher) {
        List<ClassYearSemester> classes = new ArrayList<>();
        ClassYearSemester classyearsemester;
        ClassRoom classroom;
        String sql = "select * from teacher "
                + "left join classyearsemester on teacher.teacher_code = classyearsemester.homeroom_teacher "
                + "WHERE teacher.teacher_code = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getTeacherCode());
            ResultSet rs = prepare_stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("class_code") != null) {
                    classyearsemester = new ClassYearSemester();
                    classroom = new ClassRoom();
                    classroom.setClassCode(rs.getString("class_code"));
                    classyearsemester.setClassroom(classroom);
                    classyearsemester.setStartDate(rs.getDate("stat_date"));
                    classyearsemester.setEndDate(rs.getDate("end_date"));
                    classyearsemester.setYear(rs.getInt("year"));
                    classyearsemester.setSemester(rs.getInt("semester"));
                    classes.add(classyearsemester);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        teacher.setClasses(classes);
    }

    @Override
    public void getSchedules(Teacher teacher) {
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule;
        ClassRoom classroom;
        Course course;
        String sql = "SELECT * FROM teacher "
                + " INNER JOIN schedule ON teacher.teacher_code = schedule.teacher_code "
                + " WHERE teacher.teacher_code = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getTeacherCode());
            ResultSet rs = prepare_stmt.executeQuery();
            while (rs.next()) {
                schedule = new Schedule();
                classroom = new ClassRoom();
                course = new Course();
                classroom.setClassCode(rs.getString("class_code"));
                course.setCourseCode(rs.getString("course_code"));
                schedule.setClassroom(classroom);
                schedule.setCourse(course);
                schedule.setActive(rs.getInt("active"));
                schedule.setDate(rs.getDate("date"));
                schedule.setSlot(rs.getInt("slot"));
                schedules.add(schedule);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        teacher.setSchedules(schedules);
    }

    @Override
    public void getSchedules(Teacher teacher, Week week) {
        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule;
        ClassRoom classroom;
        Course course;
        String sql = "SELECT * FROM teacher "
                + " INNER JOIN schedule ON teacher.teacher_code = schedule.teacher_code "
                + " INNER JOIN course ON schedule.course_code = course.course_code "
                + " WHERE teacher.teacher_code = ? AND schedule.date >= ? AND schedule.date <= ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, teacher.getTeacherCode());
            Calendar first_day_of_week = Calendar.getInstance();
            Calendar last_day_of_week = Calendar.getInstance();
            first_day_of_week.setTime(week.getDays().get(0));
            last_day_of_week.setTime(week.getDays().get(week.getTotalDays() - 1));
            prepare_stmt.setDate(2, new java.sql.Date(first_day_of_week.getTime().getTime()));
            prepare_stmt.setDate(3, new java.sql.Date(last_day_of_week.getTime().getTime()));
            ResultSet rs = prepare_stmt.executeQuery();
            while (rs.next()) {
                schedule = new Schedule();
                classroom = new ClassRoom();
                course = new Course();
                classroom.setClassCode(rs.getString("class_code"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setType(rs.getInt("type"));
                schedule.setClassroom(classroom);
                schedule.setCourse(course);
                schedule.setActive(rs.getInt("active"));
                schedule.setDate(rs.getDate("date"));
                schedule.setSlot(rs.getInt("slot"));
                schedules.add(schedule);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        teacher.setSchedules(schedules);
    }

    @Override
    public int getTotalTeachers() {
        String sql = "SELECT COUNT(*) as totalteachers FROM teacher";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            ResultSet rs = prepare_stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalteachers");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int getTotalSearchedTeachers(String query) {
        int totalSearchedTeachers = 0;
        String sql = "SELECT * FROM teacher WHERE ";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        Teacher teacher;
        Account account;
        try {
            Date date = Date.valueOf(query);
            sql += " teacher_dob = ? ORDER BY teacher_code OFFSET ? rows FETCH next ? rows only";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setDate(1, date);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {  // CODE TO ADD NEW TEACHER WITH THEIR ACCOUNT
                    totalSearchedTeachers++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IllegalArgumentException ex) {
            sql += " teacher_code LIKE ? OR teacher_address LIKE ? OR teacher_email LIKE ? OR "
                    + "teacher_fullname LIKE ? OR teacher_phone LIKE ? OR teacher.username LIKE ? ";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                for (int i = 1; i <= 6; i++) {
                    prepare_stmt.setString(i, "%" + query + "%");
                }
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {   // CODE TO ADD NEW TEACHER WITH THEIR ACCOUNT
                    totalSearchedTeachers++;
                }
            } catch (SQLException ex1) {
                Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return totalSearchedTeachers;
    }

}
