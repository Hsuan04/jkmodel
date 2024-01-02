//新增商品
function addProduct() {

    var formData = new FormData($('#productForm')[0]);

    // 檢查圖片數量
    var photos = $('#productPhotos')[0].files;
    if (photos.length === 0 ||photos.length > 4) {
        alert("圖片至少上傳1張，最多上傳4張圖片");
        return;
    }
    // 清空所有錯誤訊息
    clearErrorMessages();

    $.ajax({
        url: 'http://localhost:8080/products',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,

        success: function(response) {
            console.log('Product added successfully:', response);

            // 錯誤訊息
            displayError('name', response.name);
            displayError('price', response.price);
            displayError('cost', response.cost);
            displayError('stock', response.stock);
            displayError('description', response.description);

            if (response.message === "成功新增商品") {
                alert("新增商品成功！");
                // 可以根據需要進行其他操作，例如跳轉頁面
                // window.location.href = 'https://www.google.com';
            }
        },
        error: function(error) {
            console.error('Error adding product:', error);
            console.log("失敗");
            if (error.status === 400 && error.responseJSON) {
                // 在各個屬性上顯示錯誤訊息
                displayErrorMessages(error.responseJSON);
            }
        }
    });
}
// 清空所有錯誤訊息
function clearErrorMessages() {
    $(".error-message").empty();
}

// 顯示錯誤訊息
function displayError(property, value) {
    if (value !== undefined && value !== null) {
        $("#error" + property.charAt(0).toUpperCase() + property.slice(1)).append('<label for="' + property + '" class="form-label" style="color: red;">' + value + '</label>');
    }
}

