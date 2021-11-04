<%-- 
    Document   : studentattendancedetail
    Created on : Oct 23, 2021, 11:08:40 PM
    Author     : My Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>THPT Quang Trung-Hà Đông</title>
        <style>
            .table{
                color : blue;
            }
            
            .table{
                text-align: center;
            }
            
            h2{
                text-align: center;
                color : blue;
            }
        </style>
    </head>
    <body>
         <jsp:include page="header.jsp"></jsp:include>
         <h2>${sessionScope.student.studentAttendances[0].schedule.course.courseName}</h2>
         <table class="table table-striped table-hover table-bordered">
             <tr>
                 <th>Date</th>
                 <th>Slot</th>
                 <th>Class Code</th>
                 <th>Teacher</th>
                 <th>Attendance</th>
             </tr>
         <c:set var="totalSlot" value="0"></c:set> 
         <c:set var="absentSlot" value="0"></c:set> 
         <c:forEach items="${sessionScope.student.studentAttendances}" var="studentAttendance">
             <tr>
                 <td>${studentAttendance.schedule.date}</td>
                 <td>${studentAttendance.schedule.slot}</td>
                 <td>${studentAttendance.schedule.classroom.classCode}</td>
                 <td>${studentAttendance.schedule.teacher.teacherCode}</td>
                 <c:choose>
                     <c:when test="${studentAttendance.status eq 0}">
                         <td><span style="color: brown">Not Yet</span></td>
                     </c:when>
                     <c:otherwise>
                         <c:if test="${studentAttendance.status eq 1}">
                             <td> <span style="color: green">Attended</span> </td>
                         </c:if>
                         <c:if test="${studentAttendance.status eq -1}">
                             <c:set var="absentSlot" value="${absentSlot + 1}"></c:set> 
                             <td> <span style="color: red">Absent</span> </td>
                         </c:if>
                     </c:otherwise>    
                 </c:choose>
             </tr>
             <c:set var="totalSlot" value="${totalSlot + 1}"></c:set> 
         </c:forEach>
             <tr>
                 <td colspan="5">
                     Absent ${absentSlot} of ${totalSlot}
                     ( ${ (absentSlot / totalSlot) * 100 } % ) 
                 </td>
             </tr>
         </table>
         <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
