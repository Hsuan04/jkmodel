//=============================== 搜尋關鍵字商品 ===============================
$('#search').click(function (event) {
    event.preventDefault();
    //取得關鍵字
    var searchKeyword = $('#keyword').val();
    console.log("關鍵字是:" + searchKeyword);

    // 發送ajax
    $.ajax({
        url: 'http://localhost:8080/products?search=' + searchKeyword,
        method: 'GET',
        dataType: 'json',
        success: function (products) {

            $("#productContent").empty();

            products.forEach(function (product) {
                console.log("product：" + product);

                // 計算商品時間
                var onTime = new Date(product.onTime);
                console.log(product.onTime);
                var currentDate = new Date();
                console.log(currentDate);
                var daysDifference = Math.abs((onTime - currentDate) / (1000 * 60 * 60 * 24));

//              // 判斷是否為最新商品或特價商品
                var isNewProduct = daysDifference <= 14;
                var isSaleProduct = daysDifference >= 90;

                // 圖片處理
                var photosContainer = '';
                // 检查商品是否有照片，且第一个照片的photoString不为null
                if (product.photos && product.photos.length > 0 && product.photos[0].photoString !== null) {
                    var imageUrl = 'data:image/jpeg;base64,' + product.photos[0].photoString;
                    photosContainer += `<img src="${imageUrl}" alt="Product Photo">`;
                    console.log(product.photos[0].photoString);
                } else {
                    photosContainer += `<img src="" alt="Default Photo">`;
                }

                // 動態產生商品卡片的HTML
                var productCard = `
                    <div class="col-lg-4 col-md-6 col-sm-6">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="img/product/product-2.jpg">
                                <div class="product__item__photos">
                                    ${photosContainer}
                                </div>
                                ${isNewProduct ? `<span class="label new" style="font-size: 14px; color: red; border: 1px solid red;" name="new">最新推出</span>` : ''}
                                ${isSaleProduct ? `<span class="label" name="sale" style="font-size: 14px; color: white; background-color: black; border: 1px solid;">特價中</span>` : ''}
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

                // 将商品卡片插入到容器中
                $("#productContent").append(productCard);
            });
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
});


//=============================== 顯示所有商品 ===============================
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

            // 變數用來記錄商品數量
            var totalProducts = 0;

            // 遍歷商品資料
            $.each(data, function (index, product) {

                // 計算商品時間
                var onTime = new Date(product.onTime);
                console.log(product.onTime);
                var currentDate = new Date();
                console.log(currentDate);
                var daysDifference = Math.abs((onTime - currentDate) / (1000 * 60 * 60 * 24));

                // 判斷是否為最新商品或特價商品
                var isNewProduct = daysDifference <= 14;
                var isSaleProduct = daysDifference >= 90;

                // 圖片處理
                var photosContainer = '';
                // 检查商品是否有照片，且第一个照片的photoString不为null
                if (product.photos && product.photos.length > 0 && product.photos[0].photoString !== null) {
                    var imageUrl = 'data:image/jpeg;base64,' + product.photos[0].photoString;
                    photosContainer += `<img src="${imageUrl}" alt="Product Photo">`;
                    console.log(product.photos[0].photoString);
                } else {
                    photosContainer += `<img src="" alt="Default Photo">`;
                }

                // 動態產生商品卡片的HTML
                var productCard = `
                    <div class="col-lg-4 col-md-6 col-sm-6">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="img/product/product-2.jpg">
                                <div class="product__item__photos">
                                    ${photosContainer}
                                </div>
                                ${isNewProduct ? `<span class="label new" style="font-size: 14px; color: red; border: 1px solid red;" name="new">最新推出</span>` : ''}
                                ${isSaleProduct ? `<span class="label" name="sale" style="font-size: 14px; color: white; background-color: black; border: 1px solid;">特價中</span>` : ''}
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

                // 商品數量加1
                totalProducts++;
            });
            // 將商品數量顯示在指定的元素中
            $("#productsCount").text(totalProducts);
        }
    });
});




