
fetch("./icon.jpg")
.then(r=>r.arrayBuffer())
.then(b=> {

    const base64ImageData = btoa(String.fromCharCode(...new Uint8Array(b)));

    fetch("http://localhost:8080/links/1", {
        method: "PUT",
        body: JSON.stringify({
            title: "Google",
            description: "This is also a new description",
            url: "https://google.com/",
            imageData: base64ImageData,
            isActive: true 
        }),
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(r=>r.json())
    .then(j=>{
        console.log(j);
    });
});