<%-- 
    Document   : newsadd
    Created on : Oct 27, 2021, 5:24:59 PM
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
            function doInsert() {
                var title = $("#title").val();
                var content = $("#content").val();
                var groups = [];
                var i = 0;
                $.each($("input[type='checkbox']:checked"), function () {
                    groups[i++] = $(this).val();
                });
                if (groups.length !== 0) {
                    $.ajax({
                        url: "/QuangTrungSchool/admin-news-add",
                        type: "POST",
                        data: {
                            title: title,
                            content: content,
                            groups: groups.toString()
                        },
                        success: function (response) {
                            $(".message").empty();
                            $(".message").append("<span class='success'>News added successfully</span>")
                        }
                    });
                } else {
                    alert("News must be one of groups");
                }
            }
        </script>
        <style>
            .success{
                color: green;
                font-weight: bold;
            }

            .right .form-control{
                width: 80%;
            }

            .right{
                padding-left: 15%;
                color: blue;
            }

            .right .btn-primary{
                margin-left: 19%;
                border-radius: 3px;
                width: 120px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="../header.jsp"></jsp:include>
            <section class="right">
                <h2>Add News</h2>
                <label for="title">Title</label>
                <input type="text" required="required" id="title" placeholder="Enter..." class="form-control">
                <label for="content">Content</label>
                <textarea cols="60" rows="15" id="content" placeholder="Enter..." class="form-control" required="required"></textarea>
                Group : 
            <c:forEach items="${requestScope.groups}" var="group">
                <c:if test="${group.gid != 3}">
                    <input type="checkbox" name="group" id="${group.gid}" class="groups" value="${group.gid}"> 
                    <label for="${group.gid}"> ${group.gname} </label>   
                </c:if>
            </c:forEach><br/>
            <button class="btn btn-primary" onclick="doInsert()">Save Changes</button>
            <div class="message"></div>
        </section>
        <jsp:include page="../footer.jsp"></jsp:include>
    </body>
</html>
