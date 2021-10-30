<%-- 
    Document   : studentdetail
    Created on : Oct 23, 2021, 1:48:49 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>THPT Quang Trung-Hà Đông</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <table class="table table-bordered table-striped">
            <tr>
                <td>Student Code</td>
                <td>${sessionScope.student.studentCode}</td>
            </tr>
            <tr>
                <td>Full Name</td>
                <td>${sessionScope.student.fullname}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${sessionScope.student.email}</td>
            </tr>
            <tr>
                <td>Address</td>
                <td>${sessionScope.student.address}</td>
            </tr>
            <tr>
                <td>Phone</td>
                <td>${sessionScope.student.phone}</td>
            </tr>
            <tr>
                <td>Date of Birth</td>
                <td>${sessionScope.student.dob}</td>
            </tr>
        </table>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
