$(document).ready(function() {
	// 대분류 변경 시 중분류 업데이트
	$('#parentCategory').change(function() {
		var parentId = $(this).val();
		$.ajax({
			url: '/categories/middle',
			type: 'GET',
			data: { parentId: parentId },
			success: function(data) {
				$('#middleCategory').html('<option value="">선택하세요</option>');
				data.forEach(function(category) {
					$('#middleCategory').append('<option value="' + category.id + '">' + category.name + '</option>');
				});
			}
		});
	});

	// 중분류 변경 시 소분류 업데이트
	$('#middleCategory').change(function() {
		var middleId = $(this).val();
		$.ajax({
			url: '/categories/sub',
			type: 'GET',
			data: { middleId: middleId },
			success: function(data) {
				$('#subCategory').html('<option value="">선택하세요</option>');
				data.forEach(function(category) {
					$('#subCategory').append('<option value="' + category.id + '">' + category.name + '</option>');
				});
			}
		});
	});

	// 검색 폼 제출 시 인벤토리 목록 업데이트
	$('#searchForm').submit(function(e) {
		e.preventDefault();
		$.ajax({
			url: $(this).attr('action'),
			type: 'GET',
			data: $(this).serialize(),
			success: function(data) {
				var inventoryList = $('#inventoryList');
				inventoryList.find('li:gt(0)').remove();
				data.forEach(function(inventory) {
					var listItem = '<li>'
						+ '<span>' + inventory.itemCode + '</span>'
						+ '<span>' + inventory.name + '</span>'
						+ '<span>' + inventory.itemSource + '</span>'
						+ '<span>' + inventory.itemMoney + '</span>'
						+ '<span>' + inventory.standard + '</span>'
						+ '<span>' + inventory.categoryId + '</span>'
						+ '</li>';
					inventoryList.append(listItem);
				});
			}
		});
	});
});