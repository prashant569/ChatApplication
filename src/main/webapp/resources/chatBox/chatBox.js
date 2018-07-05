$(function(){
	
	var username = $('.navbar-right #username').text();	// get the text - Welcome username
	username = username.substring(8,username.length); // gets the username part , trims off Welcome part
	
	//console.log("username = "+username);
	var userList = null;
	var socket = null;

	if(username !== null && username.length>0) {
		
		$.ajax({
			url: "GetAllUserList",
			type: "GET",
			async: true,
			data : {},
			success: function(result) {
				//console.log("successfully fethed allUserList");
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
	    var socket = new SockJS('../chatBox/chat-websocket');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	    	console.log('Connected frame : ' + frame);
	       	 for(const user of userList) {
	       		 //console.log(" in the looping = " + user.username);
	       		stompClient.subscribe('/user/'+ user.username+'/reply', function (chat) {
		        	//console.log("chat =  " + chat);
		        	var chatBody = JSON.parse(chat.body);
		        	//console.log("chat body = " + chat.body);
		            showGreeting(chatBody.chatMessage,chatBody.fromUsername,user.username,chatBody.timeStamp);
		        });
	       	 }       
	    });		
	}
}
	
	
	function showGreeting(message,fromUsername,toUsername,timeStamp) {
		//console.log("in the showGreeting");
	   // console.log(" message = " + message);
	    

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
	    	
	    		
	    	  $('.message '+'#'+fromUsername+toUsername).append(divAndReply);
		    		    	 
	    	
	    }
	    else {

	    	var divAndReply1 = '<div class="row message-body">';
	          divAndReply1 += '<div class="col-sm-12 message-main-receiver">';
	          divAndReply1 += '<div class="receiver">';
	          divAndReply1 += '<div class="message-text">';
	          divAndReply1 +=   message;
	          divAndReply1 +=   '</div>';
	          divAndReply1 +=   '<span class="message-time pull-right">';
	          divAndReply1 +=   timeStamp;
	          divAndReply1 +=   ' </span>';
	          divAndReply1 +=   '</div>';
	          divAndReply1 +=   '</div>';
	          divAndReply1 +=   ' </div>';
	    	
	    	 $('.message '+'#'+toUsername+fromUsername).append(divAndReply1);
	    	 
	    	
	    	
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
    	console.log($(".heading-name-meta"));
    	var toUsername = $(".heading-name-meta").attr('id');
    	
    	var time = moment().format('LT'); 
    	console.log("current time = " + time);
    	
    	//console.log('clicked on the send button');
    	if(message !== null && message.length>0) {
    		//console.log("message = " + message);   
    		var text = JSON.stringify({'chatMessage': message,'toUsername': toUsername,'fromUsername': username, 'timeStamp': time})
        	stompClient.send("/app/chat-websocket", {}, text);
        	
    	}   	

    });
    
    
    $('.sideBar-body').click(function(){
    	//console.log('you clicked on sideBar-body');
    	var clickedUsername = $(this).attr('id');
   
    	var firstName = $('#'+clickedUsername).find("#firstName")[0].innerHTML;
    	var lastName = $('#'+clickedUsername).find("#lastName")[0].innerHTML;
    	
    	console.log($('.heading-name-meta'));
    	console.log($('.heading-name-meta').innerHTML);
    	$('.heading-name-meta')[0].innerHTML = firstName + lastName;
    	
    	
    	$('.heading-name-meta').attr('id',clickedUsername);
    	//console.log('you clicked on ' + clickedUsername);
    	
    	var divId = username+clickedUsername;
    	
    	 $(".message").children().filter(':not(#'+divId+')').hide();
    	 $(".message").children().filter('#'+divId).show();
    
    });
    
   
    
})