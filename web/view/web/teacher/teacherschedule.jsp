<%-- 
    Document   : teacherschedule
    Created on : Oct 20, 2021, 10:14:40 AM
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
            .table-bordered{
                color : blue;
                text-align: center;
            }

            h2{
                color : blue;
                text-align: center;
            }

            table span{
                color: red;
            }
        </style>
        <script>
            function doChange() {
                var year = $("#year").val();
                var week = $("#week").val();
                window.location = "/QuangTrungSchool/teacher-schedule?year=" + year + "&week=" + week;
            }

            function takeAttendance(scheduleindex) {
                window.location = "/QuangTrungSchool/teacher-schedule-attendance?scheduleindex=" + scheduleindex;
            }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <table class="table table-bordered table-hover">
                <h2>Weekly Schedule</h2>
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
                                ${week.days[0][0]} - ${week.days[week.totalDays - 1][0]} 
                            </option>
                        </c:forEach>
                    </select>
                    <c:forEach items="${requestScope.week.days}" var="day">
                    <td>${day[1]}</td>
                </c:forEach>
                </td>
            </tr>
            <tr>
                <c:forEach items="${requestScope.week.days}" var="day">
                    <td>${day[0]}</td>
                </c:forEach>
            </tr>

            <c:set var="scheduleindex" value="0"></c:set>

            <c:forEach begin="1" end="6" var="slot">
                <tr>
                    <td>Slot ${slot} </td>
                    <c:forEach items="${requestScope.week.days}" var="day">
                        <c:set value="false" var="isHaving"></c:set>
                        <c:forEach items="${sessionScope.teacher.schedules}" var="schedule">
                            <c:if test="${schedule.slot eq slot and ('').concat(schedule.date) eq ('').concat(day[0])}">
                                <c:set value="true" var="isHaving"></c:set>
                                    <td>   
                                        Class : <span> ${schedule.classroom.classCode} </span> <br/>
                                    Course : <span> ${schedule.course.courseCode} </span><br/>
                                <c:if test="${schedule.course.type != 0}"> <!-- 0 REPERSENT FOR OTHERS ACTIVIETES-->
                                    <c:choose>
                                        <c:when test="${schedule.active eq 0}">
                                            <span>Not Yet</span>
                                        </c:when>
                                        <c:when test="${schedule.active eq 1}">
                                            <span>Closed</span>
                                        </c:when>
                                        <c:otherwise>
                                            
                                                <button class="btn btn-success" onclick="takeAttendance(this.value)" 
                                                        value="${scheduleindex}">
                                                    Edit attendance
                                                </button>
                                        </c:otherwise>    
                                    </c:choose>
                                    </c:if>        
                                </td>
                                <c:set var="scheduleindex" value="${scheduleindex + 1}"></c:set>
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
