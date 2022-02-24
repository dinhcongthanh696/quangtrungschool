<%-- 
    Document   : studentschedule
    Created on : Oct 24, 2021, 10:49:03 AM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <title>THPT Quang Trung-Hà Đông</title>
        <script>
            function doChange() {
                var year = $("#year").val();
                var week = $("#week").val();
                window.location = "/QuangTrungSchool/student-schedule?year=" + year + "&week=" + week;
            }
        </script>
        <style>
            .table{
                text-align: center;
                color: blue;
            }
            
            h2{
                text-align: center;
                color: #00FFFF;
            }
            
            table span{
                color: red;
            }

            #attended{
                color: green;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <h2>Weekly Schedule</h2>
            <table class="table table-bordered table-hover">
                <tr>
                    <td rowspan="2">
                        <label for="year">Year : </label>
                        <select name="year" id="year" onchange="doChange()">
                        <c:forEach begin="2019" end="2029" var="year">
                            <option value="${year}" ${(year eq requestScope.year.year) ? "selected='selected'" : ""}>
                                ${year}
                            </option>
                        </c:forEach>
                    </select><br/>
                    <label for="week">Week: </label>
                    <select name="week" id="week" onchange="doChange()">
                        <c:forEach items="${requestScope.year.weeks}" var="week">
                            <option value="${week.weekNumber}" ${(week.weekNumber eq requestScope.week.weekNumber) ? "selected='selected'" : ""}>
                                <fmt:formatDate value="${week.days[0]}" pattern="dd/MM/yyyy"></fmt:formatDate> 
                                - <fmt:formatDate value="${week.days[week.totalDays - 1]}" pattern="dd/MM/yyyy"></fmt:formatDate> 
                            </option>
                        </c:forEach>
                    </select>
                    <c:forEach items="${requestScope.week.days}" var="day">
                    <td><fmt:formatDate value="${day}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                </c:forEach>
                </td>
            </tr>
            <tr>
                <c:forEach items="${requestScope.week.days}" var="day">
                    <td><fmt:formatDate value="${day}" pattern="EEEE"></fmt:formatDate></td>
                </c:forEach>
            </tr>

            <c:forEach begin="1" end="6" var="slot">
                <tr>
                    <td>Slot ${slot} </td>
                    <c:forEach items="${requestScope.week.days}" var="day">
                        <fmt:formatDate var="formatDay" value="${day}" pattern="yyyy-MM-dd"></fmt:formatDate>
                        <c:set value="false" var="isHaving"></c:set>
                        <c:forEach items="${sessionScope.student.studentAttendances}" var="attendance">
                            <c:if test="${attendance.schedule.slot eq slot and attendance.schedule.date eq formatDay}">
                                <c:set value="true" var="isHaving"></c:set>
                                    <td>   
                                        Class : <span> ${attendance.schedule.classroom.classCode} </span> <br/>
                                    Course : <span> ${attendance.schedule.course.courseCode} </span><br/>
                                    <c:if test="${attendance.schedule.course.type != 0}">
                                        <c:choose>
                                            <c:when test="${attendance.status eq 0}" >
                                                ( Not Yet )
                                            </c:when>
                                            <c:when test="${attendance.status eq 1}" >
                                                ( <span id="attended">Attended</span> )
                                            </c:when>
                                            <c:otherwise>
                                                ( <span id="absent">Absent</span> )
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>        
                                </td>
                            </c:if>
                        </c:forEach>
                        <c:if test="${!isHaving}">
                            <td> - </td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
