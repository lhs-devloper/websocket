<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Web Socket Example</title>
<link rel="stylesheet" href="https://unpkg.com/mvp.css@1.12/mvp.css"> 
</head>
<body>

<% 
	String id = request.getParameter("id");
	int roomId = Integer.valueOf(id);
%>
  <!-- 채팅 영역 -->
  <form>
    <!-- 텍스트 박스에 채팅의 내용을 작성한다. -->
    <select id="msgOpt">
    	<option value="all">전체</option>
    	<option value="target">귓속말</option>
    </select>
    <input id="textMessage" type="text" onkeydown="return enter()">
    <!-- 서버로 메시지를 전송하는 버튼 -->
    <input onclick="sendMessage()" value="Send" type="button">
  </form>
  <br />
  <!-- 서버와 메시지를 주고 받는 콘솔 텍스트 영역 -->
  <textarea id="messageTextArea" rows="10" cols="50" disabled="disabled"></textarea>
  <script type="text/javascript">
    // 서버의 broadsocket의 서블릿으로 웹 소켓을 한다.
    let webSocket = new WebSocket("ws://192.168.0.128:8080/chat/broadsocket");
    // 콘솔 텍스트 영역
    let messageTextArea = document.getElementById("messageTextArea");
    // 접속이 완료되면
    webSocket.onopen = function(message) {
      // 콘솔에 메시지를 남긴다.
      messageTextArea.value += "Server connect...\n";
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
    	console.log(typeof message.data)
    	let parsemessage = JSON.parse(message.data);
    	userNickname = parsemessage.user
    //  messageTextArea.value += "(operator) => " + parsemessage.data + "\n";
      messageTextArea.value += "(" + parsemessage.user +")" + " => " + parsemessage.data + "\n";
      messageTextArea.scrollTop = messageTextArea.scrollHeight;
    };
    // 서버로 메시지를 발송하는 함수
    // Send 버튼을 누르거나 텍스트 박스에서 엔터를 치면 실행
    function sendMessage() {
      // 텍스트 박스의 객체를 가져옴
      let message = document.getElementById("textMessage");
      // 콘솔에 메세지를 남긴다.
      messageTextArea.value += "(me) => " + message.value + "\n";
      // 소켓으로 보낸다.
      let messageJson = {
    		  "roomId": <%=roomId%>,
    		  "order": "message",
    		  "content": message.value
      }
      console.log(messageJson);
      webSocket.send(JSON.stringify(messageJson));
      // 텍스트 박스 추기화
      message.value = "";
      messageTextArea.scrollTop = messageTextArea.scrollHeight;
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
</body>
</html>