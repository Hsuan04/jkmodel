$(document).ready(function () {
    "use strict";

    $.ajax({
        url: `http://localhost:8080/findAll`,//資料請求網址${}為jquery取參數的寫法
        type: 'GET',//請求的方法
        // data: JSON.stringify(userData),//物件的資料轉為string(裡面的userData需要符合對應的Dto資料規格ex:name, password)
        contentType: 'application/json',  // 向後端通知 Content-Type 為 JSON
        dataType: 'json',//資料格式為jason
        success: function (data) {
            $.each(data,function(key,item){
                if(item.total!==0) {
                    $('#admins').append(`
                        <tr id="admins">
                            <th scope="row" type="text" id="admId">${item.admId}</th>
                            <td type="text" id="floatingInput">${item.name}</td>
                            <td type="text" id="floatingAccount">${item.account}</td>
                            <td type="text" id="floatingPassword">${item.password}</td>
                            <td type="text" id="createdTime">${item.createdTime}</td>
                            <td type="text" id="productNo">${item.productNo}</td>
                            <td type="button" class="btn btn-primary update" id=${item.admId} style="background-color:#009CFF">修改</td>
                            <td type="button" class="btn btn-primary delete" id=${item.admId} style="background-color:#009CFF;">確定刪除</td>
                        </tr>`);
                }
            });
        },
    })
});

$(document).ready(function () {
    "use strict";
    // 事件委派：當點擊表格中擁有 'delete' 類別的 td 元素時觸發
    $(document).on("click", "td.update", function () {
        // 獲取點擊的 td 元素的 id 屬性值
        var admId = $(this).attr('id');
        // 從行中獲取 admid 的值
        console.log(admId)
        window.location.href = `./update.html?adminId=${admId}`;
    })
});