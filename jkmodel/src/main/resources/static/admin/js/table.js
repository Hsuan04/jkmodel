(function ($) {
    "use strict";

    const adminId = window.location.href.spilt("=")[1];

    $.ajax({
        url: `http://localhost:8080/read/${adminId}`,//資料請求網址${}為jquery取參數的寫法
        type: 'GET',//請求的方法
        // data: JSON.stringify(userData),//物件的資料轉為string(裡面的userData需要符合對應的Dto資料規格ex:name, password)
        contentType: 'application/json',  // 向後端通知 Content-Type 為 JSON
        dataType: 'json',//資料格式為jason
        success: function (data) {

            $("#adminId").val(data.id); // 获取输入框的值
            $("#floatingInput").val(data.name); // 获取输入框的值
            $("#floatingAccount").val(data.account); // 获取输入框的值
            $("#floatingPassword").val(data.password); // 获取输入框的值
            $("#createdTime").val(data.createdTime); // 获取输入框的值
            $("#productNo").val(data.productNo); // 获取输入框的值


        },

    })
});