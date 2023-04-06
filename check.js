
const fileInput = document.getElementById("file");
const fileDiv = document.getElementById("fileName")

function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);  // 인코딩
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}

async function sendFile() {
    const img = document.createElement("img");
    let file = fileInput.files[0];
    img.classList.add('obj');
    img.file = file;
    img.src = await getBase64(file);
    fileDiv.appendChild(img);
    let json = {
        "img": img.src
    }
    console.log(json)
    fileInput.value = null;
}