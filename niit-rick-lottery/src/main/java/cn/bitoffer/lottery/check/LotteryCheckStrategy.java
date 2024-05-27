package cn.bitoffer.lottery.check;

import cn.bitoffer.lottery.model.CheckResult;
import cn.bitoffer.lottery.model.LotteryResult;

import java.text.ParseException;
import java.util.Date;

public interface LotteryCheckStrategy {
    LotteryResult check(Long userID, String userName, String ip, Date now) throws ParseException;
}
