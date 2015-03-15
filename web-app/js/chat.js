var count = 0;

var app = {
	url: context + '/chat',

	initialize: function() {

        $("#login-button").click(function(){
            app.login();
        });

        $("#post-button").click(function(){
            app.post();
        });

		$('#login-name').focus();
		app.listen();
	},

	listen: function() {
        var mySrc = app.url + '/listen?c=' + count;
		$('#comet-frame').prop("src",mySrc);
		count++;
	},

	login: function() {

        var name = $('#login-name').prop("value");

        if (!name.length) {
			$('#system-message').css("color", "red");
			$('#login-name').focus();
			return;
		}

        $('#system-message').css("color", "#2d2b3d");
        $('#system-message').html(name + ':');

		$('#login-button').prop("disabled", true);
        $('#login-button').css("display","none");

        $('#message-form').css("display","inline");

		var query = 'name=' + encodeURI(name);


        $.ajax({
            url: app.url + '/login',
            data: query,
            type: "POST"
        }).done(function() {
            $('#message').focus();
        });

	},

	post: function() {
        var name = $('#login-name').prop("value");
		var message = $('#message').prop("value");

		if (!message.length) {
			return;
		}

        $('#message').prop("disabled",true);
		$('#post-button').prop("disabled",true);

		var query = 'name=' + encodeURI(name) + '&message=' + encodeURI(message);

        $.ajax({
            url: app.url + '/post',
            data: query,
            type: "POST"
        }).done(function() {
            $('#message').prop("disabled",false);
            $('#post-button').prop("disabled",false);
            $('#message').focus();
          	$('#message').prop("value", '');
        });
	},

	update: function(data) {
		var p = document.createElement('p');
		p.innerHTML = data.name + ':<br/>' + data.message;

		$('#display').append(p);
	}
};

$(document).ready(function(){
    app.initialize();

});
