window.addEventListener('message', (event) => {
    const cmd = JSON.parse(event.data);
    eval(cmd.action);
});
