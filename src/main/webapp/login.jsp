<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="./setting.jsp" %>
<link rel="stylesheet" href="./css/form.css">
</head>
<body>
<% if(request.getAttribute("name") != null){ %>
		<h1> Check Id or Password </h1>
<% } %>
<div class="form">
	<h2 style="color: #04B431;">Green 채팅방 로그인하기</h2>
	<form method="post" action="login">
	  <div class="form-group">
	    <label>id: </label>
	    <input type="text" name="id" required="required" class="form-control">
	  </div>
	  <div class="form-group">
		<label>password: </label>
		<input type="password" name="pw" required="required" class="form-control">
	  </div>
	  <button type="submit" class="btn btn-success">로그인하기</button>
	</form>
	<div class="link">
		<a href="signUp">아이디가 없으신가여? &rarr; 회원가입하러가기</a>
	</div>
</div>

<%@ include file="./footer.jsp" %>
</body>
</html>