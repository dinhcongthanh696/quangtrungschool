<%-- 
    Document   : scheduleupdate
    Created on : Oct 16, 2021, 5:15:36 PM
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
            <h2>Update Schedule</h2>
            <form action="/QuangTrungSchool/admin-classyearsemester-schedule-update" method="POST">
                <input type="hidden" name="classCode" value="${requestScope.classCode}">
            Class Code : <input disabled="disabled" value="${requestScope.classCode}" class="form-control">
            Teacher Code :  <input disabled="disabled" value="${requestScope.teacherCode}" class="form-control"> 
            <input type="hidden" name="year" value="${requestScope.year}">
            Year :  <input disabled="disabled" value="${requestScope.year}" class="form-control">    
            <input type="hidden" name="date" value="${requestScope.date}">
            Date :  <input disabled="disabled" value="${requestScope.date}" class="form-control"> 
                <input type="hidden" name="slot" value="${requestScope.slot}">
            Slot : <input disabled="disabled" value="${requestScope.slot}" class="form-control">
            <input type="hidden" name="semester" value="${requestScope.semester}">
            Semester : <input disabled="disabled" value="${requestScope.semester}" class="form-control">
            <label for="teacher">Teacher : </label>
            <select id="teacher" name="teacher" class="form-select">
                <c:forEach items="${requestScope.teachers}" var="teacher">
                    <option value="${teacher.teacherCode}">${teacher.account.username}</option>
                </c:forEach>
            </select><br/>
            <button class="btn btn-primary">Save changes</button>
            </form>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
