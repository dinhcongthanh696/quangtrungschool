<%-- 
    Document   : home
    Created on : Sep 26, 2021, 2:09:43 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <title>THPT Quang Trung-Hà Đông</title>
        <style>
            .left{
                width: 40%;
                float: left;
                border: 1px solid black;
                border-radius: 5px;
            }
            .left h2{
                margin-top: 0px;
                background-color: #00FFFF;
                text-align: center;
                color: blue;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <div>
            <section class="left">
            <h2>Activities</h2>
            <ul class="list-group">
                <li class="list-group-item">
                    <a href="/QuangTrungSchool/student-schedule">
                        <span class="glyphicon glyphicon-plus"></span> Lịch học hàng tuần
                    </a>
                </li>
                <li class="list-group-item">
                    <a href="/QuangTrungSchool/student-attendance">
                        <span class="glyphicon glyphicon-plus"></span> Báo cáo điểm danh
                    </a>
                </li>
                <li class="list-group-item">
                    <a href="/QuangTrungSchool/student-class-liststudent">
                        <span class="glyphicon glyphicon-plus"></span> Danh Sách Lớp
                    </a>
                </li>
                <li class="list-group-item">
                    <a href="/QuangTrungSchool/student-class-liststudent">
                        <span class="glyphicon glyphicon-plus"></span> Báo cáo điểm
                    </a>
                </li>
            </ul>
        </section>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
