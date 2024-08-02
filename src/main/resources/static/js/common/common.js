
/*담당자: 전송희 절대 건들지 말아주세요*/

document.addEventListener("DOMContentLoaded", function() {


	//메뉴바 기능을 위한 변수
	const nav=document.querySelector('.cmnav');
	const layoutWrap =document.querySelector('.layoutWrap');
	const menubar = document.querySelector('.menubar');
	const menuspan = document.querySelectorAll('.cmnav-bttom span, .logo');
	const menua = document.querySelectorAll('.cmnav-bttom a');
	
	//메인 기능을 위한 변수 
	const main=document.querySelector('main');
	const cmsubnav =document.querySelector('.cmsubnav');


	

	//메뉴바 호버이벤트
	menubar.addEventListener("mouseover", function() {
		menubar.classList.add('menubarhover');
	});

	menubar.addEventListener("mouseout", function() {
		menubar.classList.remove('menubarhover');
	});
	

	//메뉴바 클릭 이벤트
	menubar.addEventListener("click", function() {
		menuspan.forEach(function(span) {
			if (span.classList.contains('spanhidden')) {
				span.classList.remove('spanhidden');
				
				layoutWrap.style.gridTemplateColumns = "200px auto";
				
				menua.forEach(function(a){
					a.style.textAlign = 'left';
				})
				

			} else {
				span.classList.add('spanhidden');
				layoutWrap.style.gridTemplateColumns = "75px auto";
				
				menua.forEach(function(a){
					a.style.textAlign = 'center';
					
					a.addEventListener("mouseover",function(){
						
						var span = a.querySelector('span');
						span.classList.add('spanshow')
						
					})
					
					a.addEventListener("mouseout",function(){
						
						var span = a.querySelector('span');
						span.classList.remove('spanshow')
						
					})
					
				});
				
		
			}


		});

	});
	
	//메인에 서브메뉴가 없다면 실행될 코드
	if(!cmsubnav){
		
		main.style.gridTemplateColumns ="0 100%"
		main.style.padding ="10px 10px 10px 10px"
	}
	
	
	



});