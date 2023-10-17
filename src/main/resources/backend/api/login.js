function loginApi(data) {
  return $axios({
    'url': '/employee/login',
    'method': 'post',
    data
  })
}
function sendMsgApi(data) {
  return $axios({
    'url': '/user/sendMsg',
    'method': 'post',
    data
  })
}


function logoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
  })
}
