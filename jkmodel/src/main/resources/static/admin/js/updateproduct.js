//
// $(function () {
//     // 取得當前網址並解析URL
//     var currentUrl = window.location.href;
//     var queryString = currentUrl.split('?')[1];
//
//     // 將query轉換為物件
//     var queryParams = {};
//     if (queryString) {
//         queryString.split('&').forEach(function (param) {
//             var keyValue = param.split('=');
//             queryParams[keyValue[0]] = keyValue[1];
//         });
//     }
//
//     // 取得productNo
//     if ('productNo' in queryParams) {
//         var productNoValue = queryParams.productNo;
//
//         // 使用 AJAX 發送 GET 請求到控制器端點
//         $.ajax({
//             url: 'http://localhost:8080/products/' + productNoValue,
//             method: 'GET',
//             dataType: 'json',
//             success: function (data) {
//                 console.log('成功取得商品資訊:', data);
//                 // 商品資料
//                 $('#name').val(data.name);
//                 $('#category').val(data.category);
//                 $('#price').val(data.price);
//                 $('#cost').val(data.cost);
//                 $('#stock').val(data.stock);
//                 $('#status').val(data.status.toString());
//                 $('#description').val(data.description);
//
//                 // 日期格式處理
//                 var onTimeArray = data.onTime;
//                 var offTimeArray = data.offTime;
//                 function formatDate(dateArray) {
//                     return dateArray[0] + '-' +
//                         (dateArray[1] < 10 ? '0' : '') + dateArray[1] + '-' +
//                         (dateArray[2] < 10 ? '0' : '') + dateArray[2] + 'T' +
//                         (dateArray[3] < 10 ? '0' : '') + dateArray[3] + ':' +
//                         (dateArray[4] < 10 ? '0' : '') + dateArray[4];
//                 }
//                 $('#onTime').val(formatDate(onTimeArray));
//                 $('#offTime').val(formatDate(offTimeArray));
//
//                 //將圖片放進標籤裡(未完成)
//
//             },
//             error: function (error) {
//                 // 請求失敗的處理邏輯
//                 console.error('取得商品資訊時發生錯誤:', error);
//             }
//         });
//     } else {
//         console.log("productNo參數不存在於URL中。");
//     }
// });
//
// //更新商品功能
// $("#updateProduct").on("click", function () {
//     var productData = {
//         name: $("#name").val(),
//         category: $("#category").val(),
//         price: $("#price").val(),
//         cost: $("#cost").val(),
//         stock: $("#stock").val(),
//         onTime: $("#onTime").val(),
//         offTime: $("#offTime").val(),
//         status: $("#status").val(),
//         description: $("#description").val(),
//         photos: getBase64Images()
//     };
//     console.log("productNoValue:" + productNoValue)
//     // 使用 jQuery 的 AJAX 發送 POST 請求
//     $.ajax({
//         url: 'http://localhost:8080/products/' + productNoValue,
//         type: 'POST',
//         contentType: 'application/json',
//         data: JSON.stringify(productData),
//         success: function (data) {
//             console.log('Success:', data);
//         },
//         error: function (error) {
//             console.error('Error:', error);
//         }
//     });
// });
//
// // 函數返回 base64 圖片數組
// function getBase64Images() {
//     var filesInput = $("#productPhotos")[0];
//     var files = filesInput.files;
//     var images = [];
//
//     for (var i = 0; i < files.length; i++) {
//         var reader = new FileReader();
//         reader.readAsDataURL(files[i]);
//
//         reader.onload = function () {
//             images.push({ photoString: reader.result });
//         };
//     }
//
//     return images;
// }
//
//

var productNoValue;
var isPageLoaded = false;  // 新增一個變數

if (!isPageLoaded) {
    $(function () {
    // 只有在第一次載入頁面時執行以下程式碼

        // 取得當前網址並解析URL
        var currentUrl = window.location.href;
        var queryString = currentUrl.split('?')[1];

        // 將query轉換為物件
        var queryParams = {};
        if (queryString) {
            queryString.split('&').forEach(function (param) {
                var keyValue = param.split('=');
                queryParams[keyValue[0]] = keyValue[1];
            });
        }

        // 取得productNo
        if ('productNo' in queryParams) {
            productNoValue = queryParams.productNo;  // 將值賦給外層的 productNoValue

            // 使用 AJAX 發送 GET 請求到控制器端點
            $.ajax({
                url: 'http://localhost:8080/products/' + productNoValue,
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    console.log('成功取得商品資訊:', data);
                    // 商品資料
                    $('#name').val(data.name);
                    $('#category').val(data.category);
                    $('#price').val(data.price);
                    $('#cost').val(data.cost);
                    $('#stock').val(data.stock);
                    $('#status').val(data.status.toString());
                    $('#description').val(data.description);

                    // 日期格式處理
                    var onTimeArray = data.onTime;
                    var offTimeArray = data.offTime;
                    function formatDate(dateArray) {
                        return dateArray[0] + '-' +
                            (dateArray[1] < 10 ? '0' : '') + dateArray[1] + '-' +
                            (dateArray[2] < 10 ? '0' : '') + dateArray[2] + 'T' +
                            (dateArray[3] < 10 ? '0' : '') + dateArray[3] + ':' +
                            (dateArray[4] < 10 ? '0' : '') + dateArray[4];
                    }
                    $('#onTime').val(formatDate(onTimeArray));
                    $('#offTime').val(formatDate(offTimeArray));

                    //將圖片放進標籤裡(未完成)

                },
                error: function (error) {
                    // 請求失敗的處理邏輯
                    console.error('取得商品資訊時發生錯誤:', error);
                }
            });
        } else {
            console.log("productNo參數不存在於URL中。");
        }
        // 將 isPageLoaded 設為 true，表示已經執行過
        isPageLoaded = true;
        console.log('isPageLoaded已經轉成true')
});
}

//更新商品功能
$("#updateProduct").on("click", function () {
    event.preventDefault();

    if (productNoValue) {
        var productData = {
            name: $("#name").val(),
            category: $("#category").val(),
            price: $("#price").val(),
            cost: $("#cost").val(),
            stock: $("#stock").val(),
            onTime: $("#onTime").val(),
            offTime: $("#offTime").val(),
            status: $("#status").val(),
            description: $("#description").val(),
            photos: getBase64Images()
        };

        $.ajax({
            url: 'http://localhost:8080/products/' + productNoValue,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(productData),
            success: function (data) {
                console.log("成功，準備執行跳轉頁面");
                window.location.href = 'http://localhost:63342/jkmodel/jkmodel/src/main/resources/templetes/admin/allProduct.html';
            },
            error: function (error) {
                console.error('Error:', error);
            }
        });
    } else {
        console.log("productNoValue 尚未被設置。");
    }
});

// 函數返回 base64 圖片數組
function getBase64Images() {
    var filesInput = $("#productPhotos")[0];
    var files = filesInput.files;
    var images = [];

    for (var i = 0; i < files.length; i++) {
        var reader = new FileReader();
        reader.readAsDataURL(files[i]);

        reader.onload = function () {
            images.push({ photoString: reader.result });
        };
    }

    return images;
}

