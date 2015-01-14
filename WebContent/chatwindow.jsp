<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page import="com.cardan.bo.User" %>
    <%@page import="com.cardan.bo.User" %> 
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

	<script language="javascript" type="text/javascript">
 
		var wsUri = "ws://81.170.194.62:8091/chat";
		var output;
		function init() { output = document.getElementById("output"); testWebSocket();
		}
		function testWebSocket()
		{
		        websocket = new WebSocket(wsUri);
		        websocket.onopen = function(evt) { onOpen(evt) };
		        websocket.onclose = function(evt) { onClose(evt) };
		        websocket.onmessage = function(evt) { onMessage(evt) };
		        websocket.onerror = function(evt) { onError(evt) };
		}
		function onOpen(evt)
		{
			writeToScreen("CONNECTED");
			doSend("has connected",document.getElementById('name').value);
			$.post("/GCM-App-Server/AuthServlet",
	          		  { checkReq: "getAllMessagesWebApp",fromEmail: $('#name').val(), toEmail: $('#toEmail').val() },
	          		  function(data){
	          			  var obj = jQuery.parseJSON(data);
						$.each(obj,function(){
							if(this.fromEmail == $('#name').val()){
								writeToScreen($('#name').val()+": "+this.message);
							}else{
								writeToScreen($('#toEmail').val()+": "+this.message);
							}
							console.log("Message: "+this.message);
						});
	          		  }
        		);
		}
		function onClose(evt)
		{
		        writeToScreen("DISCONNECTED");
		}
		function onMessage(evt)
		{
			var message = evt.data;
			if(message.indexOf(document.getElementById('name').value) > -1){
				 writeToScreen('<span style="color: green;"> ' + evt.data+'</span>');
			}else{
				 writeToScreen('<span style="color: blue;"> ' + evt.data+'</span>');
			}
            //setTimeout(function() {   websocket.close();}, 20000);
		}
		function onError(evt)
		{
		        writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
		}
		function doSend(message, from, to)
		{
		        writeToScreen('<span style="color: green;">' + from + '> ' + message + '</span>');
		        var strucMessage = message + '<:>' + from + '<:>' + to;
		        console.log(strucMessage);
		        websocket.send(strucMessage);
		        if ( $('#value').val().length > 0 ){
		        	//Send to db
		        	$.post("/GCM-App-Server/AuthServlet",
			          		  { checkReq: "sendMessageWebApp",message: $('#value').val(), fromEmail: $('#name').val(), toEmail: $('#toEmail').val() },
			          		  function(data){

			          		  }
	          		);
		        	//Send to android
		        	$.post("/GCM-App-Server/GCMNotification",
			          		  { fromWho: "computer", messageType: "pm",message: $('#value').val(), fromEmail: $('#name').val(), toEmail: $('#toEmail').val() },
			          		  function(data){

			          		  }
	          		);
		        }    
		}
		function writeToScreen(message)
		{
		        var pre = document.createElement("p");
		        pre.style.wordWrap = "break-word";
		        pre.innerHTML = message; output.appendChild(pre);
		}
		window.addEventListener("load", init, false);
	</script>


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
</head>

<body>

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
                        <a href="#">About</a>
                    </li>
                    <li>
                        <a href="#">Services</a>
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
            
            <div class="col-md-9">

                <div class="row carousel-holder">

                    <div class="col-md-12">
                    <h1>Chat window</h1>
                    </div>
			        
                </div>

       		    
				<div class="col-md-9">
				<form>
					
				</form>
				<h2>Vert.x Chat</h2>
       
		        <div id="output"></div>
		        <div>
		            <input type="text" maxlength="256" name="value" id='value' />
		            <input type="hidden" maxlength="" name="name" id="name" value="<%=session.getAttribute("loggedIn") %>"/>
		            <input type="hidden" maxlength="" name="toEmail" id="toEmail" value="<%=request.getParameter("toEmail") %>"/>
		            <input type="button" value="Send" onclick="doSend(document.getElementById('value').value,document.getElementById('name').value,document.getElementById('toEmail').value)" />
		        </div>
		        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
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