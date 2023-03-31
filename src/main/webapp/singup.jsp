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
	<form action="signUp" method="post">
		<label>id: </label><input type="text" name="id" required="required" style="width: 24%">
		<label>password: </label><input type="password" name="pw" required="required">
		<label>nickname: </label><input type="text" name="nickname" required="required">
		<input type="submit" value="회원가입">
	</form>
	<pre>
		닉네임 중복 허용 (DB Unique설정 필요)
	</pre>
</body>
</html>