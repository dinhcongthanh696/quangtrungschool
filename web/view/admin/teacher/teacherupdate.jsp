<%-- 
    Document   : teacherupdate
    Created on : Oct 7, 2021, 6:30:02 PM
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
            
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
        <section class="right">
            <form action="/QuangTrungSchool/admin-teacher-update" method="POST">
                <input type="hidden" name="username" value="${requestScope.teacher.account.username}">
                <h2>Update Student</h2>
                <input type="hidden" value="${requestScope.teacher.teacherCode}" name="teacherCode">  
              Teacher code :  <input disabled="disabled" value="${requestScope.teacher.teacherCode}" class="form-control"> <br/>
              Full name :  <input disabled="disabled" value="${requestScope.teacher.fullname}" class="form-control"> <br/>
              Date of Birth :  <input disabled="disabled" value="${requestScope.teacher.dob}" class="form-control"> <br/>
              <label for="address">Address</label>
              <input type="text" value="${requestScope.teacher.address}" id="address" name="address" class="form-control"><br/>
              <label for="email">Email</label>
              <input type="text" value="${requestScope.teacher.email}" id="email" name="email" class="form-control"><br/>
              <label for="phone">Phone</label>
              <input type="text" value="${requestScope.teacher.phone}" id="phone" name="phone" class="form-control" pattern="[0-9]{10}|[0-9]*"><br/>
              <c:set var="isadmin" value="false"></c:set>
              <c:forEach items="${requestScope.teacher.account.groups}" var="group">
                  <c:if test="${group.gid == 4}">
                      <c:set var="isadmin" value="true"></c:set>
                  </c:if>
              </c:forEach>
              <label for="isadmin">  Is Admin : </label>
              <input type="checkbox" ${isadmin ? "checked='checked'" : ""} id="isadmin" name="isadmin" value="true"><br/>
              <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
