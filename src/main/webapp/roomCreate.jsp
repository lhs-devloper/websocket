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
<%@ include file="./setting.jsp" %>
<style>
	.form{
		display: flex;
		height: 100vh;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	form{
		width: 40%;
	}
	button{
		float: right;
	}
	label{
		font-family: 'Noto Sans KR', sans-serif;
		font-weight: bold;
	}
	#hidden{
		display: none;
	}
</style> 
</head>
<body>
<div class="form">
	<h2 style="color: #04B431;">Green 채팅방 만들기</h2>
	<form method="post" action="room">
	  <div class="form-group">
	    <label>입장 제한 수: </label>
	    <input type="number" name="limit" min="2" max="30" class="form-control" required="required">
	  </div>
	  <div class="form-group">
		<label>방 이름: </label>
		<input type="text" name="title" required="required" class="form-control">
	  </div>
	  <div class="form-group">
	  	<label>비밀 방 설정 여부: </label>
	  	<input type="checkbox" name="check" class="form-control" id="checked" onchange="checkbox(this)">
	  </div>
	  <div class="form-group" id="hidden">
	  	<label>비밀번호 입력: </label>
	  	<input type="password" name="pw" class="form-control" placeholder="비밀번호 입력">
	  </div>
	  <button type="submit" class="btn btn-success">방 만들기</button>
	</form>
</div>
<script>
	// let checkbox = document.querySelector("input[name=check]");
	function checkbox(checked){
		if(checked.checked == true){
			let passwordGroup = document.getElementById("hidden");
			console.log(passwordGroup)
			passwordGroup.style.display = "block";
		}else{
			let passwordGroup = document.getElementById("hidden");
			passwordGroup.style.display = "none";
		}
	}
</script>
</body>
</html>