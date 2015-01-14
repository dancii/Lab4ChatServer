<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page import="com.cardan.bo.User" %>
        <%if(session.getAttribute("loggedIn") == null || session.getAttribute("loggedIn") == ""){
    		response.sendRedirect("ChatIndex.jsp");
    		} %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ChatUp - Web applications</title>
    <!-- Include the API client and Google+ client. -->
    <script src = "https://plus.google.com/js/client:plusone.js"></script>
    
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/shop-homepage.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <script type="text/javascript">
        // Wait for the page to load first
        
        function goToFriends(){
        	document.forms["getAllConvos"].submit();
        }
        
        function goToRooms(){
        	document.forms["getAllRooms"].submit();
        }
        
        function logOut(){
        	window.location.href = "/GCM-App-Server/ChatIndex.jsp";
        }
    </script>

</head>

<body>

		<form id="getAllConvos" action="AuthServlet" method="POST">
	      	<input type="hidden" name="checkReq" value="getAllConvosWebApp">
      	</form>
      	<form id="getAllRooms" action="AuthServlet" method="POST">
	      	<input type="hidden" name="checkReq" value="getAllRoomsWebApp">
      	</form>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">ChatUp</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                	<li>
                        <a href="javascript:goToFriends()">Friends</a>
                    </li>
                    <li>
                        <a href="javascript:goToRooms()">Rooms</a>
                    </li>
                    <li>
                        <a href="terminal.jsp">Terminal</a>
                    </li>
                    <li>
                        <a href="#">About</a>
                    </li>
                    <li>
                        <a href="#">Services</a>
                    </li>
                     <li>
                        <a href="javascript:logOut();">Log out</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <div class="col-md-3">
                <p class="lead">All convos</p>
                <div class="list-group">
						
					<%
					//Gets all convos from db
					if(request.getAttribute("allConvos")==null){%>
						<h5>No conversations ongoing</h5>
					<%}else{ %>
						<div class="list-group-item">
						<% 
						ArrayList<User> allConvos = (ArrayList<User>) request.getAttribute("allConvos");
						if(!allConvos.isEmpty()){
							for(User userItem : allConvos){%>
							<a href="chatwindow.jsp?toEmail=<%=userItem.getEmail()%>"><%=userItem.getEmail() %></a>
						<% }
						}else{%>
							<h5>No conversations ongoing</h5>
						<%}
						} %>
						</div>
					
                </div>
            </div>
            
            <div class="col-md-9">

                <div class="row carousel-holder">

                    <div class="col-md-12">
                    <h4>Add friend</h4>
                    <form action="AuthServlet" method="POST">
                    	Name: <input type="text" id="email" name="toUsername">
                    	<input type="hidden" name="checkReq" value="registerConvoWebApp">
                    	<input type="submit" value="Send" />
                    </form>
                    
                    </div>
                <p class="lead">Pending friends</p>
                <div class="list-group">
						
					<%
					//Gets all convos from db
					if(request.getAttribute("pendingFriendReq")==null){%>
						<h5>No pending friend request</h5>
					<%}else{ %>
						<div class="list-group-item">
						<% 
						ArrayList<String> pendingFriendReq = (ArrayList<String>) request.getAttribute("pendingFriendReq");
						if(!pendingFriendReq.isEmpty()){
							for(String friendName : pendingFriendReq){ %>
							<h5 style="float: left"><%=friendName %></h5>
							<form action="AuthServlet" method="POST">
								<input type="hidden" name="checkReq" value="declinePendingFriendWebApp">
								<input type="hidden" name="fromEmail" value="<%=friendName %>">
								<input type="submit" value="Decline" style="float: right">
							</form>
							
							<form id="acceptForm" action="AuthServlet" method="POST">
	      						<input type="hidden" name="checkReq" value="acceptPendingFriendWebApp">
	      						<input type="hidden" name="fromEmail" value="<%=friendName %>">
	      						<input type="submit" value="Accept" style="float: right">
      						</form>
							<br><br>
						<% }
						}else{%>
							<h5>No pending friend request</h5>
						<%}%>
						
						
						</div>
					<%} %>
                </div>

                </div>

       		    
				<div class="col-md-9">
			</div>
            </div>

        </div>

    </div>
    <!-- /.container -->

    <div class="container">

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; ChatUp 2015</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->


    <!-- jQuery Version 1.11.0 -->
    <script src="js/jquery-1.11.0.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>