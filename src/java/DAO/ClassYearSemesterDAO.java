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
import Model.Learning;
import Model.Student;
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
public class ClassYearSemesterDAO extends AbstractClassYearSemesterDAO {

    @Override
    public List<ClassYearSemester> getAll() {
        List<ClassYearSemester> classYearSemesters = new ArrayList<>();
        String sql = "SELECT * FROM classyearsemester";
        ClassYearSemester cys;
        ResultSet rs;
        ClassRoom classroom;
        Teacher teacher;
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            rs = prepare_stmt.executeQuery();
            while (rs.next()) {
                cys = new ClassYearSemester();
                classroom = new ClassRoom();
                teacher = new Teacher();
                classroom.setClassCode(rs.getString("class_code"));
                teacher.setTeacherCode(rs.getString("homeroom_teacher"));
                cys.setHomeroomTeacher(teacher);
                cys.setClassroom(classroom);
                cys.setYear(rs.getInt("year"));
                cys.setSemester(rs.getInt("semester"));
                cys.setStartDate(rs.getDate("stat_date"));
                cys.setEndDate(rs.getDate("end_date"));
                classYearSemesters.add(cys);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classYearSemesters;
    }

    @Override
    public List<ClassYearSemester> search(String query, int offset, int fetch) {
        List<ClassYearSemester> classYearSemesters = new ArrayList<>();
        String sql = "SELECT * FROM classyearsemester WHERE ";
        PreparedStatement prepare_stmt;
        ClassYearSemester cys;
        ResultSet rs;
        ClassRoom classroom;
        Teacher teacher;
        try {
            int intType = Integer.parseInt(query);
            sql += "? IN (year,semester) ORDER BY class_code OFFSET ? rows FETCH next ? rows only ";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setInt(1, intType);
                prepare_stmt.setInt(2, offset);
                prepare_stmt.setInt(3, fetch);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {
                    cys = new ClassYearSemester();
                    classroom = new ClassRoom();
                    teacher = new Teacher();
                    classroom.setClassCode(rs.getString("class_code"));
                    teacher.setTeacherCode(rs.getString("homeroom_teacher"));
                    cys.setHomeroomTeacher(teacher);
                    cys.setClassroom(classroom);
                    cys.setYear(rs.getInt("year"));
                    cys.setSemester(rs.getInt("semester"));
                    cys.setStartDate(rs.getDate("stat_date"));
                    cys.setEndDate(rs.getDate("end_date"));
                    classYearSemesters.add(cys);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (NumberFormatException ex) {
            sql += "? IN (class_code,homeroom_teacher) ORDER BY class_code OFFSET ? rows FETCH next ? rows only ";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setString(1, query);
                prepare_stmt.setInt(2, offset);
                prepare_stmt.setInt(3, fetch);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {
                    cys = new ClassYearSemester();
                    classroom = new ClassRoom();
                    teacher = new Teacher();
                    classroom.setClassCode(rs.getString("class_code"));
                    teacher.setTeacherCode(rs.getString("homeroom_teacher"));
                    cys.setHomeroomTeacher(teacher);
                    cys.setClassroom(classroom);
                    cys.setYear(rs.getInt("year"));
                    cys.setSemester(rs.getInt("semester"));
                    cys.setStartDate(rs.getDate("stat_date"));
                    cys.setEndDate(rs.getDate("end_date"));
                    classYearSemesters.add(cys);
                }
            } catch (SQLException ex1) {
                Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return classYearSemesters;
    }

    @Override
    public void insert(ClassYearSemester cys) {
        String sql = "INSERT INTO classyearsemester (class_code,[year],semester,stat_date,end_date) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, cys.getClassroom().getClassCode());
            prepare_stmt.setInt(2, cys.getYear());
            prepare_stmt.setInt(3, cys.getSemester());
            prepare_stmt.setDate(4, cys.getStartDate());
            prepare_stmt.setDate(5, cys.getEndDate());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<ClassYearSemester> getSemesters(String classCode, int year) {
        List<ClassYearSemester> classYearSemesters = new ArrayList<>();
        String sql = "SELECT * FROM classyearsemester WHERE class_code = ? AND year = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classCode);
            prepare_stmt.setInt(2, year);
            ResultSet rs = prepare_stmt.executeQuery();
            ClassRoom classroom;
            Teacher homeroomeTeacher;
            ClassYearSemester classYearSemester;
            while (rs.next()) {
                classYearSemester = new ClassYearSemester();
                classroom = new ClassRoom();
                homeroomeTeacher = new Teacher();
                classroom.setClassCode(rs.getString("class_code"));
                homeroomeTeacher.setTeacherCode(rs.getString("homeroom_teacher"));
                classYearSemester.setHomeroomTeacher(homeroomeTeacher);
                classYearSemester.setClassroom(classroom);
                classYearSemester.setYear(rs.getInt("year"));
                classYearSemester.setSemester(rs.getInt("semester"));
                classYearSemesters.add(classYearSemester);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classYearSemesters;
    }

    @Override
    public void getTeachers(ClassYearSemester classroom) {
        String sql = "select t.teacher_code,t.teacher_fullname,t.username from schedule as s inner join teacher as t on s.teacher_code = t.teacher_code\n"
                + "WHERE s.class_code = ? AND s.semester = ? AND DATEPART(year,s.date) = ? "
                + " group by t.teacher_code,t.teacher_fullname,t.username";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classroom.getClassroom().getClassCode());
            preparedStatement.setInt(2, classroom.getSemester());
            preparedStatement.setInt(3, classroom.getYear());
            ResultSet rs = preparedStatement.executeQuery();
            Teacher teacher;
            Account account;
            while (rs.next()) {
                teacher = new Teacher();
                account = new Account();
                teacher.setTeacherCode(rs.getString("teacher_code"));
                teacher.setFullname(rs.getString("teacher_fullname"));
                account.setUsername(rs.getString("username"));
                teacher.setAccount(account);
                classroom.getTeachersOfClass().add(teacher);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ClassYearSemester classroom) {
        String sql = "UPDATE classyearsemester SET homeroom_teacher = ? "
                + "WHERE class_code = ? AND year = ? AND semester = ? ";
        try {
            connection.setAutoCommit(false);
            PreparedStatement prepared_stmt = connection.prepareStatement(sql);
            prepared_stmt.setString(1, classroom.getHomeroomTeacher().getTeacherCode());
            prepared_stmt.setString(2, classroom.getClassroom().getClassCode());
            prepared_stmt.setInt(3, classroom.getYear());
            prepared_stmt.setInt(4, classroom.getSemester());
            prepared_stmt.executeUpdate();
            for (Teacher teacher : classroom.getTeachersOfClass()) {
                if (teacher.getTeacherCode().equals(classroom.getHomeroomTeacher().getTeacherCode())) {
                    sql = "INSERT INTO groupaccount VALUES(?,?) ";
                } else {
                    sql = "DELETE FROM groupaccount WHERE gid = ? AND username = ? ";
                }
                prepared_stmt = connection.prepareStatement(sql);
                prepared_stmt.setInt(1, 3); // 3 repersent for main teacher group
                prepared_stmt.setString(2, teacher.getTeacherCode());
                prepared_stmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void getStudents(ClassYearSemester classroom) {
        List<Student> students = new ArrayList<>();
        String sql = "select * from classyearsemester as cys "
                + "inner join learning as l on cys.class_code = l.class_code AND cys.year = l.year\n"
                + "AND cys.semester = l.semester inner join student as s on l.student_code = s.student_code "
                + "WHERE cys.class_code = ? AND cys.year = ? AND cys.semester = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classroom.getClassroom().getClassCode());
            prepare_stmt.setInt(2, classroom.getYear());
            prepare_stmt.setInt(3, classroom.getSemester());
            ResultSet rs = prepare_stmt.executeQuery();
            Student student;
            while (rs.next()) {
                student = new Student();
                student.setStudentCode(rs.getString("student_code"));
                student.setFullname(rs.getString("student_fullname"));
                student.setAddress(rs.getString("student_address"));
                student.setDob(rs.getDate("student_dob"));
                student.setEmail(rs.getString("student_email"));
                student.setPhone(rs.getString("student_phone"));
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        classroom.setStudents(students);
    }

    @Override
    public void getCourses(ClassYearSemester classroom) {
        List<Course> courses = new ArrayList<>();
        String sql = "select cys.class_code,cys.year,cys.semester,c.course_code,c.course_name,c.is_marked\n"
                + "from classyearsemester as cys inner join schedule as sch ON\n"
                + "cys.class_code = sch.class_code AND cys.semester = sch.semester AND\n"
                + "sch.date >= cys.stat_date AND sch.date <= cys.end_date inner join course as c\n"
                + "on sch.course_code = c.course_code\n"
                + "WHERE cys.class_code = ? AND cys.year = ? AND cys.semester = ? "
                + "GROUP BY cys.class_code,cys.year,cys.semester,c.course_code,c.course_name,c.is_marked";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, classroom.getClassroom().getClassCode());
            prepare_stmt.setInt(2, classroom.getYear());
            prepare_stmt.setInt(3, classroom.getSemester());
            ResultSet rs = prepare_stmt.executeQuery();
            Course course;
            while (rs.next()) {
                course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setIsMarked(rs.getBoolean("is_marked"));
                courses.add(course);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        classroom.setCourses(courses);
    }

    @Override
    public int getTotalSearchedClasses(String query) {
        String sql = "SELECT COUNT(*) as totalclasses FROM classyearsemester WHERE ";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        try {
            int intType = Integer.parseInt(query);
            sql += "? IN (year,semester)";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setInt(1, intType);
                rs = prepare_stmt.executeQuery();
                if(rs.next()){
                    return rs.getInt("totalclasses");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException ex) {
            sql += "? IN (class_code,homeroom_teacher) ";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setString(1, query);
                rs = prepare_stmt.executeQuery();
                if(rs.next()){
                    return rs.getInt("totalclasses");
                }
            } catch (SQLException ex1) {
                Logger.getLogger(ClassYearSemesterDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return 0;
    }
    
    
}
