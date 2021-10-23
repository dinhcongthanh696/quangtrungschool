/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.teacher;

import DAO.AbstractClassYearSemesterDAO;
import DAO.AbstractScheduleDAO;
import DAO.ClassYearSemesterDAO;
import DAO.ScheduleDAO;
import Login.BaseAuthorization;
import Model.ClassRoom;
import Model.ClassYearSemester;
import Model.Course;
import Model.Schedule;
import Model.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "ScheduleAttendanceController", urlPatterns = {"/teacher-schedule-attendance"})
public class ScheduleAttendanceController extends BaseAuthorization {

    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    private final AbstractScheduleDAO scheduleDAO;

    public ScheduleAttendanceController() {
        classyearsemesterDAO = new ClassYearSemesterDAO();
        scheduleDAO = new ScheduleDAO();
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("slot") == null || request.getParameter("date") == null
                || request.getParameter("classCode") == null) {
            response.sendError(403);
            return;
        }
        int slot = Integer.parseInt(request.getParameter("slot"));
        Date date = Date.valueOf(request.getParameter("date"));
        String raw_classCode = request.getParameter("classCode");
        ClassRoom classroom = new ClassRoom();
        classroom.setClassCode(raw_classCode);
        Schedule schedule = new Schedule();
        schedule.setClassroom(classroom);
        schedule.setDate(date);
        schedule.setSlot(slot);
        scheduleDAO.getById(schedule);
        if (schedule.getActive() != 2) {
            response.sendError(403);
            return;
        }
        getStudentAttendance(schedule);
        request.setAttribute("schedule", schedule);
        request.setAttribute("classCode", raw_classCode);
        request.setAttribute("slot", slot);
        request.setAttribute("date", date);
        request.getRequestDispatcher("view/web/teacher/scheduleattendance.jsp").forward(request, response);
    }

    public void getStudentAttendance(Schedule schedule) {
        if (schedule.getAttendance() == null) {
            return;
        }
        String[] attendance_split = schedule.getAttendance().split("[,]");
        for (Student student : schedule.getStudents()) {
            for (String studentAttend : attendance_split) {
                if (student.getStudentCode().equals(studentAttend)) {
                    student.setIsAttended(false);
                    break;
                }
            }
        }
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String attendance = "";
        String totalStudent = request.getParameter("totalStudent");
        String[] totalStudent_split = totalStudent.split("[,]");
        String studentCode;
        for (String student : totalStudent_split) {
            studentCode = request.getParameter(student);
            if (!studentCode.isEmpty()) {
                attendance += studentCode + ",";
            }
        }
        
        String classCode = request.getParameter("classCode");
        int slot = Integer.parseInt(request.getParameter("slot"));
        Date date = Date.valueOf(request.getParameter("date"));
        Schedule schedule = new Schedule();
        ClassRoom classroom = new ClassRoom();
        classroom.setClassCode(classCode);
        schedule.setClassroom(classroom);
        schedule.setClassroom(classroom);
        schedule.setAttendance(attendance);
        schedule.setSlot(slot);
        schedule.setDate(date);
        scheduleDAO.updateAttendance(schedule);
        response.sendRedirect("teacher-schedule");
    }

}
