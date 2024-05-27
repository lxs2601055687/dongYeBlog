package cn.bitoffer.lottery.check;

import cn.bitoffer.lottery.common.ErrorCode;
import cn.bitoffer.lottery.model.CheckResult;
import cn.bitoffer.lottery.model.LotteryResult;
import cn.bitoffer.lottery.service.impl.LotteryServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@Slf4j
public class BlackIpCheck extends LotteryServiceImpl2 implements LotteryCheckStrategy {
    @Override
    public LotteryResult check(Long userID, String userName, String ip, Date now) throws ParseException {
        LotteryResult result = new LotteryResult();
        CheckResult checkResult = checkBlackIpWithCache(now, ip);
        if (!checkResult.isOk()) {
            log.info("lotteryV3|checkBlackIp failed，ip：{}", ip);
            result.setErrcode(ErrorCode.ERR_BLACK_IP);
            result.setCheckResult(false);
        } else {
            result.setCheckResult(true);
        }
        return result;
    }
}
