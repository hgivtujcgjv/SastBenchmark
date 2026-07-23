const cfg = document.getElementById('appConfig');
const script = document.createElement('script');
script.src = cfg.dataset.src || window.CDN;
document.head.appendChild(script);
