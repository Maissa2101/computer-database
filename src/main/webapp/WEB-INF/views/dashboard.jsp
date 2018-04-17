<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Computer Database</title>
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>

	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard"> Application -
			Computer Database </a>
	</div>
	</header>


	<c:choose>
		<c:when test="${computerList == null}">
			<c:redirect url="dashboard" />
		</c:when>
	</c:choose>

	<section id="main">
	<div class="container">
		<h1 id="homeTitle">${count} Computers found</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">

					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name" /> <input
						type="submit" id="searchsubmit" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="addComputer">Add
					Computer</a> <a class="btn btn-default" id="deleteComputer" href="#"
					onclick="$.fn.toggleEditMode();">Delete</a>
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

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<th>Computer name
						<div class="btn-group">
							<input type="button" class="btn btn-primary btn-xs"
								value="&uarr;"
								onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=${limit}&columnName=computer.name&order=ASC'" />
							<input type="button" class="btn btn-primary btn-xs"
								value="&darr;"
								onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=${limit}&columnName=computer.name&order=DESC'" />
						</div>
					</th>
					<th>Introduced date
						<div class="btn-group">
							<input type="button" class="btn btn-primary btn-xs"
								value="&uarr;"
								onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=${limit}&columnName=introduced&order=ASC'" />
							<input type="button" class="btn btn-primary btn-xs"
								value="&darr;"
								onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=${limit}&columnName=introduced&order=DESC'" />
						</div>
					</th>
					<th>Discontinued date
						<div class="btn-group">
							<input type="button" class="btn btn-primary btn-xs"
								value="&uarr;"
								onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=${limit}&columnName=discontinued&order=ASC'" />
							<input type="button" class="btn btn-primary btn-xs"
								value="&darr;"
								onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=${limit}&columnName=discontinued&order=DESC'" />
						</div>
					</th>
					<th>Company
						<div class="btn-group">
							<input type="button" class="btn btn-primary btn-xs"
								value="&uarr;"
								onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=${limit}&columnName=company.name&order=ASC'" />
							<input type="button" class="btn btn-primary btn-xs"
								value="&darr;"
								onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=${limit}&columnName=company.name&order=DESC'" />
						</div>
					</th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach items="${computerList}" var="computer">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="${computer.id}"></td>
						<td><a id="edit" href="editComputer?id=${computer.id}">${computer.name}</a>
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

	<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<ul class="pagination">

			<c:if test="${pageNumber > 1}">
				<li><a
					href="?search=${search}&pageNumber=${pageNumber-1}&limit=${limit}&columnName=${columnName}&order=${order}'"
					aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
			<c:forEach var="i"
				begin="${((pageNumber - 3) > 0) ? (pageNumber - 3) : 1}"
				end="${((pageNumber + 3) < (count/limit)) ? (pageNumber + 3) : (count/limit +1)}">
				<li><a
					href="?search=${search}&pageNumber=${i}&limit=${limit}&columnName=${columnName}&order=${order}">${i}</a></li>
			</c:forEach>
			<c:if test="${pageNumber < count}">
				<li><a
					href="?search=${search}&pageNumber=${pageNumber+1}&limit=${limit}&columnName=${columnName}&order=${order}"
					aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
			</c:if>

		</ul>

		<div class="btn-group btn-group-sm pull-right" role="group">
			<button type="button" class="btn btn-default"
				onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=10&columnName=${columnName}&order=${order}'">10</button>
			<button type="button" class="btn btn-default"
				onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=50&columnName=${columnName}&order=${order}'">50</button>
			<button type="button" class="btn btn-default"
				onclick="location.href='?search=${search}&pageNumber=${pageNumber}&limit=100&columnName=${columnName}&order=${order}'">100</button>
		</div>
	</div>
	</footer>

	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>
</body>
</html>