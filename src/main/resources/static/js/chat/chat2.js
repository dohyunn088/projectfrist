let peer = null;
let lastPeerId = null;
let conn = null;


function init() {
	peer = new Peer();

	peer.on('open', function(id) {
		if (peer.id === null) {
			peer.id = lastPeerId;
		} else {
			lastPeerId = peer.id;
		}

		$('#receiver-id').html("ID: " + peer.id);
		$('#status').html('Awaiting connection..');
	});

	peer.on('connection', function(con2) {
		if (conn && conn.open) {
			con2.on('open', function() {
				con2.send('Already connected to another client');
				setTimeout(function() {
					con2.close();
				}, 500);
			});
		}

		conn = con2;
		$('#status').html("Connected to: " + conn.peer);
		ready();
	});

	peer.on('disconnected', function() {
		$('#status').html('Connection lost. Please reconnect.');
		peer.id = lastPeerId;
		peer._lastServerId = lastPeerId;
		peer.reconnect();
	});

	peer.on('close', function() {
		conn = null;
		$('#status').html('connection destroyed');
	});

	peer.on('error', function(err) {
		alert(err);
	});
}

function ready() {
	conn.on('data', function(data) {
		addMessage(data, "right");
	});

	conn.on('close', function() {
		$('#status').html('Connection reset. Awaiting connection...');
		conn = null;
	});
}

function addMessage(msg, side) {
	var now = new Date();
	var h = now.getHours();
	var m = now.getMinutes();
	var s = now.getSeconds();

	if (h < 10) h = "0" + h;
	if (m < 10) m = "0" + m;
	if (s < 10) s = "0" + s;


	var msgHtml = [];
	if (side == 'left') {
		msgHtml.push('<li class="chat-right">');
		msgHtml.push(' <div class="chat-avatar">');
		msgHtml.push('  <img src="https://www.bootdey.com/img/Content/avatar/avatar3.png" alt="Retail Admin">');
		msgHtml.push('   <div class="chat-name">Russell</div>');
		msgHtml.push('  </div>');
		msgHtml.push(' <div class="chat-text">' + msg + '</div>');
		msgHtml.push(' <div class="chat-hour">' + h + ':' + m + ':' + s + '<span class="fa fa-check-circle"></span></div>');
		msgHtml.push('</li>');
	} else {
		msgHtml.push('<li class="chat-left">');
		msgHtml.push(' <div class="chat-hour">' + h + ':' + m + ':' + s + '<span class="fa fa-check-circle"></span></div>');
		msgHtml.push('  <div class="chat-text">' + msg + '</div>');
		msgHtml.push('  <div class="chat-avatar">');
		msgHtml.push('   <img src="https://www.bootdey.com/img/Content/avatar/avatar4.png" alt="Retail Admin">');
		msgHtml.push('   <div class="chat-name">Jin</div>');
		msgHtml.push(' </div>');
		msgHtml.push('</li>');

	}

	$('#chat_box').append(msgHtml.join(""));
}

$(document).ready(function() {
	init();

	$('#sendMessageBox').keydown(function(key) {
		if (key.keyCode == 13) {
			if (conn && conn.open) {
				var msg = $('#sendMessageBox').val();
				$('#sendMessageBox').val('');
				conn.send(msg);
				addMessage(msg, "left");
			} else {
				$('#status').html('Connection is closed');
			}
		}
	});
});