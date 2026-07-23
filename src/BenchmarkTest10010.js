const clean = DOMPurify.sanitize(location.hash.slice(1));
document.getElementById('out').innerHTML = clean;
