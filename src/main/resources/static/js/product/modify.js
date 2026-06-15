function previewImage(input){

    const file = input.files[0];

    if(!file){
        return;
    }

    const reader = new FileReader();

    reader.onload = function(e){

        const preview =
            document.querySelector("#preview");

        preview.src =
            e.target.result;

        preview.classList.remove("hidden");

    };

    reader.readAsDataURL(file);

}