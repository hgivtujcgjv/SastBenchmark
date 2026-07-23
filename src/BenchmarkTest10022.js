window.addEventListener('message', (event) => {
    if (event.origin !== 'https://trusted.example.com') return;
    document.getElementById('content').textContent = event.data;
});
