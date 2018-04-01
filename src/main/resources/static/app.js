

        $(document).keydown(function(e) {
            switch(e.which) {
                case 37: // left
                moveRef('left');
                break;

                case 38: // up
                moveRef('up');
                break;

                case 39: // right
                moveRef('right');
                break;

                case 40: // down
                moveRef('down');
                break;

                case 32: // spacebar
                onMouseClick();
                break;

                case 27: // esc
                onKeyPress('esc');
                break;

                case 13: // enter
                if ($('#inputURL').is(':focus'))
                   onRedirectURL();
                break;

                case 189: // -
                if ($('#movementSpeed').val() > 10) {
                    $('#movementSpeed').val(parseInt($('#movementSpeed').val(), 10) - 10);
                }
                break;

                case 187: // =
                if ($('#movementSpeed').val() < 100) {
                    $('#movementSpeed').val(parseInt($('#movementSpeed').val(), 10) + 10);
                }
                break;
                default: return; // exit this handler for other keys
        }
        e.preventDefault(); // prevent the default action (scroll / move caret)
    });

var startx = 0;
var starty = 0;
var stompClient = null;
var connected = false;

function moveRef(direction) {
    if (!connected)
        connect();
    stompClient.send("/app/move/" + direction + "/" + $('#movementSpeed').val());
}

function swipe(diffx, diffy) {
    if (!connected)
        connect();
    stompClient.send("/app/swipe/" + dx + "/" + dy);
}

function onMouseClick() {
    if (!connected)
        connect();
    stompClient.send("/app/click");
}

function onKeyPress(keyName) {
    if (!connected)
        connect();
    stompClient.send("/app/press/" + keyName);
}

function onRedirectURL() {
    if (!connected)
        connect();
    stompClient.send("/app/browse", {},  $('#inputURL').val());
}



function connect() {
    var socket = new SockJS('/device-control-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        connected = true;
        console.log('Connected: ' + frame);
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    connected = false;
    console.log("Disconnected");
}




$(function () {
    document.addEventListener('touchstart', function(e){
        var touchobj = e.changedTouches[0]; // reference first touch point (ie: first finger)
        startx = parseInt(touchobj.clientX); // get x position of touch point relative to left edge of browser
        starty = parseInt(touchobj.clientY);
        e.preventDefault()
    }, false)
     
    document.addEventListener('touchend', function(e){
        var touchobj = e.changedTouches[0] // reference first touch point for this event
        var diffx = parseInt(touchobj.clientX) - startx;
        var diffy = parseInt(touchobj.clientY) - starty;
        swipe(diffx, diffy);
    }, false)

    connect();

});