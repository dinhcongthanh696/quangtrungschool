<%-- 
    Document   : header
    Created on : Sep 26, 2021, 10:21:23 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            .navbar{
                font-size: 23px;
            }
            
            .img-rounded{
                width: 100%;
                height: 160px;
            }
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
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
                         <a href="../QuangTrungSchool/web-student-home">
                            <span class="glyphicon glyphicon-home"></span>  Home
                         </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="../QuangTrungSchool/web-student-home?action=logout"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
                </ul>
            </div>
        </nav>
        </header>
    </body>
</html>
