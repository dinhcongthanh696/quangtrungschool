<%-- 
    Document   : classstudentlist
    Created on : Oct 21, 2021, 5:54:57 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung - Hà Đông</title>
        <script>
            function doEdit(studentIndex){
                var classIndex = document.getElementById("classindex").value;
                window.location = "/QuangTrungSchool/student-mark?classindex="+classIndex
                        +"&studentindex="+studentIndex;
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <input type="hidden" name="classindex" value="${requestScope.classindex}" id="classindex">
        <h2>Class ${requestScope.class.classroom.classCode}</h2>
        <table class="table table-bordered table-hover">
            <tr>
                <th>STT</th>
                <th>Student Code</th>
                <th>Full Name</th>
                <th></th>
            </tr>
            <c:set var="stt" value="1"></c:set>
            <c:forEach items="${requestScope.class.students}" var="student">
                <tr>
                    <td>${stt}</td>
                    <td>${student.studentCode}</td>
                    <td>${student.fullname}</td>
                    <td>
                        <button class="btn btn-primary" onclick="doEdit(this.value)" value="${stt}">
                            Edit Mark
                        </button>
                    </td>
                </tr>
                <c:set var="stt" value="${stt+1}"></c:set>
            </c:forEach>
        </table>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
