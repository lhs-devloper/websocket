<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	if(session.getAttribute("user") == null){
		response.sendRedirect("login.jsp");
	}
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://unpkg.com/mvp.css@1.12/mvp.css"> 
</head>
<body>
	<form method="post" action="room">
		<label>입장 제한 수: </label><input type="number" name="limit" min="2" max="30">
		<label>방 이름: </label><input type="text" name="title">
		<input type="submit" value="방 만들기">
	</form>
</body>
</html>