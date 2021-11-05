<%-- 
    Document   : teacherlist
    Created on : Oct 7, 2021, 7:04:30 PM
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
            .right div a{
                font-size: 20px;
                padding: 10px;
                border-radius: 8px;
                color: black;
            }

            .right{
                color: blue;
            }

            .right .form-control{
                width: 40%;
            }
            .right form .btn{
                margin-left: 15%;
            }
        </style>
        <script>
            var gap = 2;
            
            function activePage() {
                var pageId = $("#curentPage").val();
                $("#" + pageId).css("background-color", "blue");
            }

            function doUpdate(teacherCode) {
                window.location = "/QuangTrungSchool/admin-teacher-update?teacherCode=" + teacherCode;
            }

            function doDelete(teacherCode) {
                var check = confirm("Are you sure?");
                if (check) {
                    $.ajax({
                        url: "/QuangTrungSchool/admin-teacher-delete",
                        type: 'GET',
                        data: {
                            teacherCode: teacherCode
                        },
                        success: function (response) {

                        }
                    });
                    $("#" + teacherCode).css("display", "none");
                }
            }
            
            function paging(){
                var currentPage = parseInt($("#curentPage").val());
                var totalPage = $("#totalPage").val();
                var query = $("#query").val();
                var totalSearchedTeachers = $("#totalSearchedTeachers").val();
                var teacherperpage = $("#teacherperpage").val();
                
                if(currentPage - gap > 1){
                    $("#paging").append(
                        "<a id='1' href=/QuangTrungSchool/admin-teacher-list?query="+query
                        +"&pageId=1&totalsearchedteachers="+ totalSearchedTeachers +"&teacherperpage="+ teacherperpage +"> 1 </a>"
                         );
                }
                for(let i = currentPage - gap ; i <= currentPage ; i++){
                    if(i >= 1){
                        $("#paging").append(
                          "<a id='"+ i +"' href=/QuangTrungSchool/admin-teacher-list?query="+query
                            +"&pageId="+i+"&totalsearchedteachers="+ totalSearchedTeachers +"&teacherperpage="+ teacherperpage +">"+ i +"</a>"      
                        );
                    }
                }
                
                for(let i = currentPage + 1 ; i <= currentPage + gap ; i++){
                    if(i <= totalPage){
                        $("#paging").append(
                          "<a id='"+ i +"' href=/QuangTrungSchool/admin-teacher-list?query="+query
                            +"&pageId="+i+"&totalsearchedteachers="+ totalSearchedTeachers +"&teacherperpage="+ teacherperpage +">"+ i +"</a>"      
                        );
                    }
                }
                
                if(currentPage + gap < totalPage){
                    $("#paging").append(
                        "<a id='"+ totalPage +"' href=/QuangTrungSchool/admin-teacher-list?query="+query
                        +"&pageId="+ totalPage +"&totalsearchedteachers="+ totalSearchedTeachers +"&teacherperpage="+ teacherperpage +"> "+ totalPage +" </a>"
                            );
                }
            }
            
            function changePerPage() {
                var teacherperpage = $("#teacherperpage").val();
                var query = $("#query").val();
                var totalsearchedteachers = $("#totalSearchedTeachers").val();
                window.location = "/QuangTrungSchool/admin-teacher-list?"
                        + "query=" + query + "&totalsearchedteachers=" + totalsearchedteachers + "&teacherperpage=" + teacherperpage;
            }
        </script>
    </head>
    <body onload="paging();activePage()">
        <input type="hidden" value="${requestScope.pageId}" id="curentPage">
        <input type="hidden" value="${requestScope.totalPage}" id="totalPage">
        <input type="hidden" value="${requestScope.totalsearchedteachers}" id="totalSearchedTeachers">
        
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Teacher List</h2>
                <label for="teacherperpage" class="form-label">Teacher Per Page : </label>
                <select id="teacherperpage" onchange="changePerPage()" class="form-select">
                    <option value=""></option>
                <c:forEach begin="1" end="${requestScope.totalsearchedteachers}" var="number">
                    <option value="${number}" ${number eq requestScope.teacherperpage ? "selected='selected'" : ""}>${number}</option>
                </c:forEach> 
                </select>
                <form action="/QuangTrungSchool/admin-teacher-list" method="POST">
                    <label for="query">Search: </label>
                <input type="text" class="form-control" id="query" name="query" value="${requestScope.query}" placeholder="Search..."><br/>
                <button type="submit" class="btn btn-primary">Search</button>
                </form>
            <table class="table table-hover table-striped">
                <tr>
                    <td>Teacher Code</td>
                    <td>Full Name</td>
                    <td>Address</td>
                    <td>Date of birth</td>
                    <td>Email</td>
                    <td>Phone</td>
                    <td>Username</td>
                    <td></td>
                    <td></td>
                </tr>
                <c:forEach items="${requestScope.teachers}" var="teacher">
                    <tr id="${teacher.teacherCode}">
                        <td>${teacher.teacherCode}</td>
                        <td>${teacher.fullname}</td>
                        <td>${teacher.address}</td>
                        <td>${teacher.dob}</td>
                        <td>${teacher.email}</td>
                        <td>${teacher.phone}</td>
                        <td>${teacher.account.username}</td>
                        <td>
                            <button class="btn btn-primary" onclick="doUpdate(this.value)" value="${teacher.teacherCode}">Edit</button>
                        </td>
                        <td>
                            <button class="btn btn-danger" onclick="doDelete(this.value)" value="${teacher.teacherCode}">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div id="paging">
                <h2>Pages</h2>
            </div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
