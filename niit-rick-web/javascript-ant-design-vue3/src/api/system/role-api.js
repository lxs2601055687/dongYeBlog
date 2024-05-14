/*
 * 角色
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 22:00:41
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */
import { getRequest, postRequest } from '/src/lib/axios';
const prefix = '/admin';
export const roleApi = {
  /**
   * @description: 获取所有角色
   */
  queryAll: () => {
    return getRequest(prefix+'/role/getAll');
  },
  /**
   * @description:添加角色
   */
  addRole: (data) => {
    return postRequest(prefix+'/role/add', data);
  },
  /**
   * @description:更新角色
   */
  updateRole: (data) => {
    return postRequest(prefix+'/role/update', data);
  },
  /**
   * @description: 删除角色
   */
  deleteRole: (roleId) => {
    return getRequest(prefix+`/role/delete/${roleId}`);
  },
  /**
   * @description: 批量设置某角色数据范围
   */
  updateDataScope: (data) => {
    return postRequest(prefix+'/role/dataScope/updateRoleDataScopeList', data);
  },
  /**
   * @description: 获取当前系统所配置的所有数据范围
   */
  getDataScopeList: () => {
    return getRequest(prefix+'/dataScope/list');
  },
  /**
   * @description: 获取某角色所设置的数据范围
   */
  getDataScopeByRoleId: (roleId) => {
    return getRequest(prefix+`/role/dataScope/getRoleDataScopeList/${roleId}`);
  },
  /**
   * @description: 获取角色成员-员工列表
   */
  queryRoleEmployee: (params) => {
    return postRequest(prefix+'/role/employee/queryEmployee', params);
  },
  /**
   * @description: 从角色成员列表中移除员工
   */
  deleteEmployeeRole: (employeeId, roleId) => {
    return getRequest(prefix+'/role/employee/removeEmployee?employeeId=' + employeeId + '&roleId=' + roleId);
  },
  /**
   * @description: 从角色成员列表中批量移除员工
   */
  batchRemoveRoleEmployee: (data) => {
    return postRequest(prefix+'/role/employee/batchRemoveRoleEmployee', data);
  },
  /**
   * @description: 根据角色id获取角色员工列表(无分页)
   */
  getRoleAllEmployee: (roleId) => {
    return getRequest(prefix+`/role/employee/getAllEmployeeByRoleId/${roleId}`);
  },
  /**
   * @description: 角色成员列表中批量添加员工
   */
  batchAddRoleEmployee: (data) => {
    return postRequest(prefix+'/role/employee/batchAddRoleEmployee', data);
  },
};
