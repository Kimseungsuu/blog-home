let targetId;

$(document).ready(function () {
    if ($.cookie('token')) {
        $.ajaxSetup({
            headers: {
                'Authorization': $.cookie('token')
            }
        })
    } else {
        window.location.href = '/user/loginView';
    }


    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })

    showArticles();
})

function showArticles() {
    // 1. GET /api/products 요청
    // 2. #product-container(관심상품 목록), #search-result-box(검색결과 목록) 비우기
    // 3. for 문 마다 addProductItem 함수 실행시키고 HTML 만들어서 #product-container 에 붙이기
    $.ajax({
        type: 'GET',
        url: '/api/articles',
        success: function (response) {
            $('#product-container').empty();
            $('#search-result-box').empty();
            for (let i = 0; i < response.length; i++) {
                let product = response[i];
                let tempHtml = addProductItem(product);
                $('#product-container').append(tempHtml);
            }
        }
    })
}

function addProductItem(product) {
    return `<div class="product-card" onclick="window.location.href='/api/articles/${product.id}'">
                <div class="card-header">
                </div>
                <div class="card-body">
                    <div class="title">
                        ${product.title}
                    </div>
                    <div class="lprice">
                        <span>${product.writer}</span>
                    </div>
                </div>
            </div>`;
}
