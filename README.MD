# JAVA WEBSOCKET

Java로 구현한 채팅통신

이 프로젝트의 가장 큰 목표는 다음과 같습니다    

1. Java static Memoery에 대한 이해 Static Memory에 올라간 데이터와 DB를 일치하게 만드는 것이 핵심
2. WebSocketHandling(Spring이 아닌 서블릿과 JSP를 통해 어떻게 해결하는지에 대한 고민)
3. 코드 작성을 통해 WS 프로토콜을 더 이해하기   
   
<img src="https://velog.velcdn.com/images/qwd101/post/096ac043-b958-46a0-86e8-309811c882aa/image.png" alt="WebSocket" />

### 주의할 점
room.jsp의 javascript 코드 변경
```javascript
new Socket("ws://192.168.0.128:8080/chat/broadsocket");
/*
이대로 실행할 시 localhost의 HTTpSession과 WebSocket에서의 가져오는 HTTPSession이 일치하지 않을 수 있음
*/

// 다음과 같이 변경
new Socket("ws://localhost:8080/chat/broadsocket")
// 또는
new Socket("ws://localhost:port/chat/broadsocket")
```

### TABLE 설계
```sql
create table if not exists User(
	id int primary key auto_increment,
	user_id varchar(20) not null unique,
    password varchar(100) not null,
    nickname varchar(50) not null
);

create table if not exists Room(
	id int primary key auto_increment,
    owner int,
    title varchar(100) not null,
	entry_limit int not null,
    password varchar(100),
    foreign key(owner) references User(id) on delete cascade
);

create table if not exists Chat(
	id int primary key auto_increment,
    room_id int,
	user_id int,
	content text not null,
    created_at timestamp default now(),
    foreign key(room_id) references Room(id) on delete cascade,
    foreign key(user_id) references User(id) on delete cascade
);
```