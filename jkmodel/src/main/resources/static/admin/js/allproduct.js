//顯示所有商品
$(function () {
    $.ajax({
        // url: `${window.location.path}/products`,
        url: "http://localhost:8080/products",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);

            // 將商品按照productNo由小到大排序
            data.sort((a, b) => a.productNo - b.productNo);

            var productContainer = $("#productContainer");
            productContainer.empty();

            // 遍歷商品資料
            $.each(data, function (index, product) {

                var statusText = product.status ? '上架' : '下架';
                var statusStyle = product.status ? 'color:green;' : 'color:red;';
                console.log(product.lastModifiedTime);

                var time = product.lastModifiedTime[0]+"/"+product.lastModifiedTime[1]+"/"+product.lastModifiedTime[2];
                var isoDate = new Date(product.lastModifiedTime);
                var formattedTime = isoDate.toLocaleString();


                // 動態產生商品
                var productRow = `
                                <tr>
                                    <th>${product.productNo}</th>
                                    <td>${product.name}</td>
                                    <td>${product.category}</td>
                                    <td>${product.price}</td>
                                    <td style="${statusStyle}">${statusText}</td>
                                    <td>${formattedTime}</td>
                                    <td>
                                        <a class="update" data-productNo="${product.productNo}" href="#">修改</a>
                                        /
                                        <a class="deleteProduct" data-productNo="${product.productNo}" href="#">刪除</a>
                                    </td>
                                </tr>`;
                // console.log(productRow);

                // 將商品卡片插入到容器中
                $("#productContainer").append(productRow);

                //修改商品功能
                $(document).on('click', '.update', function (e) {
                    e.preventDefault();

                    var productRow = $(this).closest('tr');
                    var productNo = $(this).data('productno');

                    // 跳轉 updateProduct.html + productNo
                    window.location.href = "updateProduct.html?productNo=" + productNo;
                });
            });
        }
    });
});

$(document).on('click', '.deleteProduct', function (e) {
    e.preventDefault();

    var productRow = $(this).closest('tr');
    var productNo = productRow.find('.deleteProduct').data('productno');

    var confirmDelete = confirm('確定要刪除此商品嗎？');

    if (confirmDelete) {
        // 調用刪除商品的 API
        $.ajax({
            url: "http://localhost:8080/products/" + productNo,
            method: 'DELETE',
            success: function (data) {
                // alert('商品已成功刪除');
                productRow.remove();
            },
            error: function (error) {
                console.error('刪除商品時發生錯誤', error);
            }
        });
    }
});

// 點擊修改按鈕
function updateProduct() {
    var productRow = $(this).closest('tr');
    var productNo = productRow.find('.deleteProduct').data('productno');
    console.log(productNo);
    // 使用 window.location.href 跳轉到 updateProduct.html 並附加 productNo 參數
    window.location.href = "updateProduct.html?productNo=" + productNo;
}






