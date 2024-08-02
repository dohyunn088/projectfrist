function checkOnlyOne(element) {
	const checkboxes
		= document.getElementsByName("team");
	checkboxes.forEach((cb) => {
		cb.checked = false;
	})
	element.checked = true;
}

//첨부파일 기능
$("#file").on('change', function() {
	var fileName = $("#file").val();
	$(".upload-name").val(fileName);
});

const textInput = document.getElementById('textInput');
const charCount = document.getElementById('charCount');
textInput.addEventListener('input', () => {
	const currentLength = textInput.value.length;
	charCount.textContent = `${currentLength}/300`;
});