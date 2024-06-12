<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
    <style>
      @import url("https://fonts.cdnfonts.com/css/product-sans");

        body {
            font-family: "Product Sans", sans-serif;
            background-color: rgb(34, 34, 34);
        }
      .criarConta {
        display: inline-block;
        font-size: medium;
        font-weight: bold;
        width: 25%;
        color: rgb(34, 34, 34);
        padding: 14px;
        margin: 8px;
        border: none;
        border-radius: 25px;
        text-align: center;
        text-decoration: none;
      }
      .criarConta:hover {
        background-color: rgb(232, 232, 232);
      }
      .Header {
        margin: 1px;
        color: rgb(34, 34, 34);
        margin-left: 10px;
        margin-bottom: 20px;
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
      input[id="username"],
      select {
        width: 96.5%;
        padding: 12px;
        margin: 8px;
        display: inline-block;
        border: 1px solid #888;
        border-radius: 25px;
        box-sizing: border-box;
      }
      input[id="password"],
      select {
        width: 72%;
        padding: 12px;
        margin: 8px;
        display: inline-block;
        border: 1px solid #888;
        border-radius: 25px;
        box-sizing: border-box;
      }
      input[type="submit"] {
        font-size: medium;
        font-weight: bold;
        width: 20%;
        background-color: rgb(34, 34, 34);
        color: rgb(232, 232, 232);
        padding: 14px;
        margin: 8px;
        border: none;
        border-radius: 25px;
        cursor: pointer;
        text-align: center;
      }
      input[type="submit"]:hover {
        background-color: gray;
      }
      @media (max-width: 600px) {
        .container {
          width: auto; /* Adjust container width */
        }
        input[type="submit"]{
        	width: auto;
        }
        input[id="password"]{
        	width: 96.5%
        }
    </style>
  </head>
  <body>
    <div class="container">
      <h1 class="Header">Login</h1>
      <form action="j_security_check" method="post">
        <input
          type="text"
          name="j_username"
          id="username"
          placeholder="Username"
        />
        <div class="button-container">
          <input
            type="text"
            name="j_password"
            id="password"
            placeholder="Password"
          />
          <input type="submit" value="Login" />
        </div>
        <a href="/TrabalhoFinal/createUser.jsp" class="criarConta"
          >Create new user</a
        >
        
      </form>
    </div>
  </body>
</html>
