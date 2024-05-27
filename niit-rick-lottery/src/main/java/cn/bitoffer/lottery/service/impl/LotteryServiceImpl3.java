package cn.bitoffer.lottery.service.impl;

import cn.bitoffer.lottery.check.LotteryCheckStrategy;
import cn.bitoffer.lottery.common.ErrorCode;
import cn.bitoffer.lottery.constant.Constants;
import cn.bitoffer.lottery.model.LotteryPrizeInfo;
import cn.bitoffer.lottery.model.LotteryResult;
import cn.bitoffer.lottery.model.LotteryUserInfo;
import cn.bitoffer.lottery.prize.dao.PrizeDao;
import cn.bitoffer.lottery.service.LotteryService;
import cn.bitoffer.lottery.utils.UtilTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class LotteryServiceImpl3 extends LotteryServiceImpl2 implements LotteryService {

    @Autowired
    private PrizeDao prizeDao;

    @Autowired
    private List<LotteryCheckStrategy> checkStrategies;

    public LotteryResult lottery(Long userID, String userName, String ip) throws ParseException {
        LotteryResult lotteryResult = new LotteryResult();
        lotteryResult.setUserId(userID);
        String lockKey = String.format(Constants.lotteryLockKeyPrefix + "%d", userID);
        RLock lock = super.redissonClient.getLock(lockKey);

        if (lock.tryLock()) {
            log.info("get lock success!!!!!!!!");
            try {
                getLuckyV3(lotteryResult, userID, userName, ip);
            } finally {
                lock.unlock();
            }
        }

        System.out.println("lotteryResult=" + JSON.toJSONString(lotteryResult, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
        return lotteryResult;
    }

    public void getLuckyV3(LotteryResult lotteryResult, Long userID, String userName, String ip) throws ParseException {
        Date now = new Date();

        for (LotteryCheckStrategy strategy : checkStrategies) {
            LotteryResult checkResult = strategy.check(userID, userName, ip, now);
            if (!checkResult.getCheckResult()) {
                lotteryResult.setErrcode(checkResult.getErrcode());
                return;
            }
        }

        int prizeCode = UtilTools.getRandom(Constants.prizeCodeMax);
        log.info("lotteryV1|prizeCode===={}", prizeCode);
        LotteryPrizeInfo prize = getPrizeWithCache(now, prizeCode);
        if (prize == null || prize.getPrizeNum() <= 0 || getPrizeWithPool(prize.getId()) <= 0 || !giveOutPrizeWithPool(prize.getId())) {
            lotteryResult.setErrcode(ErrorCode.ERR_NOT_WON);
            return;
        }

        if (prize.getPrizeType() == Constants.prizeTypeCouponDiff) {
            String code = prizeCodeDiff(prize.getId());
            if (code.isEmpty()) {
                log.info("lotteryV1|coupon code is empty: prize_id: {}", prize.getId());
                lotteryResult.setErrcode(ErrorCode.ERR_NOT_WON);
                return;
            }
            prize.setCouponCode(code);
        }

        lotteryResult.setErrcode(ErrorCode.SUCCESS);
        lotteryResult.setLotteryPrize(prize);

        logLotteryResult(prize, now, userID, ip, userName, prizeCode);

        if (prize.getPrizeType() == Constants.prizeTypeEntityLarge) {
            LotteryUserInfo lotteryUserInfo = new LotteryUserInfo();
            lotteryUserInfo.setUserId(userID);
            lotteryUserInfo.setUserName(userName);
            lotteryUserInfo.setIp(ip);
            prizeLargeBlackLimit(now, null, null, lotteryUserInfo);
        }
    }

    public int getPrizeWithPool(Long prizeId) {
        return cacheMgr.getPrizeNumByPool(prizeId);
    }

    public boolean giveOutPrizeWithPool(Long prizeId) {
        int cnt = cacheMgr.decrPrizeLeftNumByPool(prizeId);
        if (cnt < 0) {
            log.info("giveOutPrizeWithPool|prize_pool not enough.prize_id: {}", prizeId);
            return false;
        }
        return giveOutPrize(prizeId);
    }

    public void updatePrizePlanWithCache(Long prizeId, String prizePlan) {
        cacheMgr.updatePrizeByCache(prizeId);
        prizeDao.updatePrizePlan(prizeId, prizePlan);
    }

    public void updatePrizePlanAndTimeWithCache(Long prizeId, String prizePlan, Date prizeBegin, Date prizeEnd) {
        cacheMgr.updatePrizeByCache(prizeId);
        prizeDao.updatePrizePlanWithTime(prizeId, prizePlan, prizeBegin, prizeEnd);
    }
}
