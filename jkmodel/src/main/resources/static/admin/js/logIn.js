(function ($) {
    "use strict";

    //宣告變數(按鈕)
    var logIn = $('#logIn');
    //當按鈕觸發點擊時會取出前端name, 及password的值
    logIn.click(function() {
        var name = $("#floatingInput").val(); // 获取输入框的值
        var pass = $("#floatingPassword").val(); // 获取输入框的值

        //再將取到的值放到物件並用jason格式
        var userData = {
            name: name,
            password: pass
        };
        //用ajax傳輸
        $.ajax({
            url: `http://localhost:8080/logIn`,//資料請求網址
            type: 'post',//請求的方法
            data: JSON.stringify(userData),//物件的資料轉為string(裡面的userData需要符合對應的Dto資料規格ex:name, password)
            contentType: 'application/json',  // 向後端通知 Content-Type 為 JSON
            dataType: 'json',//資料格式為jason
            success: function (data) {
                // 成功時回應頁面
                //url路徑+data的id(controller login對應的id)
                window.location.href = `./allAdmin.html`;
            },
            error: function () {
                $.msg.alert("添加異常");
            }

        });
    })

})(jQuery);