<%-- 
    Document   : teacherdetail
    Created on : Oct 20, 2021, 8:43:33 AM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung - Hà Đông</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <div>
            <h2>Teacher Detail</h2>
            <table class="table table-bordered table-hover">
                <tr>
                    <th>Teacher Code</th><td>${sessionScope.teacher.teacherCode}</td>
                </tr>
                <tr>    
                    <th>Full Name</th>
                    <td>${sessionScope.teacher.fullname}</td>
                </tr>
                <tr>
                    <th>Address</th>
                    <td>${(empty sessionScope.teacher.address) ? 'Unkown' : sessionScope.teacher.address}</td>
                </tr>
                <tr>
                    <th>Date of Birth</th>
                    <td>${sessionScope.teacher.dob}</td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>${(empty sessionScope.teacher.email) ? 'Unkown' : sessionScope.teacher.email}</td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>${(empty sessionScope.teacher.phone) ? 'Unkown' : sessionScope.teacher.phone}</td>
                </tr>
                <tr>
                    <th>Username</th>
                    <td>${sessionScope.teacher.account.username}</td>
                </tr>
            </table>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
