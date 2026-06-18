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

    const category =
        document.querySelector("[name='categoryId']");

    const cpu =
        document.querySelector("[name='cpu']");

    const ram =
        document.querySelector("[name='ram']");

    const storage =
        document.querySelector("[name='storageCapacity']");

    const screenSize =
        document.querySelector("[name='screenSize']");

    const os =
        document.querySelector("[name='os']");

    function toggleSpec() {

        const isEtc =
            category.value === "4";

        cpu.readOnly = isEtc;

        ram.readOnly = isEtc;

        storage.readOnly = isEtc;

        screenSize.readOnly = isEtc;

        os.readOnly = isEtc;

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
        "cpuSelect",
        "cpu"
    );

    setupCustomInput(
        "ramSelect",
        "ram"
    );
    setupCustomInput(
        "brandSelect",
        "brandInput"
    );

    setupCustomInput(
        "storageSelect",
        "storageCapacity"
    );

    setupCustomInput(
        "screenSizeSelect",
        "screenSize"
    );

    setupCustomInput(
        "osSelect",
        "os"
    );

    setupCustomInput(
        "colorSelect",
        "color"
    );
});