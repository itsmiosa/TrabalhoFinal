<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results</title>
    <style>
          @import url("https://fonts.cdnfonts.com/css/product-sans");
    
        body {
            font-family: "Product Sans", sans-serif;
            background-image: url("https://static.vecteezy.com/system/resources/previews/030/639/865/non_2x/library-image-hd-free-photo.jpg");
            background-repeat: no-repeat;
            background-size: cover;
            background-position: center;
            background-color: rgb(30, 21, 17);
        }
        .DeleteUser {
            display: inline-block;
            font-size: medium;
            width: 100%;
            color: rgb(232, 232, 232);
            border: none;
            border-radius: 70px;
            text-align: center;
            text-decoration: none;
            background-color: rgb(34, 34, 34);
            padding: 10px;
        }
        .DeleteUser:hover {
            background-color: gray;
            cursor: pointer;
        }
        .BackButton {
            display: inline-block;
            font-size: medium;
            width: 20%;
            color: rgb(232, 232, 232);
            border: none;
            border-radius: 70px;
            text-align: center;
            text-decoration: none;
            background-color: rgb(34, 34, 34);
            margin-top: 20px;
			padding: 14px;
        }
        .BackButton:hover {
            background-color: gray;
        }
        .container {
            margin: auto;
            width: 60%;
            background-color: rgb(255, 255, 255);
            margin-top: 2%;
            border: 0px;
            border-radius: 30px;
            padding: 40px;
        }
        .Header {
            text-align: center;
            color: rgb(34, 34, 34);
            margin-bottom: 40px;
            margin-top: 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid rgb(34, 34, 34);
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: rgb(34, 34, 34);
            color: white;
        }
        .notAvailable {
            font-weight: bold;
            font-size: large;
            margin-bottom: 0px;
            color: red;
        }
        .criarConta {
            display: inline-block;
            font-size: medium;
            width: 20%;
            color: rgb(232, 232, 232);
            background-color: rgb(34, 34, 34);
            padding: 14px;
            margin: 8px;
            border: none;
            border-radius: 25px;
            text-align: center;
            text-decoration: none;
        }
        th {
        	text-align: center;
        }
        .criarConta:hover {
            background-color: gray;
        }
        @media (max-width: 600px) {
            .container {
                width: auto;
            }
            .DeleteUser {
                width: 100%;
            }
            .BackButton {
                width: 92%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="Header">Users registered in our library!</h1>   		
        <% 
        List<Object[]> users = (List<Object[]>) request.getAttribute("users");
        if (users != null && !users.isEmpty()) {
        %>
        <table>
            <tr>
                <th>Username</th>
                <th>NIF</th>
                <th>Email</th>
                <th>Action</th>
            </tr>
            <% for (Object[] user : users) {
                String nif = (String) user[0];
                String username = (String) user[1];
                String email = (String) user[3];
            %>
            <tr>
                <td><%= username %></td>
                <td><%= nif %></td>
                <td><%= email %></td>
                <td>
                    <form action="ManageUsersServlet" method="post">
                        <input type="hidden" name="nif" value="<%= nif %>">
                        <input class="DeleteUser" type="submit" value="Delete user">
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
        <% } else { %>
        <p>No users registered!</p>
        <% } %>
        <div>
        <a class="BackButton" href="/TrabalhoFinal">Go back</a>
        <a href="/TrabalhoFinal/createUser.jsp" class="criarConta">Create new user</a> 
        </div>
    </div>
</body>
</html>
