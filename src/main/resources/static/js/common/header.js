
document.addEventListener("DOMContentLoaded", function() {

	const mainmenu = document.querySelector(".cmnav-bttom a");

	document.getElementById('toggle-dark-mode').addEventListener('change', function() {
		const isDarkMode = this.checked;
		document.body.classList.toggle('dark-mode', this.checked);
		document.getElementById('sun-icon').style.display = this.checked ? 'none' : 'inline';
		document.getElementById('moon-icon').style.display = this.checked ? 'inline' : 'none';
		localStorage.setItem('darkMode', isDarkMode);
	});

	window.addEventListener('load', function() {
		const isDarkMode = localStorage.getItem('darkMode') === 'true';
		document.body.classList.toggle('dark-mode', isDarkMode);
		document.getElementById('toggle-dark-mode').checked = isDarkMode;


	
	});
});
