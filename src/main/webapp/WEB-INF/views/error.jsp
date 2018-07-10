<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
</head>
<body>

You are not authorized see this page.
Redirecting you to login page in <span id="timeRemaining">5</span> seconds.

<script type="text/javascript">
	//window.setTimeout(function(){ window.location = "/ROOT/login/login"; },3000);
	
	var count = 5;
setInterval(function(){
    count--;
    document.getElementById('timeRemaining').innerHTML = count;
    if (count == 0) {
        window.location = '../login/login'; 
    }
},1000);
	
</script>

</body>
</html>