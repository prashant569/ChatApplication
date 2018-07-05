<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "spring" uri = "http://www.springframework.org/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List</title>

<spring:url value="../resources/DataTables/datatables.css" var="datatablesCssUrl"/>
<spring:url value="../resources/DataTables/datatables.js" var="datatablesJsUrl"/>
<spring:url value="../resources/jquery-ui.js" var="jqueryUiJsUrl"/>

 <%@ include file="../commonFiles.jsp" %>

<link href="${datatablesCssUrl}" rel="stylesheet">
<script type="text/javascript" src="${jqueryUiJsUrl}"></script>
<script type="text/javascript" src="${datatablesJsUrl}"></script>




</head>
<body>

 <%@ include file="../navigation.jsp" %>

<spring:url value="/user/form" var="addURL"></spring:url>
<a href="${addURL}">Add user</a>


<h1>
<table border="1">
	<thead>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Username</th>
			<th colspan="2"> Action</th>
		</tr>		
	</thead>
	<tbody>
		<c:forEach items="${listUser}" var="currentUser">
		<tr>			
			<td>
				${currentUser.firstName}
			</td>
			<td>
				${currentUser.lastName }
			</td>
			<td>
				${currentUser.username }
			</td>
			<td>
				<spring:url value="/user/update/${currentUser.id}" var="updateURL"></spring:url>
				<a href="${updateURL}">Update</a>
			</td>
			<td>
				<spring:url value="/user/delete/${currentUser.id}" var="deleteURL"></spring:url>
				<a href="${deleteURL}">Delete</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
	
</table>

</h1>




</body>
</html>