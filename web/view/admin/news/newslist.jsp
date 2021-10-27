<%-- 
    Document   : newslist
    Created on : Oct 27, 2021, 10:39:03 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            var gap = 2;

            function paging() {
                var currentPage = parseInt($("#curentPage").val());
                var totalPage = $("#totalPage").val();
                var query = $("#query").val();
                var totalSearchedNews = $("#totalSearchedNews").val();
                var newsperpage = $("#newsperpage").val();

                if (currentPage - gap > 1) {
                    $("#paging").append(
                            "<a id='1' href=/QuangTrungSchool/admin-news-list?query=" + query
                            + "&pageId=1&totalsearchednews=" + totalSearchedNews + "&newsperpage=" + newsperpage + "> 1 </a>"
                            );
                }
                for (let i = currentPage - gap; i <= currentPage; i++) {
                    if (i >= 1) {
                        $("#paging").append(
                                "<a id='" + i + "' href=/QuangTrungSchool/admin-news-list?query=" + query
                                + "&pageId=" + i + "&totalsearchednews=" + totalSearchedNews + "&newsperpage=" + newsperpage + ">" + i + "</a>"
                                );
                    }
                }

                for (let i = currentPage + 1; i <= currentPage + gap; i++) {
                    if (i <= totalPage) {
                        $("#paging").append(
                                "<a id='" + i + "' href=/QuangTrungSchool/admin-news-list?query=" + query
                                + "&pageId=" + i + "&totalsearchednews=" + totalSearchedNews + "&newsperpage=" + newsperpage + ">" + i + "</a>"
                                );
                    }
                }

                if (currentPage + gap < totalPage) {
                    $("#paging").append(
                            "<a id='" + totalPage + "' href=/QuangTrungSchool/admin-news-list?query=" + query
                            + "&pageId=" + totalPage + "&totalsearchednews=" + totalSearchedNews + "&newsperpage=" + newsperpage + "> " + totalPage + " </a>"
                            );
                }
            }

            function activePage() {
                var pageId = $("#curentPage").val();
                $("#" + pageId).css("background-color", "blue");
            }

            function changePerPage() {
                var newsperpage = $("#newsperpage").val();
                var query = $("#query").val();
                var totalsearchednews = $("#totalSearchedNews").val();
                window.location = "/QuangTrungSchool/admin-news-list?"
                        + "query=" + query + "&totalsearchednews=" + totalsearchednews + "&newsperpage=" + newsperpage;
            }

            function doEdit(no) {
                window.location = "/QuangTrungSchool/admin-news-update?no=" + no;
            }

            function doDelete(no) {
                var checked = confirm("Are you sure?");
                if (checked) {
                    $.ajax({
                        url: "/QuangTrungSchool/admin-news-delete",
                        type: "POST",
                        data: {
                            no: no
                        },
                        success: function () {
                            $("#no" + no).css("display", "none");
                        }
                    });
                }
            }
        </script>

        <style>
            .right div a{
                font-size: 20px;
                padding: 10px;
                border-radius: 8px;
                color: black;
            }

            .right{
                color: blue;
            }

            .right .form-control{
                width: 40%;
            }
            .right form .btn{
                margin-left: 10%;
                margin-top: 3%;
                margin-bottom: 3%;
                border-radius: 10px;
            }
        </style>
    </head>
    <body onload="paging();
            activePage()">
        <input type="hidden" id="curentPage" value="${requestScope.pageId}">
        <input type="hidden" id="totalPage" value="${requestScope.totalPage}">
        <input type="hidden" id="totalSearchedNews" value="${requestScope.totalsearchednews}">

        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>News List</h2>
                <label for="newsperpage" class="form-label">News Per Page : </label>
                <select id="newsperpage" onchange="changePerPage()" class="form-select">
                    <option value=""></option>
                <c:forEach begin="1" end="${requestScope.totalsearchednews}" var="number">
                    <option value="${number}" ${number eq requestScope.newsperpage ? "selected='selected'" : ""}>${number}</option>
                </c:forEach> 
            </select>
            <form action="admin-news-list" method="POST">
                <label for="query" class="form-label">Search : </label>
                <input type="text" required="required" id="query" name="query" value="${requestScope.query}" class="form-control">
                <button class="btn btn-primary">Search</button>
            </form>
            <table class="table table-bordered table-striped">
                <tr>
                    <th>Title</th>
                    <th>Content</th>
                    <th>Posted Date</th>
                    <th>Constructor</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${requestScope.piecesofnews}" var="news">
                    <tr id="${'no'.concat(news.no)}">
                        <td>${news.title}</td>
                        <td>${fn:length(news.content) > 50 ? fn:substring(news.content, 0, 50).concat('.....') : news.content}</td>
                        <td>${news.postedDate}</td>
                        <td>${news.account.username}</td>
                        <td>
                            <button class="btn btn-primary" value="${news.no}" onclick="doEdit(this.value)">
                                Edit
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-danger" value="${news.no}" onclick="doDelete(this.value)">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div id="paging">
                <h2>Pages</h2>
            </div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
