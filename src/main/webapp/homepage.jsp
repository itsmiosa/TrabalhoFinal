<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Homepage</title>
<style>
@import url("https://fonts.cdnfonts.com/css/product-sans");

body {
	font-family: "Product Sans", sans-serif;
	background-image:
		url("https://static.vecteezy.com/system/resources/previews/030/639/865/non_2x/library-image-hd-free-photo.jpg");
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center;
	background-color: rgb(30, 21, 17);
}

.Header {
	color: rgb(34, 34, 34);
	text-align: center;
	font-weight: bold;
	padding-bottom: 15px;
}

.mainBox {
	background-color: rgb(255, 255, 255);
	width: 500px;
	margin: auto;
	margin-top: 10%;
	border: 2px solid rgb(255, 255, 255);
	border-radius: 30px;
	padding: 30px;
}

input[id="bookQuery"], select {
	width: 96.5%;
	padding: 12px;
	margin: 8px;
	display: inline-block;
	border: 1px solid #888;
	border-radius: 25px;
	box-sizing: border-box;
}

.buttons {
	display: inline-block;
	font-size: medium;
	width: 96.5%;
	background-color: rgb(34, 34, 34);
	color: white;
	padding: 14px;
	margin: 8px;
	border: none;
	border-radius: 25px;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
}

.buttons:hover {
	background-color: gray;
}

@media ( max-width : 600px) {
	.mainBox {
		width: auto;
	}
}
</style>
</head>
<body>
	<div class="mainBox">
		<h1 class="Header">Welcome to the library</h1>
		<form action="SearchBookServlet" method="get">
			<input type="text" name="bookQuery" id="bookQuery"
				placeholder="Search by book name" />
		</form>
		<form action="ReturnBookServlet" method="get">
			<input class="buttons" type="submit" value="Return books" />
		</form>
		<form action="ManageBooksServlet" method="get">
			<input class="buttons" type="submit" value="Manage books" />
		</form>
		<form action="ManageUsersServlet" method="get">
			<input class="buttons" type="submit" value="Manage users" />
		</form>
	</div>
</body>
</html>
