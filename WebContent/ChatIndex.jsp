<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Chat Webapp - Welcome!</title>
    <!-- Include the API client and Google+ client. -->
    <script src = "https://plus.google.com/js/client:plusone.js"></script>
</head>
	<body>
	    <script type="text/javascript">
        // Wait for the page to load first
        window.onload = function() {
        	console.log("Du loggar ut nu");
        	gapi.auth.signOut();
        }
    </script>
	<!-- Container with the Sign-In button. -->
	    <div id="gConnect" class="button">
	      <button class="g-signin"
	          data-scope="email"
	          data-clientid="520614404467-qemmufqphqijcsm3e0770otqkdqi0ihr.apps.googleusercontent.com"
	          data-callback="onSignInCallback"
	          data-theme="dark"
	          data-cookiepolicy="single_host_origin">
	      </button>
	      <!-- Textarea for outputting data -->
	      <div id="response">
	      <form id="registerAccWebApp" action="AuthServlet" method="POST">
	      	<input type="hidden" id="responseContainer" name="username">
	      	<input type="hidden" name="checkReq" value="registerAccWebApp">
	      </form>
	      	
	      </div>
	    </div>
	</body>
	
	
	<!-- G+ login js -->
  <script type="text/javascript">
  /**
   * Handler for the signin callback triggered after the user selects an account.
   */
   var first_run = true;
   var helper = (function () {
	   return{
		   onSiginInCallback : function(resp){ 
			   gapi.client.load('plus', 'v1', apiClientLoaded);
			    if (resp['status']['signed_in']) {
			        // Update the app to reflect a signed in user
			        // Hide the sign-in button now that the user is authorized, for example:
			        document.getElementById('signinButton').setAttribute('style', 'display: none');
			        
			      } else {
			        // Update the app to reflect a signed out user
			        // Possible error values:
			        //   "user_signed_out" - User is signed-out
			        //   "access_denied" - User denied access to your app
			        //   "immediate_failed" - Could not automatically log in the user
			        console.log('Sign-in state: ' + resp['error']);
			      }
		   }
	   };
   })();
  
  
  function onSignInCallback(resp) {
   if(!first_run){
	   helper.onSiginInCallback(resp);
   }
   first_run=false;
  }

  /**
   * Sets up an API call after the Google API client loads.
   */
  function apiClientLoaded() {
    gapi.client.plus.people.get({userId: 'me'}).execute(handleEmailResponse);
  }

  /**
   * Response callback for when the API client receives a response.
   *
   * @param resp The API response object with the user email and profile information.
   */
  function handleEmailResponse(resp) {
    var primaryEmail;
    for (var i=0; i < resp.emails.length; i++) {
      if (resp.emails[i].type === 'account') primaryEmail = resp.emails[i].value;
    }
    document.getElementById('responseContainer').value = primaryEmail;
    document.forms["registerAccWebApp"].submit();
  }

  </script>
</html>