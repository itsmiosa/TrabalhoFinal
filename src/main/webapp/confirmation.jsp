<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login error</title>
    <style>
      @import url("https://fonts.cdnfonts.com/css/product-sans");

        body {
            font-family: "Product Sans", sans-serif;
            background-color: rgb(34, 34, 34);
        }

      .container {
        background-color: rgb(255, 255, 255);
        width: 500px;
        margin: auto;
        margin-top: 10%;
        border: 2px solid rgb(255, 255, 255);
        border-radius: 30px;
        padding: 30px;
        text-align: center;
      }
		.BackButton {
            display: inline-block;
            font-size: medium;
            width: 26%;
            color: rgb(232, 232, 232);
            border: none;
            border-radius: 70px;
            text-align: center;
            text-decoration: none;
            background-color: rgb(34, 34, 34);
            padding: 10px;
            margin-top: 20px;
        }
        .BackButton:hover {
            background-color: gray;
        }
      .Header {
        margin: 1px;
        color: rgb(34, 34, 34);
        margin-left: 10px;
      }

      .buttons {
        display: inline-block;
        font-size: 1.2rem; /* Relative font size */
        width: 45%; /* Relative width */
        color: rgb(232, 232, 232);
        border: none;
        border-radius: 70px;
        text-align: center;
        text-decoration: none;
        background-color: rgb(34, 34, 34);
        padding: 10px;
        margin: 10px 0;
      }

      .buttons:hover {
        background-color: gray;
      }

      @media (max-width: 600px) {
        .container {
          width: auto; /* Adjust container width */
        }

        .buttons {
          width: 90%; /* Full width for buttons */
          font-size: 1rem; /* Adjust font size */
        }
		.BackButton {
        	width: 92%;
        }
      }
    </style>
  </head>
  <body>
  <% 
	String title = (String) request.getAttribute("title");
	String author = (String) request.getAttribute("author");
                    %>
    <div class="container">
      <h1 class="Header">You have successfully requested: <%=title%> by <%=author%></h1>
      <a class="BackButton" href="SearchBookServlet?bookQuery=">Request more</a>
    </div>
  </body>
</html>
