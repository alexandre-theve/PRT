<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="page_title" fragment="true"%>
<%@attribute name="title" fragment="true"%>
<%@attribute name="body" fragment="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><jsp:invoke fragment="page_title" /></title>
<link href="/AndrEventServer/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/AndrEventServer/resources/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="/AndrEventServer/resources/css/docs.css" rel="stylesheet">
<script src="/AndrEventServer/resources/js/jquery.js"></script>
<script src="/AndrEventServer/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container well">
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<h1>
					<jsp:invoke fragment="title" />
				</h1>
			</div>
		</div>
		<div class="row-offcanvas row-offcanvas-right">
			<!-- <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar"
				role="navigation">
				<div class="list-group">
					<a href="#" class="list-group-item active">Link</a> <a href="#"
						class="list-group-item">Link</a> <a href="#"
						class="list-group-item">Link</a> <a href="#"
						class="list-group-item">Link</a> <a href="#"
						class="list-group-item">Link</a> <a href="#"
						class="list-group-item">Link</a> <a href="#"
						class="list-group-item">Link</a> <a href="#"
						class="list-group-item">Link</a> <a href="#"
						class="list-group-item">Link</a> <a href="#"
						class="list-group-item">Link</a>
				</div>
			</div> -->
			<div class="col-xs-12 col-sm-12">
				<p>
					<jsp:invoke fragment="body" />
				</p>
			</div>
		</div>
	</div>
</body>
</html>
