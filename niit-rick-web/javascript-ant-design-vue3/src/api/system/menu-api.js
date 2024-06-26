/*
 * 菜单
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 22:00:32
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */
import { getRequest, postRequest } from '/src/lib/axios';
const prefix = '/admin';
export const menuApi = {
  /**
   * 添加菜单
   */
  addMenu: (param) => {
    return postRequest(prefix+'/menu/add', param);
  },

  /**
   * 更新菜单
   */
  updateMenu: (param) => {
    return postRequest(prefix+'/menu/update', param);
  },

  /**
   * 批量删除菜单
   */
  batchDeleteMenu: (menuIdList) => {
    return getRequest(prefix+`/menu/batchDelete?menuIdList=${menuIdList}`);
  },

  /**
   * 查询所有菜单列表
   */
  queryMenu: () => {
    return getRequest(prefix+'/menu/query');
  },

  /**
   * 查询菜单树
   */
  queryMenuTree: (onlyMenu) => {
    return getRequest(prefix+`/menu/tree?onlyMenu=${onlyMenu}`);
  },

  /**
   * 获取所有请求路径
   */
  getAuthUrl: () => {
    return getRequest(prefix+'/menu/auth/url');
  },
};
