import { boot } from 'quasar/wrappers'

// // "async" is optional;
// // more info on params: https://v2.quasar.dev/quasar-cli/boot-files
// export default boot(async (/* { app, router, ... } */) => {
//   // something to do
// })
const openSocket = (store) => {
    var socketUrl = "http://test.vividbtn.top:8081/websocket";
    console.log(socketUrl);
    store.commit('addSocket', new SockJS(socketUrl))

    //打开事件
    store.state.socket.onopen = function() {
        console.log("websocket已打开");
        //socket.send("这是来自客户端的消息" + location.href + new Date());
    };
    //获得消息事件
    // store.state.socket.onmessage = function(msg) {
    //     console.log(msg.data);
    //
    //
    // };
    //关闭事件
    store.state.socket.onclose = function() {
        console.log("websocket已关闭");
    };
    //发生了错误事件
    store.state.socket.onerror = function() {
        console.log("websocket发生了错误");
    };
}

const sendMessage = (store, message) => {
    store.state.socket.send(message);
}

export default boot(async ({ app }) => {
    app.config.globalProperties.$openSocket = openSocket
    app.config.globalProperties.$sendMessage = sendMessage
})