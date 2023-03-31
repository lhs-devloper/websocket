<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://unpkg.com/mvp.css@1.12/mvp.css"> 
</head>
<body>
	<form action="login" method="post">
		<label>id: </label><input type="text" name="id" required="required" style="width: 24%">
		<label>password: </label><input type="password" name="pw" required="required">
		<input type="submit" value="로그인">
	</form>
	<div>
		<a href="signUp">아이디가 없으신가여?</a>
	</div>
</body>
</html>