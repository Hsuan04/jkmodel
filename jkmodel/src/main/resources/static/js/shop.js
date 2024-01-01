//顯示所有商品
$(function () {
    $.ajax({
        // url: `${window.location.path}/products`,
        url: "http://localhost:8080/products",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);

            var row = $("#row");
            row.empty();

            // 遍歷商品資料
            $.each(data, function (index, product) {

                // 計算商品時間
                var onTime = new Date(product.onTime);
                var currentDate = new Date();
                var daysDifference = Math.abs((onTime - currentDate) / (1000 * 60 * 60 * 24));

                // 判斷是否為最新商品或特價商品
                var isNewProduct = daysDifference <= 14;
                var isSaleProduct = daysDifference >= 90;

                // 動態產生商品卡片的HTML
                var productCard = `
    <div class="col-lg-4 col-md-6 col-sm-6">
        <div class="product__item">
            <div class="product__item__pic set-bg" data-setbg="img/product/product-2.jpg">
                ${isNewProduct ? '<span class="label new" style="font-size: 14px; color: red; border: 1px solid red;" name="new">最新推出</span>' : ''}
                ${isSaleProduct ? '<span class="label" name="sale" style="font-size: 14px; color: white; background-color: black; border: 1px solid;">特價中</span>' : ''}
                <ul class="product__hover">
                    <li><a href="#"><img src="../static/images/icon/heart.png" alt=""></a></li>
                    <li><a href="shopDetails.html?productNo=${product.productNo}"><img src="../static/images/icon/search.png" alt=""></a></li>
                </ul>
            </div>
            <div class="product__item__text">
                <h6>
                    <span name="productName">${product.name}</span>
                    （
                    <span name="score">${product.score}</span>
                    分）
                </h6>
                ${isSaleProduct ?
                    `<span name="price" style="text-decoration-line: line-through;">${product.price} 元</span>
                    <span style="color: red; margin-left: 10px;">
                        <span name="salePrice">${Math.round(product.price * 0.9)}</span>
                        <span>元</span>
                    </span>` :
                    `<span name="price">${product.price} 元</span>`}
                <a href="#" class="add-cart">+ 加入購物車</a>
            </div>
        </div>
    </div>`;
                // 將商品卡片插入到容器中
                $("#productContent").append(productCard);
            });
        }
    });
});




