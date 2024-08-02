
document.addEventListener('DOMContentLoaded', function() {
    /*토큰*/
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    
    //스케쥴을 위한 변수 
    var calendarEl = document.getElementById('calendar');
    
    //출퇴근을 위한 변수 
    const clockInBTN = document.querySelector('.clock-in-button');
    const clockOutBTN = document.querySelector('.clock-out-button');

    /*스케줄 js*/
    // 비동기로 이벤트 데이터 가져오기
    fetch('/calendar/events')
        .then(response => response.json())
        .then(eventsData => {
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                height: '100%',
                events: eventsData.map(event => ({
                    title: event.title,
                    start: event.start
                }))
            });
            calendar.render();
        })
        .catch(error => {
            console.error('Error fetching events:', error);
        });

    /*출퇴근 js*/
    let currentData = null;

    function getAttendanceStatusKoName(status) {
        const statusMap = {
            LATE: "지각",
            EARLY_DEPARTURE: "조퇴",
            NORMAL: "정상",
            HOLIDAY: "휴일",
            SICK_LEAVE: "병가",
            VACATION: "휴가",
            COMPASSIONATE_LEAVE: "경조사",
            LATE_AND_EARLY_LEAVE: "지각/조퇴"
        };
        return statusMap[status] || "미출근";
    }

    function fetchAttendanceData() {
        $.ajax({
            url: '/status',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                currentData = data;
                updateAttendanceDisplay();
            },
            error: function(xhr, status, error) {
                console.error('출근 데이터 가져오기 실패:', error);
            }
        });
    }

    function updateAttendanceDisplay() {
        if (!currentData) return;

        // 출근 시간 표시
        $('.today-commute-status li:nth-child(2) span:last-child').text(currentData.clokIn ? formatTime(currentData.clokIn) : '출근 전');

        // 퇴근 시간 표시
        $('.today-commute-status li:nth-child(3) span:last-child').text(currentData.clokOut ? formatTime(currentData.clokOut) : '퇴근 전');

        // 근무 시간 계산 및 표시
        updateWorkingTime();

        // 초과 근무 시간 표시
        $('.today-commute-status li:nth-child(5) span:last-child').text(
            currentData.overtimeHoursInMinutes ? formatMinutes(currentData.overtimeHoursInMinutes) : '00:00'
        );

        // 출근 상태 표시
        $('.today-commute-status li:nth-child(6) span:last-child').text(getAttendanceStatusKoName(currentData.attendanceStatus));
    }

    function updateWorkingTime() {
        if (!currentData || !currentData.clokIn) {
            $('.today-commute-status li:nth-child(4) span:last-child').text('00:00');
            return;
        }

        const clockInTime = new Date(currentData.clokIn);
        const currentTime = new Date();
        let workMinutes;

        if (currentData.clokOut) {
            workMinutes = currentData.workHoursInMinutes;
        } else {
            workMinutes = Math.floor((currentTime - clockInTime) / (1000 * 60));
        }

        $('.today-commute-status li:nth-child(4) span:last-child').text(formatMinutes(workMinutes));

        if (!currentData.clokOut) {
            setTimeout(updateWorkingTime, 1000);
        }
    }

    // 시간 형식 변환 함수 (HH:MM 형식으로 변환)
    function formatTime(dateTimeString) {
        const date = new Date(dateTimeString);
        return `${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}`;
    }

    // 분을 HH:MM 형식으로 변환하는 함수
    function formatMinutes(minutes) {
        const hours = Math.floor(minutes / 60);
        const mins = minutes % 60;
        return `${String(hours).padStart(2, '0')}:${String(mins).padStart(2, '0')}`;
    }

    // 페이지 로드 시 초기 상태 표시
    fetchAttendanceData();

    // 1분마다 서버에서 데이터 가져오기
    setInterval(fetchAttendanceData, 60000);

    /* 출근버튼 클릭시 */
    $('.clock-in-button').click(function() {
        $.ajax({
            beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
            url: '/clock-in',
            type: 'POST',
            success: function(response) {
                fetchAttendanceData();
            },
            error: function(xhr, status, error) {
                alert('오늘은 이미 출근하셨습니다.');
            }
        });
    });

    // 퇴근 버튼 클릭 이벤트
    $('.clock-out-button').click(function() {
        $.ajax({
            beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
            url: '/clock-out',
            type: 'PUT',
            success: function(response) {
                fetchAttendanceData();
            },
            error: function(xhr, status, error) {
                alert('출근을 먼저해주세요.');
            }
        });
    });
});