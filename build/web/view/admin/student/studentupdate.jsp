<%-- 
    Document   : studentupdate
    Created on : Sep 29, 2021, 12:09:36 AM
    Author     : My Computer
--%>

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
                <form action="/QuangTrungSchool/admin-student-update" method="POST">   
                    <h2>Update Student</h2>
                    Student Code:  ${requestScope.student.studentCode}<input type="hidden" name="studentCode" value="${requestScope.student.studentCode}"> 
                <br/>
                <label for="fullname">Student full name:   </label>
                <input type="text" class="form-control" name="fullname" id="fullname" pattern="[A-Za-z ]*" value="${requestScope.student.fullname}"><br>
                <label for="dob">Student Date of Birth:   </label>
                <input type="date" name="dob" id="dob" class="form-control" required="required" value="${requestScope.student.dob}"><br>
                Username:  ${requestScope.student.account.username}
                <br>
                <label for="email">Email:    </label>
                <input type="text" class="form-control" name="email" id="email" value="${requestScope.student.email}"><br>
                <label for="address">Address:     </label>
                <input type="text" class="form-control"  name="address" id="address" value="${requestScope.student.address}"><br>
                <label for="phone">Phone:     </label>
                <input type="text" class="form-control"  name="phone" id="phone" pattern="[0-9]{10}" value="${requestScope.student.phone}"><br>
                <button class="btn btn-primary" type="submit" id="submit">Save changes</button>
            </form>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
