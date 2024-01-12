$(document).ready(function () {
    "use strict";
    // 事件委派：當點擊表格中擁有 'delete' 類別的 td 元素時觸發
    $(document).on("click", "td.delete", function () {
        // 獲取點擊的 td 元素的 id 屬性值
        var admId = $(this).attr('id');

        // 從行中獲取 admid 的值
        console.log(admId)

        $.ajax({
            url: `http://localhost:8080/admin/${admId}`,//資料請求網址${}為jquery取參數的寫法
            type: 'DELETE',//請求的方法
            // data: JSON.stringify(userData),//物件的資料轉為string(裡面的userData需要符合對應的Dto資料規格ex:name, password)
            contentType: 'application/json',  // 向後端通知 Content-Type 為 JSON
            dataType: 'json',//資料格式為jason
            success: function () {

                window.location.href = `./allAdmin.html`;

            }
        })
    })
});