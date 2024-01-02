$(document).ready(function () {
    "use strict";

    const adminId = window.location.href.split("=")[1];

    $.ajax({
        url: `http://localhost:8080/findAll`,//資料請求網址${}為jquery取參數的寫法
        type: 'GET',//請求的方法
        // data: JSON.stringify(userData),//物件的資料轉為string(裡面的userData需要符合對應的Dto資料規格ex:name, password)
        contentType: 'application/json',  // 向後端通知 Content-Type 為 JSON
        dataType: 'json',//資料格式為jason
        success: function (data) {
            // 清空 id 為 demo 的元素內容
            // $("#demo").empty();

            // 遍歷 data 並將 email 附加到 id 為 demo 的元素內
            // $.each(data, function (index, item) {
            //     $("#adminId").append(item.email + "<br>");
            // });

            // $("#adminId").html(data.id); // 获取输入框的值
            // $("#floatingInput").html(data.name); // 获取输入框的值
            // $("#floatingAccount").html(data.account); // 获取输入框的值
            // $("#floatingPassword").html(data.password); // 获取输入框的值
            // $("#createdTime").html(data.createdTime); // 获取输入框的值
            // $("#productNo").html(data.productNo); // 获取输入框的值

            $.each(data,function(key,item){
                if(item.total!==0) {
                    $('#admins').append(`
                        <tr id="admins">
                            <th scope="row" type="text" id="adminId">${item.id}</th>
                            <td type="text" id="floatingInput">${item.name}</td>
                            <td type="text" id="floatingAccount">${item.account}</td>
                            <td type="text" id="floatingPassword">${item.password}</td>
                            <td type="text" id="createdTime">${item.createdTime}</td>
                            <td type="text" id="productNo">${item.productNo}</td>
<!--                            <td type="text" class="form-control" id=" ">Member</td>-->
                        </tr>`);

            }
            });

        },

    })
});