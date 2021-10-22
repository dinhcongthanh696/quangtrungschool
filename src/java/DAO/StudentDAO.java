/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.ClassRoom;
import Model.Course;
import Model.Learning;
import Model.Mark;
import Model.Student;
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
            Learning learning;
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
                    break;
                }
                learning = new Learning();
                classroom = new ClassRoom();
                classroom.setClassCode(rs.getString("class_code"));
                learning.setStudent(student);
                learning.setClassroom(classroom);
                learning.setYear(rs.getInt("year"));
                learning.setSemester(rs.getInt("semester"));
                student.getStudentLearning().add(learning);
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
    public void getMarks(Student student) {
        List<Mark> marks = new ArrayList<>();
        String sql = "select m.no,m.course_code,m.exam_type,m.mark,c.is_marked from student as s inner join learning as l on s.student_code = l.student_code\n"
                + "inner join mark as m on l.student_code = m.student_code AND m.class_code = l.class_code AND m.year = l.year\n"
                + "AND m.semester = l.semester "
                + "INNER JOIN course as c ON m.course_code = c.course_code "
                + "WHERE l.class_code = ? AND l.year = ? AND l.semester = ? "
                + "ORDER BY m.course_code,m.exam_type";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            Learning learning = student.getStudentLearning().get(0);
            prepare_stmt.setString(1, learning.getClassroom().getClassCode());
            prepare_stmt.setInt(2, learning.getYear());
            prepare_stmt.setInt(3, learning.getSemester());
            ResultSet rs = prepare_stmt.executeQuery();
            Mark mark;
            Course course;
            while(rs.next()){
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

}
