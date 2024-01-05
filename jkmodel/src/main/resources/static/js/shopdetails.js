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
        url: 'http://localhost:8080/testRedis',
        contentType: 'application/json', // 指定要发送的数据类型
        dataType: 'json', // 指定期望的响应数据类型
        data: JSON.stringify({ uuid: uuid, productNo: productNo }),
        success: function(response) {
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













