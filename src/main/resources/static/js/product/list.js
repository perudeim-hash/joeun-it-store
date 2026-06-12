let currentCategory = 0;

async function loadCategory(categoryId, page = 1) {

    currentCategory = categoryId;

    const response =
        await fetch(`/product/category/${categoryId}?page=${page}`);

    const data =
        await response.json();

    const productList =
        data.productList;

    let html = "";

    productList.forEach(product => {

        html += `
            <div class="card bg-base-100 shadow-md hover:shadow-xl transition duration-300">

                <a href="/product/view?productId=${product.productId}">

                    <figure class="h-[250px] overflow-hidden">

                        <img
                            src="/upload/${product.imageName}"
                            alt="${product.productName}"
                            class="w-full h-full object-cover">

                    </figure>

                </a>

                <div class="card-body p-4">

                    <a href="/product/view?productId=${product.productId}"
                       class="font-bold text-lg hover:text-primary">

                        ${product.productName}

                    </a>

                    <div class="text-sm text-gray-500">

                        브랜드 : ${product.brand}

                    </div>

                    <div class="text-2xl font-bold text-error">

                        ${product.price.toLocaleString()} 원

                    </div>

                    <div class="text-sm text-gray-500">

                        판매량 :
                        ${product.salesCount ?? 0}
                        개 판매

                    </div>

                    <div>

                        <span class="badge badge-success">

                            ${product.status}

                        </span>

                    </div>

                </div>

            </div>
        `;
    });

    document.querySelector("#productList").innerHTML = html;

    drawPaging(
        data.currentPage,
        data.totalPage
    );
}

function drawPaging(currentPage,totalPage){

    let pagingHtml = "";

    if(currentPage > 1){

        pagingHtml += `
            <button
                class="btn btn-sm btn-outline"
                onclick="loadCategory(${currentCategory},${currentPage - 1})">

                이전

            </button>
        `;
    }

    let startPage = Math.max(1,currentPage - 2);
    let endPage = Math.min(totalPage,currentPage + 2);

    for(let i=startPage;i<=endPage;i++){

        pagingHtml += `
            <button
                class="btn btn-sm ${i===currentPage ? 'btn-primary' : 'btn-outline'}"
                onclick="loadCategory(${currentCategory},${i})">

                ${i}

            </button>
        `;
    }

    if(currentPage < totalPage){

        pagingHtml += `
            <button
                class="btn btn-sm btn-outline"
                onclick="loadCategory(${currentCategory},${currentPage + 1})">

                다음

            </button>
        `;
    }

    document.querySelector("#paging").innerHTML =
        pagingHtml;
}

window.addEventListener("load",()=>{

    loadCategory(0,1);

});