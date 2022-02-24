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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
        <style>
            .right a{
                margin-right: 10px;
                border-radius: 8px;
                padding: 13px;
                text-decoration: none;
                color: black;
            }

            .right tbody tr td:nth-child(2){
                padding: 20px;
            }

            .right tbody tr td:nth-child(3){
                padding: 20px;
            }

            .right tbody tr td:nth-child(4){
                padding: 20px;
            }

            .right tbody tr td:nth-child(5){
                padding: 20px;
            }

            .right tbody tr td:nth-child(6){
                padding: 20px;
            }

            .right{
                color: blue;
            }

            h2{
                text-align: center;
                color: #00FFFF;
            }

            .right .table input{
                width: 100%;
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
                var from = 2;
                var to = 6;
                var attributes = [];
                for (var i = from; i <= to; i++) {
                    attributes[i] = $("#" + studentCode + " > td:nth-child(" + i + ")").html();
                    $("#" + studentCode + " > td:nth-child(" + i + ")").empty();
                }
                $("#" + studentCode + " > td:nth-child(2)").append(
                        "<input type='text' class='form-control' pattern='[A-Za-z ]*' value=' " + attributes[2] + " ' >"
                        );

                $("#" + studentCode + " > td:nth-child(3)").append(
                        "<input type='text' class='form-control'  value=' " + attributes[3] + " ' >"
                        );
                $("#" + studentCode + " > td:nth-child(4)").append(
                        "<input type='date' class='form-control' required='required'>"
                        );
                $("#" + studentCode + " > td:nth-child(4) input").attr("value", attributes[4]);

                $("#" + studentCode + " > td:nth-child(5)").append(
                        "<input type='email' class='form-control' required='required' value=' " + attributes[5] + " ' >"
                        );
                $("#" + studentCode + " > td:nth-child(6)").append(
                        "<input type='text' class='form-control' value=' " + attributes[6] + " ' >"
                        );
                $("#" + studentCode + " > td:nth-child(8)").empty();
                $("#" + studentCode + " > td:nth-child(8)").append(
                        "<button class='btn btn-success' onclick='doConfirm(this.value);' value='" + studentCode + "'> Confirm </button>"
                        );
            }

            function doConfirm(studentCode) {
                var check = confirm("Are you really sure? ");
                var phone = $("#" + studentCode + " > td:nth-child(6) input").val();
                if (phone.length == 0 || phone.match("[0-9]{10}")) {
                    if (check) {
                        var student = {
                            studentCode: studentCode
                            , fullname: $("#" + studentCode + " > td:nth-child(2) input").val()
                            , address: $("#" + studentCode + " > td:nth-child(3) input").val()
                            , dob: $("#" + studentCode + " > td:nth-child(4) input").val()
                            , email: $("#" + studentCode + " > td:nth-child(5) input").val()
                            , phone: $("#" + studentCode + " > td:nth-child(6) input").val()
                        };
                        $.ajax({
                            url: "/QuangTrungSchool/admin-student-list",
                            type: "PUT",
                            contentType: 'application/json',
                            data: JSON.stringify(student),
                            success: function (response) {
                                var from = 2;
                                var to = 6;
                                var temp = "";
                                for (var i = from; i <= to; i++) {
                                    temp = $("#" + studentCode + " > td:nth-child(" + i + ") input").val();
                                    $("#" + studentCode + " > td:nth-child(" + i + ")").empty();
                                    $("#" + studentCode + " > td:nth-child(" + i + ")").append(temp);
                                }
                            }
                        });

                        $("#" + studentCode + " > td:nth-child(8)").empty();
                        $("#" + studentCode + " > td:nth-child(8)").append(
                                "<button class='btn btn-primary' onclick='doUpdate(this.value);' value='" + studentCode + "'> Update </button>"
                                );
                    }
                }else{
                    alert("Phone must be have 10 numbers");
                }
            }

            function doDelete(value) {
                var split = value.split(" ");
                var studentCode = split[0];
                var username = split[1];
                var check = confirm("Are you sure?");
                if (check) {
                    $.ajax({
                        url: "/QuangTrungSchool/admin-student-list?studentCode=" + studentCode + "&username=" + username,
                        type: 'DELETE',
                        success: function (response) {
                            $("#" + studentCode).css("display", "none");
                        }
                    });
                }
            }

            function doTakeClass(studentCode) {
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
        <div>
            <img src="/QuangTrungSchool/view/img/header.png" class="img-rounded">
        </div>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a href="#" class="navbar-brand">QT-HD</a>
                </div>
                <ul class="nav navbar-nav">
                    <li>
                        <a href="/QuangTrungSchool/admin-home">
                            <span class="glyphicon glyphicon-home"></span>  Home
                        </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/QuangTrungSchool/admin-home?action=logout"> <span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
        </nav>
        <h2>Danh sách học sinh</h2>
        <section class="right">
            <form action="/QuangTrungSchool/admin-student-list" method="POST">
                <input type="hidden" value="${requestScope.pageId}">
                <label for="query">Search: </label>
                <input type="text" class="form-control" placeholder="Search...." id="query" name="query" value="${requestScope.query}"><br/>
                <button type="submit" class="btn-primary">Search</button>
            </form>
            <table class="table table-hover table-striped">
                <thead>
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
                </thead>
                <tbody>
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
                                <button class="btn btn-danger" onclick="doDelete(this.value)"  value="${student.studentCode.concat(' ').concat(student.account.username)}"> Delete </button>
                            </td>
                            <td>
                                <button class="btn btn-success" onclick="doTakeClass(this.value)" value="${student.studentCode}">Take Class</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3>Page</h3>
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
