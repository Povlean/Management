import request from '@/utils/request'

export default {
  getRoleList(searchModel) {
    return request({
      url: '/role/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        roleName: searchModel.roleName
      }
    })
  },
  addRole(role) {
    return request({
      url: '/role/add',
      method: 'post',
      data: role
    })
  },
  saveRole(role) {
    if (role.roleId == null && role.roleId === undefined) {
      return this.addRole(role)
    }
    return this.updateRole(role)
  },
  getRoleById(id) {
    return request({
      url: `/role/get/${id}`,
      method: 'get'
    })
  },
  updateRole(role) {
    return request({
      url: '/role/update',
      method: 'put',
      data: role
    })
  },
  deleteRoleById(id) {
    return request({
      url: `/role/${id}`,
      method: 'delete'
    })
  }
}
