/**
 * 奖品表 api 封装
 *
 * @Author:    李祥生
 * @Date:      2024-05-16 16:35:02
 * @Copyright  niit-阿升
 */
import { postRequest, getRequest } from '/@/lib/axios';
const prefix = '/lottery';
export const prizeApi = {

  /**
   * 分页查询  @author  李祥生
   */
  queryPage : (param) => {
    return postRequest(prefix+'/prize/queryPage', param);
  },
  addPrize: (param) => {
    return postRequest(prefix+'/prize/add_prize_list', param);
  },
  /**
   * 增加  @author  李祥生
   */
  add: (param) => {
      return postRequest(prefix+'/prize/add', param);
  },

  /**
   * 修改  @author  李祥生
   */
  update: (param) => {
      return postRequest(prefix+'/prize/update', param);
  },


  /**
   * 删除  @author  李祥生
   */
  delete: (id) => {
      return getRequest(prefix+`/prize/delete/${id}`);
  },

  /**
   * 批量删除  @author  李祥生
   */
  batchDelete: (idList) => {
      return postRequest(prefix+'/prize/batchDelete', idList);
  },
  /**
   * 抽奖接口 @author 李祥生
   */
  getLucky:(param) => {
    return postRequest(prefix+'/v3/get_lucky',param)

}
};
