<%-- 
    Document   : studentmark
    Created on : Oct 22, 2021, 10:27:38 AM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung - Hà Đông</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            table{
                color: blue;
            }

            #pass{
                color: green;
                text-transform: uppercase;
                font-weight: bold;
            }

            #notpass{
                color: red;
                text-transform: uppercase;
                font-weight: bold;
            }
        </style>
        <script>
            function inputMark(container) {
                var isMarked = $("#course").val().split(" ")[1];
                $("#" + container).empty();
                if (isMarked === 'true') {
                    $("#" + container).append(
                            "<label for='type'> Type : </label> "
                            + "<select name='type' id='type' > "
                            + "<option value='1'>1</option>"
                            + "<option value='2'>2</option>"
                            + "<option value='3'>3</option>"
                            + "</select>"
                            + "<br/>"
                            + "<label for='mark'> Mark : </label> "
                            + "<input type='number' step='0.01' name='mark' id='mark' min='0' max='10'>"
                            );
                } else {
                    $("#" + container).append(
                            "<input type='hidden' name='type' value='0'>"
                            + "<input type='radio' name='mark' value='-1' id='pass'> <label for='pass'>Pass</label> "
                            + "<input type='radio' name='mark' value='-2' id='notpass'> <label for='notpass'>Not pass </label>"
                            );
                }
            }

            function doEdit(str) {
                var str_split = str.split(" ");
                var classindex = $("#classindex").val();
                var studentindex = $("#studentindex").val();
                var studentcourseindex = str_split[0];
                var markindex = str_split[1];
                window.location = "/QuangTrungSchool/student-mark-update?classindex=" + classindex + "&studentindex=" + studentindex
                        + "&markindex=" + markindex + "&studentcourseindex=" +studentcourseindex;
            }

            function doDelete(no) {
                var checked = confirm('Are you sure?');
                var classindex = $("#classindex").val();
                var studentindex = $("#studentindex").val();
                if (checked) {
                    window.location = '/QuangTrungSchool/student-mark-delete?no=' + no +
                            "&classindex=" + classindex + "&studentindex=" + studentindex;
                }
            }
        </script>
    </head>
    <body onload="inputMark('mark')"> 
        <jsp:include page="header.jsp"></jsp:include>
            <form action="/QuangTrungSchool/student-mark" method="POST">
                <input type="hidden" name="classindex" value="${requestScope.classindex}" id="classindex">
            <input type="hidden" name="studentindex" value="${requestScope.studentindex}" id="studentindex">

            <table class="table table-bordered table-striped">
                <tr>
                    <th>Student Code</th>
                    <th>Full Name</th>
                    <th>Class</th>
                    <th>Year</th>
                    <th>Semester</th>
                </tr>
                <tr>
                    <td>${requestScope.student.studentCode}</td>
                    <td>${requestScope.student.fullname}</td>
                    <td>${requestScope.class.classroom.classCode}</td>
                    <td>${requestScope.class.year}</td>
                    <td>${requestScope.class.semester}</td>
                </tr>
            </table>
            <label for="course">Course : </label>
            <select name="course" id="course" onchange="inputMark('mark')">
                <c:forEach items="${requestScope.class.courses}" var="course">
                    <option value="${course.courseCode.concat(' ').concat(course.isMarked)}">${course.courseName}</option>
                </c:forEach>
            </select>
            <div id="mark"></div>
            <button class="btn btn-primary">Save changes</button>
        </form>

        <table class="table table-bordered">
            <tr>
                <th>NO</th>
                <th>Course</th>
                <th>Exam Type</th>
                <th>Mark</th>
                <th></th>
                <th></th>
            </tr>
            <h2>Student Marks</h2>
            <c:set value="1" var="studentcourseindex"></c:set>
            <c:forEach items="${requestScope.student.studentcourses}" var="studentcourse">
                <c:set value="1" var="markindex"></c:set>
                <c:forEach items="${studentcourse.marks}" var="mark">
                        <tr>
                            <td>${mark.no}</td>
                        <td>${studentcourse.course.courseCode}</td>
                        <td>${mark.exam_type}</td>
                        <td>
                            <c:choose>
                                <c:when test="${mark.score eq -1}">
                                    <span id="pass"> Pass </span>
                                </c:when>
                                <c:when test="${mark.score eq -2}">
                                    <span id="notpass">  Not Pass </span>
                                </c:when>    
                                <c:otherwise>
                                    ${mark.score}
                                </c:otherwise>    
                            </c:choose>
                        </td>
                        <td>
                            <button class="btn btn-primary" onclick="doEdit(this.value)" 
                                    value="${''.concat(studentcourseindex).concat(' ').concat(markindex)}">
                                Edit
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-danger" onclick="doDelete(this.value)" value="${mark.no}">
                                Delete
                            </button>
                        </td>
                    </tr>
                    <c:set value="${markindex + 1}" var="markindex"></c:set>
                </c:forEach>
                <c:set value="${studentcourseindex + 1}" var="studentcourseindex"></c:set>    
            </c:forEach>

        </table>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
