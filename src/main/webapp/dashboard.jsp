<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Computer Database</title>
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	
	<header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>
	
	<c:choose>
	<c:when test="${computerList == null}">
	<c:redirect url="DashboardServlet"/>
	</c:when>
	</c:choose>
	
	
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                ${count} Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer.html">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                <c:forEach items="${computerList}" var="computer" >
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">${computer.name}</a>
                        </td>
                        <td>${computer.introduced}</td>
                        <td>${computer.discontinued}</td>
                        <td>${computer.manufacturer}</td>

                  	</tr>
                    </c:forEach>
                </tbody>
                
            </table>
        </div>
    </section>
    
    
    <c:set var="from1" value="${from - 10}" />
	<c:set var="from2" value="${from + 10}" />
	<c:set var="to1" value="${to - 10}" />
	<c:set var="to2" value="${to + 10}" />


    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                	<c:if test="${from1 >= 0 }">
                    <a href="DashboardServlet?from=${from1}&to=${to1}" aria-label="Previous"></c:if>
                      <span aria-hidden="true">&laquo;</span>
                  	</a>
                  	
              </li>
              <li><a href="DashboardServlet">1</a></li>
              <li><a href="DashboardServlet?from=${from2}&to=${to2}">2</a></li>
              <li><a href="DashboardServlet?from=${from2}&to=${to2}">3</a></li>
              <li><a href="DashboardServlet?from=${from2}&to=${to2}">4</a></li>
              <li><a href="DashboardServlet?from=${from2}&to=${to2}">5</a></li>
              <li>
                <a href="DashboardServlet?from=${from2}&to=${to2}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
		</div>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button>
        </div>

    </footer>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/dashboard.js"></script>
</body>
</html>