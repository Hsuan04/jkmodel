//顯示所有商品
$(function () {
    $.ajax({
        // url: `${window.location.path}/products`,
        url: "http://localhost:8080/products",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);

            var productContainer = $("#productContainer");
            productContainer.empty();

            // 遍歷商品資料
            $.each(data, function (index, product) {

                var statusText = product.status ? '上架中' : '下架中';
                var statusStyle = product.status ? 'color:green;' : 'color:red;';

                var formattedLastModifiedTime = product.lastModifiedTime.replace("T", " ");

                // 動態產生商品
                var productRow = `
                                <tr>
                                    <th>${product.productNo}</th>
                                    <td>${product.name}</td>
                                    <td>${product.category}</td>
                                    <td>${product.price}</td>
                                     <td style="${statusStyle}">${statusText}</td>
                                    <td>${formattedLastModifiedTime}</td>
                                    <td>
                                        <a href="update.html">修改</a>
                                        <a class="deleteProduct" data-productNo="${product.productNo}" href="#">刪除</a>
                                    </td>
                                </tr>`;
                console.log(productRow);

                // 將商品卡片插入到容器中
                $("#productContainer").append(productRow);
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






