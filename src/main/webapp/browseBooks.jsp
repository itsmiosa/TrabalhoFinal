<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Browse books</title>
    <style>
      @import url("https://fonts.cdnfonts.com/css/product-sans");

      body {
        font-family: "Product Sans", sans-serif;
        background-color: #e6e6e6;
      }
      .criarConta {
        display: inline-block;
        background-color: rgb(171, 33, 33);
        font-size: medium;
        width: 21%;
        color: rgb(255, 255, 255);
        padding: 14px;
        margin: 8px;
        border: none;
        border-radius: 25px;
        text-align: center;
        text-decoration: none;
      }
      .criarConta:hover {
        background-color: rgb(164, 80, 80);
      }
      .Header {
        text-align: center;
        font-weight: normal;
      }
      .loginBox {
        background-color: rgb(255, 255, 255);
        width: 50%;
        margin: auto;
        margin-top: 10%;
        border: 2px solid rgb(255, 255, 255);
        border-radius: 30px;
        padding: 40px;
      }
      input[type="text"],
      select {
        width: 97%;
        padding: 12px;
        margin: 8px;
        display: inline-block;
        border: 1px solid #888;
        border-radius: 25px;
        box-sizing: border-box;
      }
      input[type="submit"]:hover {
        background-color: rgb(164, 80, 80);
      }
    </style>
  </head> 
  <body>
    <div class="loginBox">
      <h1 class="Header">Browse books</h1>
      <form action="SearchBookServlet.java" method="get">
        <input
          type="text"
          name="bookQuery"
          id="bookQuery"
          placeholder="Search by book name"
          required
        />
        <input type="submit" value="Search">
        <a href="" class="criarConta">Request book</a>
      </form>
    </div>
  </body>
</html>
