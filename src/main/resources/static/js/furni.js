// Code for opening and closing lightbox
function openLightbox(element) {
    const imageSrc = element.src;
    const lightbox = document.querySelector('.lightbox');
    const lightboxImage = document.getElementById('lightboxImage');

    lightboxImage.src = imageSrc;
    lightbox.style.display = 'block';
}

function closeLightbox() {
    const lightbox = document.querySelector('.lightbox');
    lightbox.style.display = 'none';
}

// Code for showing and hiding arrows on hover
var imgContainer = document.querySelector('.relative');
imgContainer.addEventListener('mouseenter', function() {
    var arrows = imgContainer.querySelectorAll('.bi');
    arrows.forEach(function(arrow) {
        arrow.style.display = 'block';
    });
});
imgContainer.addEventListener('mouseleave', function() {
    var arrows = imgContainer.querySelectorAll('.bi');
    arrows.forEach(function(arrow) {
        arrow.style.display = 'none';
    });
});

// Code for image navigation
var arr_hinh = [
    "/images/AcerNB.jpg",
    "/images/AcerDS.jpg",
    "/images/AsusDS.jpg",
    "/images/laptopMSI.jpg"
];

var index = 0;

function prevImage() {
    index--;
    if (index < 0) index = arr_hinh.length - 1;
    document.getElementById("hinh").src = arr_hinh[index];
}

function nextImage() {
    index++;
    if (index == arr_hinh.length) index = 0;
    document.getElementById("hinh").src = arr_hinh[index];
}