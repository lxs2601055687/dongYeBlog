package cn.bitoffer.lottery.check;

import cn.bitoffer.lottery.common.ErrorCode;
import cn.bitoffer.lottery.model.LotteryResult;
import cn.bitoffer.lottery.service.impl.LotteryServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class IpLimitCheck extends LotteryServiceImpl2 implements LotteryCheckStrategy {
    @Override
    public LotteryResult check(Long userID, String userName, String ip, Date now) {
        LotteryResult result = new LotteryResult();
        if (!checkIpLimit(ip)) {
            log.info("lotteryV3|checkIpLimit failed，ip：{}", ip);
            result.setErrcode(ErrorCode.ERR_IP_LIMIT_INVALID);
            result.setCheckResult(false);
        } else {
            result.setCheckResult(true);
        }
        return result;
    }
}
