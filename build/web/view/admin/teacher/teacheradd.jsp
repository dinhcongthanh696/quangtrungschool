<%-- 
    Document   : teacheradd
    Created on : Sep 29, 2021, 5:35:15 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quang Trung-Hà Đông ADMIN</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>            
            .right .form-control{
                width: 50%;
            }
            
            .right{
                padding-left: 15%;
                color: deepskyblue;
            }
            
            .right span{
                display: none;
                color: red;
            }
            
            .right .btn-primary{
                margin-left: 19%;
                border-radius: 3px;
                width: 80px;
            }
        </style> 
        <script>
            var teachers;

            function loadData() {  // PAGE LOADED --> LOAD teachers AND ACCOUNTS IN DATABASE --> PUT IT INTO students and usernames;
                    $.ajax(
                        {url: "/QuangTrungSchool/admin-teacher-check-add",
                            type: 'GET',
                            success: function (response) {  // servlet /admin-teacher-check-add response 2 arraylist STUDENTS AND ACCOUTS IN JSON FORMAT
                                const JSONtoObjects = JSON.parse(response);
                                teachers = JSONtoObjects.teachers;
                            }
                        }
                    );
            }

            function checkTeacherExsist(value) {
                var isDuplicate = false;
                for (let i = 0; i < teachers.length; i++) {
                    if (value.toLowerCase().localeCompare(teachers[i].teacherCode.toLowerCase()) == 0) {
                        isDuplicate = true;
                        break;
                    }
                }
                return isDuplicate;
            }

            function alertTeacherWarning(value) {
                var warning = document.getElementById("teacherduplicate");
                var isDuplicate = checkTeacherExsist(value);
                var button = document.getElementById("submit");
                if (!isDuplicate) {
                    button.disabled = false;
                    warning.style.display = "none";
                } else {
                    button.disabled = true;
                    warning.style.display = "block";
                }
            }

            function checkAccountExsist(value) {
                var isExist = false;
                for (let i = 0; i < teachers.length; i++) {
                    if (value.toLowerCase().localeCompare(teachers[i].account.username.toLowerCase()) == 0) {
                        isExist = true;
                        break;
                    }
                }
                return isExist;
            }

            function alertAccountWarning(value) {
                var warningInvalid = document.getElementById("accountinvalid");
                var isDuplicate = checkAccountExsist(value);
                var button = document.getElementById("submit");
                if (!isDuplicate) {
                    warningInvalid.style.display = "none";
                    button.disabled = false;
                } else {
                    button.disabled = true;
                    warningInvalid.style.display = "block";
                }
            }
        </script>
    </head>
    <body onload="loadData()">
        <jsp:include page="../header.jsp"></jsp:include>
    <section class="right">
        <form action="/QuangTrungSchool/admin-teacher-add" method="POST">   
            <h2>Add New Teacher</h2>
            <label for="teacherCode">Teacher Code:  </label>
            <input type="text" name="teacherCode" id="teacherCode" onkeyup="alertTeacherWarning(this.value)" required class="form-control" placeholder="Enter..."> 
            <span id="teacherduplicate"> X teacher id has already exist </span> 
            <br/>
            <label for="fullname">Teacher full name:   </label>
            <input type="text" class="form-control" name="fullname" id="fullname" pattern="[A-Za-z ]*" placeholder="Enter..."><br>
            <label for="dob">Teacher Date of Birth:   </label>
            <input type="date" name="dob" id="dob" class="form-control" required><br>
            <label for="username">Username:    </label>
            <input type="text" class="form-control" name="username" id="username" onkeyup="alertAccountWarning(this.value)" pattern="[a-z0-9]{2,}" placeholder="Enter...">
            <span id="accountinvalid"> X username is invalid </span> 
            <label for="password">Password:    </label>
            <input type="password" class="form-control" name="password" id="password" required placeholder="Enter..."> 
            <br>
            <label for="email">Email:    </label>
            <input type="text" class="form-control" name="email" id="email" placeholder="Not required..."><br>
            <label for="address">Address:     </label>
            <input type="text" class="form-control"  name="address" id="address" placeholder="Not required..."><br>
            <label for="phone">Phone:     </label>
            <input type="text" class="form-control"  name="phone" id="phone" pattern="[0-9]{10}" placeholder="Not required..."><br>
            <button class="btn btn-primary" type="submit" id="submit">ADD</button>
        </form>
    </section>
    <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
