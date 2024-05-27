package cn.bitoffer.lottery.model;

import cn.bitoffer.lottery.common.ErrorCode;
import lombok.Data;

@Data
public class LotteryResult {
    private ErrorCode errcode;
    private  Long userId;

    private Boolean checkResult;

    LotteryPrizeInfo lotteryPrize;

}
