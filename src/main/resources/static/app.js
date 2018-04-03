

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
    onMouseClick("left");
    break;

    case 27: // esc
    onKeyPress('esc');
    break;

    case 13: // enter
    if ($('#inputURL').is(':focus'))
    onRedirectURL();
    if ($('#inputText').is(':focus'))
    onEnterText();
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

var startX = 0;
var startY = 0;
var startTouchx = 0;
var startTouchY = 0;
var stompClient = null;
var connected = false;
var isMultiTouch = false;

function moveRef(direction) {
  stompClient.send("/app/move/" + direction + "/" + $('#movementSpeed').val());
}

function swipe(diffx, diffy) {
  stompClient.send("/app/swipe/" + diffx + "/" + diffy);
}

function onMouseClick(keyPressed) {
  stompClient.send("/app/click/" + keyPressed);
}

function onKeyPress(keyName) {
  stompClient.send("/app/press/" + keyName);
}

function onRedirectURL() {
  stompClient.send("/app/browse", {},  $('#inputURL').val());
}

function onEnterText() {
  stompClient.send("/app/type", {},  $('#inputText').val());
}

function scroll(direction) {
  stompClient.send("/app/scroll/" + direction);
}


function connect() {
  var socket = new SockJS('/device-control-websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    connected = true;
    console.log('Connected: ' + frame);
  }, function () {
    setTimeout(connect(), 1000);
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
    isMultiTouch = (e.touches.length > 1);
    startTouchX = startX = parseInt(touchobj.clientX); // get x position of touch point relative to left edge of browser
    startTouchY = startY = parseInt(touchobj.clientY);
    //e.preventDefault();
  }, false)

  document.addEventListener('touchmove', function(e){
    var touchobj = e.changedTouches[0]; // reference first touch point for this event
    var diffx = parseInt(touchobj.clientX) - startX;
    var diffy = parseInt(touchobj.clientY) - startY;
    var diff = 10;
    if (isMultiTouch) {
      if (diffy < -4 * diff ) {
        startX = parseInt(touchobj.clientX);
        startY = parseInt(touchobj.clientY);
        scroll("up");
      }
      else if (diffy > 4 * diff) {
        startX = parseInt(touchobj.clientX);
        startY = parseInt(touchobj.clientY);
        scroll("down");
      }
    } else if (Math.abs(parseInt(diffx)) > diff || Math.abs(parseInt(diffy)) > diff) {
      startX = parseInt(touchobj.clientX);
      startY = parseInt(touchobj.clientY);
      swipe(diffx, diffy);
    }
    e.preventDefault();
  }, false)

  document.addEventListener('touchend', function(e){
    var touchobj = e.changedTouches[0]; // reference first touch point for this event
    var diffx = parseInt(touchobj.clientX) - startTouchX;
    var diffy = parseInt(touchobj.clientY) - startTouchY;
    var diff = 10;
    if (Math.abs(parseInt(diffx)) <= diff && Math.abs(parseInt(diffy)) <= diff) {
      startTouchX = startX = 0;
      startTouchY = startY = 0;
      if (isMultiTouch) {
        onMouseClick("right");
      } else {
        onMouseClick("left");
      }
    }
    isMultiTouch = false;
    //e.preventDefault();
  }, false)


  $(window).on('beforeunload', function(){
    console.log("beforeUnload event!");
    disconnect();
  });

  connect();

  stompClient.ws.onclose = function() {
    console.log("Websocket connection closed and handled from our app.");
    connect();
  };

});
