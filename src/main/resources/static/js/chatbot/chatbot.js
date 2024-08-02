var client;
var key;
let flag = false;
let userState = {
    expectingName: false
};

// 브라우저가 WebSocket을 지원하는지 확인하는 함수
function isWebSocketSupported() {
    return 'WebSocket' in window;
}

// WebSocket 지원 여부를 출력
if (isWebSocketSupported()) {
    console.log("이 브라우저는 WebSocket을 지원합니다.");
} else {
    console.log("이 브라우저는 WebSocket을 지원하지 않습니다.");
}

function formatDate(now) {
    const year = now.getFullYear();
    const month = now.getMonth() + 1;
    const date = now.getDate();
    const days = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    const dayOfWeek = days[now.getDay()];
    return `${year}년 ${month}월 ${date}일 ${dayOfWeek}`;
}

// 대화 내용 추가
function showMessage(tag) {
    $("#chat-content").append(tag);
    $("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}

// 웹소켓 연결 후 인사말 출력
function connect() {
    client = Stomp.over(new SockJS('/ws'));
    client.connect({}, (frame) => {
        key = new Date().getTime();
        client.subscribe(`/topic/bot/${key}`, (answer) => {
            var msgObj = answer.body;
            var now = new Date();
            var date = formatDate(now);
            var tag = `<div class="flex center date">${date}</div>
                        <div class="msg bot flex">
                            <div class="icon">
                                <img src="/images/chatbot.png">
                            </div>
                            <div class="message">
                                <div class="part">
                                    <p>${msgObj}</p>
                                </div>
                            </div>
                        </div>
                        <div id="categories">
                            <button class="btn" onclick="toggleCategoryMessage(this, '직원 검색')">직원 검색</button>
                            <button class="btn" onclick="toggleCategoryMessage(this, '스케줄 확인')">스케줄 확인</button>
                            <button class="btn" onclick="toggleCategoryMessage(this, '내 정보 변경')">내 정보 변경</button>
                            <button class="btn" onclick="toggleCategoryMessage(this, '공지사항 확인')">공지사항 확인</button>
                        </div>`;
            showMessage(tag);
        });

        var data = {
            key: key,
            content: "hello",
            name: "guest"
        };
        client.send("/app/hello", {}, JSON.stringify(data));
    });
}

// 웹소켓 종료
function disconnect() {
    client.disconnect(() => {
        console.log("Disconnected...");
    });
}

// 종료(X) 버튼 클릭 시 이벤트
function btnCloseClicked() {
    $("#bot-container").hide();
    $("#chat-content").html("");
    disconnect();
    flag = false;
}

function btnBotClicked() {
    if (flag) {
        $("#bot-container").hide();
        $("#chat-content").html("");
        disconnect();
        flag = false;
    } else {
        $("#bot-container").show();
        connect();
        flag = true;
    }
}

function clearQuestion() {
    $("#question").val("");
}

// 직원 검색 함수 (실제로는 서버에 요청을 보내야 함)
function searchEmployee(name) {
    const employees = {
        "전송희": "인사부 / 총지배인 / 연락처: 010-1111-2222 / 이메일 : test21@test.com" ,
        "이유진": "시설관리부 / 부장 / 연락처: 010-6225-6102 / 이메일 : test22@test.com",
        "식음료부": "식음료부 / 사원 / 연락처: 010-1111-2222 / 이메일 : test23@test.com" 
    };
    return employees[name] || "해당 이름의 직원을 찾을 수 없습니다.";
}

// 사용자 입력 처리 함수
function handleUserInput(input) {
    if (userState.expectingName) {
        const searchResult = searchEmployee(input);
        const response = `직원 검색 결과: ${input}\n${searchResult}`;
        showBotMessage(response);
        userState.expectingName = false;
    } else {
        var data = {
            key: key,
            content: input,
            name: "guest"
        };
        client.send("/bot/question", {}, JSON.stringify(data));
    }
}

// 카테고리 메시지 전송 (토글 기능 포함)
function toggleCategoryMessage(btn, content) {
    $("#categories .btn").removeClass('active');
    $(btn).addClass('active');
    $("#categories .btn").prop('disabled', true);
    $(btn).prop('disabled', false);

    if (content === '직원 검색') {
        showBotMessage("이름을 검색해주세요.");
        userState.expectingName = true;
    } else {
        var data = {
            key: key,
            content: content,
            name: "guest"
        };
        client.send("/bot/question", {}, JSON.stringify(data));
        showNextQuestion(content);
    }
}

// 다음 질문 보여주는 함수
function showNextQuestion(category) {
    let options = '';
    let message = '';

    switch (category) {
        case '스케줄 확인':
            message = '찾으시는 스케줄을 눌러주세요';
            options = `
            <div id="categories">
                <button class="btn" onclick="toggleCategoryMessage(this, '연차 확인')">연차 확인</button>
                <button class="btn" onclick="toggleCategoryMessage(this, '휴일 확인')">휴일 확인</button>
            </div>`;
            break;
        case '내 정보 변경':
            message = '우측 상단 마이페이지 클릭 > 우측 상단 프로필 수정 클릭 후 변경 하시면 됩니다.';
            break;
        case '공지사항 확인':
            message = '찾으시는 공지를 눌러주세요';
            options = `
            <div id="categories">
                <button class="btn" onclick="toggleCategoryMessage(this, '오늘 공지 확인')">오늘 공지 확인</button>
                <button class="btn" onclick="toggleCategoryMessage(this, '이전 공지 확인')">이전 공지 확인</button>
            </div>`;
            break;
        case '연차 확인':
            message = '남은 연차는 3개입니다.';
            break;
        case '휴일 확인':
            message = '이번 달 휴일은 3일,8일,10일,11일,17일,22일,25일,30일 입니다.';
            break;
        case '오늘 공지 확인':
            message = '오늘 올라온 공지는 0개 입니다. 다른 공지사항은 좌측 게시판에서 확인바랍니다.';
            break;
        case '이전 공지 확인':
            message = '이전 공지 확인은 좌측 게시판에서 공지사항을 확인바랍니다.';
            break;
        default:
            message = '선택하신 카테고리에 대한 정보를 찾을 수 없습니다.';
    }

    if (message) {
        showBotMessage(message);
    }
    if (options) {
        showMessage(options);
    }
}

// 챗봇 메시지 표시 함수
function showBotMessage(message) {
    var tag = `<div class="msg bot flex">
                <div class="icon">
                    <img src="/images/chatbot.png">
                </div>
                <div class="message">
                    <div class="part">
                        <p>${message}</p>
                    </div>
                </div>
               </div>`;
    showMessage(tag);
}

// 메시지 전송
function btnMsgSendClicked() {
    var question = $("#question").val().trim();
    if (question.length < 2) {
        alert("질문은 최소 2글자 이상으로 부탁드립니다.");
        return;
    }

    var userMessageTag = `<div class="msg user flex">
                            <div class="message">
                                <div class="part">
                                    <p>${question}</p>
                                </div>
                            </div>
                          </div>`;
    showMessage(userMessageTag);

    handleUserInput(question);
    clearQuestion();
}

$(function() {
    $("#btn-bot").click(btnBotClicked);
    $("#btn-send").click(btnMsgSendClicked);
});

// 엔터 키 입력 처리
$("#question").keypress(function(e) {
    if (e.which == 13) {  // 13은 엔터 키의 키코드
        e.preventDefault();  // 기본 엔터 키 동작 방지
        btnMsgSendClicked();
    }
});
