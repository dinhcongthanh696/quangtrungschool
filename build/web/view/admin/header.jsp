<%-- 
    Document   : header
    Created on : Sep 27, 2021, 6:32:12 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
        <style>
            div{
                height: 150px;
            }
            div img{
                width: 100%;
                height: 150px;
            }

            img{
                height: 80px;
            }

            .left {
                float: left;
                width: 25%;
                text-align: left;
                border-right: 3px solid #b7e5fc;
            }

            .left ul li {
                margin-top: 15px;
                list-style-type: circle;
                color: orange;
            }
            
            .left ul li a{
                width: 55%;
            }
            
            .left ul li li{
                list-style-type: none;
            }
            
            .left ul li:nth-child(2) div{
                margin-top: 85px;
            }
            
            .left ul{
                padding-left: 0px;
            }
            

            .right {
                float: left;
                width: 57%;
                margin-left: 2%;
            }

            .right h1 {
                color: orange;
                font-weight: 100;
                margin-bottom: 5px;
            }

            #list1,#list2,#list3,#list4{
                display: none;
                font-size: 25px;
                text-decoration: none;
            }

            .list-group-item{
                font-size: 15px;
                width: 95%;
            }

            .navbar{
                height: 60px;
                font-size: 23px; 
                margin-bottom: 70px;
            }
            
            .fas{
                color: blue;
                font-size: 15px;
            }
            
            .img-rounded{
                width: 100%;
                height: 160px;
            }
        </style>
        <script src="/QuangTrungSchool/view/js/functions.js"></script>
    </head>
    <body>
        <div>
            <img src="/QuangTrungSchool/view/img/header.png" class="img-rounded">
        </div>
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a href="#" class="navbar-brand">QT-HD</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li><a href="/QuangTrungSchool/admin-home">Home</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="/QuangTrungSchool/admin-home?action=logout"> <span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                    </ul>
                </div>
            </nav>
        <section class="left">
            <ul>
                <li>
                    <div>
                        <button class="btn btn-primary" type="button" onclick="displayElement('list2')">Thêm  <span class="caret"></span></button>
                        <ul id="list2" class="list-group">
                           <li> <a href="/QuangTrungSchool/admin-student-add" class="list-group-item"><i class="fas fa-plus"></i>  Student</a>  </li>
                            <li>  <a href="/QuangTrungSchool/admin-teacher-add" class="list-group-item"><i class="fas fa-plus"></i>  Teacher</a> </li>
                            <li>  <a href="/QuangTrungSchool/admin-classyearsemester-add" class="list-group-item"><i class="fas fa-plus"></i>Class Year Semester</a> </li>
                        </ul>
                    </div>
                </li>
                <li>
                    <div>
                        <button class="btn btn-primary" type="button" onclick="displayElement('list3')">Danh sách<span class="caret"></span></button>
                        <ul id="list3" class="list-group">
                              <a href="/QuangTrungSchool/admin-student-list?pageId=1" class="list-group-item"><i class="fas fa-plus"></i>  Student</a>
                              <a href="/QuangTrungSchool/admin-teacher-list?pageId=1" class="list-group-item"><i class="fas fa-plus"></i>  Teacher</a>
                              <a href="/QuangTrungSchool/admin-classyearsemester-list?pageId=1" class="list-group-item"><i class="fas fa-plus"></i>Class Year Semester</a>
                              
                        </ul>
                    </div>
                </li>
            </ul>
        </section>

    </body>
</html>
