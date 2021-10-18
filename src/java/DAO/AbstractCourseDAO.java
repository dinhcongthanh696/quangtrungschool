/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Course;
import java.util.List;

/**
 *
 * @author My Computer
 */
public abstract class AbstractCourseDAO extends BaseDAO{
    public abstract List<Course> getAll();
    public abstract List<Course> getUntakenCourse(String classCode,int year,int semester);
}
