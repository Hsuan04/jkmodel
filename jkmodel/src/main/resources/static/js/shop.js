//=============================== 分類、價格區間、排序 ===============================
// 儲存條件參數
var filterParams = {
    category: 'null', // 分類方式
    minPrice: null, // 最低金額
    maxPrice: null, // 最高金額
    orderBy: null,  // 排序方式
    sort: null      // 升降冪
};

// ============= 點擊分類取得對應的值 =============
// 所有商品
$('#allProduct').click(function (event) {
    event.preventDefault();
    filterParams.category = 'null';
    // console.log("有點到所有商品，且種類為：" + filterParams.category);
    sendFilterRequest();
});
// 上衣
$('#tops').click(function (event) {
    event.preventDefault();
    filterParams.category = '上衣';
    // console.log("點擊 tops:" + filterParams.category);
    sendFilterRequest();
});
// 褲子
$('#pants').click(function (event) {
    event.preventDefault();
    filterParams.category = '褲子';
    // console.log("點擊 pants:" + filterParams.category);
    sendFilterRequest();
});
// 配件
$('#accessories').click(function (event) {
    event.preventDefault();
    filterParams.category = '配件';
    // console.log("點擊 配件:" + filterParams.category);
    sendFilterRequest();
});
// 其他
$('#others').click(function (event) {
    event.preventDefault();
    filterParams.category = '其他';
    console.log("點擊 others:" + filterParams.category);
    sendFilterRequest();
});

// ============= 點擊價格區間取得對應的值 =============
//不限價格
$('#priceAll').click(function (event) {
    event.preventDefault();
    filterParams.minPrice = null;
    filterParams.maxPrice = null;
    sendFilterRequest();
});
//999以下
$('#price1').click(function (event) {
    event.preventDefault();
    filterParams.minPrice = null;
    filterParams.maxPrice = 999;
    // console.log("點擊 999:" + filterParams.maxPrice);
    sendFilterRequest();
});
//1000-1999以下
$('#price2').click(function (event) {
    event.preventDefault();
    filterParams.minPrice = 1000;
    filterParams.maxPrice = 1999;
    sendFilterRequest();
});
//2000-2499以下
$('#price3').click(function (event) {
    event.preventDefault();
    filterParams.minPrice = 2000;
    filterParams.maxPrice = 2499;
    sendFilterRequest();
});
//2500以上
$('#price4').click(function (event) {
    event.preventDefault();
    filterParams.minPrice = 2500;
    filterParams.maxPrice = null;
    sendFilterRequest();
});


$('#sortingOptions').change(function () {
    var selectedOption = $(this).val();

    if (selectedOption === "timeNewToOld") {
        filterParams.orderBy = 'onTime';
        filterParams.sort = 'DESC';
    } else if (selectedOption === "timeOldToNew") {
        filterParams.orderBy = 'onTime';
        filterParams.sort = 'ASC';
    } else if (selectedOption === "viewsManyToLess") {
        filterParams.orderBy = 'views';
        filterParams.sort = 'DESC';
    } else if (selectedOption === "viewsLessToMany") {
        filterParams.orderBy = 'views';
        filterParams.sort = 'ASC';
    }
    sendFilterRequest();
});

// ============= 發送ajax =============
function sendFilterRequest() {

    $.ajax({
        url: 'http://localhost:8080/products',
        method: 'GET',
        dataType: 'json',
        data: filterParams,
        success: function (products) {
            // console.log("成功接收");
            //判斷回傳的資料是否存在或空
            if (products && products.length > 0) {
                // console.log("filterParams"+ filterParams);
                $("#productContent").empty();

                // 變數用來記錄商品數量
                var totalProducts = 0;

                products.forEach(function (product) {

                    // 計算商品時間
                    var onTime = new Date(product.onTime);
                    // console.log(product.onTime);
                    var currentDate = new Date();
                    // console.log(currentDate);
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
                        // console.log(product.photos[0].photoString);
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

                    $("#productContent").append(productCard);
                    // 商品數量加1
                    totalProducts++;
                });
                // 將商品數量顯示在指定的元素中
                $("#productsCount").text(totalProducts);

            } else {
                // console.log("No products found.");
                $("#productContent").empty();
                $("#productsCount").text(0);
            }
        },
        error: function (error) {
            // console.error('Error:', error);
            $("#productContent").empty();
            $("#productsCount").text(0);
        }
    });
}


//=============================== 搜尋關鍵字商品 ===============================
$('#search').on('click',function (event) {
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

            // 變數用來記錄商品數量
            var totalProducts = 0;

            products.forEach(function (product) {
                // console.log("product：" + product);

                // 計算商品時間
                var onTime = new Date(product.onTime);
                // console.log(product.onTime);
                var currentDate = new Date();
                // console.log(currentDate);
                var daysDifference = Math.abs((onTime - currentDate) / (1000 * 60 * 60 * 24));

//              // 判斷是否為最新商品或特價商品
                var isNewProduct = daysDifference <= 14;
                var isSaleProduct = daysDifference >= 90;

                // 圖片處理
                var photosContainer = '';

                if (product.photos && product.photos.length > 0 && product.photos[0].photoString !== null) {
                    var imageUrl = 'data:image/jpeg;base64,' + product.photos[0].photoString;
                    photosContainer += `<img src="${imageUrl}" alt="Product Photo">`;
                    // console.log(product.photos[0].photoString);
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

                $("#productContent").append(productCard);

                // 商品數量加1
                totalProducts++;
            });
            // 將商品數量顯示在指定的元素中
            $("#productsCount").text(totalProducts);
        },
        error: function (error) {
            // console.error('Error:', error);
            $("#productContent").empty();
            $("#productsCount").text(0);
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
            // console.log(data);

            var row = $("#row");
            row.empty();

            // 變數用來記錄商品數量
            var totalProducts = 0;

            // 遍歷商品資料
            $.each(data, function (index, product) {

                // 計算商品時間
                var onTime = new Date(product.onTime);
                // console.log(product.onTime);
                var currentDate = new Date();
                // console.log(currentDate);
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
                    // console.log(product.photos[0].photoString);
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

// ================================== 優惠卷發送功能 ==================================
function addMessageToList(couponInfoList) {
    console.log("Received CouponInfoList: ", couponInfoList);

    $('#couponContainer').empty();

    // 判斷 List 增加關閉優惠卷消息的通道
    if (couponInfoList != null) {
        var closeConnection = `<button id="closeWebsocketConnection" type="button" class="btn btn-secondary btn-sm"
        style="position: fixed; bottom: 20px; right: 20px; max-width: 420px; z-index: 1;">關閉優惠卷消息</button>`;
        $('#couponContainer').append(closeConnection);
    }

    // 遍歷 couponInfoList 並生成 HTML 元素
    couponInfoList.forEach(function(couponInfo, index) {
        if (couponInfo.coupon) {
            var coupon = couponInfo.coupon;
            var remainingQuantityText = (couponInfo.remainingQuantity !== null) ? couponInfo.remainingQuantity : "無限";
            var couponHtml = `
                    <div id="coupon-${index}" class="card" style="max-width: 420px;margin-bottom: 10px;">
                        <div class="card-header">${coupon.name}</div>
                        <div class="card-body">
                            <div class="card-text">jkModel發送了優惠卷，趕快來取得更優惠的購物價格</div>
                            <span>優惠價格： ${coupon.discount} 元</span>，
                            <span>剩餘票數： ${remainingQuantityText}</span>
                            <br>
                            <button type="button" class="btn btn-success" onclick="getCoupon(${index})">取得優惠券</button>
                           <button type="button" class="btn btn-danger" onclick="giveUpCoupon(${index})">放棄</button>
                        </div>
                    </div>
            `;

            $('#couponContainer').append(couponHtml);
        }
    });

    // 關閉連線
    $('#closeWebsocketConnection').click(function () {
        alert("成功關閉websocket連線");
        $('#couponContainer').empty();
        stompClient.disconnect();
    });
};


// ================================== 設置與取得cookie(test) ==================================
$(function () {
    localStorage.setItem('userId','1001');
});

// ================================== 取得/放棄優惠卷 ==================================
function getCoupon(index) {
    var userId = localStorage.getItem("userId");
    console.log('getCoupon方法中' + userId);
    if( userId !== null ){
        //發送ajax判斷資料庫是否有該優惠卷，如果有回傳你已經有此優惠卷，如果沒有則成功取得
        console.log("開始執行會員取得優惠卷的方法");
        var couponNo = couponInfoList[index].coupon.couponNo;
        $.ajax({
            url: `http://localhost:8080/coupon?userId=${userId}&couponNo=${couponNo}`,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data == "success"){
                    alert("成功取得該優惠卷");
                } else {
                    alert("你已經有這張優惠卷了,請查看會員中心的優惠卷管理");
                }
            }
        });
    } else {
        confirm ("請先登入後才能取得優惠卷");
        window.location.href="signIn.html";
    }
}

function giveUpCoupon(index) {
    var confirmResult = confirm("確定要放棄優惠卷吗？");
    if (confirmResult) {
        stompClient.send('/app/giveUpCoupon', {}, JSON.stringify({ index: index }));
    }
}

// ================================== websocket 通道 ==================================

// 使用 STOMP.js 連結 WebSocket
var socket = new SockJS('http://localhost:8081/coupon-websocket'); // 使用 WebSocket 的端点路径
var stompClient = Stomp.over(socket);

// 連接 STOMP 服務
stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    // 訂閱消息
    stompClient.subscribe('/topic/coupons', function (message) {
        var couponInfoList = JSON.parse(message.body);
        // console.log("Received coupons: ", couponInfoList);
        addMessageToList(couponInfoList);
    });
});


