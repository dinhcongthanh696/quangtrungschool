<%-- 
    Document   : takeclass
    Created on : Oct 2, 2021, 10:22:25 AM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>            
            .right .form-control{
                width: 50%;
                margin-bottom: 50px;
            }

            .right{
                padding-left: 15%;
                color: blue;
            }

            .right span{
                display: none;
                color: red;
            }

            .right form .btn-primary{
                margin-left: 19%;
                border-radius: 3px;
                width: 120px;
            }
        </style>  

        <script>
            function doDelete(key, stt) {
                check = confirm("Are you sure? ");
                if (check) {
                    const arr = key.split(",");
                    $.post("/QuangTrungSchool/admin-student-class-delete",
                            {
                                studentCode: arr[0],
                                year: arr[1],
                                semester: arr[2]
                            },
                            function (data, textStatus) {
                                $(".right table tbody #" + stt).css("display", "none");
                                $("#totalClasses").attr("value",$("#totalClasses").val() - 1);
                                $(".right table tbody #" + stt).attr("id", "-1");
                                checkDuplidate();
                            }
                    );
                }
            }

            function checkDuplidate() {
                var classCode = $(".right #classroom").val();
                var year = $(".right #year").val();
                var semester = $("input[type=radio]:checked").val();
                var totalClasses = $("#totalClasses").val();
                var isDuplicate = false;
                for (let i = 1; i <= totalClasses; i++) {
                    if (year == $("#" + i).children()[3].innerHTML &&
                            semester == $("#" + i).children()[4].innerHTML) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (isDuplicate) {
                    document.getElementById("submit").disabled = true;
                    $(".right form span").css("display", "inline");
                } else {
                    document.getElementById("submit").disabled = false;
                    $(".right form span").css("display", "none");
                }
            }
        </script>
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Student Taking Class</h2>
                <form action="/QuangTrungSchool/admin-student-class-add" method="POST">
                    <h4>Student Code: ${param.studentCode} </h4>
                <input type="hidden" name="studentCode" value="${param.studentCode}"> <br/>
                <label for="classroom">Class Room: </label>
                <select id="classroom" name="classroom" class="form-select" required="required" onchange="checkDuplidate()">
                    <option value="">None</option>
                    <c:forEach items="${requestScope.classrooms}" var="classroom">
                        <option value="${classroom.classCode}">${classroom.classCode}</option>
                    </c:forEach>
                </select>
                <br/>
                <label for="year">Year: </label>
                <select id="year" name="year" class="form-select" required="required" onchange="checkDuplidate()">
                    <option value="">None</option>
                    <c:forEach begin="2019" end="2029" var="year">
                        <option value="${year}">${year}</option>
                    </c:forEach>
                    <br/>
                </select> <br/>
                <input type="radio" checked="checked" id="semester1" name="semester" value="1" onclick="checkDuplidate()"> 
                <label for="semester1">Semester 1</label>
                <input type="radio" id="semester2" name="semester" value="2" onclick="checkDuplidate()"> 
                <label for="semester2">Semester 2</label> <br/>
                <button class="btn btn-primary" type="submit" id="submit">Take Class</button> 
                <span>X ${param.studentCode} already take this year semester</span>
                <br/>
            </form>
            <h2>Classes ${param.studentCode} has taken</h2>
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Student Code </th>
                        <th>Class Code </th>
                        <th>Year </th>
                        <th>Semester </th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="stt" value="0"></c:set>
                    <c:forEach items="${requestScope.student.classes}" var="sl">
                        <c:set var="stt" value="${stt + 1}"></c:set>
                        <tr id="${stt}">
                            <td>${stt}</td>
                            <td>${requestScope.student.studentCode}</td>
                            <td>${sl.classroom.classCode}</td>
                            <td>${sl.year}</td>
                            <td>${sl.semester}</td>
                            <td>
                                <button class="btn btn-danger" 
                                        onclick="doDelete(this.value,${stt})" 
                                        value="${requestScope.student.studentCode.concat(",").concat(sl.year).concat(",").concat(sl.semester)}">
                                    Delete
                                </button> 
                            </td>
                        </tr>
                    </c:forEach> 
                <input type="hidden" value="${stt}" id="totalClasses">    <!-- For javascript get total classes student take -->
                </tbody>
            </table>
        </section>        
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
