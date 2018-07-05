<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Crypto World : Login</title>

<spring:url value="/resources/LoginAndRegister/LoginAndRegister.js" var="loginAndRegisterJsUrl" />
<spring:url value="/resources/LoginAndRegister/LoginAndRegister.css" var="loginAndRegisterCssUrl" />

<spring:url value="/resources/gradients.css" var="gradientsCss" />
<spring:url value="/resources/styles.css" var="stylesCss" />
<spring:url value="resources/set-background.js" var="setBackgroundJs" />
<spring:url value="/user/list" var="listURL" />
<spring:url value="/cryptocurrency/coinmarketcap" var="cryptocurrencyListURL" />
<spring:url value="/chatBox/chatBox" var="chatBoxUrl" />
<spring:url value="/login" var="LoginAndRegisterUrl" />


<%@ include file="../commonFiles.jsp" %>

<link href="${loginAndRegisterCssUrl}" rel="stylesheet"/>
<script src="${loginAndRegisterJsUrl}"></script>
    

</head>
<body>

<%@ include file="../navigation.jsp" %>


<div class="container">
    	<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-6">
							
								<a href="#" class="${loginFormClassValue}" id="login-form-link">Login</a>
							</div>
							<div class="col-xs-6">
								<a href="#" class="${registerFormClassValue}" id="register-form-link">Register</a>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form:form id="login-form" action="userLogin" modelAttribute="userProfile" method="post" role="form" style="display: block;">
									<div class="form-group">
										<form:input type="text" path="username" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value=""/>
									</div>
									<div class="form-group">
										<form:input type="password" path="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password"/>
									</div>
									<div>
										<label id="login-message" class="text-danger"></label>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
											</div>
										</div>
									</div>
									<!-- 
									<div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<a href="../recover" tabindex="5" class="forgot-password">Forgot Password?</a>
												</div>
											</div>
										</div>
									</div>
									 -->
								</form:form>
								<form:form id="register-form" action="userRegister" method="post" modelAttribute="userProfile" role="form" style="display: none;">
									<div class="form-group">
										<form:input type="text" path="firstName" name="firstName" id="firstName" tabindex="1" class="form-control" placeholder="First Name" value="" />
									</div>
									<div class="form-group">
										<form:input type="text" path="lastName" name="lastName" id="lastName" tabindex="1" class="form-control" placeholder="Last Name" value="" />
									</div>
									
									<div class="form-group">
										<form:input type="text" path="username" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="" />
										
									</div>
									<div class="form-group">
										<form:input type="password" path="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" />
										
									</div>
									<div class="form-group">
										<input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password"/>
										
									</div >
									<div>
										<label id="register-message" class="text-danger"></label>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now">
											</div>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>