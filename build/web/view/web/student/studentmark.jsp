<%-- 
    Document   : studentmark
    Created on : Oct 24, 2021, 6:27:41 PM
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
        <c:set var="classindex" value="1"></c:set>
        <table class="table table-bordered">
            <tr>
                <td>CLASS</td>
                <td></td>
            </tr>
            <c:forEach items="${sessionScope.student.classes}" var="class">
            <tr>
                <td>
                    Class : ${class.classroom.classCode} - Main Teacher : ${class.homeroomTeacher.teacherCode} <br/>
                    FROM : ${class.startDate} -  TO : ${class.endDate} - Year : ${class.year} - Semester : ${class.semester}
                </td>
                <td>
                    <ul>
                        <form action="/QuangTrungSchool/student-class-mark" method="POST">
                            <input type="hidden" name="classindex" value="${classindex}">
                            <button class="btn btn-primary">Detail</button>
                        </form>

                </td>
            </tr>
            <c:set var="classindex" value="${classindex + 1}"></c:set>
        </c:forEach>
    </table>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
