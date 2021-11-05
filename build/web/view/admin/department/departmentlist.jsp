<%-- 
    Document   : departmentclasslist
    Created on : Oct 31, 2021, 11:40:30 PM
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
                text-align: center;
            }
            
            .right li{
                list-style-type: none;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            function doEdit(departmentCode){
                $("#"+departmentCode).empty();
                $.ajax({
                   url : "/QuangTrungSchool/admin-department-update",
                   type : 'GET',
                   success : function(response){
                       var toObject = JSON.parse(response);
                       $("#"+departmentCode).append(
                            "<label for='dean"+ departmentCode +"'>Dean</label> "
                            +"<select id='dean"+ departmentCode +"' class='form-select'>"
                            );
                       for(let i = 0 ; i < toObject.length ; i++){
                           $("#dean"+departmentCode).append(
                                "<option value='"+ toObject[i].teacherCode +"' >"
                                + toObject[i].teacherCode 
                                + "</option>"
                            );
                       }
                       $("#"+departmentCode).append(
                         "|  <button class='btn btn-primary' onclick='saveChanges(this.value)' value='"+ departmentCode +"' >Save changes</button> "     
                        );
                   }
                });
            }
            
            function saveChanges(departmentCode){
                var dean = $("#dean"+departmentCode).val();
                $.ajax({
                   url : "/QuangTrungSchool/admin-department-update",
                   type : 'POST',
                   data : {
                       dean : dean,
                       departmentCode : departmentCode
                   },
                   success : function(response){
                       $("#"+departmentCode).empty();
                       $("#"+departmentCode).append(
                            dean 
                            + "  |  <button class='btn btn-primary' onclick='doEdit(this.value)' value='"+ departmentCode +"' > "
                            + " Edit   </button>"
                        );
                   }
                }); 
            }
        </script>
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Department List</h2>
                <table class="table table-bordered">
                    <tr>
                        <th>Department</th>
                        <th>Class</th>
                        <th>Dean</th>
                    </tr>
                <c:forEach items="${requestScope.departments}" var="department">
                    <tr>
                        <td>${department.departmentCode}</td>
                        <td>
                            <ul class="list-group">
                                <c:set var="isHaving" value="false"></c:set>
                                <c:forEach items="${department.classrooms}" var="class">
                                    <li class="list-group-item">
                                        <c:set var="isHaving" value="true"></c:set>
                                        ${class.classCode}
                                    </li>
                                </c:forEach>
                                <c:if test="${!isHaving}">
                                    <li>EMPTY</li>
                                </c:if> 
                            </ul>
                        </td>
                        <td id="${department.departmentCode}">
                            ${department.dean.teacherCode} |
                            <button class="btn btn-primary" onclick="doEdit(this.value)" value="${department.departmentCode}">Edit</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
