
document.addEventListener('DOMContentLoaded', function () {
	/*직원추가 변수*/
    const cancelbtn = document.querySelector('.cancelbtn');
    const createEmployeeDiv = document.querySelector('#create-employee');
    const createbtn = document.querySelector('.createbtn');
    const employeeForm = createEmployeeDiv.querySelector('form');
    
    
    /*직원수정 변수*/
    
    const updatecancelbtn = document.querySelector('.update-cancelbtn');
    const updateEmployeeDiv = document.querySelector('#update-employee');
    const updatebtn = document.querySelectorAll('.updatebtn');
    const updateForm = updateEmployeeDiv.querySelector('form');
    
    /*직원수정 변수*/
    
    /*직원추가 버튼 이벤트*/
    cancelbtn.addEventListener('click', function () {
        createEmployeeDiv.classList.remove('create');
        createEmployeeDiv.classList.add('cancel');
        employeeForm.reset();
        document.querySelectorAll('.error').forEach(function(message) {
            message.classList.add('hide');
        });
    });
    
    createbtn.addEventListener('click', function () {
        createEmployeeDiv.classList.add('create');
    });
    
    /*직원 수정 버튼 이벤트*/
    
	updatecancelbtn.addEventListener('click', function() {
		updateEmployeeDiv.classList.remove('create');
		updateEmployeeDiv.classList.add('cancel');
		updateForm.reset();
		document.querySelectorAll('.error').forEach(function(message) {
			message.classList.add('hide');
		});
	});
	
	updatebtn.forEach(function(btn){
		btn.addEventListener('click',function(){
			const employeeId = this.getAttribute('data-employee-id');
			updateEmployeeDiv.classList.add('create')
			fetchEmployeeData(employeeId);
		})
	});
	
	/*수정 버튼 눌렀을때 기존 직원데이터 가져오기*/
	function fetchEmployeeData(employeeId) {
    fetch(`/personnel/employee/${employeeId}`)
        .then(response => response.json())
        .then(data => {
            // 폼 필드에 데이터 채우기
            document.querySelector('#update-employee #name').value = data.name;
            document.querySelector('#update-employee #department').value = data.department;
            document.querySelector('#update-employee #position').value = data.highestRole;;
            document.querySelector('#update-employee #hireDate').value = data.hireDate;
            document.querySelector('#update-employee #status').value = data.employeeStatus;
            
            // 폼의 action URL 업데이트
            document.querySelector('#update-employee form').action = `/personnel/update/${employeeId}`;
         
        })
        .catch(error => console.error('Error:', error));
}

	

    
    /*신규직원추가 유효성검사*/
    
    let name = document.querySelector('#name');
    let nameFailureMessage = document.querySelector('.name-failure');
    
    let email = document.querySelector('#email');
    let emailFailureMessage = document.querySelector('.email-failure');
    
    let pass = document.querySelector('#pass');
    let passFailureMessage = document.querySelector('.pass-failure');
    
    let department = document.querySelector('#department');
    let position = document.querySelector('#position');
    let hireDate = document.querySelector('#hireDate');
    let status = document.querySelector('#status');
    
    
    
    /*유효성검사 함수*/
    
    /*이름 검사*/
    function nameVerification(value) {
        return value.length >= 2 && /^[가-힣a-zA-Z]+$/.test(value);
    }
    
     /*이메일 검사*/
    function emailVerification(value) {
        return /^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
    }
    
     /*비밀번호 검사*/
    function passVerification(value) {
        return value.length >= 6 && /[a-zA-Z\d!@$%*]/.test(value);
    }
    

    /*실시간 피드백 함수*/
    
    /*이름*/
    name.onkeyup = function() {
        if (name.value.length === 0 || nameVerification(name.value)) {
            nameFailureMessage.classList.add('hide');
        } else {
            nameFailureMessage.classList.remove('hide');
        }
    };
    
    /*이메일*/
    email.onkeyup = function() {
        if (email.value.length === 0 || emailVerification(email.value)) {
            emailFailureMessage.classList.add('hide');
        } else {
            emailFailureMessage.classList.remove('hide');
        }
    };
    
    /*비밀번호*/
    pass.onkeyup = function() {
        if (pass.value.length === 0 || passVerification(pass.value)) {
            passFailureMessage.classList.add('hide');
        } else {
            passFailureMessage.classList.remove('hide');
        }
    };
    
         /*제출시 모든 유효성 검사처리*/
    function isCreatFormValid() {
        return (
            nameVerification(name.value) &&
            emailVerification(email.value) &&
            passVerification(pass.value) 
            
        );
    }
    
    
    // 제출 이벤트
    employeeForm.onsubmit = function(event) {
        if (!isCreatFormValid()) {
            event.preventDefault();
            alert('모든 입력란을 올바르게 작성해주세요.');
        }
    };
});
