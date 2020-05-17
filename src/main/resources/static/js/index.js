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
        getAjxx: function (event) {

            Artery.ajax.get('/index/getAjxx', {}).then(function (result) {
                console.log(result);
            });
        },
        /**
         *
         * @param event click事件
         */
        getXyrxx : function (event) {
            Artery.ajax.get('/index/getXyrxx', {}).then(function (result) {
                console.log(result);
            });
        }
    }
})