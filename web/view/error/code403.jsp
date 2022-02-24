<%-- 
    Document   : code403
    Created on : Nov 24, 2021, 4:45:29 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Warning</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
        <style>
            h1{
                text-align: center;
                color: red;
            }
            
            div{
                text-align: center;
            }
        </style>
        <script>
            function doDirection(roleNumber){
                var url = "";
                if(roleNumber === 1){
                    url = "/QuangTrungSchool/web-student-home";
                }else if(roleNumber === 2){
                    url = "/QuangTrungSchool/teacher-home";
                }else{
                    url = "/QuangTrungSchool/admin-home";
                }
                window.location = url;
            }
        </script>
    </head>
    <body>
        <div>
            <img src="https://bhldnhatduong.vn/wp-content/uploads/2018/12/khong-phan-su-mien-vao476.jpg" alt="warning">
            <h1>Your access is denied hehehehe</h1>
        </div>
        <c:choose>
            <c:when test="${sessionScope.account.roleNumber eq 1}">
                <button class="btn-primary" onclick="doDirection(1)">Back to student home page</button>
            </c:when>
            <c:when test="${sessionScope.account.roleNumber eq 2}">
                <button class="btn-primary" onclick="doDirection(2)">Back to teacher home page</button>
            </c:when>
            <c:otherwise>
                <button class="btn-primary" onclick="doDirection(4)">Back to admin home page</button>
            </c:otherwise>

        </c:choose>
    </body>
</html>
