document.addEventListener('DOMContentLoaded', function () {
    const cancelbtn = document.querySelector('.cancelbtn');
    const createEmployeeDiv = document.querySelector('#edit-profile');
    const editbtn = document.querySelector('.editbtn');
    const profileForm = document.querySelector('.edit-profile-form');
    
    /*새비밀번호 변수*/
    const editPassBtn = document.querySelector('.edit-pass-btn');
    const editPassDiv = document.querySelector('.edit-pass-wrap');
    const passCancelBtn = document.querySelector('.pass-cancel-btn');
    const passForm = document.querySelector('#edit-pass-form');
    
    /*토큰*/
 	 const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
     const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
   	
   	  console.log('CSRF Header:', csrfHeader); // CSRF 헤더 이름 확인
            console.log('CSRF Token:', csrfToken);  // CSRF 토큰 값 확인
    
    

    
    /*프로필 수정버튼 이벤트*/
    cancelbtn.addEventListener('click', function () {
        createEmployeeDiv.classList.remove('create');
        createEmployeeDiv.classList.add('cancel');
        profileForm.reset();
        document.querySelectorAll('.error').forEach(function(message) {
            message.classList.add('hide');
        });
    });
    
    editbtn.addEventListener('click', function () {
        createEmployeeDiv.classList.add('create');
    });
  
    
    /*새비밀번호 버튼 이벤트*/
      passCancelBtn.addEventListener('click', function () {
        editPassDiv.classList.remove('create');
        editPassDiv.classList.add('cancel');
        passForm.reset();
        document.querySelectorAll('.error').forEach(function(message) {
            message.classList.add('hide');
        });
    });
    
    editPassBtn.addEventListener('click', function () {
        editPassDiv.classList.add('create');
    });
    
    

   
    
    
    /*프로필 수정 유효성검사*/
    
    let phone = document.querySelector('#phone');
    let phoneFailureMessage = document.querySelector('.phone-failure');
    
    let email = document.querySelector('#email');
    let emailFailureMessage = document.querySelector('.email-failure');
    
    const currentPass = document.querySelector('#current-password');
    const currentPassFailureMessage = document.querySelector('.current-pass-failure');
    let debounceTimer;
    const debounceDelay = 20; 
    
    let newPass = document.querySelector('#new-password');
    let newPassFailureMessage = document.querySelector('.new-pass-failure');
    
    let confirmPass = document.querySelector('#confirm-new-password');
    let confirmPassFailureMessage = document.querySelector('.confirm-pass-failure');
 
    const elRoadAddress = document.querySelector("#roadAddress");
    const elRoadAddressDetail = document.querySelector("#roadAddressDetail");
    
    
    /*유효성검사 함수*/
    
  
    
     /*이메일 검사*/
    function emailVerification(value) {
        return /^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
    }
    
    

    
    
     /*새비밀번호 검사*/
    function newPassVerification(value) {
        return value.length >= 6 && /[a-zA-Z\d!@$%*]/.test(value);
    }
    
    
    /*새비밀번호 확인 검사*/
     function confirmNewPassVerification(newPassValue, confirmPassValue) {
        return newPassValue === confirmPassValue;
    }
    
    /*휴대폰 검사*/

	function phoneVerification(value) {
		if (value.length < 4) return value;
		if (value.length < 8) return value.replace(/(\d{3})(\d+)/, "$1-$2");
		return value.replace(/(\d{3})(\d{4})(\d+)/, "$1-$2-$3");
	}
	
	/*주소검색창 열기*/
	   document.querySelector("#search-btn").addEventListener("click", () => {
        new daum.Postcode({
            oncomplete: function (data) {
                elRoadAddress.value = data.address;
            }
        }).open();
    });

    
    /*프로필 제출시 모든 유효성 검사처리*/
    function profileIsFormValid() {
        return (
            emailVerification(email.value)
    
        );
    }
    
    /*비밀번호 제출시 유효성 검사처리 */
    
	function newpassIsFormValid() {
	    return (
	        newPassVerification(newPass.value) &&
	        confirmNewPassVerification(newPass.value, confirmPass.value)
	    );
	}
    /*실시간 피드백 함수*/
    
    

    
    /*이메일*/
    email.onkeyup = function() {
        if (email.value.length === 0 || emailVerification(email.value)) {
            emailFailureMessage.classList.add('hide');
        } else {
            emailFailureMessage.classList.remove('hide');
        }
    };
    
	/*현재 비밀번호 검사*/


	currentPass.onkeyup = function() {
		clearTimeout(debounceTimer);

		debounceTimer = setTimeout(function() {
			if (currentPass.value.length > 0) {
				$.ajax({
					beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
					url: '/personnel/check-password',
					type: 'POST',
					data: { pass: currentPass.value },
					success: function(response) {
						if (response.valid) {
							currentPassFailureMessage.classList.add('hide');
						} else {
							currentPassFailureMessage.classList.remove('hide');
						}
					},
					error: function(xhr, status, error) {
						console.error('Error:', error);
					}
				});
			} else {
				currentPassFailureMessage.classList.add('hide');
			}
		}, debounceDelay);
	};

    /*새비밀번호*/
    newPass.onkeyup = function() {
        if (newPass.value.length === 0 || newPassVerification(newPass.value)) {
            newPassFailureMessage.classList.add('hide');
        } else {
            newPassFailureMessage.classList.remove('hide');
        }
    };
    
    /*새비밀번호 확인*/
	confirmPass.onkeyup = function() {
        if (confirmPass.value.length === 0 || confirmNewPassVerification(newPass.value, confirmPass.value)) {
            confirmPassFailureMessage.classList.add('hide');
        } else {
            confirmPassFailureMessage.classList.remove('hide');
        }
    };
    
    /*휴대폰*/
	phone.addEventListener("input", function() {
		const value = phone.value.replace(/[^0-9]/g, "");
		phone.value = phoneVerification(value); 
		phoneFailureMessage.classList.toggle('hide', /^\d{3}-\d{4}-\d{4}$/.test(phone.value));
	});

    
    
    
    //프로필 제출 이벤트
	profileForm.onsubmit = function(event) {
		const phoneWithoutHyphen = phone.value.replace(/-/g, "");
		const fullAddress = `${elRoadAddress.value} ${elRoadAddressDetail.value}`;

		if (!profileIsFormValid() || !/^\d{3}-\d{4}-\d{4}$/.test(phone.value)) {
			event.preventDefault();
			alert('모든 입력란을 올바르게 작성해주세요.');
			return;
		}
		
		const addressInput = document.createElement('input');
		addressInput.type = 'hidden';
		addressInput.name = 'address';
		addressInput.value = fullAddress;
		profileForm.appendChild(addressInput);

	};
	
	//새비밀번호 제출 이벤트
	
	passForm.onsubmit = function(event) {
	
		if (!newpassIsFormValid()) {
			event.preventDefault();
			alert('모든 입력란을 올바르게 작성해주세요.');
			return;
		}
		
	};
	
});