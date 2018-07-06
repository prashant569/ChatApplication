
<spring:url value="/user/list" var="listURL" />
<spring:url value="/cryptocurrency/coinmarketcap" var="cryptocurrencyListURL" />
<spring:url value="/chatBox/chatBox" var="chatBoxUrl" />
<spring:url value="/login" var="LoginAndRegisterUrl" />
<spring:url value="/login/register" var="registerUrl" />
<spring:url value="/login/login" var="loginUrl" />
<spring:url value="/login/logout" var="logoutUrl" />
<spring:url value="/" var="homeUrl" />


<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${homeUrl}">Home</a>
    </div>
    <ul class="nav navbar-nav">
    
    <%    
    	if((String)session.getAttribute("isAdmin") != null) {
    %>
    	<li><a href="${listURL}">User List</a></li>
    <%
		}
    %>
           
	   <%
	    	String username = (String)session.getAttribute("username");
	   		if(username != null) {
	   %>
      		<li><a href="${chatBoxUrl}">Chat Box</a></li>
      <%
	   		}
      %>
    </ul>
    <ul class="nav navbar-nav navbar-right">
    
    <%	
    	if(username == null)
    	{
    %>
      		<li><a href="${registerUrl}"><span class="glyphicon glyphicon-user"></span>Register </a></li>
      		<li><a href="${loginUrl}"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
    <%
    	}
    	else {
    		
    %>
    		<li id="username"><a><span></span>Welcome ${username}</a></li>
      		<li><a href="${logoutUrl}"> <button type="submit" class='btn btn-default btn-sm'><span class='glyphicon glyphicon-log-out'></span>Log out</button></a></li>
    	
    <%
    	}
    %>

    </ul>
  </div>
</nav>


