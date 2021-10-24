<%-- 
    Document   : login
    Created on : Sep 25, 2021, 10:52:04 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>THPT Quang Trung-Hà Đông</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
        <style>
            .form-control{
                width: 50%;
                margin-bottom: 30px;
            }
            form{
                margin-top: 50px;
                margin-left: 30%;
                font-size: 21px;
            }
            .img-rounded{
                width: 100%;
                height: 160px;
            }

            footer{
                margin-top: 93px; 
                text-align: right;
                background-color: #b7e5fc;
                height: 60px;
                border-radius: 5px;
                font-size: 15px;
                font-family: Arial,sans-serif;
            }
        </style>
    </head>
    <body>
        <img class="img-rounded" src="/QuangTrungSchool/view/img/header.png" alt="header">
        <form action="/QuangTrungSchool/login" method="POST">
            <div class="form-group">
                <label for="username">Username : </label> 
                <input type="text" name="username" id="username" pattern="[a-z0-9]{2,}" class="form-control"><br>
            </div>
            <div class="form-group">
                <label for="password" >Password : </label>
                <input type="password" id="password" name="password" pattern="[a-z0-9]{2,}" class="form-control"><br>
            </div>
            <div class="form-check">
                <input type="checkbox" id="remember" name="remember" checked class="form-check-input">
                <label for="remember" class="form-check-label"> Remember me </label>
            </div><br/>
            <label for="role">Select: </label>
            <select name="role" class="form-select" id="role">
                <c:forEach items="${requestScope.groups}" var="group">
                    <c:if test="${group.gid != 3}">
                        <option value="${group.gid}">
                            ${group.gname}
                        </option>
                    </c:if>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">Login</button> 
        </form>
        <footer>
            Fanpage:   <a href="https://www.facebook.com/HumansofQuangTrung/" target="_blank"><i class="fab fa-facebook fa-2x">Humans of Quang Trung </i></a>
        </footer>
    </body>
</html>
