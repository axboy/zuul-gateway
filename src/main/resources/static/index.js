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
        fit: true,
        queryParams: {
            page: 0,
            size: 20
        },
        columns: [[
            {
                field: 'apiName',
                title: 'Api名',
                width: '10%',
                editor: 'text'
            },
            {
                field: 'path',
                title: '路径',
                width: '10%',
                editor: 'text'
            },
            {
                field: 'url',
                title: 'url',
                width: '25%',
                editor: 'text'
            },
            {
                field: 'stripPrefix',
                title: '去除前缀',
                width: '10%',
                align: 'center',
                formatter: (value) => {
                    return value;
                },
                editor: {
                    type: 'combobox',
                    options: {
                        valueField: 'value',
                        textField: 'text',
                        data: [{text: "true", value: true}, {text: "false", value: false}],
                        required: true,
                        panelHeight: 'auto'
                    }
                }
            },
            // {
            //     field: 'enabled',
            //     title: '是否可用',
            //     width: '10%',
            //     align: 'center',
            //     editor: {
            //         //FIXME 如何默认选中
            //         type: 'checkbox',
            //         options: {
            //             on: true,
            //             off: false
            //         }
            //     }
            // },
            {
                field: 'memo',
                title: '备注',
                width: '20%',
                editor: 'text'
            },
            {
                field: 'action',
                title: '操作',
                width: "25%",
                align: 'center',
                formatter: (value, row, index) => {
                    if (row.editing) {
                        let s = '<a href="#" onclick="saverow(this)">Save</a> ';
                        let c = '<a href="#" onclick="cancelrow(this)">Cancel</a>';
                        return s + c;
                    } else {
                        let e = '<a href="#" onclick="editrow(this)">Edit</a> ';
                        let d = '<a href="#" onclick="deleterow(this)">Delete</a>';
                        return e + d;
                    }
                }
            }
        ]],
        data: data,
        onBeforeEdit: (index, row) => {
            console.log(`onBeforeEdit`);
            row.editing = true;
            updateActions(index);
        },
        onAfterEdit: (index, row) => {
            console.log(`onAfterEdit`);
            row.editing = false;
            updateActions(index);
        },
        onCancelEdit: (index, row) => {
            console.log(`onCancelEdit`);
            row.editing = false;
            updateActions(index);
        }
    });
};

//获得当前行的下标
let getRowIndex = (target) => {
    let tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
};

//打开编辑
let editrow = (target) => {
    $('#tt').datagrid('beginEdit', getRowIndex(target));
};

//删除
let deleterow = (target) => {
    $.messager.confirm('Confirm', 'Are you sure?', (r) => {
        if (r) {
            let index = getRowIndex(target);
            let row = $('#tt').datagrid('getData').rows[index];
            $('#tt').datagrid('deleteRow', index);
            if (!row.id) {
                //如果是新插入的，不存在该种情况，只有取消按钮，没有删除按钮
                return;
            }
            $.ajax({
                url: `/route/delete?id=${row.id}`,
                type: 'delete',
                contentType: "application/json",
                dataType: 'json',
                success: (data) => {
                    $('#tt').datagrid('endEdit', index);
                    getData(0, 20).then(data => {
                        loadData(data.data.content);
                    });
                }
            });
        }
    });
};

//保存
let saverow = (target) => {
    let index = getRowIndex(target);
    let row = $('#tt').datagrid('getData').rows[index];
    // 关闭编辑框
    $('#tt').datagrid('endEdit', index);
    //TODO 数据简单校验

    $.ajax({
        url: `/route/save`,
        type: 'post',
        data: JSON.stringify(row),
        contentType: "application/json",
        dataType: 'json',
        success: (data) => {
            getData(0, 20).then(data => {
                loadData(data.data.content);
            });
        }
    });
};

//取消编辑
let cancelrow = (target) => {
    let index = getRowIndex(target);
    let row = $('#tt').datagrid('getData').rows[index];
    if (!row.id) {
        //如果是新插入的，删除该行
        $('#tt').datagrid('deleteRow', index);
        return;
    }
    $('#tt').datagrid('cancelEdit', getRowIndex(target));
};

//插入一行
let insert = () => {
    // var row = $('#tt').datagrid('getSelected');
    // if (row) {
    //     var index = $('#tt').datagrid('getRowIndex', row);
    // } else {
    //     index = 0;
    // }
    let index = 0;
    $('#tt').datagrid('insertRow', {
        index: index,
        row: {
            status: 'P'
        }
    });
    $('#tt').datagrid('selectRow', index);
    $('#tt').datagrid('beginEdit', index);
};

let updateActions = (index) => {
    $('#tt').datagrid('updateRow', {
        index: index,
        row: {}     //FIXME 为什么是空
    });
};

//从后台获取分页数据
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

//刷新
let refresh = () => {
    $.ajax({
        url: `/route/refresh`,
        success: () => {
            getData(0, 20).then(data => {
                loadData(data.data.content);
            });
        }
    });
};

$(function () {
    getData(0, 20).then(data => {
        loadData(data.data.content);
    });
});
//http://www.jeasyui.net/tutorial/36.html