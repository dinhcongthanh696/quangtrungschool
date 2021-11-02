<%-- 
    Document   : courseadd
    Created on : Oct 28, 2021, 4:14:12 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            var courses = [];
            function loadData(){
                $.ajax({
                   url : "/QuangTrungSchool/admin-course-check-add",
                   type : "GET",
                   success : function(response){
                       var object = JSON.parse(response);
                       for(let i = 0 ; i < object.length ; i++){
                           courses[i] = object[i];
                       }
                   }
                });
            }
            
            function checkDuplicateCourseCode(input){
                for(let i = 0 ; i < courses.length ; i++){
                    if(courses[i].courseCode.toLowerCase() === input.toLowerCase()){
                        return true;
                    }
                }
                return false;
            }
            
            function alertMessage(input){
                if(checkDuplicateCourseCode(input)){
                    $("#alertDuplicate").css("display","inline");
                    document.getElementById("submit").disabled = true;
                }else{
                    $("#alertDuplicate").css("display","none");
                    document.getElementById("submit").disabled = false;
                }
            }
            
            function submitForm(){
                $("#alertSuccess").empty();
                var courseCode = $("#courseCode").val();
                var courseName = $("#courseName").val();
                var type = $("#type").val();
                $.ajax({
                    url : "/QuangTrungSchool/admin-course-add",
                    type : 'POST',
                    data : {
                      courseCode : courseCode,
                      courseName : courseName,
                      type : type
                    },
                    success : function(){
                      $("#alertSuccess").append("<span style='color : green'>Add successfully</span> ");  
                    }
                });
            }
        </script>
        <style>
            #alertDuplicate{
                display : none;
                color: red;
            }
        </style>
    </head>
    <body onload="loadData()">
        <jsp:include page="../header.jsp"></jsp:include>
        <section class="right">
            <h2>Course Add</h2>
            <label for="courseCode" class="form-label">Course Code</label>
            <input type="text" required="required" class="form-control" id="courseCode" 
                   placeholder="Enter..." onkeyup="alertMessage(this.value)">
            <span id="alertDuplicate">X course code is duplicated</span><br/>
            <label for="courseName" class="form-label">Course Code</label>
            <input type="text" required="required" class="form-control" id="courseName" 
                   placeholder="Enter...">
            <label for="type" class="form-label">Type : </label>
            <select class="form-select" id="type">
                <option value="0">Other</option>
                <option value="1">No score</option>
                <option value="2">Scored</option>
            </select><br/>
            <button type="submit" id="submit" class="btn btn-primary" onclick="submitForm()">Add</button>
            <div id="alertSuccess"></div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
