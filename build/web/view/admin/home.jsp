<%-- 
    Document   : home
    Created on : Sep 27, 2021, 6:54:11 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <style>
            h2{
                margin-top: 0px;
                background-color: #00FFFF;
                text-align: center;
                color: blue;
            }
            
            .news{
                margin-top: 5%;
                border-radius: 10px;
                border: 1px solid black;
            }
            
            .fa-newspaper{
                color: green;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <section class="right">
                <p>Admin page cho việc cập nhật thông tin sinh viên,giảng viên và tin tức,.... cho Trường THPT-Quang Trung Hà Đông</p>
                <section class="news">
                    <h2>News</h2>
                    <ul class="list-group">
                        <c:forEach items="${sessionScope.account.group_of_news}" var="news">
                        <li class="list-group-item">
                            <a href="/QuangTrungSchool/admin-news-detail?no=${news.no}">
                                <i class="fa fa-newspaper"></i>  ${news.title} by ${news.account.username} - ${news.postedDate}  
                            </a>
                        </li>
                    </c:forEach>
                    </ul>
            </section>
        </section>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
