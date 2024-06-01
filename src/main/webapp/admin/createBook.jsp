<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Create Book</title>
<style>
@import url("https://fonts.cdnfonts.com/css/product-sans");

body {
	font-family: "Product Sans", sans-serif;
	background-color: rgb(34, 34, 34);
}

.criarConta {
	display: inline-block;
	font-size: medium;
	width: 21%;
	color: rgb(171, 33, 33);
	padding: 14px;
	margin: 8px;
	border: none;
	border-radius: 25px;
	text-align: center;
	text-decoration: none;
}

.criarConta:hover {
	background-color: rgb(229, 195, 195);
}

.Header {
	margin: 1px;
	color: rgb(34, 34, 34);
	margin-left: 10px;
	margin-bottom: 20px;
	text-align: center;
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

input[type="text"], select {
	width: 96.5%;
	padding: 12px;
	margin: 8px;
	display: inline-block;
	border: 1px solid #888;
	border-radius: 25px;
	box-sizing: border-box;
}

input[type="number"], select {
	width: 96.5%;
	padding: 12px;
	margin: 8px;
	display: inline-block;
	border: 1px solid #888;
	border-radius: 25px;
	box-sizing: border-box;
}

input[id="password"], select {
	width: 60%;
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
	width: 32%;
	background-color: rgb(34, 34, 34);;
	color: white;
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

@media ( max-width : 600px) {
	.container {
		width: auto; /* Adjust container width */
	}
	input[type="submit"] {
		width: auto;
	}
	input[id="password"] {
		width: 96.5%
	}
}
</style>
</head>
<body>
	<div class="container">
		<h1 class="Header">Add a new book</h1>
		<form action="/TrabalhoFinal/AddBookServlet" method="post">
			<input type="text" name="isbn" id="isbn" placeholder="ISBN" required/> 
			<input type="text" name="title" id="title" placeholder="Title" required/> 
			<input type="text" name="author" id="author" placeholder="Author" required/>
			<input type="text" name="abstract" id="abstract" placeholder="Synopsis" required/>
			<input type="text" name="genre" id="genre" placeholder="Genre" required/>
			<input type="number" name="numberOfCopies" id="numberOfCopies" placeholder="Copies available" min="0" required/> 
			<input type="submit" value="Add new book" />
		</form>
	</div>
</body>
</html>
