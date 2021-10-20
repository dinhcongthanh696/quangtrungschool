/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ClassYearSemester;
import java.util.List;

/**
 *
 * @author My Computer
 */
public abstract class AbstractClassYearSemesterDAO extends BaseDAO{
    public abstract List<ClassYearSemester> getAll();
    public abstract List<ClassYearSemester> search(String query,int offset,int fetch);
    public abstract List<ClassYearSemester> getSemesters(String classCode,int year);
    public abstract void update(ClassYearSemester classroom);
    public abstract void getTeachers(ClassYearSemester classroom);
    public abstract void insert(ClassYearSemester cys);
}
