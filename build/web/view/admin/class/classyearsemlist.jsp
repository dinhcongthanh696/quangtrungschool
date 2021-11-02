<%-- 
    Document   : classyearsemlist
    Created on : Oct 7, 2021, 9:56:45 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            var gap = 2;
            function activePage() {
                var pageId = $("#pageId").val();
                $("#" + pageId).css("background-color", "blue");
            }

            function changePerPage() {
                var classperpage = $("#classperpage").val();
                var query = $("#query").val();
                var totalsearchedclasses = $("#totalsearchedclasses").val();
                window.location = "/QuangTrungSchool/admin-classyearsemester-list?"
                        + "query=" + query + "&totalsearchedclasses=" + totalsearchedclasses + "&classperpage=" + classperpage;
            }

            function paging(container) {
                var pageId = parseInt($("#pageId").val());
                var totalsearchedclasses = $("#totalsearchedclasses").val();
                var query = $("#query").val();
                var totalPage = $("#totalpage").val();
                if (pageId - gap > 1) {
                    $("#" + container).append(
                            "<a class='navigator' href=/QuangTrungSchool/admin-classyearsemester-list"
                            + "?pageId=" + 1 + "&query=" + query + "&totalsearchedclasses=" + totalsearchedclasses + ">"
                            + " 1  </a>   ..."
                            );
                }
                for (let i = pageId - gap; i <= pageId ; i++) {
                    if (i > 0) {
                        $("#" + container).append(
                                "<a id = \"" + i + "\" href=/QuangTrungSchool/admin-classyearsemester-list"
                                + "?pageId=" + i + "&query=" + query + "&totalsearchedclasses=" + totalsearchedclasses + ">"
                                + i + "</a>"
                                );
                    }
                }
                for (let i = (pageId + 1); i <= pageId + gap; i++) {
                    if (i <= totalPage) {
                        $("#" + container).append(
                                "<a id = \" " + i + "\" href=/QuangTrungSchool/admin-classyearsemester-list"
                                + "?pageId=" + i + "&query=" + query + "&totalsearchedclasses=" + totalsearchedclasses + ">"
                                + i + "</a>"
                                );
                    }
                }

                if (pageId + gap < totalPage) {
                    $("#" + container).append(
                            "...   <a class='navigator' href=/QuangTrungSchool/admin-classyearsemester-list"
                            + "?pageId=" + totalPage + "&query=" + query + "&totalsearchedclasses=" + totalsearchedclasses + ">"
                            + totalPage + " </a>"
                            );
                }
            }
        </script>
        <style>
            .right{
                color: blue;
            }

            .right a{
                margin-right: 10px;
                border-radius: 8px;
                padding: 13px;
                text-decoration: none;
                color: black;
            }

            .right input{
                width: 50%;
            }

            .right form .btn-primary{
                width: 80px;
                height: 35px;
                margin-left: 20%;
                border-radius: 10px;
            }
            
            .navigator{
                border: 1px solid black;
                border-radius: 5px;
                padding: 7px;
            }
        </style>
    </head>
    <body onload="paging('paging');activePage()">
        <jsp:include page="../header.jsp"></jsp:include>
        <input type="hidden" id="totalsearchedclasses" value="${requestScope.totalsearchedclasses}">
        <section class="right">
            <h2>Classes By Year Semester List</h2>
            <label for="classperpage">Class Per Page : </label>
            <select id="classperpage" name="classperpage" onchange="changePerPage()">
                <option value=""></option>
                <c:forEach begin="1" end="${requestScope.totalsearchedclasses}" var="num">
                    <option value="${num}" ${ (num eq requestScope.classperpage ) ? "selected='selected'" : "" }>${num}</option>
                </c:forEach>
            </select>
            <form action="/QuangTrungSchool/admin-classyearsemester-list" method="POST">
                <label for="query">Search: </label>
                <input type="hidden" value="${requestScope.pageId}" id="pageId">
                <input type="text" class="form-control" id="query" name="query" placeholder="Search..." value="${param.query}"><br/>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
            <table class="table table-hover table-bordered">
                <thead>
                    <tr>
                        <td>Class Code</td>
                        <td>Year</td>
                        <td>Semester</td>
                        <td>Start Date</td>
                        <td>End Date</td>
                        <td>Homeroom Teacher</td>
                        <td>Schedule</td>
                    </tr>
                </thead>
                <!-- Display Classes by year and semester-->    
                <tbody>
                    <c:forEach items="${requestScope.classyearsemesters}" var="cys">
                        <tr>
                            <td>${cys.classroom.classCode}</td>
                            <td>${cys.year}</td>
                            <td>${cys.semester}</td>
                            <td>${cys.startDate}</td>
                            <td>${cys.endDate}</td>
                            <td>
                                ${empty cys.homeroomTeacher.teacherCode  ? 'Unkown' :  cys.homeroomTeacher.teacherCode}
                                |  <a href="/QuangTrungSchool/admin-classyearsemester-update?classCode=${cys.classroom.classCode}&year=${cys.year}&semester=${cys.semester}&teacherCode=${cys.homeroomTeacher.teacherCode}">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <a href="/QuangTrungSchool/admin-classyearsemester-schedule?classCode=${cys.classroom.classCode}&year=${cys.year}&semester=${cys.semester}&startDate=${cys.startDate}&endDate=${cys.endDate}">
                                    Edit
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>    
            </table>
            <div>
                <h2>Pages</h2>
                <input type="hidden" id="totalpage" value="${requestScope.totalPage}">

                <div id="paging"></div>
            </div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
