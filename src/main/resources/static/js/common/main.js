document.addEventListener("DOMContentLoaded", () => {
    const slides = document.querySelectorAll(".hero-slide");
    const dots = document.querySelectorAll(".dot");
    const prevBtn = document.querySelector(".hero-prev");
    const nextBtn = document.querySelector(".hero-next");

    let current = 0;

    if (slides.length === 0 || dots.length === 0) {
        return;
    }

    function showSlide(index) {
        slides.forEach(slide => {
            slide.classList.remove("active");
        });

        dots.forEach(dot => {
            dot.classList.remove("active");
        });

        slides[index].classList.add("active");
        dots[index].classList.add("active");

        current = index;
    }

    function nextSlide() {
        let next = current + 1;

        if (next >= slides.length) {
            next = 0;
        }

        showSlide(next);
    }

    function prevSlide() {
        let prev = current - 1;

        if (prev < 0) {
            prev = slides.length - 1;
        }

        showSlide(prev);
    }

    nextBtn.addEventListener("click", nextSlide);
    prevBtn.addEventListener("click", prevSlide);

    dots.forEach((dot, index) => {
        dot.addEventListener("click", () => {
            showSlide(index);
        });
    });

    setInterval(nextSlide, 5000);

    showSlide(0);
});