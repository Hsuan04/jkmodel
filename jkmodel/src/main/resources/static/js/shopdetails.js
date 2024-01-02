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

        },
        error: function () {
            console.error("取得商品資料失敗");
        }
    });
})

//點第一張圖時












