<%@page import="com.lhs.dto.Room"%>
<%@page import="com.lhs.dao.RoomDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	Room room = new RoomDAO().select(id);
	String roomPassword = room.getPassword();
%>
<body>
	<script type="text/javascript">
		let inputString = prompt('비밀번호를 입력해주세요');
		if(inputString == "<%=roomPassword%>"){
			<% 
				application.setAttribute("cetify", id);
			%>
			location.href = "room.jsp?id="+<%=id%>;
		}else{
			
		}
	</script>
	
</body>
</html>