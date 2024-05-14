/*
 * @Description:
 * @Author: zhuoda
 * @Date: 2021-11-05
 * @LastEditTime: 2022-06-23
 * @LastEditors: zhuoda
 */
import {postRequest, getRequest, getDownload} from '/@/lib/axios';
const prefix = '/admin';
export const goodsApi = {
  // 添加商品 @author zhuoda
  addGoods: (param) => {
    return postRequest(prefix+'/goods/add', param);
  },
  // 删除 @author zhuoda
  deleteGoods: (goodsId) => {
    return getRequest(prefix+`/goods/delete/${goodsId}`);
  },
  // 批量 @author zhuoda
  batchDelete: (goodsIdList) => {
    return postRequest(prefix+'/goods/batchDelete', goodsIdList);
  },
  // 分页查询 @author zhuoda
  queryGoodsList: (param) => {
    return postRequest(prefix+'/goods/query', param);
  },
  // 更新商品 @author zhuoda
  updateGoods: (param) => {
    return postRequest(prefix+'/goods/update', param);
  },

  // 导入 @author 卓大
  importGoods : (file) =>{
    return postRequest(prefix+'/goods/importGoods',file);
  },

  // 导出 @author 卓大
  exportGoods : () =>{
    return getDownload(prefix+'/goods/exportGoods');
  }
};
