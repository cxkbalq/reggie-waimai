
//分页查询
let getmendianPage = (params) => {
    return $axios({
        url: '/mendian/page',
        method: 'get',
        params
    })
}

//状态更改
function enableOrDisableEmployee (params) {
    return $axios({
        url: '/mendian',
        method: 'put',
        data: { ...params }
    })
}

//信息回读
const querymendianById = (id) => {
    return $axios({
        url: `/mendian/${id}`,
        method: 'get'
    })
}
//获得创建人下的所有员工信息
let querysupersonList = (id) => {
    return $axios({
        url: `/mendian/supersonList/${id}`,
        method: 'get'
    })
}