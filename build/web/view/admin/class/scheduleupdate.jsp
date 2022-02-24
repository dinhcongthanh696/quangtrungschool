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
                    <input type="hidden" name="classCode" value="${param.classCode}">
                Class Code : <input disabled="disabled" value="${param.classCode}" class="form-control">
                Teacher Code :  <input disabled="disabled" value="${empty param.teacherCode ? 'None' : param.teacherCode}" class="form-control"> 
                Course Code :   <input disabled="disabled" value="${param.courseCode}" class="form-control">
                <input type="hidden" name="year" value="${param.year}">
                Year :  <input disabled="disabled" value="${param.year}" class="form-control">    
                <input type="hidden" name="date" value="${requestScope.date}">
                Date :  <input disabled="disabled" value="${requestScope.date}" class="form-control"> 
                <input type="hidden" name="slot" value="${requestScope.slot}">
                Slot : <input disabled="disabled" value="${requestScope.slot}" class="form-control">
                <input type="hidden" name="semester" value="${param.semester}">
                Semester : <input disabled="disabled" value="${param.semester}" class="form-control">
                <input type="hidden" name="startDate" value="${param.startDate}">
                Start Date : <input disabled="disabled" value="${param.startDate}" class="form-control">
                <input type="hidden" name="endDate" value="${param.endDate}">
                End Date : <input disabled="disabled" value="${param.endDate}" class="form-control">
                <label for="teacher">Change Teacher : </label>
                <select id="teacher" name="teacher" class="form-select">
                    <option value="${param.teacherCode}" selected="selected">${empty param.teacherCode ? 'None' : param.teacherCode}</option>
                    <c:forEach items="${requestScope.teachers}" var="teacher">
                        <option value="${teacher.teacherCode}">
                            ${teacher.account.username}
                        </option>
                    </c:forEach>
                </select><br/>
                <c:if test="${param.type != 0}">
                    <label for="active">Active</label> <input type="radio" 
                                                   value="2" name="active" ${param.active == 2 ? "checked='checked'" : ""} id="active">
                <label for="closed">Closed</label> <input type="radio" value="1" name="active" ${param.active == 1 ? "checked='checked'" : ""} id="closed"><br/>
                </c:if>
                <input type="hidden" name="weekNumber" value="${param.weekNumber}">
                <button class="btn btn-primary">Save changes</button>
            </form>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
