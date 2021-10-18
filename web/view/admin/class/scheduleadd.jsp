<%-- 
    Document   : scheduleadd
    Created on : Oct 15, 2021, 11:52:42 AM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <form action="/QuangTrungSchool/admin-classyearsemester-schedule-add" method="POST">  
                    <input type="hidden" name="classCode" value="${param.classCode}">
                Class Code : <input disabled="disabled" value="${param.classCode}" class="form-control">
                <input type="hidden" name="year" value="${param.year}">
                Year : <input disabled="disabled" value="${param.year}" class="form-control">
                <input type="hidden" name="slot" value="${param.slot}">
                Slot : <input disabled="disabled" value="${param.slot}" class="form-control">
                <input type="hidden" name="date" value="${param.day}">
                Date : <input disabled="disabled" value="${param.day}" class="form-control">
                <input type="hidden" name="semester" value="${param.semester}">
                Semester : <input disabled="disabled" value="${param.semester}" class="form-control">
                <label for="course">Course : </label>
                <select name="course" id="course" class="form-select">
                    <c:forEach items="${requestScope.courses}" var="course">
                        <option value="${course.courseCode}">${course.courseName}</option>
                    </c:forEach>
                </select> <br/>
                <label for="teacher">Teacher : </label>
                <select name="teacher" id="teacher" class="form-select">
                    <c:forEach items="${requestScope.teachers}" var="teacher">
                        <option value="${teacher.teacherCode}">${teacher.account.username}</option>
                    </c:forEach>
                </select><br/>
                <button type="submit" class="btn btn-primary">Save changes</button>
            </form>  


        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
