/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
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
public abstract class AbstractTeacherDAO extends BaseDAO{
    public abstract List<Teacher> getAll();
    public abstract Teacher getById(String teacherCode);
    public abstract Teacher getByUsername(Account account);
    public abstract void getSchedules(Teacher teacher);
    public abstract void insert(Teacher teacher);
    public abstract List<Teacher> search(String query,int offset,int fetch);
    public abstract List<Teacher> getFreeTeacher(Date date,int slot);
    public abstract void getMainClasses(Teacher teacher);
    public abstract void delete(Teacher teacher);
    public abstract void update(Teacher teacher);
    
    
   
}
