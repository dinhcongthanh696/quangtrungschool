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
                    <th>Teacher Code</th>
                    <th>Full Name</th>
                    <th>Address</th>
                    <th>Date of Birth</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Username</th>
                </tr>
                <tr>
                    <td>${sessionScope.teacher.teacherCode}</td>
                    <td>${sessionScope.teacher.fullname}</td>
                    <td>${(empty sessionScope.teacher.address) ? 'Unkown' : sessionScope.teacher.address}</td>
                    <td>${sessionScope.teacher.dob}</td>
                    <td>${(empty sessionScope.teacher.email) ? 'Unkown' : sessionScope.teacher.email}</td>
                    <td>${(empty sessionScope.teacher.phone) ? 'Unkown' : sessionScope.teacher.phone}</td>
                    <td>${sessionScope.teacher.account.username}</td>
                </tr>
            </table>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
