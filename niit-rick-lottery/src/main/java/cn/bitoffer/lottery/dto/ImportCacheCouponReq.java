package cn.bitoffer.lottery.dto;

import cn.bitoffer.lottery.controller.ViewCoupon;

public  class ImportCacheCouponReq {
    int userId;
    ViewCoupon viewCoupon;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ViewCoupon getViewCoupon() {
        return viewCoupon;
    }

    public void setViewCoupon(ViewCoupon viewCoupon) {
        this.viewCoupon = viewCoupon;
    }
}