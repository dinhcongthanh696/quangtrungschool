/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Student;

import DAO.AbstractStudentDAO;
import DAO.StudentDAO;
import Login.BaseAuthorization;
import Model.Student;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "StudentUpdateController", urlPatterns = {"/admin-student-update"})
public class StudentUpdateController extends BaseAuthorization {
    private final AbstractStudentDAO studentDAO;
    
    public StudentUpdateController(){
        studentDAO = new StudentDAO();
    }
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentCode = request.getParameter("studentCode");
        Student student = studentDAO.getById(studentCode);
        request.setAttribute("student", student);
        request.getRequestDispatcher("view/admin/student/studentupdate.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentCode = request.getParameter("studentCode");
        String fullname = request.getParameter("fullname");
        String address = request.getParameter("address");
        Date dob = Date.valueOf(request.getParameter("dob"));
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        Student student = new Student();
        student.setStudentCode(studentCode);
        student.setFullname(fullname);
        student.setAddress(address);
        student.setEmail(email);
        student.setDob(dob);
        student.setPhone(phone);
        studentDAO.update(student);
        response.sendRedirect("admin-student-list?pageId=1");
    }

}
