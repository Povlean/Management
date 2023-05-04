import request from '@/utils/request'

export default {
  getAllMenu() {
    return request({
      url: '/menu/list',
      method: 'get'
    })
  }
}
