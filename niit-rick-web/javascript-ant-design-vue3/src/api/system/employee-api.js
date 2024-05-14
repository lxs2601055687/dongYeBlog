/*
 *  员工
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 21:59:15
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import { getRequest, postRequest } from '/src/lib/axios';
const prefix = '/admin';
export const employeeApi = {
  /**
   * 查询所有员工 @author 卓大
   */
  queryAll: () => {
    return getRequest(prefix+'/employee/queryAll');
  },
  /**
   * 员工管理查询
   */
  queryEmployee: (params) => {
    return postRequest(prefix+'/employee/query', params);
  },
  /**
   * 添加员工
   */
  addEmployee: (params) => {
    return postRequest(prefix+'/employee/add', params);
  },
  /**
   * 更新员工信息
   */
  updateEmployee: (params) => {
    return postRequest(prefix+'/employee/update', params);
  },
  /**
   * 删除员工
   */
  deleteEmployee: (employeeId) => {
    return getRequest(prefix+`/employee/delete/${employeeId}`);
  },
  /**
   * 批量删除员工
   */
  batchDeleteEmployee: (employeeIdList) => {
    return postRequest(prefix+'/employee/update/batch/delete', employeeIdList);
  },
  /**
   * 批量调整员工部门
   */
  batchUpdateDepartmentEmployee: (updateParam) => {
    return postRequest(prefix+'/employee/update/batch/department', updateParam);
  },
  /**
   * 重置员工密码
   */
  resetPassword: (employeeId) => {
    return getRequest(prefix+`/employee/update/password/reset/${employeeId}`);
  },
  /**
   * 修改面面
   */
  updateEmployeePassword: (param) => {
    return postRequest(prefix+'/employee/update/password', param);
  },
  /**
   * 更新员工禁用状态
   */
  updateDisabled: (employeeId) => {
    return getRequest(prefix+`/employee/update/disabled/${employeeId}`);
  },

  /**
   * 查询员工-根据部门id
   */
  queryEmployeeByDeptId: (departmentId) => {
    return getRequest(prefix+`/employee/query/dept/${departmentId}`);
  },
};
