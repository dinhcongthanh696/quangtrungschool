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
            function activePage() {
                var pageId = $(".right input[type='hidden']").val();
                $(".right a#" + pageId).css("background-color", "blue");
            }

            function doSearch() {
                $(".right .table tbody").empty();
                $("#paging").empty();
                var query = $("#query").val();
                $.ajax({
                    url: "/QuangTrungSchool/admin-classyearsemester-list",
                    type: 'POST',
                    data: {
                        query: query
                    },
                    success: function (response) {
                        var toObject = JSON.parse(response);
                        var totalPage = toObject.totalPage;
                        var classes = toObject.classes;
                        for (let i = 0; i < classes.length; i++) {
                            var endDate = classes[i].endDate.toString();
                            var startDate = classes[i].startDate.toString();
                            $(".right .table tbody").append(
                                "<tr>   "
                                    + "<td>" + classes[i].classroom.classCode + "</td>"
                                    + "<td>" + classes[i].year + "</td>"
                                    + "<td>" + classes[i].semester + "</td>"
                                    + "<td>" + classes[i].startDate + "</td>"
                                    + "<td>" + classes[i].endDate  + "</td>"
                                    + "<td>"
                                        + ((typeof classes[i].homeroomTeacher.teacherCode === 'undefined') ? "Unkown" : classes[i].homeroomTeacher.teacherCode)
                                        + " | <a href=/QuangTrungSchool/admin-classyearsemester-update?classCode=" + classes[i].classroom.classCode + "&year=" + classes[i].year
                                        + "&semester=" + classes[i].semester + "&teacherCode=" + classes[i].homeroomTeacher.teacherCode + ">Edit</a>"
                                    + "</td>"
                                    +"<td>"
                                        + "<a href=/QuangTrungSchool/admin-classyearsemester-schedule?"
                                        +"classCode="+ classes[i].classroom.classCode +"&year="+ classes[i].year 
                                        +"&semester="+ classes[i].semester +"&startDate="+ startDate + "&endDate="+ endDate
                                        +">Edit</a>"
                                    +"</td>"
                                + "</tr>");
                        }
                        var query = $("#query").val();
                        for (let pageId = 1; pageId <= totalPage; pageId++) {
                            $("#paging").append(
                                    "<a id=" + pageId + " href=/QuangTrungSchool/admin-classyearsemester-list?pageId=" + pageId + "&query=" + query + ">" + pageId + "</a>"
                                    );
                        }
                        $(".right a#" + 1).css("background-color", "blue");
                    }
                });
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
        </style>
    </head>
    <body onload="activePage()">
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Classes By Year Semester List</h2>
                <label for="query">Search: </label>
                <input type="hidden" value="${requestScope.pageId}">
            <input type="text" class="form-control" id="query" name="query" placeholder="Search..." value="${param.query}"><br/>
            <button type="submit" class="btn btn-primary" onclick="doSearch()">Search</button>
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
                                <a href="/QuangTrungSchool/admin-classyearsemester-schedule?
                                   classCode=${cys.classroom.classCode}&year=${cys.year}&semester=${cys.semester}&startDate=${cys.startDate}&endDate=${cys.endDate}">
                                    Edit
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>    
            </table>
            <div>
                <h2>Pages</h2>
                <div id="paging">
                    <c:forEach begin="1" end="${requestScope.totalPage}" var="page">
                        <a id="${page}" href="/QuangTrungSchool/admin-classyearsemester-list?pageId=${page}&query=${param.query}">${page}</a>
                    </c:forEach>
                </div>
            </div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
