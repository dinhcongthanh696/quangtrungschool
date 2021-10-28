<%-- 
    Document   : classyearsemesterupdate
    Created on : Oct 9, 2021, 4:50:11 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <style>
            .right{
                color: blue;
            }
        </style>
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Class Detail</h2>
                <table class="table table-hover">
                    <tr>
                        <td>Class Code</td>
                        <td>Year</td>
                        <td>Semester</td>
                        <td>Homeroom Teacher</td>
                    </tr>
                    <tr>
                        <td>${requestScope.classyearsemester.classroom.classCode}</td>
                    <td>${requestScope.classyearsemester.year}</td>
                    <td>${requestScope.classyearsemester.semester}</td>
                    <td>
                        ${ (empty requestScope.classyearsemester.homeroomTeacher.teacherCode) ? 'unkown' : 
                           requestScope.classyearsemester.homeroomTeacher.teacherCode
                        }
                    </td>
                </tr>
            </table>
            <div>
                <h2>All Teacher Of class ${requestScope.classyearsemester.classroom.classCode}</h2>
                <table class="table table-hover table-striped">
                    <tr>
                        <td>Teacher Code</td>
                        <td>Full Name</td>
                        <td>Email</td>
                        <td>Date of birth</td>
                        <td>Username</td>
                        <td>Phone</td>
                        <td>Set Main Teacher</td>
                    </tr>
                    <form action="/QuangTrungSchool/admin-classyearsemester-update" method="POST">
                        <input type="hidden" name="classCode" value="${requestScope.classyearsemester.classroom.classCode}">
                        <input type="hidden" name="year" value="${requestScope.classyearsemester.year}">
                        <input type="hidden" name="semester" value="${requestScope.classyearsemester.semester}">
                        <c:forEach items="${requestScope.classyearsemester.teachersOfClass}" var="teacher">
                            <tr>
                                <td>${teacher.teacherCode}</td>
                                <td>${teacher.fullname}</td>
                                <td>${teacher.email}</td>
                                <td>${teacher.dob}</td>
                                <td>${teacher.account.username}</td>
                                <td>${teacher.phone}</td>
                                <td>
                                    <input type="radio" name="mainteacher" value="${teacher.teacherCode}"
                                           ${ (teacher.teacherCode == requestScope.classyearsemester.homeroomTeacher.teacherCode) ? "checked='checked'" : ""} 
                                           >
                                </td>
                            </tr>
                        </c:forEach>
                </table>  
                <button type="submit" class="btn btn-primary">Save changes</button>    
                </form>
            </div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
