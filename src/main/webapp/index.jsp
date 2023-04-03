<%@page import="com.lhs.RoomStaticList"%>
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
	ArrayList<Room> roomList = RoomStaticList.getroomList();
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
<%@ include file="./setting.jsp" %> 
<style>
	a{
		text-decoration: none;
	}
	.table-styled{
		display:flex;
		
	}
	.table{
		display: flex;
		overflow-y:scroll;
		justify-content: center;
		width: 100%;
		height: auto;
		height: 80vh;
		table-layout : fixed;
		text-align: center;
	}
	.table tr, .table td, table th{
		padding-left: 50px;
		padding-right: 50px;
	}
</style>
</head>
<body>
	<h3 style="text-align: right;">환영합니다: <%= userNickName %>님</h3>
	<div class="alert alert-success" role="alert">
  		<a href="roomCreate.jsp">방 만들러 가기 &rarr; link</a>
	</div>
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
	<div class="table-styled">
	<table class="table table-striped table-hover">
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
			<td><%= roomList.get(i).getId()%></td>
			<td><%= roomList.get(i).getTitle() %></td>
			<td><%= roomList.get(i).getUserList().size() %> / <%= roomList.get(i).getEntryLimit() %></td>
			<td><button onclick="linktoclick(<%=roomList.get(i).getId()%>,<%=i%>)" class="btn btn-success">입장</button></td>
			<td><button onclick="deletetoclick(<%=roomList.get(i).getId()%>, <%=i%>)" class="btn btn-success">삭제</button></td>
		</tr>
		<% } %>
		<% } %>
	</table>
	</div>
<%@ include file="./footer.jsp" %>
</body>
</html>