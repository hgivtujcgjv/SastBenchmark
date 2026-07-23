window.addEventListener('message', (event) => {
    document.getElementById('content').innerHTML = event.data;
});
