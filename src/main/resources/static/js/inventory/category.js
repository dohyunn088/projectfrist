$(function() {
    // 대분류 변경 시 중분류 업데이트
    $('#parentCategory').change(function() {
        var parentId = $(this).val();
        console.log("parentId: ", parentId);
        if (parentId) {
            $.get('/inventory/categories', { parentId: parentId }, function(data) {
                console.log(data);
                var middleCategorySelect = $('#middleCategory');
                middleCategorySelect.empty();
                middleCategorySelect.append('<option value="">선택하세요</option>');
                // 중분류 세팅
                data.forEach(function(category) {
                    middleCategorySelect.append('<option value="' + category.id + '">' + category.name + '</option>');
                });
                // 소분류 리셋
                $('#subCategory').empty().append('<option value="">선택하세요</option>');
            });
        } else {
            $('#middleCategory').empty().append('<option value="">선택하세요</option>');
            $('#subCategory').empty().append('<option value="">선택하세요</option>');
        }
    });

    // 중분류 변경 시 소분류 업데이트
    $('#middleCategory').change(function() {
        var parentId = $(this).val();
        if (parentId) {
            $.get('/inventory/subcategories', { parentId: parentId }, function(data) {
                var subCategorySelect = $('#subCategory');
                subCategorySelect.empty();
                subCategorySelect.append('<option value="">선택하세요</option>');
                data.forEach(function(category) {
                    subCategorySelect.append('<option value="' + category.id + '">' + category.name + '</option>');
                });
            });
        } else {
            $('#subCategory').empty().append('<option value="">선택하세요</option>');
        }
    });

    // 검색 폼 제출 시 인벤토리 목록 업데이트
    $('#searchForm').submit(function(e) {
        e.preventDefault();
        
        // 선택된 카테고리 값들을 결합하여 하나의 categoryId로 만듦
        var parentCategory = $('#parentCategory').val();
        var middleCategory = $('#middleCategory').val();
        var subCategory = $('#subCategory').val();
        var name = $('input[name="name"]').val();

        var queryParams = {
            categoryId: [parentCategory, middleCategory, subCategory].filter(Boolean).join(','),
            name: name
        };

        $.ajax({
            url: $(this).attr('action'),
            type: 'GET',
            data: queryParams,
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