/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Mark;
import Model.StudentCourse;

/**
 *
 * @author My Computer
 */
public abstract class AbstractMarkDAO extends BaseDAO{
    public abstract int insert(StudentCourse studentcourse,Mark mark);
    public abstract void update(Mark mark);
    public abstract void delete(Mark mark);
}
