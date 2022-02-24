<%-- 
    Document   : departmentadd
    Created on : Oct 31, 2021, 10:58:22 PM
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
        <script>
            var departments = [];
            function loadData(){
                $.ajax({
                    url : "/QuangTrungSchool/admin-department-check-add",
                    type : 'GET',
                    success : function(response){
                        var toObject = JSON.parse(response);
                        departments = toObject;
                    }
                });
            }
            
            function checkDuplicate(input){
                for(let i = 0 ; i < departments.length ; i++){
                    if(input.toLowerCase().localeCompare(departments[i].departmentCode.toLocaleLowerCase()) == 0){
                        return true;
                    }
                }
                return false;
            }
            
            function alertMessage(input){
                if(checkDuplicate(input)){
                    document.getElementById("submit").disabled = true;
                    $("#duplicateMess").css("display","inline");
                }else{
                    document.getElementById("submit").disabled = false;
                    $("#duplicateMess").css("display","none");
                }
            }
        </script>
        <style>
            #duplicateMess{
                display: none;
                color : red;
            }
            .right{
                color : blue;
            }
        </style>
    </head>
    <body onload="loadData()">
        <jsp:include page="../header.jsp"></jsp:include>
        <section class="right">
            <h2>Add New Department</h2>
            <form action="admin-department-add" method="POST">
                <label for="departmentcode">Department Code : </label>
                <input type="text" required="required" name="departmentcode" id="departmentcode" class="form-control" onkeyup="alertMessage(this.value)">
                <span id="duplicateMess">X department code is duplicated</span><br/>
                <label for="departmentname">Department Name : </label>
                <input type="text" required="required" name="departmentname" id="departmentname" class="form-control">
                <label for="dean">Dean : </label>
                <select name="dean" id="dean">
                <c:forEach items="${requestScope.teachers}" var="teacher">
                    <option value="${teacher.teacherCode}">${teacher.teacherCode}</option>
                </c:forEach>
                </select>        
                    <button type="submit" class="btn btn-primary" id="submit">Add</button>
                
            </form>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
