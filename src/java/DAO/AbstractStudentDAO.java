/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.ClassYearSemester;
import Model.Course;
import Model.Student;
import Model.Week;
import java.util.List;

/**
 *
 * @author My Computer
 */
public abstract class AbstractStudentDAO extends BaseDAO {

    public abstract List<Student> getAll();

    public abstract Student getById(String studentCode);

    public abstract void insert(Student student);
      
    public abstract void update(Student student);
    
    public abstract void delete(Student student);
    
    public abstract List<Student> search(String query,int offset,int fetch);
    
    public abstract void getStudentCourses(Student student,ClassYearSemester classyearsemester);
    
    public abstract void getClasses(Student student);
    
    public abstract Student getByUsername(Account account);
    
    public abstract void getStudentAttendance(Student student,ClassYearSemester classyearsemester,Course course);
    
    public abstract void getStudentAttendance(Student student,Week week);
    
}
