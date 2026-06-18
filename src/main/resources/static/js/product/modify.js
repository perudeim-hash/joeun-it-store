document.addEventListener("DOMContentLoaded", () => {

    const imageFile =
        document.querySelector("#imageFile");

    const preview =
        document.querySelector("#preview");

    console.log(imageFile);
    console.log(preview);

    imageFile.addEventListener("change", (e) => {

        const file = e.target.files[0];

        if (!file) {
            return;
        }

        const reader = new FileReader();

        reader.onload = (event) => {

            preview.src =
                event.target.result;

        };

        reader.readAsDataURL(file);

    });

});