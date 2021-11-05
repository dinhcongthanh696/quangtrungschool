/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.student;

import DAO.AbstractStudentDAO;
import DAO.StudentDAO;
import Login.BaseAuthorization;
import Model.Student;
import Model.Week;
import Model.Year;
import java.io.IOException;
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
@WebServlet(name = "StudentScheduleController", urlPatterns = {"/student-schedule"})
public class StudentScheduleController extends BaseAuthorization {

    private final AbstractStudentDAO studentDAO;

    public StudentScheduleController() {
        studentDAO = new StudentDAO();
    }

    public Object[] generateDate(Calendar calendar, SimpleDateFormat formatter_EEEE, SimpleDateFormat formatter_yyyyMMdd) {
        String weekName;
        Object[] date;
        weekName = formatter_EEEE.format(calendar.getTime());
        date = new Object[2];
        date[0] = formatter_yyyyMMdd.format(calendar.getTime());
        date[1] = weekName;
        return date;
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
            weekNumber = 1;
        }
        Week currentWeek = year.getWeeks().get(weekNumber - 1);
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        studentDAO.getStudentAttendance(student, currentWeek);
        request.setAttribute("year", year);
        request.setAttribute("week", currentWeek);
        request.getRequestDispatcher("view/web/student/studentschedule.jsp").forward(request, response);
    }

    public void getDays(Year year, int currentWeek) {
        SimpleDateFormat formatter_EEEE = new SimpleDateFormat("EEEE");
        SimpleDateFormat formatter_yyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
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
        Object[] day;
        while (calendarFirstDay.getTime().before(last_day_of_year)) {
            if (week.getWeekNumber() == 52 && calendarFirstDay.get(Calendar.WEEK_OF_YEAR) == 1) {
                if (week.getWeekNumber() != currentWeek) { // add last day for normal week
                    calendarFirstDay.set(Calendar.DAY_OF_YEAR, calendarFirstDay.get(Calendar.DAY_OF_YEAR) - 1);   // MINUS 1
                    day = generateDate(calendarFirstDay, formatter_EEEE, formatter_yyyMMdd);
                    week.getDays().add(day);
                    week.setTotalDays(week.getDays().size());
                    calendarFirstDay.set(Calendar.DAY_OF_YEAR, calendarFirstDay.get(Calendar.DAY_OF_YEAR) + 1);   // ADD 1 
                }

                week = new Week();
                week.setWeekNumber(53);
                while (calendarFirstDay.getTime().before(last_day_of_year)) {
                    day = generateDate(calendarFirstDay, formatter_EEEE, formatter_yyyMMdd);
                    week.getDays().add(day);
                    calendarFirstDay.add(Calendar.DAY_OF_YEAR, 1);
                }
                day = generateDate(calendarFirstDay, formatter_EEEE, formatter_yyyMMdd); // including last day of the year
                week.getDays().add(day);
                week.setTotalDays(week.getDays().size());
                year.getWeeks().add(week);
                return;
            }

            if (week.getWeekNumber() != calendarFirstDay.get(Calendar.WEEK_OF_YEAR)) {
                if (week.getWeekNumber() != currentWeek) { // add last day for normal week
                    calendarFirstDay.set(Calendar.DAY_OF_YEAR, calendarFirstDay.get(Calendar.DAY_OF_YEAR) - 1);   // MINUS 1
                    day = generateDate(calendarFirstDay, formatter_EEEE, formatter_yyyMMdd);
                    week.getDays().add(day);
                    calendarFirstDay.set(Calendar.DAY_OF_YEAR, calendarFirstDay.get(Calendar.DAY_OF_YEAR) + 1);   // ADD 1 
                }
                week.setTotalDays(week.getDays().size());
                week = new Week();
                week.setWeekNumber(calendarFirstDay.get(Calendar.WEEK_OF_YEAR));
                year.getWeeks().add(week);
            }
            if (week.getWeekNumber() == currentWeek || (week.getWeekNumber() != currentWeek && week.getDays().isEmpty())) {
                day = generateDate(calendarFirstDay, formatter_EEEE, formatter_yyyMMdd);
                week.getDays().add(day);
            }
            calendarFirstDay.add(Calendar.DAY_OF_YEAR, 1);
        }
        day = generateDate(calendarFirstDay, formatter_EEEE, formatter_yyyMMdd); // including last day of the year
        week.getDays().add(day);
        week.setTotalDays(week.getDays().size());
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
