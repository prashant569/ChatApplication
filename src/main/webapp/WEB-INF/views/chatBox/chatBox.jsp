<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "spring" uri = "http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Chat Box</title>
   
    <spring:url value="../resources/sockjs.js" var="sockJsUrl"></spring:url>
    <spring:url value="../resources/stomp.js" var="stompJsUrl"></spring:url> 
    <spring:url value="../resources/font-awesome-4.7.0/css/font-awesome.css" var="fontAwesomeCssUrl" />
    <spring:url value="../resources/moment.js" var="momentJsUrl"></spring:url>
    
	<%@ include file="../commonFiles.jsp" %>
    
    <script src="${sockJsUrl}"></script>
    <script src="${stompJsUrl}"></script>
   <script src="${momentJsUrl}"></script>
	<script src="../resources/chatBox/chatBox.js"></script>
	<link href="../resources/chatBox/chatBox.css" rel="stylesheet" />
	<link rel="stylesheet" href="${fontAwesomeCssUrl}">
	 
    
</head>
<body>

<%@ include file="../navigation.jsp" %>

<div class="container app">
  <div class="row app-one">
    <div class="col-sm-4 side">
      <div class="side-one">
        <div class="row heading">
          <div class="col-sm-2 col-xs-3 heading-avatar">
            <div class="heading-avatar-icon">
              <img src="https://bootdey.com/img/Content/avatar/avatar1.png">
            </div>
          </div>
          <div class="col-sm-5 col-xs-7 heading-name">
          <a class="name-meta" id="${currentUser}"> ${firstName} ${lastName }</a>
          </div>
          <div class="col-sm-1 col-xs-1  heading-dot  pull-right">
            <i class="fa fa-ellipsis-v fa-2x  pull-right" aria-hidden="true"></i>
          </div>
          <div class="col-sm-2 col-xs-2 heading-compose  pull-right">
            <i class="fa fa-comments fa-2x  pull-right" aria-hidden="true"></i>
          </div>
        </div>

        <div class="row searchBox">
          <div class="col-sm-12 searchBox-inner">
            <div class="form-group has-feedback">
              <input id="searchText" type="text" class="form-control" name="searchText" placeholder="Search">
              <span class="glyphicon glyphicon-search form-control-feedback"></span>
            </div>
          </div>
        </div>

        <div class="row sideBar">
        
        <div class="row sideBar-body" id="allusersgroup" >
            <div class="col-sm-3 col-xs-3 sideBar-avatar">
              <div class="avatar-icon">
                <img src="https://bootdey.com/img/Content/avatar/avatar1.png">
              </div>
            </div>
            <div class="col-sm-9 col-xs-9 sideBar-main">
              <div class="row">
                <div class="col-sm-8 col-xs-8 sideBar-name">
                  <span class="name-meta" id="firstName">All Users </span>
                  <span class="name-meta" id="lastName">Group </span>
                </div>
                <div class="col-sm-4 col-xs-4 pull-right sideBar-time">
                  <span class="time-meta pull-right">Time
                </span>
                </div>
              </div>
            </div>
          </div>
          
        
        <c:forEach items="${userList}" var="user">
        
        	<div class="row sideBar-body" id="${user.username}" >
            <div class="col-sm-3 col-xs-3 sideBar-avatar">
              <div class="avatar-icon">
                <img src="https://bootdey.com/img/Content/avatar/avatar1.png">
              </div>
            </div>
            <div class="col-sm-9 col-xs-9 sideBar-main">
              <div class="row">
                <div class="col-sm-8 col-xs-8 sideBar-name">
                  <span class="name-meta" id="firstName">${user.firstName} </span>
                  <span class="name-meta" id="lastName">${user.lastName} </span>
                </div>
                <div class="col-sm-4 col-xs-4 pull-right sideBar-time">
                  <span class="time-meta pull-right">Time
                </span>
                </div>
              </div>
            </div>
          </div>
          
          
        </c:forEach>
        
        </div>
      </div>

    </div>

    <div class="col-sm-8 conversation">
      <div class="row heading">
        <div class="col-sm-2 col-md-1 col-xs-3 heading-avatar">
          <div class="heading-avatar-icon">
            <img src="https://bootdey.com/img/Content/avatar/avatar1.png">
          </div>
        </div>
        <div class="col-sm-8 col-xs-7 heading-name">
          <a class="heading-name-meta">John Doe
          </a>
          <span class="heading-online">Online</span>
        </div>
        <div class="col-sm-1 col-xs-1  heading-dot pull-right">
          <i class="fa fa-ellipsis-v fa-2x  pull-right" aria-hidden="true"></i>
        </div>
      </div>

	  
      <div class="row message" id="conversation">
       
       <div id="allusersgroup">      
		</div>

	<c:forEach items="${userList}" var="user">
		<div id="${currentUser}${user.username}">      
		</div>		
	</c:forEach>		
        
      </div>

      <div class="row reply">
        <div class="col-sm-1 col-xs-1 reply-emojis">
          <i class="fa fa-smile-o fa-2x"></i>
        </div>
        <div class="col-sm-9 col-xs-9 reply-main">
          <textarea class="form-control" rows="1" id="comment"></textarea>
        </div>
        <div class="col-sm-1 col-xs-1 reply-recording">
          <i class="fa fa-microphone fa-2x" aria-hidden="true"></i>
        </div>
        <div class="col-sm-1 col-xs-1 reply-send">
          <i class="fa fa-send fa-2x" aria-hidden="true"></i>
        </div>
      </div>
    </div>
  </div>
</div>




</body>
</html>