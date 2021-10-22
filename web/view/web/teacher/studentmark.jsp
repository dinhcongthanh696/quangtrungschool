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
        </style>
        <script>
            function inputMark(container){
                var isMarked = $("#course").val().split(" ")[1];
                $("#"+container).empty();
                if(isMarked === 'true'){
                    $("#"+container).append(
                       "<label for='type'> Type : </label> "
                      +"<select name='type' id='type' > "
                      +     "<option value='1'>1</option>"
                      +     "<option value='2'>2</option>"
                      +     "<option value='3'>3</option>"
                      +"</select>"
                      +"<br/>"
                      +"<label for='mark'> Mark : </label> "
                      +"<input type='number' step='0.01' name='mark' id='mark' min='0' max='10'>" 
                    );
                }else{
                    $("#"+container).append(
                       "<input type='hidden' name='type' value='0'>"
                       +"<input type='radio' name='mark' value='-1' id='pass'> <label for='pass'>Pass</label> "
                       +"<input type='radio' name='mark' value='-2' id='notpass'> <label for='notpass'>Not pass </label>"
                    );
                }
            }
        </script>
    </head>
    <body onload="inputMark('mark')"> 
        <jsp:include page="header.jsp"></jsp:include>
        <form action="/QuangTrungSchool/student-mark" method="POST">
        <input type="hidden" name="year" value="${requestScope.class.year}">
        <input type="hidden" name="classCode" value="${requestScope.class.classroom.classCode}">
        <input type="hidden" name="semester" value="${requestScope.class.semester}">
        <input type="hidden" name="studentCode" value="${requestScope.student.studentCode}">
        <input type="hidden" name="classindex" value="${requestScope.classindex}">
        
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
        
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
