//新增商品
function addProduct() {
    var formData = new FormData($('#productForm')[0]);

    // 檢查圖片數量
    var photos = $('#productPhotos')[0].files;
    if (photos.length === 0 ||photos.length > 4) {
        alert("圖片至少上傳1張，最多上傳4張圖片");
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/products',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        // contentType: 'multipart/form-data',
        success: function(response) {
            console.log('Product added successfully:', response);
            console.log(formData);
        },
        error: function(error) {
            console.error('Error adding product:', error);
        }
    });
}