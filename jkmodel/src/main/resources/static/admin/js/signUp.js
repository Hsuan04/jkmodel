(function ($) {
    "use strict";

    //宣告變數(按鈕)
    var signUp = $('#test');
    //當按鈕觸發點擊時會取出前端name, 及password的值
    signUp.click(function() {
        console.log("SIGNUP....")

        var name = $("#floatingInput").val(); // 获取输入框的值
        var account = $("#floatingMail").val(); // 获取输入框的值
        var pass = $("#floatingPassword").val(); // 获取输入框的值

    //再將取到的值放到物件並用jason格式
        var signUpData = {
            name: name,
            account: account,
            password: pass
        };
        $.ajax({
            url: `http://localhost:8080/insert`,//資料請求網址
            type: 'POST',//請求的方法
            data: JSON.stringify(signUpData),//物件的資料轉為string(裡面的signUpData需要符合對應的Dto資料規格ex:name,account, password)
            contentType: 'application/json',  // 向後端通知 Content-Type 為 JSON
            dataType: 'json',//資料格式為jason
            success: function (data) {
                //成功時回應頁面
                //url路徑+data的id(controller login對應的id)
                window.location.href = `./allAdmin.html?id=${data.adminId}`;
                console.log(data);
            },
            error: function () {
                $.msg.alert("添加異常");
            }

        })

    })
})(jQuery);