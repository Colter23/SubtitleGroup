export function getSheetData(path) {
    return this.$api({
        url: '/query'+path
    })
}