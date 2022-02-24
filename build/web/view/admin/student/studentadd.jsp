<%-- 
    Document   : studentadd
    Created on : Sep 27, 2021, 8:58:19 PM
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
            var students;

            function loadData() {  // PAGE LOADED --> LOAD STUDENTS AND ACCOUNTS IN DATABASE --> PUT IT INTO students and usernames;
                $.ajax(
                        {url: "/QuangTrungSchool/admin-student-check-add",
                            type: 'GET',
                            success: function (response) {  // servlet /admin-student-add response 2 arraylist STUDENTS AND ACCOUTS
                                const JSONtoObject = JSON.parse(response);
                                students = JSONtoObject.students;
                            }
                        }
                );
            }

            function checkStudentExsist(value) {
                var isDuplicate = false;
                for (let i = 0; i < students.length; i++) {
                    if (value.toLowerCase().localeCompare(students[i].studentCode.toLowerCase()) == 0) {
                        isDuplicate = true;
                        break;
                    }
                }
                return isDuplicate;
            }

            function alertStudentWarning(value) {
                var warning = document.getElementById("studentduplicate");
                var isDuplicate = checkStudentExsist(value);
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
                for (let i = 0; i < students.length; i++) {
                    if (value.toLowerCase().localeCompare(students[i].account.username.toLowerCase()) == 0) {
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
        <style>            
            .right .form-control{
                width: 50%;
            }

            .right{
                padding-left: 15%;
                color: blue;
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

    </head>
    <body onload="loadData()">
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <form action="/QuangTrungSchool/admin-student-add" method="POST">   
                    <h2>Add New Student</h2>
                    <label for="studentCode">Student Code:  </label>
                    <input type="text" name="studentCode" id="studentCode" onkeyup="alertStudentWarning(this.value)" required class="form-control" placeholder="Enter..."> 
                    <span id="studentduplicate"> X student id has already exist </span> 
                    <br/>
                    <label for="fullname">Student full name:   </label>
                    <input type="text" class="form-control" name="fullname" id="fullname" pattern="[A-Za-z ]*" placeholder="Enter..."><br>
                    <label for="dob">Student Date of Birth:   </label>
                    <input type="date" name="dob" id="dob" class="form-control" required><br>
                    <label for="username">Username:    </label>
                    <input type="text" class="form-control" name="username" id="username" onkeyup="alertAccountWarning(this.value)" pattern="[a-z0-9]{2,}" placeholder="Enter...">
                    <span id="accountinvalid"> X username is invalid </span> 
                    <label for="password">Password:    </label>
                    <input type="password" class="form-control" name="password" id="password" required placeholder="Enter..."> 
                    <br>
                    <label for="email">Email:    </label>
                    <input type="text" class="form-control" name="email" id="email" placeholder="Not Required..."><br>
                    <label for="address">Address:     </label>
                    <input type="text" class="form-control"  name="address" id="address" placeholder="Not Required..."><br>
                    <label for="phone">Phone:     </label>
                    <input type="text" class="form-control"  name="phone" id="phone" pattern="[0-9]{10}" placeholder="Not Required"><br>
                    <button class="btn btn-primary" type="submit" id="submit">ADD</button>
                </form>
            </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
