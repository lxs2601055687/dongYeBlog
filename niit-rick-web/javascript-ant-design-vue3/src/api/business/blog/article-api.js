/**
 * 文章管理 api 封装
 *
 * @Author:    李祥生
 * @Date:      2024-05-22 15:28:58
 * @Copyright  niit-阿升
 */
import { postRequest, getRequest } from '/@/lib/axios';
const prefix = '/blog';
export const articleApi = {

  /**
   * 分页查询  @author  李祥生
   */
  queryPage : (param) => {
    return postRequest(prefix+'/article/queryPage', param);
  },

  /**
   * 增加  @author  李祥生
   */
  add: (param) => {
      return postRequest(prefix+'/article/add', param);
  },

  /**
   * 修改  @author  李祥生
   */
  update: (param) => {
      return postRequest(prefix+'/article/update', param);
  },


  /**
   * 删除  @author  李祥生
   */
  delete: (id) => {
      return getRequest(prefix+`/article/delete/${id}`);
  },

  /**
   * 批量删除  @author  李祥生
   */
  batchDelete: (idList) => {
      return postRequest(prefix+'/article/batchDelete', idList);
  },

};
