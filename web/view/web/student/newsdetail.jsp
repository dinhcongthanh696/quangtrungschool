<%-- 
    Document   : newsdetail
    Created on : Oct 28, 2021, 10:19:21 AM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>THPT Quang Trung-Hà Đông</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <h2>News Detail</h2>
        <table class="table table-bordered">
            <tr>
                <td>Title</td>
                <td>${requestScope.news.title}</td>
            </tr>
            <tr>
                <td>Content</td>
                <td>${requestScope.news.content}</td>
            </tr>
        </table>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
