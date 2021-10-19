<%-- 
    Document   : takecourse
    Created on : Oct 11, 2021, 5:56:14 PM
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

            .right .form-control{
                width: 30%;
            }

            .right .table td{
                padding: 20px;
            }

            .right table span{
                color : red;
            }
        </style>
        <script>
            function doChange() {
                var year = $("#year").val();
                var week = $("#week").val();
                var semester = $("#semester").val();
                var classCode = $("#classCode").val();
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                window.location = "/QuangTrungSchool/admin-classyearsemester-schedule?year=" + year + "&weekNumber=" + week
                        + "&classCode=" + classCode +"&semester="+ semester +"&startDate="+startDate+"&endDate="+endDate; 
            }

            function doDelete(str, stt) {
                var check = confirm("Are you sure?");
                if (check) {
                    var str_split = str.split(" ");
                    var day = str_split[0];
                    var slot = str_split[1];
                    var classCode = $("#classCode").val();
                    $.ajax({
                        url: '/QuangTrungSchool/admin-classyearsemester-schedule-delete',
                        type: 'POST',
                        data: {
                            day: day,
                            slot: slot,
                            classCode: classCode
                        },
                        success: function (response) {
                            $("#" + stt).empty();
                            $("#" + stt).append(
                                    "<button class='btn btn-success' onclick='doInsert(this.value," + stt + ")' value=" + day + " " + slot + ">"
                                    + "Insert"
                                    + "</button> "
                                    );
                        }
                    });
                }
            }

            function doInsert(str) {
                var str_split = str.split(" ");
                var day = str_split[0];
                var slot = str_split[1];
                var year = $("#year").val();
                var semester = $("#semester").val();
                var classCode = $("#classCode").val();
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                window.location = "/QuangTrungSchool/admin-classyearsemester-schedule-add?year=" + year + "&day=" +
                        day + "&classCode=" + classCode + "&slot=" + slot + "&semester=" + semester 
                        +"&startDate="+startDate+"&endDate="+endDate;
            }

            function doUpdate(str) {
                var str_split = str.split(" ");
                var day = str_split[0];
                var slot = str_split[1];
                var teacherCode = str_split[2];
                var courseCode = str_split[3];
                var year = $("#year").val();
                var semester = $("#semester").val();
                var classCode = $("#classCode").val();
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                window.location = "/QuangTrungSchool/admin-classyearsemester-schedule-update?year=" + year + "&day=" +
                        day + "&classCode=" + classCode + "&slot=" + slot + "&semester=" + semester + "&teacherCode="+teacherCode
                        +"&startDate="+startDate+"&endDate="+endDate+"&courseCode="+courseCode;
            }
        </script>
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>CLASS TAKE COURSE</h2>
                <input type="hidden" value="${requestScope.classCode}" id="classCode">
            Class Code: <input value="${requestScope.classCode}" disabled="disabled" class="form-control">
            Year : <input type="hidden" value="${requestScope.year}" id="year"> 
            <input value="${requestScope.year}" disabled="disabled" class="form-control">
            Semester : <input type="hidden" value="${requestScope.semester}" id="semester"> 
            <input value="${requestScope.semester}" disabled="disabled" class="form-control"><br/>
            Start Date : <input type="hidden" value="${requestScope.startDate}" id="startDate">
            <input value="${requestScope.startDate}" disabled="disabled" class="form-control"><br/>
            End Date : <input type="hidden" value="${requestScope.endDate}" id="endDate">
            <input value="${requestScope.endDate}" disabled="disabled" class="form-control"><br/>
            <table class="table table-bordered table-hover">
                <thead>    
                    <tr>
                        <td rowspan="2">
                            <br/>
                            Week : <select onchange="doChange()" id="week">
                                <c:forEach items="${requestScope.currentYear.weeks}" var="week"> 
                                    <option value="${week.weekNumber}" ${(week.weekNumber eq requestScope.currentWeek.weekNumber) ? "selected = 'selected'" : ""}>
                                        ${week.days[0][0].concat('  -  ').concat(week.days[week.totalDays - 1][0])}
                                    </option>
                                </c:forEach>    
                            </select>
                            <c:forEach items="${requestScope.currentWeek.days}" var="day">
                            <td>${day[1]}</td>
                        </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <c:forEach items="${requestScope.currentWeek.days}" var="day">
                            <td>${day[0]}</td>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="stt" value="1"></c:set>
                    <c:forEach begin="1" end="6" var="slot">
                        <tr>
                            <td>Slot ${slot}</td>
                            <c:forEach items="${requestScope.currentWeek.days}" var="day">
                                <c:set var="isHaving" value="false"></c:set>
                                <c:forEach items="${requestScope.schedules}" var="schedule">
                                    <c:if test="${(schedule.slot eq slot) and  ''.concat(schedule.date)  eq day[0]  }">
                                        <c:set var="isHaving" value="true"></c:set>
                                        <td id="${stt}"> 
                                            <p>
                                                Course : <span> ${schedule.course.courseCode} </span>
                                                Teacher : <span> ${schedule.teacher.teacherCode} </span>
                                            </p>
                                            <button class="btn btn-primary" onclick="doUpdate(this.value)" 
                                                    value="${day[0].concat(' ').concat(slot).concat(' ').concat(schedule.teacher.teacherCode).concat(' ').concat(schedule.course.courseCode)}
                                                    ">
                                                Edit
                                            </button>
                                            <c:if test="${schedule.active == 0}" >
                                                <button class="btn btn-danger" onclick="doDelete(this.value,${stt})" value="${day[0].concat(' ').concat(slot)}">
                                                    Delete
                                                </button>
                                            </c:if>    
                                        </td>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${!isHaving}">
                                    <td id="${stt}"> 
                                        <button class="btn btn-success" onclick="doInsert(this.value,${stt})" value="${day[0].concat(' ').concat(slot)}">
                                            Insert
                                        </button> 
                                    </td>
                                </c:if>
                                <c:set var="stt" value="${stt + 1}"></c:set>    
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
