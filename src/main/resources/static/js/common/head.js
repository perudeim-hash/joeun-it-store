document.addEventListener("DOMContentLoaded", function () {
    const allCategoryBtn = document.getElementById("allCategoryBtn");
    const categoryDropdown = document.getElementById("categoryDropdown");

    if (!allCategoryBtn || !categoryDropdown) {
        return;
    }

    allCategoryBtn.addEventListener("click", function (event) {
        event.stopPropagation();
        categoryDropdown.classList.toggle("active");
    });

    document.addEventListener("click", function () {
        categoryDropdown.classList.remove("active");
    });

    categoryDropdown.addEventListener("click", function (event) {
        event.stopPropagation();
    });
});