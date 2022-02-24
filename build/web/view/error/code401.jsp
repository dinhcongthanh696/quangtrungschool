<%-- 
    Document   : code401
    Created on : Nov 24, 2021, 4:17:53 PM
    Author     : My Computer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Warning</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"/>
        <style>
            div{
                text-align: center;
            }
            
            h1{
                color : red;
                text-align: center;
            }
        </style>
        
        <script>
            function doDirection(){
                window.location = "/QuangTrungSchool/login";
            }
        </script>
    </head>
    <body>
        <div>
            <img src="https://memegenerator.net/img/instances/60426417.jpg" alt="warning"/>
            <h1>You must login before,The System does not know who are you?</h1>
        </div>
        <button onclick="doDirection()" class="btn btn-primary">Back To Login</button>
    </body>
</html>
