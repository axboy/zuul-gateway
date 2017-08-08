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
            {
                field: 'apiName',
                title: 'Api名',
                width:'10%'
            },
            {
                field: 'path',
                title: '路径',
                width:'10%'
            },
            {
                field: 'url',
                title: 'url',
                width:'20%'
            },
            {
                field: 'stripPrefix',
                title: '去除前缀',
                width: '10%',
                align: 'center',
                editor: {
                    type: 'checkbox',
                    options: {
                        on: true,
                        off: false
                    }
                }
            },
            {
                field: 'enabled',
                title: '是否可用',
                width: '10%',
                align: 'center',
                editor: {
                    type: 'checkbox',
                    options: {
                        on: true,
                        off: false
                    }
                }
            },
            {
                field: 'memo',
                title: '备注',
                width:'20%'
            },
            {
                field:'action',
                title:'操作',
                width:"20%",
                align:'center',
                formatter:(value,row,index)=>{
                    if (row.editing){
                        var s = '<a href="#" onclick="saverow(this)">Save</a> ';
                        var c = '<a href="#" onclick="cancelrow(this)">Cancel</a>';
                        return s+c;
                    } else {
                        var e = '<a href="#" onclick="editrow(this)">Edit</a> ';
                        var d = '<a href="#" onclick="deleterow(this)">Delete</a>';
                        return e+d;
                    }
                }
            }
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