<%-- 
    Document   : classyearsemesteradd
    Created on : Oct 7, 2021, 9:45:32 PM
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
        <style>
            .right{
                color: blue;
            }

            .right span{
                display: none;
                color: red;
            }

            .right .form-control{
                width : 30%;
            }
        </style>
        <script>
            var semesters = [];

            function searchSemester() {
                semesters = [];
                var classCode = $("#classroom").val();
                var year = $("#year").val();
                $.ajax(
                        {url: "/QuangTrungSchool/admin-classyearsemester-check",
                            type: 'GET',
                            data: {
                                classCode: classCode,
                                year: year
                            },
                            success: function (response) {
                                var toObject = JSON.parse(response);
                                var checkedBoxValue = $("input[type=radio]:checked").val();
                                var isDuplicated = false;
                                for (let i = 0; i < toObject.length; i++) {
                                    semesters[i] = (i + 1);
                                    if (checkedBoxValue == semesters[i]) {
                                        isDuplicated = true;
                                        break;
                                    }
                                }
                                if (isDuplicated) {
                                    document.getElementById("submit").disabled = true;
                                    $(".right span#alertClass").css("display", "inline");
                                } else {
                                    document.getElementById("submit").disabled = false;
                                    $(".right span#alertClass").css("display", "none");
                                }

                            }
                        }
                );
            }

            function checkDateBeforeDate() {
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                if (startDate.length != 0 && endDate.length != 0) {
                    if (startDate < endDate) {
                        document.getElementById("submit").disabled = false;
                        $(".right span#alertDate").css("display", "none");
                    } else {
                        document.getElementById("submit").disabled = true;
                        $(".right span#alertDate").css("display", "inline");
                    }
                }
            }

            function onClickSemester(checkedBoxValue) {
                var isDuplicate = false;
                for (let i = 0; i < semesters.length; i++) {
                    if (checkedBoxValue == semesters[i]) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (isDuplicate) {
                    document.getElementById("submit").disabled = true;
                    $(".right span#alertClass").css("display", "inline");
                } else {
                    document.getElementById("submit").disabled = false;
                    $(".right span#alertClass").css("display", "none");
                }
            }
        </script>
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Add Class By Year Semester</h2>
                <form action="/QuangTrungSchool/admin-classyearsemester-add" method="POST">
                    <label for="classroom">Class Room: </label>
                    <select id="classroom" name="classroom" onchange="searchSemester()" required="required">
                        <option value="">None</option>
                    <c:forEach items="${requestScope.classrooms}" var="classroom">
                        <option value="${classroom.classCode}">${classroom.classCode}</option>
                    </c:forEach>
                </select><br/>
                <label for="year">Year: </label>
                <select id="year" name="year" onchange="searchSemester()" required="required">
                    <option value="">None</option>
                    <c:forEach begin="${requestScope.currentYear}" end="${requestScope.currentYear + 10}" var="year">
                        <option value="${year}">${year}</option>
                    </c:forEach>
                </select>
                <br/>
                <label for="semester1">Semester 1</label>
                <input type="radio" checked="checked" value="1" name="semester" id="semester1" onclick="onClickSemester(this.value)">
                <label for="semester2">Semester 2</label>
                <input type="radio" value="2" name="semester" id="semester2" onclick="onClickSemester(this.value)"><br/>
                <label for="startDate">Start Date: </label>
                <input type="date" name="startDate" id="startDate" required="required" class="form-control" onchange="checkDateBeforeDate()">
                <label for="endDate">End Date: </label>
                <span id="alertDate">X start date must before end date</span>
                <input type="date" name="endDate" id="endDate" required="required" class="form-control" onchange="checkDateBeforeDate()">
                <button type="submit" class="btn btn-primary" id="submit">Add</button> 
                <span id="alertClass">X This class has already taken this semester of year </span>
            </form>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
