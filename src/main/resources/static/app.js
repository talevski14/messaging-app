const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/message-websocket'
});

let fragments = window.location.pathname.split("/");
let id = fragments[fragments.length - 1];
let usernameEl = document.getElementById("username");
let username = usernameEl.value;
usernameEl.parentNode.removeChild(usernameEl);


stompClient.onConnect = (frame) => {
    stompClient.subscribe('/chat/' + id, (message) => {
        console.log(message)
        console.log(message.body)
        showGreeting(message.body);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
}

function sendMessage() {
    stompClient.publish({
        destination: "/app/send-message/" + id,
        body: JSON.stringify({'body': $("#message").val(), 'sender': username})
    });

    var message = document.getElementById("message").value;

    fetch("/chat/" + id, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({'body': $("#message").val(), 'sender': username})
    }).then(response => {
        console.log('POST request successful');
    }).catch(error => {
        console.error('Error making POST request:', error);
    });
}

function showGreeting(messageL) {
    console.log(messageL)
    let message = JSON.parse(messageL)
    console.log(message)
    $("#messages").append(
        "<tr><td>" + message.username + "</td><td>" + message.body + "</td><td>" + message.time + "</td></tr>"
    );
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#send").click(() => sendMessage());
});

window.onload = connect();
window.addEventListener("beforeunload", function (event) {
    disconnect()
});