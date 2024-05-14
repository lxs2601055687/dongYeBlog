/**
 * 系统更新日志 api 封装
 *
 * @Author:    卓大
 * @Date:      2022-09-26 14:53:50
 * @Copyright  1024创新实验室
 */
import { postRequest, getRequest } from '/src/lib/axios';
const prefix = '/admin';
export const changeLogApi = {
  /**
   * 分页查询  @author  卓大
   */
  queryPage: (param) => {
    return postRequest(prefix+'/support/changeLog/queryPage', param);
  },

  /**
   * 增加  @author  卓大
   */
  add: (param) => {
    return postRequest(prefix+'/support/changeLog/add', param);
  },

  /**
   * 修改  @author  卓大
   */
  update: (param) => {
    return postRequest(prefix+'/support/changeLog/update', param);
  },

  /**
   * 删除  @author  卓大
   */
  delete: (id) => {
    return getRequest(prefix+`/support/changeLog/delete/${id}`);
  },

  /**
   * 批量删除  @author  卓大
   */
  batchDelete: (idList) => {
    return postRequest(prefix+'/support/changeLog/batchDelete', idList);
  },
};
