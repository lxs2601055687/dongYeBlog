package cn.bitoffer.lottery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public  class LotteryReq{
    @JsonProperty("userId")
    int userId;
    @JsonProperty("userName")
    String userName;
    @JsonProperty("ip")
    String ip;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
