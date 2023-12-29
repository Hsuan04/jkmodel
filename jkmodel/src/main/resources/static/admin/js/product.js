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
        // contentType: 'multipart/form-data',
        success: function(response) {
            console.log('Product added successfully:', response);
            // 錯誤訊息
            // if (response.name !== undefined && response.name !== null) {
            //     $("#errorName").append('<label for="name" class="form-label" style="color: red;">' + response.name + '</label>');
            // }
            // if (response.price !== undefined && response.price !== null) {
            //     $("#errorPrice").append('<label for="price" class="form-label" style="color: red;">' + response.price + '</label>');
            // }
            // if (response.cost !== undefined && response.cost !== null) {
            //     $("#errorCost").append('<label for="cost" class="form-label" style="color: red;">' + response.cost + '</label>');
            // }
            // if (response.stock !== undefined && response.stock !== null) {
            //     $("#errorStock").append('<label for="stock" class="form-label" style="color: red;">' + response.stock + '</label>');
            // }
            // if (response.description !== undefined && response.description !== null) {
            //     $("#errorDescription").append('<label for="description" class="form-label" style="color: red;">' + response.description + '</label>');
            // }

            displayError('name', response.name);
            displayError('price', response.price);
            displayError('cost', response.cost);
            displayError('stock', response.stock);
            displayError('description', response.description);

            console.log("成功");
            // if (response === "added success") {
            //     window.location.href = 'https://www.google.com';
            // }
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