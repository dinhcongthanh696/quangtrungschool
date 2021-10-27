<%-- 
    Document   : classstudentlistdetail
    Created on : Oct 24, 2021, 11:39:33 AM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>THPT Quang Trung-Hà Đông</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        Class : ${requestScope.class.classroom.classCode} <br/>
        Main Teacher : ${requestScope.class.homeroomTeacher.teacherCode}
        <table class="table table-striped table-bordered">
            <tr>
                <td>STT</td>
                <td>Student Code</td>
                <td>Full Name</td>
            </tr>
            <c:set var="stt" value="1"></c:set>
            <c:forEach items="${requestScope.class.students}" var="student">
                <tr>
                    <td>${stt}</td>
                    <td>${student.studentCode}</td>
                    <td>${student.fullname}</td>
                </tr>
                <c:set var="stt" value="${stt + 1}"></c:set>
            </c:forEach>
        </table>
        
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
