$(function(){
	
	var username = $('.navbar-right #username').text();	// get the text - Welcome username
	username = username.substring(8,username.length); // gets the username part , trims off Welcome part
	var userList = null;
	var socket = null , socket1 = null , stompClient1 = null;

	if(username !== null && username.length>0) {
		
		$.ajax({
			url: "GetAllUserList",
			type: "GET",
			async: true,
			data : {},
			success: function(result) {
				var firstUsername = null;
				if(result.length>0) {
					userList = result;
					for(var u of result) {
						if(u.username !== username) {
							firstUsername = u.username;
							break;
						}
					}
				}
				
				if(firstUsername !== null) {
					$('.sideBar-body'+ '#' + firstUsername).click();
				}
				openWebSocketAndSubscribe(userList);
			}
		});
	}

var openWebSocketAndSubscribe = function(userList) {
		
	if(socket==null && username !== null && username.length>0) {
	    socket = new SockJS('../chatBox/chat-websocket');
	    stompClient = Stomp.over(socket);   
	    stompClient.connect({}, function (frame) {

	    	stompClient.subscribe('/user/allusersgroup/reply', function (chat) {
	        	var chatBody = JSON.parse(chat.body);
	            showGreeting(chatBody.chatMessage,chatBody.fromUsername,"allusersgroup",chatBody.timeStamp);
	        });
	    	
	       	 for(const user of userList) {
	       		stompClient.subscribe('/user/'+ user.username+'/reply', function (chat) {
		        	var chatBody = JSON.parse(chat.body);
		            showGreeting(chatBody.chatMessage,chatBody.fromUsername,user.username,chatBody.timeStamp);
		        });
	       	 }       	 
	    });	

	    socket1 = new SockJS('../chatBox/userstate-websocket');
	    stompClient1 = Stomp.over(socket1);
	    
	    stompClient1.connect({}, function (frame) {
	    	
	    	stompClient1.subscribe('/topic/userstate', function (usersState) {
	    		var userstate = JSON.parse(usersState.body);
	    		
	    		for(const key in userstate) {					
					if(key===username)
						continue;    /// skipping the current logged in user
					
					if(userstate[key]==false)	    		
		    			$('#'+key +'.heading-online')[0].textContent = "Offline" ;
		    		else
		    			$('#'+key +'.heading-online')[0].textContent = "Online" ;
	    		}
	        });     
	   
	    	
	    	// get logged in username
	    	// send stomp client 1 message that he is "online"
	    	// when log out is clicked , send stomp client 1 message that he is "offline"
	    	
	    });		    
	}
}
	
	
	function showGreeting(message,fromUsername,toUsername,timeStamp) {
	    if(fromUsername===username) {
	    
	    	var divAndReply = '<div class="row message-body">';
	          divAndReply += '<div class="col-sm-12 message-main-sender">';
	          divAndReply += '<div class="sender">';	          
	          divAndReply += '<div class="message-text">';
	          divAndReply +=   message;
	          divAndReply +=   '</div>';
	          divAndReply +=   '<span class="message-time pull-right">';
	          divAndReply +=    timeStamp;
	          divAndReply +=   ' </span>';
	          divAndReply +=   '</div>';
	          divAndReply +=   '</div>';
	          divAndReply +=   ' </div>';
	    	
	          if(toUsername==="allusersgroup") {
	        	 $('.message '+'#allusersgroup').append(divAndReply);
	    		}
	          else {
	        	  $('.message '+'#'+fromUsername+toUsername).append(divAndReply);
	          }
	    }
	    else {

	    	var divAndReply = '<div class="row message-body">';
	          divAndReply += '<div class="col-sm-12 message-main-receiver">';
	          divAndReply += '<div class="receiver">';
	          if(toUsername==="allusersgroup") {
		          divAndReply +=  '<div class="row-no-margin" >';
		          divAndReply +=	fromUsername;
		          divAndReply +=	'</div>';
	          }
	          divAndReply += '<div class="message-text">';
	          divAndReply +=   message;
	          divAndReply +=   '</div>';
	          divAndReply +=   '<span class="message-time pull-right">';
	          divAndReply +=   timeStamp;
	          divAndReply +=   ' </span>';
	          divAndReply +=   '</div>';
	          divAndReply +=   '</div>';
	          divAndReply +=   ' </div>';
	    	
	          if(toUsername==="allusersgroup") {
	        	  $('.message '+'#allusersgroup').append(divAndReply);
	    		}
	          else {
	        	  $('.message '+'#'+toUsername+fromUsername).append(divAndReply);
	          }
	    }	
	    
	    var mydiv = $("#conversation");
	    mydiv.scrollTop(mydiv.prop("scrollHeight"));
	  
	    $('#comment')[0].value = '';
	   
	    	 
	}
	
    $(".heading-compose").click(function() {
      $(".side-two").css({
        "left": "0"
      });
    });

    $(".newMessage-back").click(function() {
      $(".side-two").css({
        "left": "-100%"
      });
    });
    
    $('#comment').keypress(function(e){
    	var key = e.which;
    	if(key==13){
    		$(".reply-send").click();
    		return false;
    	}    	
    });
    
    $(".reply-send").click(function(e){
    	var message = $('#comment').val();
    	var toUsername = $(".heading-name-meta").attr('id');    	
    	var time = moment().format('LT'); 
    	if(message !== null && message.length>0) {  
    		var text = JSON.stringify({'chatMessage': message,'toUsername': toUsername,'fromUsername': username, 'timeStamp': time})
        	stompClient.send("/app/chat-websocket", {}, text);        	
    	}
    });
    
    
    $('.sideBar-body').click(function(){
    	var clickedUsername = $(this).attr('id');   
    	var firstName = $('#'+clickedUsername).find("#firstName")[0].innerHTML;
    	var lastName = $('#'+clickedUsername).find("#lastName")[0].innerHTML; 
    	
    	$('.heading-name-meta')[0].innerHTML = firstName + lastName;    	
    	$('.heading-name-meta').attr('id',clickedUsername);
    	
    	var divId = null;
    	
    	if(clickedUsername=="allusersgroup") {
    		divId = clickedUsername;
    	}
    	else {
    		divId  = username+clickedUsername;
    	}	
    	 $(".message").children().filter(':not(#'+divId+')').hide();
    	 $(".message").children().filter('#'+divId).show();
    
    });
    
  
    setInterval(function(){
    	
    	$.ajax({
			url: "GetUsersState",
			type: "GET",
			async: true,
			data : {},
			success: function(result) {
				stompClient1.send("/app/userstate-websocket", {}, JSON.stringify(result));			
			}
    	})
    	},5000); 
    
})