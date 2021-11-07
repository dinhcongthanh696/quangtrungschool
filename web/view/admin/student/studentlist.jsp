<%-- 
    Document   : studentdetail
    Created on : Sep 28, 2021, 10:46:51 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            .right a{
                margin-right: 10px;
                border-radius: 8px;
                padding: 13px;
                text-decoration: none;
                color: black;
            }
            
            .right{
                color: blue;
            }
            
            .right input{
                width: 50%;
            }
            
            .right .btn-danger,.right .btn-primary,.right .btn-success{
                width: 90px;
                height: 43px;
            }
            
            .right form .btn-primary{
                width: 60px;
                height: 35px;
                margin-left: 20%;
                border-radius: 10px;
            }
        </style>
        <script>
            function doUpdate(studentCode) {
                window.location = "/QuangTrungSchool/admin-student-update?studentCode=" + studentCode;
            }

            function doDelete(studentCode) {
                var check = confirm("Are you sure?");
                if (check) {
                    $.ajax({
                        url: "/QuangTrungSchool/admin-student-delete",
                        type : 'GET',
                        data: {
                            studentCode: studentCode
                        },
                        success: function (response) {

                        }
                    });
                    $("#"+studentCode).css("display","none");
                }
            }
            
            function doTakeClass(studentCode){
                window.location = "/QuangTrungSchool/admin-student-class-add?studentCode=" + studentCode;
            }

            function activePage() {
                var pageId = $(".right input[type='hidden']").val();
                var currentPage = document.getElementById(pageId);
                currentPage.style.backgroundColor = "blue";
            }
        </script>
    </head>
    <body onload="activePage()">
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Student List</h2>
                <form action="/QuangTrungSchool/admin-student-list" method="POST">
                    <input type="hidden" value="${requestScope.pageId}">
                    <label for="query">Search: </label>
                    <input type="text" class="form-control" placeholder="Search...." id="query" name="query" value="${requestScope.query}"><br/>
                    <button type="submit" class="btn-primary">Search</button>
                </form>
                <table class="table table-hover table-striped">
                    <tr>
                        <td>Student Code</td>
                        <td>Full name</td>
                        <td>Address</td>
                        <td>Date of birth</td>
                        <td>Email</td>
                        <td>Phone</td>
                        <td>Username</td>
                        <td>Update</td>
                        <td>Delete</td>
                        <td>Taking Class</td>
                    </tr>
                <c:forEach items="${requestScope.searchedstudents}" var="student">
                    <tr id="${student.studentCode}">
                        <td>${student.studentCode}</td>
                        <td>${student.fullname}</td>
                        <td>${student.address}</td>
                        <td>${student.dob}</td>
                        <td>${student.email}</td>
                        <td>${student.phone}</td>
                        <td>${student.account.username}</td>
                        <td>
                            <button class="btn btn-primary" onclick="doUpdate(this.value);" value="${student.studentCode}"> Update </button>
                        </td>
                        <td>
                            <button class="btn btn-danger" onclick="doDelete(this.value)"  value="${student.studentCode}"> Delete </button>
                        </td>
                        <td>
                            <button class="btn btn-success" onclick="doTakeClass(this.value)" value="${student.studentCode}">Take Class</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <h2>Page</h2>
            <div>
                <c:forEach begin="1" end="${requestScope.totalPage}" var="page">
                    <a id="${page}" 
                        href="/QuangTrungSchool/admin-student-list?pageId=${page}&query=${requestScope.query}&totalsearchedstudents=${requestScope.totalsearchedstudents}">
                        ${page}
                    </a>
                </c:forEach>
            </div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
