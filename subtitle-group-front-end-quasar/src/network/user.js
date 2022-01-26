export function getUserList(pertain) {
    return this.$api({
        url: '/userlist'+pertain
    })
}