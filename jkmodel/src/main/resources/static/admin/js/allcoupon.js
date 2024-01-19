//顯示所有商品
$(function () {
    $.ajax({
        url: "http://localhost:8081/coupons",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);

            var couponContainer = $("#couponContainer");
            couponContainer.empty();

            // 遍歷商品資料
            $.each(data, function (index, coupon) {

                var startTime = new Date(coupon.startTime);
                var formattedStartTime = startTime.toLocaleString();

                var expireTime = new Date(coupon.expireTime);
                var formattedExpireTime = expireTime.toLocaleString();

                // 動態產生優惠卷
                var couponRow = `
                                <tr>
                                    <th>${coupon.couponNo}</th>
                                    <th>${coupon.name}</th>
                                    <td>${coupon.discount}</td>
                                    <td>${coupon.limitPrice}</td>
                                    <td>${coupon.quantity}</td>
                                    <td>${formattedStartTime}</td>
                                    <td>${formattedExpireTime}</td>
                                </tr>`;
                console.log(couponRow);

                // 將優惠卷卡片插入到容器中
                $("#couponContainer").append(couponRow);

            });
        }
    });
});