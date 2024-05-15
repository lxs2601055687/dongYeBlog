package cn.bitoffer.lottery.dto;

import cn.bitoffer.lottery.controller.ViewPrize;

import java.util.List;

public  class AddPrizeListReq {
    int userId;
    List<ViewPrize> viewPrizeList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ViewPrize> getViewPrizeList() {
        return viewPrizeList;
    }

    public void setViewPrizeList(List<ViewPrize> viewPrizeList) {
        this.viewPrizeList = viewPrizeList;
    }
}