// 生成UUID
function generateUUID() {
    let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        let r = (Math.random() * 16) | 0,
            v = c == 'x' ? r : (r & 0x3) | 0x8;
        return v.toString(16);
    });
    return uuid;
}

// 將UUID存儲在sessionStorage中
function saveUUIDToSessionStorage() {
    let uuid = generateUUID();
    sessionStorage.setItem('uuid', uuid);
    return uuid;
}

// 取得sessionStorage中的UUID
function getUUIDFromSessionStorage() {
    return sessionStorage.getItem('uuid');
}

// 產生或獲取sessionStorage中的UUID
function getOrCreateUUID() {
    let uuid = getUUIDFromSessionStorage();
    if (!uuid) {
        uuid = saveUUIDToSessionStorage();
    }
    return uuid;
}

// 使用Ajax將UUID發送到後端
function sendUUIDToBackend(uuid,productNo) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/addUuidAndView',
        contentType: 'application/json', // 指定要发送的数据类型
        dataType: 'json', // 指定期望的响应数据类型
        data: JSON.stringify({ uuid: uuid, productNo: productNo }),
        success: function(topProducts) {
            $("#topProducts").empty();

            //顯示喜好商品
            topProducts.forEach(function (product) {

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

                $("#topProducts").append(productCard);
            });

            console.log('UUID已成功發送到後端');

        },
        error: function(error) {
            console.error('發送UUID時發生錯誤', error);
        }
    });
}



$(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var productNo = urlParams.get("productNo");
    console.log("productNo:"+ productNo)
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/products/" + productNo,
        dataType: "json",
        success: function (product) {
            console.log("成功發出請求")
            $("#name").text(product.name);
            $("#views").text(product.views);
            $("#price").text(product.price);
            $("#description").text(product.description);

            for (var i = 0; i < product.photos.length; i++) {
                var imageUrl = 'data:image/jpeg;base64,' + product.photos[i].photoString;
                var img = document.createElement('img');
                img.src = imageUrl;
                $("#photo" + (i + 1)).append(img);
            }
            // 大圖
            var imageUrl = 'data:image/jpeg;base64,' + product.photos[0].photoString;
            var img = document.createElement('img');
            img.src = imageUrl;
            $("#bigPhoto1").append(img);

            //發送UUID給RedisController
            let uuid = getOrCreateUUID();
            sendUUIDToBackend(uuid,productNo);

        },
        error: function () {
            console.error("取得商品資料失敗");
        }
    });
})

function changeBigPhoto(index) {
    // 隐藏所有大图
    document.querySelectorAll('.tab-pane').forEach(function (tab) {
        tab.style.display = 'none';
    });

    // 显示点击对应索引的大图
    document.getElementById('tabs-' + index).style.display = 'block';
}

//     var urlParams = new URLSearchParams(window.location.search);
//     var productNo = urlParams.get("productNo");
//     console.log("productNo:"+ productNo)
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:8080/products/" + productNo,
//         dataType: "json",
//         success: function (product) {
//             console.log("成功發出請求")
//             $("#name").text(product.name);
//             $("#views").text(product.views);
//             $("#price").text(product.price);
//             $("#description").text(product.description);
//
//             for (var i = 0; i < product.photos.length; i++) {
//                 var imageUrl = 'data:image/jpeg;base64,' + product.photos[i].photoString;
//                 var img = document.createElement('img');
//                 img.src = imageUrl;
//                 $("#photo" + (i + 1)).append(img);
//             }
//             // 大圖
//             var imageUrl = 'data:image/jpeg;base64,' + product.photos[0].photoString;
//             var img = document.createElement('img');
//             img.src = imageUrl;
//             $("#bigPhoto1").append(img);
//
//         },
//         error: function () {
//             console.error("取得商品資料失敗");
//         }
//     });
// })













