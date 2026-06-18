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
                item.querySelectorAll("input");

            inputs.forEach(input => {

                input.readOnly =
                    isEtc;

            });

        });

    }

    toggleSpec();

    category.addEventListener(
        "change",
        toggleSpec
    );



    function setupCustomInput(
        selectId,
        inputId
    ) {

        const select =
            document.querySelector(
                "#" + selectId
            );

        const input =
            document.querySelector(
                "#" + inputId
            );

        if(!select || !input){
            return;
        }

        const changeInput = () => {

            if(
                select.value ===
                "직접입력"
            ) {

                input.classList.remove(
                    "hidden"
                );

                input.value = "";

            } else {

                input.classList.add(
                    "hidden"
                );

                input.value =
                    select.value;

            }

        };

        changeInput();

        select.addEventListener(
            "change",
            changeInput
        );

    }



    setupCustomInput(
        "brandSelect",
        "brandInput"
    );

    setupCustomInput(
        "cpuSelect",
        "cpu"
    );

    setupCustomInput(
        "storageSelect",
        "storageCapacity"
    );

    setupCustomInput(
        "osSelect",
        "os"
    );

    setupCustomInput(
        "colorSelect",
        "color"
    );

    setupCustomInput(
        "ramSelect",
        "ram"
    );

    setupCustomInput(
        "screenSizeSelect",
        "screenSize"
    );
    const writeForm =
        document.querySelector("#writeForm");

    writeForm.addEventListener(
        "submit",
        (e) => {

            if(!imageFile.files.length){

                alert(
                    "상품 이미지를 선택하세요."
                );

                imageFile.focus();

                e.preventDefault();

                return;
            }

        }
    );
});