<%-- 
    Document   : classlist
    Created on : Nov 2, 2021, 8:47:20 AM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <style>
            .right{
                color: blue;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
        <section class="right">
            <h2>Class List</h2>
            <table class="table table-bordered table-striped">
                <tr>
                    <td>Class</td>
                    <td>Department</td>
                </tr>
            <c:forEach items="${requestScope.classes}" var="class">
                <tr>
                    <td>${class.classCode}</td>
                    <td>${class.department.departmentName}</td>
                </tr>
            </c:forEach>
            </table>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
