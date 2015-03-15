<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
	<title>Grails Chat</title>
	<script>
	var context = '${request.contextPath}';
	</script>

    <link href="css/chat.css" media="all" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="js/chat.js?v=2"></script>

</head>
<body>
	<div id="container">
		<div id="container-inner">
			<div id="header">
				<h1>Grails Chat</h1>
			</div>
			<div id="main">
				<div id="display">
				</div>
				<div id="form">
					<div id="system-message">Please input your name:</div>
					<div id="login-form">
						<input id="login-name" type="text" />
						<br />
						<input id="login-button" type="button" value="Login" />
					</div>
					<div id="message-form" style="display: none;">
						<div>
							<textarea id="message" name="message" rows="2" cols="40"></textarea>
							<br />
							<input id="post-button" type="button" value="Post Message" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<iframe id="comet-frame" style="display: none;"></iframe>

</body>
</html>
