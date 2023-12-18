$.ajax({
    url: `${window.location.path}/products`,
    method: 'GET',
    dataType: 'json',
    success: function (data) {
        console.log(data);

        var row = $("#row");
        row.empty();

        // 遍歷商品資料
        $.each(data, function (index, product) {
            // 計算商品建立時間與現在時間的差距（天數）
            var createdDate = new Date(product.createdDate);
            var currentDate = new Date();
            var daysDifference = Math.floor((currentDate - createdDate) / (1000 * 60 * 60 * 24));

            // 動態產生商品卡片的HTML
            var productCard = `
                <div class="col-lg-4 col-md-6 col-sm-6">
                    <div class="product__item">
                        <div class="product__item__pic set-bg" data-setbg="img/product/product-2.jpg">
                            <span class="label new" style="font-size: 14px; color: red; border: 1px solid red;">最新推出</span>
                            <span class="label" name="sale">特價中</span>
                            <ul class="product__hover">
                                <li><a href="#"><img src="../static/images/icon/heart.png" alt=""></a></li>
                                <li><a href="#"><img src="../static/images/icon/search.png" alt=""></a></li>
                            </ul>
                        </div>
                        <div class="product__item__text">
                            <h6>
                                <span name="productName">${product.name}</span>
                                （
                                <span name="score">${product.score}</span>
                                分）
                            </h6>
                            <span name="price">${product.price} 元</span>
                            <a href="#" class="add-cart">+ 加入購物車</a>
                        </div>
                    </div>
                </div>`;

            // 判斷是否為最新商品，顯示相應標籤
            if (daysDifference <= 14) {
                productCard = productCard.replace('<span class="label new" style="font-size: 14px; color: red; border: 1px solid red;">最新推出</span>', '');
            }

            // 判斷是否為特價商品，顯示相應標籤及價格調整
            if (daysDifference >= 90) {
                productCard = productCard.replace('<span class="label" name="sale">特價中</span>', '');
                productCard = productCard.replace('<span name="price">', '<span name="price" style="text-decoration-line: line-through;">');

                // 計算特價價格並四捨五入到整數
                var salePrice = Math.round(product.price * 0.8);
                productCard += `
                    <span style="color: red; margin-left: 10px;">
                        <span name="salePrice">${salePrice}</span>
                        <span>元</span>
                    </span>`;
            }

            // 將商品卡片插入到row中
            row.append(productCard);
        });
    }
});
