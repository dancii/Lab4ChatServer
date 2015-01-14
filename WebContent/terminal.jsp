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
        
        
        function writeToScreen(message)
		{
		        var pre = document.createElement("p");
		        pre.style.wordWrap = "break-word";
		        pre.innerHTML = message; output.appendChild(pre);
		}
        
        function sendCommand(command){
        	
        	
        	if(command.charAt(0) == "/"){
        		var commandArr = command.split(" ");
        		if(commandArr.length > 1){
        			if(commandArr[0] == "/join"){
        				$.post("/GCM-App-Server/AuthServlet",
    		          		  { checkReq: "joinRoomWebApp", roomName: commandArr[1] },
    		          		  function(data){
    		          			if(data == "Joining"){
    		          				writeToScreen("Room found, you can now check in the 'Rooms' tab");
    		          			}else{
    		          				writeToScreen("Room with that name does not exist, if you wish to create type /create <room name>");
    		          			}
    		          		  }
        	      		);
                	}else if(commandArr[0] == "/create"){
                		$.post("/GCM-App-Server/AuthServlet",
      		          		  { checkReq: "createRoomWebApp", roomName: commandArr[1] },
      		          		  function(data){
      		          			if(data == "roomCreated"){
      		          				writeToScreen("Room created, you can now check in the 'Rooms' tab");
      		          			}else{
      		          				writeToScreen("Error trying to create the room, try again later");
      		          			}
      		          		  }
          	      		);
                	}else{
                		writeToScreen("Invalid command type '/help' to get all commands");
                	}
            	}else if(commandArr[0] == "/help"){
            		writeToScreen("Type /join <room name> to join a room, /create <room name> to create a room");
            	}else{
            		writeToScreen("Invalid command type '/help' to get all commands");
            	}
        		
        	}else{
        		writeToScreen("Invalid command type '/help' to get all commands");
        	}
        	
        	
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
                
                
            </div>
            
            <div class="col-md-9">

                <div class="row carousel-holder">

                    <div class="col-md-12">
                    <style>
				        * { font-family:tahoma; font-size:12px; padding:0px; margin:0px; }
				        p { line-height:18px; }
				        div { width:500px; margin-left:auto; margin-right:auto;}
				        #output { padding:5px; background:#ddd; border-radius:5px; overflow-y: scroll;
				                   border:1px solid #CCC; margin-top:10px; height: 160px; }
				        #input { border-radius:2px; border:1px solid #ccc;
				                 margin-top:10px; padding:5px; width:400px;  }
				        #status { width:88px; display:block; float:left; margin-top:15px; }
			        </style>
                    
                    <div id="output"></div>
				        <div>
				            <input type="text" maxlength="256" name="value" id='value' />
				            <input type="hidden" maxlength="" name="name" id="name" value="<%=session.getAttribute("loggedIn") %>"/>
				            <input type="hidden" maxlength="" name="toEmail" id="toEmail" value="<%=request.getParameter("toEmail") %>"/>
				            <input type="button" value="Enter" onclick="sendCommand(document.getElementById('value').value)" />
				        </div>
				        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
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