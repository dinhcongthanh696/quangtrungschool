<%-- 
    Document   : scheduleattendance
    Created on : Oct 20, 2021, 6:06:23 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung - Hà Đông</title>
        <style>
            table{
                color : blue;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <table class="table table-bordered table-hover table-striped">
                <h2>Attendance</h2>
                <tr>
                    <td>Student Code</td>
                    <td>Full Name</td>
                    <td>Attended</td>
                </tr>
            <c:set var="totalStudent" value=""></c:set>
            <form action="/QuangTrungSchool/teacher-schedule-attendance" method="POST"> 
                <input type="hidden" value="${requestScope.schedule.classroom.classCode}" name="classCode"> 
                <input type="hidden" value="${requestScope.schedule.slot}" name="slot"> 
                <input type="hidden" value="${requestScope.schedule.date}" name="date"> 
                <c:forEach items="${requestScope.schedule.studentattendances}" var="studentattendance">
                    <tr>
                        <td>
                            ${studentattendance.student.studentCode}
                        </td>
                        <td>
                            ${studentattendance.student.fullname}
                        </td>
                        <td>
                            <input type="radio" name="${studentattendance.student.studentCode}" 
                                value=""    ${studentattendance.status eq 1 ? "checked='checked'" : ""}> Attended
                            <input type="radio" name="${studentattendance.student.studentCode}" value="${studentattendance.student.studentCode}" ${studentattendance.status eq -1 ? "checked='checked'" : ""}> Absent
                        </td>
                    </tr>
                    <c:set var="totalStudent" value="${totalStudent.concat(studentattendance.student.studentCode).concat(',')}"></c:set>
                </c:forEach>
                <input type="hidden" name="totalStudent" value="${totalStudent}">   
            </table>
                <button class="btn btn-primary">Save Changes</button>
            </form>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
