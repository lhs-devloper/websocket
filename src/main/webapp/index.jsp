<%@page import="com.lhs.sevlets.LoginHandler"%>
<%@page import="com.lhs.BroadSocket"%>
<%@page import="com.lhs.dto.Room"%>
<%@page import="java.util.*"%>
<%@page import="com.lhs.dto.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	if(session.getAttribute("user") == null){
		response.sendRedirect("login");
	} 
	String userNickName = null;
	ArrayList<Room> roomList = LoginHandler.getRoomList();
	User user = (User) session.getAttribute("user");
	if(user == null){	
	} else{
		userNickName = user.getNickname();		
	}

%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://unpkg.com/mvp.css@1.12/mvp.css"> 
<style>
	a{
		text-decoration: none;
	}
</style>
</head>
<body>
	<h1>Welcome: <%= userNickName %></h1>
	<script>
		function linktoclick(id, index){
			location.href = "room?id="+id+"&index="+index;
		}
		function deletetoclick(id, index){
			var form = document.createElement("form");
	        var parm = new Array();
	        var input = new Array();
			url = "delete?target=room&id="+id+"&index="+index;
	        form.action = url;
	        form.method = "post";
	        document.body.appendChild(form);
	        form.submit();
		}
		</script>
	<table>
		<tr>
			<th>방ID</th>
			<th>방 제목</th>
			<th>방 인원</th>
			<th>방 입장</th>
			<th>방 삭제</th>
		</tr>
		<% if(roomList != null) { %>
		<%  for(int i = 0; i < roomList.size(); i++){ %>
		<tr>
			<th><%= roomList.get(i).getId()%></th>
			<th><%= roomList.get(i).getTitle() %></th>
			<th><%= roomList.get(i).getUserList().size() %> / <%= roomList.get(i).getEntryLimit() %></th>
			<th><button onclick="linktoclick(<%=roomList.get(i).getId()%>,<%=i%>)">입장</button></th>
			<th><button onclick="deletetoclick(<%=roomList.get(i).getId()%>, <%=i%>)">삭제</button></th>
		</tr>
		<% } %>
		<% } %>
	</table>
	
	<div>
		<h3>방 만들러가기</h3>
		<a href="roomCreate.jsp">방 만들기</a>
	</div>
</body>
</html>