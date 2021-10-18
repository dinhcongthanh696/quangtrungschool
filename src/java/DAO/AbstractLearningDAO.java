/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Learning;
import java.util.List;

/**
 *
 * @author My Computer
 */
public abstract class AbstractLearningDAO extends BaseDAO{
    public abstract void save(Learning learning);
    public abstract Learning getStudentLearning(String studentCode,String classCode,int year,int semester);
    public abstract List<Learning> getAll();
    public abstract void delete(String studentCode,String classCode,int year,int semester);
}
