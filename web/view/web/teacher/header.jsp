<%-- 
    Document   : header
    Created on : Oct 16, 2021, 10:15:21 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung - Hà Đông</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
        <style>
            header{
                font-size: 20px;
            }
            
            .img-rounded{
                width: 100%;
                height: 160px;
            }
        </style>
    </head>
    <body>
        <header>
            <img src="/QuangTrungSchool/view/img/header.png" class="img-rounded">
            <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a href="#" class="navbar-brand">QT-HD</a>
                </div>
                <ul class="nav navbar-nav">
                    <li>
                        <a href="/QuangTrungSchool/teacher-home">
                        <span class="glyphicon glyphicon-home"></span>    Home
                        </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="/QuangTrungSchool/teacher-home?action=logout">
                            <span class="glyphicon glyphicon-log-out"></span>Logout
                        </a>
                    </li>
                    <li>
                        <a href="/QuangTrungSchool/teacher-detail">
                            <span class="glyphicon glyphicon-user"></span>Info
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
        </header>
    </body>
</html>
