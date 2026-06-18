document.addEventListener("DOMContentLoaded", () => {

    const imageFile =
        document.querySelector("#imageFile");

    const preview =
        document.querySelector("#preview");

    if(imageFile){

        imageFile.addEventListener("change", (e) => {

            const file = e.target.files[0];

            if(!file){
                return;
            }

            const reader = new FileReader();

            reader.onload = (event) => {

                preview.src =
                    event.target.result;

                preview.classList.remove("hidden");
            };

            reader.readAsDataURL(file);

        });
    }

    const category =
        document.querySelector("#categoryId");

    const specItems =
        document.querySelectorAll(".spec-item");

    function toggleSpec() {

        const isEtc =
            category.value === "4";

        specItems.forEach(item => {

            const inputs =
                item.querySelectorAll("input, select");

            if(isEtc){

                item.classList.add("hidden");

                inputs.forEach(input => {
                    input.value = "";
                });

            } else {

                item.classList.remove("hidden");

            }

        });

    }

    toggleSpec();

    category.addEventListener(
        "change",
        toggleSpec
    );

});