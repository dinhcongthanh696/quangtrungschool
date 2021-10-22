<%-- 
    Document   : teacherclasses
    Created on : Oct 21, 2021, 5:14:31 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung - Hà Đông</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <table class="table table-bordered table-hover">
            <tr>
                <th>STT</th>
                <th>Class</th>
            </tr>
            <c:set var="stt" value="1"></c:set>
            <c:forEach items="${sessionScope.teacher.classes}" var="class">
                <tr>
                    <td>${stt}</td>
                    <td>
                        <a href="/QuangTrungSchool/class-student-list?index=${stt}">
                        Class :  ${class.classroom.classCode} - Year : ${class.year} - Semester : ${class.semester} <br/>
                        Start date : ${class.startDate} - End Date : ${class.endDate}
                        </a>
                    </td>    
                    <c:set var="stt" value="${stt + 1}"></c:set>
                </tr>
            </c:forEach>
        </table>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
