const cfg = Object.create(null);
cfg.url = document.getElementById('data').dataset.url;
if (typeof cfg.url === 'string' && cfg.url.startsWith('/')) {
    location.assign(cfg.url);
}
