let vue = new Vue({
    el: "#vue-app",
    data: {
        resp: {},
        modalData: {},
        pageData: {
            curPage: 0,
            pageSize: 100,
            totalCount: 0,
            totalPages: 0
        }
    },
    methods: {
        getData(page, size) {
            if (size == null || size < 0) {
                size = vue.pageData.pageSize;
            }
            $.ajax({
                url: `../route/page?page=${page}&size=${size}`,
                success: function (resp) {
                    let data = resp.data;
                    vue.resp = data;
                    vue.pageData.curPage = data.number;
                    vue.pageData.pageSize = data.size;
                    vue.pageData.totalCount = data.totalElements;
                    vue.pageData.totalPages = data.totalPages;
                }
            });
        },
        //弹窗，新增或编辑
        showModal(index) {
            vue.modalData = {};
            if (index >= 0) {
                vue.modalData = vue.resp.content[index];
            }
            $('#myModal').modal({
                keyboard: true
            })
        },
        //刷新
        refresh() {
            $.ajax({
                url: `/route/refresh`,
                success: () => {
                    vue.getData(0, 100);
                }
            });
        },
        //提交
        submit() {
            $.ajax({
                url: `/route/save`,
                type: 'post',
                data: JSON.stringify(vue.modalData),
                contentType: "application/json",
                dataType: 'json',
                success: () => {
                    vue.getData(0, 100);
                    $('#myModal').modal('hide')
                }
            });
        },
        //删除
        remove(id) {
            $.ajax({
                url: `/route/delete?id=${id}`,
                type: 'delete',
                contentType: "application/json",
                dataType: 'json',
                success: () => {
                    vue.getData(0, 100);
                }
            });
        }
    }
});
vue.getData(0, 100);