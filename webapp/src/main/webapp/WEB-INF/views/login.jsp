<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title" /></title>
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>

	 <form method="get" action="login">
            <fieldset>
                <legend>Login</legend>

                <label for="pseudo">Pseudo :  <span class="requis">*</span></label>
                <input type="text" id="pseudo" name="pseudo" value="" />
                <br/>

                <label for="password">Password : <span class="requis">*</span></label>
                <input type="password" id="password" name="password" value="" />
                <br/>

                <input type="submit" value="Inscription" class="sansLabel" />
                <br />
            </fieldset>
        </form>


	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>
</body>
</html>