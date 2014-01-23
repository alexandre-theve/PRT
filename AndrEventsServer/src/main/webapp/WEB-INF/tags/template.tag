<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="page_title" fragment="true" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="body" fragment="true" %>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><jsp:invoke fragment="page_title"/></title>
        <link href="/AndrEventServer/resources/css/bootstrap.min.css" rel="stylesheet">
        <link href="/AndrEventServer/resources/css/bootstrap-responsive.min.css" rel="stylesheet">
        <link href="/AndrEventServer/resources/css/docs.css" rel="stylesheet">
        <script src="/AndrEventServer/resources/js/jquery.js"></script>
        <script src="/AndrEventServer/resources/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span4 offset5">
                    <h1>
                        <jsp:invoke fragment="title"/>
                    </h1>
                </div>
            </div>
            <div class="row-fluid">
                    <div class="span2 bs-docs-sidebar">
                        <form>
                            <ul class="nav nav-list bs-docs-sidenav" style="margin: 0px;">
                                <li><a>Lien 1</a>
                                </li>
                                <li><a>Lien 2</a>
                                </li>
                                <li><a>Lien 3</a>
                                </li>
                            </ul>
                        </form>
                    </div>
                
                <div class="span9 offset1 well">
                    <p>
                        <jsp:invoke fragment="body"/>
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>
