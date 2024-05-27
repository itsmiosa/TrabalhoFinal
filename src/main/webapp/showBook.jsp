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
        .RequestBook {
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
        .RequestBook:hover {
            background-color: gray;
            cursor: pointer;
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
        .container {
            margin: auto;
            width: 500px;
            background-color: rgb(255, 255, 255);
            margin-top: 2%;
            border: 0px;
            border-radius: 30px;
            padding: 40px;
        }
        .bookContainer {
            background-color: rgb(232, 232, 232);
            border-radius: 30px;
            padding: 15px;
            margin: 10px;
        }
        .bookText{
            margin: 1px;
            color: rgb(34, 34, 34);
        }
        .bookText2{
            margin-top: 2px;
            color: rgb(34, 34, 34);
        }
        .Header{
            margin: 1px;
            text-align: center;
            color: rgb(34, 34, 34);
            margin-left: 10px;
            margin-bottom: 5px;
        }
        .notAvailable{
        font-weight: bold;
        font-size: large;
        margin-bottom: 0px;
        color: red;
        }
        @media (max-width: 600px) {
        	.container {
            	width: auto;
            }
            .RequestBook {
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
    			
        		<h1 class="Header">Books available in our library!</h1>        		
                    <% 
                    List<Object[]> books = (List<Object[]>) request.getAttribute("books");
                    if (books != null && !books.isEmpty()) {
                        for (Object[] book : books) {
                            String isbn = (String) book[0];
                            String title = (String) book[1];
                            String author = (String) book[2];
                            String abstractText = (String) book[3];
                            String genre = (String) book[4];
                            boolean available = (boolean) book[5];
                            int numberCopies = (int) book[6];
                            int numberBorrowed = (int) book[7];
                            
                            int copiesAvailable = numberCopies - numberBorrowed;
                    %>
				<div class="bookContainer">
                    <h1 class="bookText" ><%=title%></h1>
                    <h2 class="bookText" ><%=author%></h2>
                    <p class="bookText2" ><%=abstractText%></p>
                    <p>Genre: <%= genre%></p>
                    <%
                    if (available){
                    %>
                    <form action="RequestBookServlet" method="get">
       					<input type="hidden" name="isbn" value="<%= isbn %>">
                		<input class="RequestBook" type="submit" value="Request book">
      				</form>
                    <% 
                    } else {
                    %>
					<p class="notAvailable">Book is not available</p>
					<% 
                    }
                    %>
        		</div>
                    <% 
                        }
                    } else {
                    %>
                            <p>No books available</p>
                    <% 
                    }
                    %>
                    <a class="BackButton" href="/TrabalhoFinal">Go back</a>
    </div>
</body>
</html>
