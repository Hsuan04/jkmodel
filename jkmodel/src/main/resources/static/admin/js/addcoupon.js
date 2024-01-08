$("#addCoupon").click(function () {
    console.log("新增有點到");
    var couponData = {
        "name": $("#name").val(),
        "discount": $("#discount").val(),
        "validDays": $("#validDays").val(),
        "limitPrice": $("#limitPrice").val(),
        "startTime": $("#startTime").val(),
        "expireTime": $("#expireTime").val(),
        "releaseTime": $("#releaseTime").val(),
        "description": $("#description").val(),
        "quantity": $("#quantity").val(),
        "admId":1
    };
    console.log(couponData);

    // 清空所有錯誤訊息
    clearErrorMessages();

    $.ajax({
        url: 'http://localhost:8081/addCoupon',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(couponData),

        success: function (response, status, xhr) {
            console.log('Coupon added successfully:', response);

            // if(response === "addCoupon false"){
            //     displayError('name', response.name);
            //     displayError('discount', response.discount);
            //     displayError('startTime', response.startTime);
            //     displayError('description', response.description);
            // }

            if (status === "success") {
                alert("成功新增優惠卷！");

                $("#name").val('');
                $("#discount").val('');
                $("#releaseTime").val('');
                $("#validDays").val('');
                $("#limitPrice").val('');
                $("#startTime").val('');
                $("#expireTime").val('');
                $("#description").val('');
                $("#quantity").val('');
                // 可以根據需要進行其他操作，例如跳轉頁面
                // window.location.href = 'http://localhost:8081/allCoupon.html';
            }
        },
        error: function (error) {
            console.error('Error adding coupon:', error);
            console.log("失敗");

            if (error.status === 400 && error.responseJSON) {
                // 在各個屬性上顯示錯誤訊息
                displayErrorMessages(error.responseJSON);
                console.log(error.responseJSON);
            }
        }
    });
});

// 清空所有錯誤訊息
function clearErrorMessages() {
    $(".error-message").empty();
}

// 顯示錯誤訊息
function displayErrorMessages(errors) {
    for (var property in errors) {
        if (errors.hasOwnProperty(property)) {
            $("#error" + property.charAt(0).toUpperCase() + property.slice(1))
                .append('<label for="' + property + '" class="form-label" style="color: red;">' + errors[property] + '</label>');
        }
    }
}


