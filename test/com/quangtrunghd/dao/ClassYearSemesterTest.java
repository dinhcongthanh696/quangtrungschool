/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quangtrunghd.dao;

import DAO.ClassYearSemesterDAO;
import Model.ClassRoom;
import Model.ClassYearSemester;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author My Computer
 */
@RunWith(value = Parameterized.class)
public class ClassYearSemesterTest {
    @Parameterized.Parameters
    public static Collection<Object[]> initData(){
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"10d5" , 2021 , new ClassYearSemester(new ClassRoom("10d5"), null, 2021, 0)});
        data.add(new Object[]{"11d5" , 2021 , new ClassYearSemester(new ClassRoom("11d5"), null, 2021, 0)});
        return data;
    }
    
    @Parameterized.Parameter(value = 0)
    public String classCode;
    @Parameterized.Parameter(value = 1)
    public int year;
    @Parameterized.Parameter(value = 2)
    public ClassYearSemester expected;
    
    @Test
    public void checkGetClassYearSemesterGivenRightArguementReturnsWell(){
        ClassYearSemesterDAO dao = new ClassYearSemesterDAO();
        List<ClassYearSemester> classes =  dao.getSemesters(classCode, year);
        for(ClassYearSemester c : classes){
            assertEquals(c.getClassroom().getClassCode(), expected.getClassroom().getClassCode());
            assertEquals(c.getYear(), expected.getYear());
        }
    }
}
