/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.teacher;

import DAO.AbstractTeacherDAO;
import DAO.TeacherDAO;
import Login.BaseAuthorization;
import Model.Account;
import Model.Schedule;
import Model.Teacher;
import Model.Week;
import Model.Year;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "TeacherSchedule", urlPatterns = {"/teacher-schedule"})
public class TeacherScheduleController extends BaseAuthorization {

    private final AbstractTeacherDAO teacherDAO;

    public TeacherScheduleController() {
        teacherDAO = new TeacherDAO();
    }

    public void getDays(Year year, int weekNumber) {
        Calendar calendarFirstDay = Calendar.getInstance();
        calendarFirstDay.set(Calendar.YEAR, year.getYear());
        calendarFirstDay.set(Calendar.DAY_OF_YEAR, 1);  // set to the first day of the year
        Calendar calendarLastday = Calendar.getInstance();
        calendarLastday.set(Calendar.YEAR, year.getYear());
        calendarLastday.set(Calendar.MONTH, 11); // BECAUSE MONTH START FROM 0
        calendarLastday.set(Calendar.DAY_OF_MONTH, 31); // set to the last day of last month
        Date last_day_of_year = calendarLastday.getTime();
        Week week = new Week();
        week.setWeekNumber(1); // first week
        year.getWeeks().add(week);
        Date date;
        while (calendarFirstDay.getTime().before(last_day_of_year)) {
            if (week.getWeekNumber() == 52 && calendarFirstDay.get(Calendar.WEEK_OF_YEAR) == 1) {
                if (week.getWeekNumber() != weekNumber) {
                    calendarFirstDay.set(Calendar.DAY_OF_YEAR, calendarFirstDay.get(Calendar.DAY_OF_YEAR) - 1);   // MINUS 1
                    date = calendarFirstDay.getTime();
                    week.getDays().add(date);
                    week.setTotalDays(week.getDays().size());
                    calendarFirstDay.set(Calendar.DAY_OF_YEAR, calendarFirstDay.get(Calendar.DAY_OF_YEAR) + 1);   // ADD 1 
                }

                week = new Week();
                week.setWeekNumber(53);
                while (calendarFirstDay.getTime().before(last_day_of_year)) {
                    date = calendarFirstDay.getTime();
                    week.getDays().add(date);
                    calendarFirstDay.add(Calendar.DAY_OF_YEAR, 1);
                }
                date = calendarFirstDay.getTime(); // including the last day of the year
                week.getDays().add(date);
                week.setTotalDays(week.getDays().size());
                year.getWeeks().add(week);
                return;
            }

            if (week.getWeekNumber() != calendarFirstDay.get(Calendar.WEEK_OF_YEAR)) {
                if (week.getWeekNumber() != weekNumber) {
                    calendarFirstDay.set(Calendar.DAY_OF_YEAR, calendarFirstDay.get(Calendar.DAY_OF_YEAR) - 1);   // MINUS 1
                    date = calendarFirstDay.getTime();
                    week.getDays().add(date);
                    calendarFirstDay.set(Calendar.DAY_OF_YEAR, calendarFirstDay.get(Calendar.DAY_OF_YEAR) + 1);   // ADD 1 
                }
                week.setTotalDays(week.getDays().size());
                week = new Week();
                week.setWeekNumber(calendarFirstDay.get(Calendar.WEEK_OF_YEAR));
                year.getWeeks().add(week);
            }
            if (week.getWeekNumber() == weekNumber || (week.getWeekNumber() != weekNumber && week.getDays().isEmpty())) {
                date = calendarFirstDay.getTime();
                week.getDays().add(date);
            }
            calendarFirstDay.add(Calendar.DAY_OF_YEAR, 1);
        }
        date = calendarFirstDay.getTime();  // including the last day of the year
        week.getDays().add(date);
        week.setTotalDays(week.getDays().size());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_year = request.getParameter("year");
        String raw_week = request.getParameter("week");
        Calendar calendar = Calendar.getInstance();
        int yearNumber = (raw_year == null) ? calendar.get(Calendar.YEAR) : Integer.parseInt(raw_year);
        int weekNumber = (raw_week == null) ? calendar.get(Calendar.WEEK_OF_YEAR) : Integer.parseInt(raw_week);
        Year year = new Year();
        year.setYear(yearNumber);
        getDays(year, weekNumber);
        if (weekNumber > year.getWeeks().size()) {
            weekNumber = 1;   // THE WEEK 53TH
        }
        Week currentWeek = year.getWeeks().get(weekNumber - 1);
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        teacherDAO.getSchedules(teacher, currentWeek);
        request.setAttribute("year", year);
        request.setAttribute("week", currentWeek);
        request.getRequestDispatcher("view/web/teacher/teacherschedule.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
