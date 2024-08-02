document.addEventListener("DOMContentLoaded", function() {
    // HTML 요소들을 변수에 저장
    const btn = document.getElementById("openModal");
    const modal = document.getElementById("myModal");
    const span = document.getElementsByClassName("close")[0];
    const chatFrame = document.getElementById("chatFrame");

    // 버튼 클릭 시 모달 열기
    btn.onclick = function(event) {
        event.preventDefault(); // 기본 동작 방지
        chatFrame.src = '/chat'; // iframe의 src를 설정
        modal.style.display = "block"; // 모달 열기
    };

    // 닫기 버튼 클릭 시 모달 닫기
    span.onclick = function() {
        modal.style.display = "none"; // 모달 닫기
        chatFrame.src = ''; // iframe src 비우기
    };

    // 모달 외부 클릭 시 모달 닫기
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none"; // 모달 닫기
            chatFrame.src = ''; // iframe src 비우기
        }
    };
});