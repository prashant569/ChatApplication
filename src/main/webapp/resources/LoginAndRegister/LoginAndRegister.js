/**
 * 
 */
	

$(function() {
	
	if($('#register-form-link').hasClass("active"))	{
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		}
	
	if($('#login-form-link').hasClass("active"))	{
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		}
	
    $('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
    
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	
	$('#register-submit').click(function(event) {
		
		
		if($('#register-form #username').val().length == 0 
				|| $('#register-form #password').val().length == 0 
				|| $('#register-form #confirm-password').val().length == 0
				|| $('#register-form #firstName').val().length == 0
				|| $('#register-form #lastName').val().length == 0)
		{
			$("#register-message")[0].innerHTML = "Please fill all the fields";
			event.preventDefault();
		}
		else if($('#register-form #username').val().length > 10 
				|| $('#register-form #password').val().length > 10
				|| $('#register-form #firstName').val().length > 10
				|| $('#register-form #lastName').val().length > 10)
		{
			$("#register-message")[0].innerHTML = "Maximum allowed length in all the fields is 10";
			event.preventDefault();
		}
		else if($('#register-form #password').val() !== $('#register-form #confirm-password').val()) 
		{
			$("#register-message")[0].innerHTML = "Passwords do not match";
			event.preventDefault();
		}
		else if($('#register-form #username').val().indexOf(' ') > -1) {
			$("#register-message")[0].innerHTML = "username cannot have space";
			event.preventDefault();
		}
		
		else if($('#register-form #username').val().length > 0 
				&& $('#register-form #username').val().length <=10 
				&& $('#register-form #password').val().length > 0
				&& $('#register-form #password').val().length <=10
				&& $('#register-form #password').val() === $('#register-form #confirm-password').val()
				&& $('#register-form #firstName').val().length > 0
				&& $('#register-form #firstName').val().length <= 10
				&& $('#register-form #lastName').val().length > 0
				&& $('#register-form #lastName').val().length <= 10
		){
			
			//if constraint is satisfied then, move on
			
			//check for existing username by ajax call
			
			var success = true;
			
			 $.ajax({
			        url: "checkForExistingUsername",
			        type: "GET",
			        async: false, //block until we get a response
			        data: { username : $("#register-form #username").val() },
			        success: function(error) {
			            if (error) {
			                $("#register-message")[0].innerHTML = error;
			                success = false;
			            }
			            
			            return success;
			        }
			    });
			
			 if(success==false) {
				 event.preventDefault();
			 }
		
		}
		
	});
	
	
	$('#login-submit').click(function(event){
		
		var success = false;
		
		$.ajax({
			url: "checkForCredentials",
			type:"POST",
			async: false,
			data: {username : $('#login-form #username').val() , password : $('#login-form #password').val() },
			success: function(isMatched) {
				if(isMatched) {
					success=true;
				}
				else {
					$('#login-message')[0].innerHTML = "Wrong username or password";
					success=false;
				}
				return success;
				
			}
			
		});
		
		if(success==false) {
			event.preventDefault();
		}
		
		
	});
	

});
