<%-- 
    Document   : home
    Created on : Oct 16, 2021, 9:48:52 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung - Hà Đông giáo viên</title>
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
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
        <section class="left">
            <h2>Activities</h2>
            <ul class="list-group">
                <li class="list-group-item">
                    <a href="/QuangTrungSchool/teacher-schedule">
                        <i class="fas fa-plus"></i>Lịch giảng dạy hàng tuần
                    </a>
                </li>
            </ul>
        </section>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
