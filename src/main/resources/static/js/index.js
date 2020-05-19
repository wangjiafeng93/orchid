// **********************************************//
// index 客户端脚本
//
// @author huayu
// @date 2020-03-30
// **********************************************//

var vm = new Vue({
    el: '#app',
    mixins: [atyTplMixin],
    data: function () {
        return {}
    },
    methods: {
        /**
         *
         * @param event click事件
         */
        getOrchid: function (event) {
            Artery.ajax.get('index/getOrchid', {
                params: {
                    bh: 1,
                    name: 'zs'
                }
            }).then(function (result) {
                console.log(result);
            });
        }
    }
})