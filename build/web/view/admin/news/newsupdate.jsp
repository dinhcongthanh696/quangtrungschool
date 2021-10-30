<%-- 
    Document   : newsupdate
    Created on : Oct 28, 2021, 12:36:19 AM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
    </head>
    <body>
       <jsp:include page="../header.jsp"></jsp:include>
       <section class="right">
       <form action="/QuangTrungSchool/admin-news-update" method="POST">
            <input type="hidden" name="no" value="${requestScope.news.no}">
            <label for="title" class="form-label">Title</label>
            <input type="text" id="title" name="title" required="required" value="${requestScope.news.title}" class="form-control" placeholder="Enter...">
            <label for="content" class="form-label">Content : </label>
            <textarea rows="20" cols="10" name="content" id="content" placeholder="Enter..." class="form-control">${requestScope.news.content}
            </textarea>
            <button type="submit" class="btn btn-primary">Save changes</button>
       </form>
       </section>
       <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
