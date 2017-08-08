/**
 * Created by zcw on 2017/8/8.
 */

let loadData = (data) => {
    $('#tt').datagrid({
        title: '动态路由',
        iconCls: 'icon-edit',
        singleSelect: true,      //只允许选中一行
        fitColumns: true,       //自适应宽度
        rownumbers: true,       //显示行号
        //idField:'itemid',     //指示标识字段
        //url: 'datagrid_data.json',
        loadMsg: "正在加载",     //加载数据时提示信息
        queryParams: {
            page: 0,
            size: 20
        },
        columns: [[
            {field: 'apiName', title: 'Api名'},
            {field: 'enabled', title: '是否可用'},
            {field: 'memo', title: '备注'},
            {field: 'path', title: '路径'},
            {field: 'url', title: 'url'},
            {field: 'stripPrefix', title: '去除前缀'}
        ]],
        data: data
    });
};

let getData = (page, size) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `/route/page?page=${page}&size=${size}`,
            success: (data) => {
                resolve(data);
            },
            error: (data) => {
                reject(data)
            }
        });
    });
};

$(function () {
    getData(0, 20).then(data => {
        loadData(data.data.content);
    });
});
//http://www.jeasyui.net/tutorial/36.html