<%-- 
    Document   : studentmarkdetail
    Created on : Oct 24, 2021, 6:38:19 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>THPT Quang Trung-Hà Đông</title>
        <style>
            .table tr:last-child {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <table class="table table-bordered table-striped table-hover">
            <tr>
                <th>Course</th>
                <th>Mark</th>
            </tr>
        <c:forEach items="${sessionScope.student.studentcourses}" var="studentcourse">
            <tr>
                <td>${studentcourse.course.courseCode}</td>
                <td>
                    <c:choose>
                        <c:when test="${studentcourse.totalScore == -1 }">
                            <span style="color: green">PASS</span>
                        </c:when>
                        <c:when test="${studentcourse.totalScore == -2 }">
                            <span style="color: red">NOT PASS</span>
                        </c:when>  
                        <c:otherwise>
                            ${studentcourse.totalScore}
                        </c:otherwise>    
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
            <tr>
                <td colspan="2">
                <c:choose>
                    <c:when test="${requestScope.averageScore eq -1 or requestScope.averageScore < 5 and requestScope.averageScore != 0}">
                        <span style="color: red">STATUS  :  ĐÚP </span>
                    </c:when> 
                    <c:when test="${requestScope.averageScore eq 0 }">
                        <span style="color : blue">STATUS  :  Đang học</span>
                    </c:when>
                    <c:when test="${requestScope.averageScore >= 8}">
                      ${requestScope.averageScore} - STATUS  :  <span style="color : green"> Học Sinh giỏi</span>
                    </c:when>   
                    <c:when test="${requestScope.averageScore >= 6.5 and requestScope.averageScore <8}">
                       ${requestScope.averageScore} - STATUS  :  <span style="color : blue">Học sinh khá</span>
                    </c:when>
                        <c:when test="${requestScope.averageScore >= 5 and requestScope.averageScore < 6.5}">
                        ${requestScope.averageScore} - STATUS  :  <span style="color : yellow">Học sinh trung bình</span>
                    </c:when>    
                </c:choose>
                </td>
            </tr>
        </table>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
