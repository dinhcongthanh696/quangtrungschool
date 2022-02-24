<%-- 
    Document   : classadd
    Created on : Nov 2, 2021, 8:26:57 AM
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
            #duplicateMess{
                color : red;
                display: none;
            }
            
            .right .form-control{
                width: 50%;
            }
        </style>
        <script>
            var classes = [];
            function loadData(){
                $.ajax({
                   url : "/QuangTrungSchool/admin-class-check-add",
                   type : 'GET',
                   success : function(response){
                       var toObject = JSON.parse(response);
                       classes = toObject;
                   }
                });
            }
            
            function checkDuplicate(input){
                for(let i = 0 ; i < classes.length ; i++){
                    if(input.toLowerCase().localeCompare(classes[i].classCode.toLocaleLowerCase()) == 0){
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
    </head>
    <body onload="loadData()">
        <jsp:include page="../header.jsp"></jsp:include>
        <section class="right">
            <form action="/QuangTrungSchool/admin-class-add" method="POST">
            <h2>Add ClassRoom</h2>
            <label for="classcode">Class Code: </label>
            <input type="text" required="required" name="classcode" id="classcode" class="form-control" onkeyup="alertMessage(this.value)">
            <span id="duplicateMess">X Class code already exsist</span> <br/>
            <label for="departmentcode">Department : </label>
            <select name="departmentcode" id="departmentcode">
                <c:forEach items="${requestScope.departments}" var="department">
                    <option value="${department.departmentCode}">${department.departmentName}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary" id="submit">Add</button>
            </form>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
