<%-- 
    Document   : newsdetail
    Created on : Oct 28, 2021, 10:06:31 AM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung - Hà Đông giáo viên</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <h2>News Detail</h2>
        <table class="table table-bordered table-hover table-striped">
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
