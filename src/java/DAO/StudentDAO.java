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
import Model.Feature;
import Model.Group;
import Model.Learning;
import Model.Mark;
import Model.Schedule;
import Model.Student;
import Model.StudentAttendance;
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
public class StudentDAO extends AbstractStudentDAO {

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student INNER JOIN account ON student.username = account.username";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        try {
            prepare_stmt = connection.prepareStatement(sql);
            rs = prepare_stmt.executeQuery();
            Student student;
            Account account;
            while (rs.next()) {
                student = new Student();
                student.setStudentCode(rs.getString("student_code"));
                student.setFullname(rs.getString("student_fullname"));
                student.setAddress(rs.getString("student_address"));
                student.setDob(rs.getDate("student_dob"));
                student.setEmail(rs.getString("student_email"));
                student.setPhone(rs.getString("student_phone"));
                account = new Account();
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                student.setAccount(account);
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;
    }

    @Override
    public Student getById(String studentCode) {
        Student student = null;
        String sql = "SELECT * FROM student INNER JOIN account ON student.username = account.username "
                + "LEFT JOIN learning ON student.student_code = learning.student_code "
                + " WHERE student.student_code = ?";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        try {
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, studentCode);
            rs = prepare_stmt.executeQuery();
            Account account;
            ClassYearSemester classyearsemester;
            ClassRoom classroom;
            while (rs.next()) {
                if (student == null) {
                    student = new Student();
                    student.setStudentCode(rs.getString("student_code"));
                    student.setFullname(rs.getString("student_fullname"));
                    student.setAddress(rs.getString("student_address"));
                    student.setDob(rs.getDate("student_dob"));
                    student.setEmail(rs.getString("student_email"));
                    student.setPhone(rs.getString("student_phone"));
                    account = new Account();
                    account.setUsername(rs.getString("username"));
                    account.setPassword(rs.getString("password"));
                    student.setAccount(account);
                }
                if (rs.getString("class_code") == null) {
                    classyearsemester = new ClassYearSemester();
                    classroom = new ClassRoom();
                    classroom.setClassCode(rs.getString("class_code"));
                    classyearsemester.setClassroom(classroom);
                    classyearsemester.setYear(rs.getInt("year"));
                    classyearsemester.setSemester(rs.getInt("semester"));
                    student.getClasses().add(classyearsemester);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return student;
    }

    @Override
    public void insert(Student student) {
        String sql = "INSERT INTO account VALUES(?,?)";
        PreparedStatement prepare_stmt;
        try {
            connection.setAutoCommit(false);
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getAccount().getUsername());
            prepare_stmt.setString(2, student.getAccount().getPassword());
            prepare_stmt.executeUpdate();
            sql = "INSERT INTO groupaccount VALUES(?,?) ";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setInt(1, student.getAccount().getGroups().get(0).getGid());
            prepare_stmt.setString(2, student.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            sql = "INSERT INTO student VALUES(?,?,?,?,?,?,?)";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getStudentCode());
            prepare_stmt.setString(2, student.getFullname());
            if (student.getAddress().isEmpty()) {
                prepare_stmt.setNull(3, java.sql.Types.NULL);
            } else {
                prepare_stmt.setString(3, student.getAddress());
            }
            prepare_stmt.setDate(4, student.getDob());
            if (student.getEmail().isEmpty()) {
                prepare_stmt.setNull(5, java.sql.Types.NULL);
            } else {
                prepare_stmt.setString(5, student.getEmail());
            }
            if (student.getPhone().isEmpty()) {
                prepare_stmt.setNull(6, java.sql.Types.NULL);
            } else {
                prepare_stmt.setString(6, student.getPhone());
            }
            prepare_stmt.setString(7, student.getAccount().getUsername());
            prepare_stmt.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex1) {
                    Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }

    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE student SET student_fullname = ?,student_address = ?,student_dob = ?,student_email = ?,student_phone = ? "
                + " WHERE student_code = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getFullname());
            prepare_stmt.setString(2, student.getAddress());
            prepare_stmt.setString(4, student.getEmail());
            prepare_stmt.setString(5, student.getPhone());
            prepare_stmt.setDate(3, student.getDob());
            prepare_stmt.setString(6, student.getStudentCode());
            prepare_stmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Student student) {
        String sql = "DELETE FROM student "
                + " WHERE student_code = ?";
        PreparedStatement prepare_stmt = null;
        try {
            connection.setAutoCommit(false);
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getStudentCode());
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM learning WHERE student_code = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getStudentCode());
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM account WHERE username = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM groupaccount WHERE username = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM news WHERE constructor = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM mark WHERE student_code = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getStudentCode());
            prepare_stmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    if (prepare_stmt != null) {
                        prepare_stmt.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public List<Student> search(String query, int offset, int fetch) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student as s INNER JOIN account ON s.username = account.username WHERE ";
        PreparedStatement prepare_stmt = null;
        ResultSet rs;
        Student student;
        Account account;
        try {
            Date date = Date.valueOf(query);
            sql += " student_dob = ? ORDER BY student_code OFFSET ? rows FETCH next ? rows only";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setDate(1, date);
                prepare_stmt.setInt(2, offset);
                prepare_stmt.setInt(3, fetch);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {
                    student = new Student();
                    student.setStudentCode(rs.getString("student_code"));
                    student.setFullname(rs.getString("student_fullname"));
                    student.setDob(rs.getDate("student_dob"));
                    student.setAddress(rs.getString("student_address"));
                    student.setEmail(rs.getString("student_email"));
                    student.setPhone(rs.getString("student_phone"));
                    account = new Account();
                    account.setUsername(rs.getString("username"));
                    student.setAccount(account);
                    students.add(student);
                }
            } catch (SQLException ex) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IllegalArgumentException ex) {
            sql += " s.student_code LIKE ? OR s.student_address LIKE ? OR s.student_email LIKE ? OR "
                    + "s.student_fullname LIKE ? OR s.student_phone LIKE ? OR s.username LIKE ? "
                    + "ORDER BY student_code OFFSET ? rows FETCH next ? rows only";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                for (int i = 1; i <= 6; i++) {
                    prepare_stmt.setString(i, "%" + query + "%");
                }
                prepare_stmt.setInt(7, offset);
                prepare_stmt.setInt(8, fetch);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {
                    student = new Student();
                    student.setStudentCode(rs.getString("student_code"));
                    student.setFullname(rs.getString("student_fullname"));
                    student.setDob(rs.getDate("student_dob"));
                    student.setAddress(rs.getString("student_address"));
                    student.setEmail(rs.getString("student_email"));
                    student.setPhone(rs.getString("student_phone"));
                    account = new Account();
                    account.setUsername(rs.getString("username"));
                    student.setAccount(account);
                    students.add(student);
                }
            } catch (SQLException ex1) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return students;
    }

    @Override
    public void getMarks(Student student, ClassYearSemester classyearsemester) {
        List<Mark> marks = new ArrayList<>();
        String sql = "select m.no,m.course_code,m.exam_type,m.mark,c.is_marked from student as s inner join learning as l on s.student_code = l.student_code\n"
                + "inner join mark as m on l.student_code = m.student_code AND m.class_code = l.class_code AND m.year = l.year\n"
                + "AND m.semester = l.semester "
                + "INNER JOIN course as c ON m.course_code = c.course_code "
                + "WHERE l.class_code = ? AND l.year = ? AND l.semester = ? AND s.student_code = ? "
                + "ORDER BY m.course_code,m.exam_type";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classyearsemester.getClassroom().getClassCode());
            prepare_stmt.setInt(2, classyearsemester.getYear());
            prepare_stmt.setInt(3, classyearsemester.getSemester());
            prepare_stmt.setString(4, student.getStudentCode());
            ResultSet rs = prepare_stmt.executeQuery();
            Mark mark;
            Course course;
            while (rs.next()) {
                mark = new Mark();
                course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                course.setIsMarked(rs.getBoolean("is_marked"));
                mark.setStudent(student);
                mark.setCourse(course);
                mark.setNo(rs.getInt("no"));
                mark.setExam_type(rs.getInt("exam_type"));
                mark.setMark(rs.getDouble("mark"));
                marks.add(mark);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        student.setMarks(marks);
    }

    @Override
    public Student getByUsername(Account account) {
        String sql = "SELECT * FROM student "
                + "WHERE username = ?";
        Student student = null;
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, account.getUsername());
            ResultSet rs = prepare_stmt.executeQuery();
            while (rs.next()) {
                if (student == null) {
                    student = new Student();
                    student.setStudentCode(rs.getString("student_code"));
                    student.setFullname(rs.getString("student_fullname"));
                    student.setAddress(rs.getString("student_address"));
                    student.setDob(rs.getDate("student_dob"));
                    student.setEmail(rs.getString("student_email"));
                    student.setPhone(rs.getString("student_phone"));
                    student.setAccount(account);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return student;
    }

    @Override
    public void getClasses(Student student) {
        List<ClassYearSemester> classes = new ArrayList<>();
        String sql = "SELECT cys.class_code,cys.stat_date,cys.end_date,cys.year,cys.semester,cys.homeroom_teacher,sche.course_code FROM student as s "
                + "inner join learning as l\n"
                + "on s.student_code = l.student_code inner join classyearsemester as cys \n"
                + "on l.semester = cys.semester AND l.year = cys.year AND l.class_code = cys.class_code\n"
                + "inner join schedule as sche on sche.date >= cys.stat_date AND sche.date <= cys.end_date AND \n"
                + "sche.semester = cys.semester AND sche.class_code = cys.class_code\n"
                + "WHERE s.student_code = ? "
                + " GROUP BY cys.class_code,cys.stat_date,cys.end_date,cys.year,cys.semester,cys.homeroom_teacher,sche.course_code";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, student.getStudentCode());
            ResultSet rs = prepare_stmt.executeQuery();
            ClassYearSemester classyearsemester = new ClassYearSemester();
            ClassRoom classroom;
            classyearsemester.setClassroom(new ClassRoom());
            Teacher teacher;
            Course course;
            while (rs.next()) {
                if (!rs.getString("class_code").equals(classyearsemester.getClassroom().getClassCode())
                        && classyearsemester.getYear() != rs.getInt("year")
                        && classyearsemester.getSemester() != rs.getInt("semester")) {
                    classyearsemester = new ClassYearSemester();
                    classroom = new ClassRoom();
                    teacher = new Teacher();
                    teacher.setTeacherCode(rs.getString("homeroom_teacher"));
                    classroom.setClassCode(rs.getString("class_code"));
                    classyearsemester.setClassroom(classroom);
                    classyearsemester.setHomeroomTeacher(teacher);
                    classyearsemester.setStartDate(rs.getDate("stat_date"));
                    classyearsemester.setEndDate(rs.getDate("end_date"));
                    classyearsemester.setYear(rs.getInt("year"));
                    classyearsemester.setSemester(rs.getInt("semester"));
                    classes.add(classyearsemester);
                }
                course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                classyearsemester.getCourses().add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        student.setClasses(classes);
    }

    @Override
    public void getStudentAttendance(Student student, ClassYearSemester classyearsemester, Course course) {
        List<StudentAttendance> studentAttendances = new ArrayList<>();
        String sql = "select * from schedule as sch\n"
                + "where sch.class_code = ? AND sch.course_code = ? AND sch.semester = ? \n"
                + "AND sch.date >= ? AND sch.date <= ? ";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classyearsemester.getClassroom().getClassCode());
            prepare_stmt.setString(2, course.getCourseCode());
            prepare_stmt.setInt(3, classyearsemester.getSemester());
            prepare_stmt.setDate(4, classyearsemester.getStartDate());
            prepare_stmt.setDate(5, classyearsemester.getEndDate());
            StudentAttendance studentAttendance;
            Schedule schedule;
            ClassRoom classroom;
            Teacher teacher;
            ResultSet rs = prepare_stmt.executeQuery();
            while(rs.next()){
                studentAttendance = new StudentAttendance();
                schedule = new Schedule();
                teacher = new Teacher();
                teacher.setTeacherCode(rs.getString("teacher_code"));
                schedule.setClassroom(classyearsemester.getClassroom());
                schedule.setCourse(course);
                schedule.setDate(rs.getDate("date"));
                schedule.setAttendance(rs.getString("attendance"));
                if(schedule.getAttendance() == null){
                    studentAttendance.setStatus(0);   //REPERSENT FOR NOT YET
                }else if(schedule.getAttendance().contains(student.getStudentCode())){
                    studentAttendance.setStatus(-1);  // ABSENT
                }else{
                    studentAttendance.setStatus(1);   // ATTENDED
                }
                
                schedule.setTeacher(teacher);
                schedule.setSlot(rs.getInt("slot"));
                studentAttendance.setSchedule(schedule);
                studentAttendances.add(studentAttendance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        student.setStudentAttendances(studentAttendances);
    }

}
