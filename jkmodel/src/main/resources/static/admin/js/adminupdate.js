(function ($) {
    "use strict";

    const adminId = window.location.href.split("=")[1];

    $.ajax({
        url: `http://localhost:8080/read/${adminId}`,//資料請求網址${}為jquery取參數的寫法
        type: 'GET',//請求的方法
        // data: JSON.stringify(userData),//物件的資料轉為string(裡面的userData需要符合對應的Dto資料規格ex:name, password)
        contentType: 'application/json',  // 向後端通知 Content-Type 為 JSON
        dataType: 'json',//資料格式為jason
        success: function (data) {

            $("#admid").val(data.admId); // 获取输入框的值
            $("#floatingInput").val(data.name); // 获取输入框的值
            $("#floatingMail").val(data.account); // 获取输入框的值
            $("#floatingPassword").val(data.password); // 获取输入框的值
        }
    })
})(jQuery);


(function ($) {
    "use strict";
    //宣告變數(按鈕)
    var update = $('#update');
    //當按鈕觸發點擊時會取出前端name, 及password的值
    update.click(function() {
        // console.log("update....")

        var id = $("#admid").val(); // 獲取输入框的值
        var name = $("#floatingInput").val(); // 獲取输入框的值
        var account = $("#floatingMail").val(); // 獲取输入框的值
        var pass = $("#floatingPassword").val(); // 獲取输入框的值

        //再將取到的值放到物件並用jason格式
        var upadateData = {
            admid: id,
            name: name,
            account: account,
            password: pass
        };
        $.ajax({
            url: `http://localhost:8080/update/${id}`,//資料請求網址
            type: 'PUT',//請求的方法
            data: JSON.stringify(upadateData),//物件的資料轉為string(裡面的signUpData需要符合對應的Dto資料規格ex:name,account, password)
            contentType: 'application/json',  // 向後端通知 Content-Type 為 JSON
            dataType: 'json',//資料格式為jason
            success: function (data) {
                //成功時回應頁面
                //url路徑+data的id(controller login對應的id)
                window.location.href = `./allAdmin.html`;
                console.log(data);
            },
            error: function () {
                $.msg.alert("添加異常");
            }

        })
    })
})(jQuery);