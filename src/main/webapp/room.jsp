<%@page import="com.lhs.dao.RoomDAO"%>
<%@page import="com.lhs.dto.Room"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Web Socket Example</title>
<%@ include file="./setting.jsp" %>
<link rel="stylesheet" href="./css/chat.css">
</head>
<body>
<% 
	String id = request.getParameter("id");
	int roomId = Integer.parseInt(id);
	Room room = new RoomDAO().select(roomId);
	if(room.getPassword() != null){
		if(application.getAttribute("cetify") == null){
			out.print("<script>alert('접근권한 없음'); location.href='./index.jsp'</script>");			
		} else{
			if((int)application.getAttribute("cetify") != roomId){
				out.print("<script>alert('접근권한 없음'); location.href='./index.jsp'</script>");
			}
		}
	}
%>
  <!-- 채팅 영역 -->
  <div class="btn-group">
  <button class="btn btn-success dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
    귓속말 보내는 법
  </button>
  <div class="dropdown-menu">
    <p>귓속말 옵션 설정</p>
    <p>[닉네임]작성 후 보내기</p>
    <p>예시) [xxx] 님 안녕하세요</p>
  </div>
  </div>
  
  <div class="chat">
  <fieldset id="chat-filedset">
  	<div id="chat-list"></div>
  </fieldset>
  <form>
    <!-- 텍스트 박스에 채팅의 내용을 작성한다. -->
    <select id="msgOpt">
    	<option value="all">전체</option>
    	<option value="target">귓속말</option>
    </select>
    <input id="textMessage" type="text" onkeydown="return enter()">
    <!-- 서버로 메시지를 전송하는 버튼 -->
    <input onclick="sendMessage()" value="Send" type="button" class="btn btn-success">
  </form>
  </div>
  <br />
  
  <!-- 서버와 메시지를 주고 받는 콘솔 텍스트 영역 -->
  <script type="text/javascript" charset="UTF-8">
    // 서버의 broadsocket의 서블릿으로 웹 소켓을 한다.
    // opopen();
    const webSocket = new WebSocket("ws://192.168.0.128:8080/chat/broadsocket");
    // 콘솔 텍스트 영역
    let messageTextArea = document.getElementById("messageTextArea");
    let chatfiled = document.querySelector('#chat-filedset')
    // 접속이 완료되면
    webSocket.onopen = function(message) {
      // 콘솔에 메시지를 남긴다.
      // messageTextArea.value += "Server connect...\n";
      console.log("isTrue?");
      let enterMessageJson = {
    		  "roomId": <%=roomId%>,
    		  "order": "enter"
      }
      webSocket.send(JSON.stringify(enterMessageJson))
    };
    // 접속이 끝기는 경우는 브라우저를 닫는 경우이기 떄문에 이 이벤트는 의미가 없음.
    webSocket.onclose = function(message) { };
    // 에러가 발생하면
    webSocket.onerror = function(message) {
      // 콘솔에 메시지를 남긴다.
      messageTextArea.value += "error...\n";
    };
    // 서버로부터 메시지가 도착하면 콘솔 화면에 메시지를 남긴다.
    webSocket.onmessage = function(message) {
    	// chat ++
    	let parsemessage = JSON.parse(message.data);
    	userNickname = parsemessage.user
    	console.log(parsemessage)
    	const div = document.createElement('div');
    	if(parsemessage.system){
    		div.classList.add('system');
    		const chat = document.createElement('div');
            div.textContent = parsemessage.data;
            div.appendChild(chat);
            document.querySelector('#chat-list').appendChild(div);
            chatfiled.scrollTop = chatfiled.scrollHeight;
    	} else if(parsemessage.dm){
    		div.classList.add('dm');
	        const chat = document.createElement('div');
	        div.textContent = "(" + parsemessage.user +")" + "=> " + parsemessage.data;
	        div.appendChild(chat);
	        document.querySelector('#chat-list').appendChild(div);
	        chatfiled.scrollTop = chatfiled.scrollHeight;
    	}else{		
	        div.classList.add('other');
	        const chat = document.createElement('div');
	        div.textContent = "(" + parsemessage.user +")" + "=> " + parsemessage.data;
	        div.appendChild(chat);
	        document.querySelector('#chat-list').appendChild(div);
	        chatfiled.scrollTop = chatfiled.scrollHeight;
    	}
        
    	
    	
    	/*
    	if(parsemessage.dm){
    		messageTextArea.value += "test";
    	}
    	*/
    	// messageTextArea.value += "(" + parsemessage.user +")" + " => " + parsemessage.data + "\n";
    	// messageTextArea.scrollTop = messageTextArea.scrollHeight;
    };
    // 서버로 메시지를 발송하는 함수
    // Send 버튼을 누르거나 텍스트 박스에서 엔터를 치면 실행
    function sendMessage() {
      // 텍스트 박스의 객체를 가져옴
      let message = document.getElementById("textMessage");
      console.log(message);
      let msgOption = document.getElementById("msgOpt");
      let targetUser = 'all';
      // 콘솔에 메세지를 남긴다.
	  if(msgOption.value === "target"){
		  let target1 = message.value.indexOf('[');
		  let target2 = message.value.indexOf(']');
		  if(target1 != undefined && target2 != undefined){		  
			  targetUser = message.value.substr(target1+1, target2-1);
			  message.value = message.value.substring(target2+1);
		  }
	  }
      const div = document.createElement('div');
      div.classList.add('mine');
      const chat = document.createElement('div');
      div.textContent = "(me) => " + message.value + "\n";
      div.appendChild(chat);
      document.querySelector('#chat-list').appendChild(div);

      // 소켓으로 보낸다.
      let messageJson = {
    		  "roomId": <%=roomId%>,
    		  "order": "message",
    		  "content": message.value,
    		  "target": msgOption.value,
    		  "targetUser": targetUser
      }
      console.log(messageJson);
      webSocket.send(JSON.stringify(messageJson));
      message.value = "";
      // 텍스트 박스 추기화
      // message.value = "";
      // messageTextArea.scrollTop = messageTextArea.scrollHeight;
      chatfiled.scrollTop = chatfiled.scrollHeight;
    }
    // 텍스트 박스에서 엔터를 누르면
    function enter() {
      // keyCode 13은 엔터이다.
      if(event.keyCode === 13) {
        // 서버로 메시지 전송
        sendMessage();
        // form에 의해 자동 submit을 막는다.
        return false;
      }
      return true;
    }
  </script>
 <%@ include file="./footer.jsp" %>
</body>
</html>