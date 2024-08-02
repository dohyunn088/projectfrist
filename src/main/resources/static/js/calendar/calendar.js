document.addEventListener("DOMContentLoaded", function() {
    let currentMonth = new Date().getMonth();
    let currentYear = new Date().getFullYear();
    let selectedDate = null;

    const calendarMonthYear = document.getElementById("calendar-month-year");
    const calendarBody = document.getElementById("calendar-body");

    function renderCalendar(month, year) {
        const firstDay = new Date(year, month).getDay();
        const daysInMonth = 32 - new Date(year, month, 32).getDate();

        calendarBody.innerHTML = "";

        calendarMonthYear.innerHTML = year + "년 " + (month + 1) + "월";

        let date = 1;
        for (let i = 0; i < 6; i++) {
            const row = document.createElement("div");
            row.classList.add("calendar-row");

            for (let j = 0; j < 7; j++) {
                if (i === 0 && j < firstDay) {
                    const cell = document.createElement("div");
                    cell.classList.add("calendar-cell");
                    row.appendChild(cell);
                } else if (date > daysInMonth) {
                    break;
                } else {
                    const cell = document.createElement("div");
                    cell.classList.add("calendar-cell");
                    cell.innerText = date;
                    cell.addEventListener("click", function() {
                        selectedDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(date).padStart(2, '0')}`;
                        document.getElementById("selected-date").value = selectedDate;
                        document.getElementById("schedule-form").style.display = "block";
                    });

                    fetch(`/calendar/events?date=${year}-${String(month + 1).padStart(2, '0')}-${String(date).padStart(2, '0')}`)
                        .then(response => response.json())
                        .then(events => {
                            if (events.length > 0) {
                                const eventMarker = document.createElement("div");
                                eventMarker.classList.add("event-marker");
                                cell.appendChild(eventMarker);
                            }
                        });

                    row.appendChild(cell);
                    date++;
                }
            }

            calendarBody.appendChild(row);
        }
    }

    document.getElementById("prev-month").addEventListener("click", function() {
        currentMonth--;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        }
        renderCalendar(currentMonth, currentYear);
    });

    document.getElementById("next-month").addEventListener("click", function() {
        currentMonth++;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        renderCalendar(currentMonth, currentYear);
    });

    document.getElementById("cancel-event").addEventListener("click", function() {
        document.getElementById("schedule-form").style.display = "none";
    });

    renderCalendar(currentMonth, currentYear);
});
