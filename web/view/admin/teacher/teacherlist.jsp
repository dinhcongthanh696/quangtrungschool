<%-- 
    Document   : teacherlist
    Created on : Oct 7, 2021, 7:04:30 PM
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
            .right div a{
                font-size: 20px;
                padding: 10px;
                border-radius: 8px;
                color: black;
            }

            .right{
                color: blue;
            }

            .right .form-control{
                width: 40%;
            }
            .right form .btn{
                margin-left: 15%;
            }
        </style>
        <script>
            function activePage() {
                var pageId = $(".right input[type='hidden']").val();
                $("#" + pageId).css("background-color", "blue");
            }

            function doUpdate(teacherCode) {
                window.location = "/QuangTrungSchool/admin-teacher-update?teacherCode=" + teacherCode;
            }

            function doDelete(teacherCode) {
                var check = confirm("Are you sure?");
                if (check) {
                    $.ajax({
                        url: "/QuangTrungSchool/admin-teacher-delete",
                        type: 'GET',
                        data: {
                            teacherCode: teacherCode
                        },
                        success: function (response) {

                        }
                    });
                    $("#" + teacherCode).css("display", "none");
                }
            }
        </script>
    </head>
    <body onload="activePage()">
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Teacher List</h2>
                <form action="/QuangTrungSchool/admin-teacher-list" method="POST">
                    <label for="query">Search: </label>
                    <input type="hidden" value="${requestScope.pageId}">
                <input type="text" class="form-control" id="query" name="query" value="${requestScope.query}" placeholder="Search..."><br/>
                <button type="submit" class="btn btn-primary">Search</button>
                </form>
            <table class="table table-hover table-striped">
                <tr>
                    <td>Teacher Code</td>
                    <td>Full Name</td>
                    <td>Address</td>
                    <td>Date of birth</td>
                    <td>Email</td>
                    <td>Phone</td>
                    <td>Username</td>
                    <td></td>
                    <td></td>
                </tr>
                <c:forEach items="${requestScope.teachers}" var="teacher">
                    <tr id="${teacher.teacherCode}">
                        <td>${teacher.teacherCode}</td>
                        <td>${teacher.fullname}</td>
                        <td>${teacher.address}</td>
                        <td>${teacher.dob}</td>
                        <td>${teacher.email}</td>
                        <td>${teacher.phone}</td>
                        <td>${teacher.account.username}</td>
                        <td>
                            <button class="btn btn-primary" onclick="doUpdate(this.value)" value="${teacher.teacherCode}">Edit</button>
                        </td>
                        <td>
                            <button class="btn btn-danger" onclick="doDelete(this.value)" value="${teacher.teacherCode}">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div>
                <h2>Pages</h2>
                <c:forEach begin="1" end="${requestScope.totalPage}" var="page">
                    <a id="${page}" href="/QuangTrungSchool/admin-teacher-list?pageId=${page}&query=${requestScope.query}">${page}</a>
                </c:forEach>
            </div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
