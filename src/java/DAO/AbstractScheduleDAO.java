/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Schedule;
import Model.Week;
import java.util.List;

/**
 *
 * @author My Computer
 */
public abstract class AbstractScheduleDAO extends BaseDAO{
    public abstract List<Schedule> getSlotsOfClassInWeek(String classCode,Week week);
    public abstract void insert(Schedule schedule);
    public abstract void delete(Schedule schedule);
    public abstract void updateTeacher(Schedule schedule);
    public abstract void updateAttendance(Schedule schedule);
    public abstract void getAttendances(Schedule schedule);
}
