/*$(document).ready(function() {
	// 검색 폼 제출 시 AJAX 요청
	$('#cmsearch-form form').on('submit', function(e) {
		e.preventDefault();
		loadInventoryData($(this).serialize());
	});

	// 페이지네이션 클릭 시 AJAX 요청
	$(document).on('click', '.pagination a', function(e) {
		e.preventDefault();
		loadInventoryData($(this).attr('href').split('?')[1]);
	});

	function loadInventoryData(queryString) {
		$.ajax({
			url: '/inventory',
			type: 'GET',
			data: queryString,
			success: function(response) {
				$('#cmposting-format').html($(response).find('#cmposting-format').html());
				$('.pagination').html($(response).find('.pagination').html());
			},
			error: function(xhr, status, error) {
				console.error("An error occurred: " + error);
			}
		});
	}
});*/

function loadInventoryData(queryString) {
    $.ajax({
        url: '/inventory',
        type: 'GET',
        data: queryString,
        dataType: 'json',
        success: function(response) {
            updateInventoryList(response.items);
            updatePagination(response.currentPage, response.totalPages);
        },
        error: function(xhr, status, error) {
            console.error("An error occurred: " + error);
        }
    });
}

function updateInventoryList(items) {
    var html = '<ul><li><span>코드</span><span>이름</span><span>공급자</span><span>가격</span><span>수량단위</span><span>소분류</span></li>';
    items.forEach(function(item) {
        html += '<li>' +
            '<span>' + item.itemCode + '</span>' +
            '<span>' + item.name + '</span>' +
            '<span>' + item.itemSource + '</span>' +
            '<span>' + item.itemMoney + '</span>' +
            '<span>' + item.standard + '</span>' +
            '<span>' + item.categoryId + '</span>' +
        '</li>';
    });
    html += '</ul>';
    $('#cmposting-format').html(html);
}

function updatePagination(currentPage, totalPages) {
    var html = '';
    if (totalPages > 1) {
        if (currentPage > 0) {
            html += '<span><a href="/inventory?page=' + (currentPage - 1) + '">&laquo; 이전</a></span>';
        }
        for (var i = 0; i < totalPages; i++) {
            html += '<span><a href="/inventory?page=' + i + '" class="' + (i == currentPage ? 'active' : '') + '">' + (i + 1) + '</a></span>';
        }
        if (currentPage < totalPages - 1) {
            html += '<span><a href="/inventory?page=' + (currentPage + 1) + '">다음 &raquo;</a></span>';
        }
    }
    $('.pagination').html(html);
}