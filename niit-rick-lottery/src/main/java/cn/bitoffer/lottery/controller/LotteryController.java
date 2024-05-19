package cn.bitoffer.lottery.controller;

import cn.bitoffer.lottery.common.ResponseEntity;
import cn.bitoffer.lottery.common.ResponseEnum;
import cn.bitoffer.lottery.dto.AddPrizeListReq;
import cn.bitoffer.lottery.dto.ImportCacheCouponReq;
import cn.bitoffer.lottery.dto.LotteryReq;
import cn.bitoffer.lottery.model.LotteryResult;
import cn.bitoffer.lottery.service.LotteryService;
import cn.bitoffer.lottery.service.impl.LotteryServiceImpl1;
import cn.bitoffer.lottery.service.impl.LotteryServiceImpl2;
import cn.bitoffer.lottery.service.impl.LotteryServiceImpl3;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/lottery")
@Slf4j
public class LotteryController {

    @Autowired
    private LotteryServiceImpl1 lotteryServiceImpl1;
    @Autowired
    private LotteryServiceImpl2 lotteryServiceImpl2;
    @Autowired
    private LotteryServiceImpl3 lotteryServiceImpl3;





    @PostMapping(value = "/v1/get_lucky", consumes = "application/json; charset=utf-8")
    public ResponseEntity<LotteryResult> lotteryV1( @RequestHeader("X-User-Id") Long tokenUserID,@RequestBody LotteryReq req) {
        LotteryResult lotteryResult = null;
        try {
            Long userID = null;
            if (tokenUserID != null) {
                userID = tokenUserID;
            }else {
                userID = new Long(req.getUserId());
            }
            String userName = req.getUserName();
            String ip = req.getIp();
            lotteryResult = lotteryServiceImpl1.lottery(userID, userName, ip);
        } catch (Exception e) {
            System.out.println("lottery err " + e.getMessage());
            return ResponseEntity.fail();
        }
        return ResponseEntity.resp(ResponseEnum.OK, lotteryResult);
    }

    @PostMapping(value = "/v2/get_lucky", consumes = "application/json; charset=utf-8")
    public ResponseEntity<LotteryResult> lotteryV2(@RequestHeader("X-User-Id") Long tokenUserID,@RequestBody LotteryReq req) {
        LotteryResult lotteryResult = null;
        try {
            Long userID = null;
            if (tokenUserID != null) {
                userID = tokenUserID;
            }else {
                userID = new Long(req.getUserId());
            }
            String userName = req.getUserName();
            String ip = req.getIp();
            lotteryResult = lotteryServiceImpl2.lottery(userID, userName, ip);
        } catch (Exception e) {
            System.out.println("lottery err " + e.getMessage());
            return ResponseEntity.fail();
        }
        return ResponseEntity.resp(ResponseEnum.OK, lotteryResult);
    }

    @PostMapping(value = "/v3/get_lucky", consumes = "application/json; charset=utf-8")
    public ResponseEntity<LotteryResult> lotteryV3(@RequestBody LotteryReq req, HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        LotteryResult lotteryResult = null;
        try {
             int userID = req.getUserId();
            String userName = req.getUserName();
            lotteryResult =  lotteryServiceImpl3.lottery((long) userID, userName, ip);

        } catch (Exception e) {
            System.out.println("lottery err " + e.getMessage());
            return ResponseEntity.fail();
        }
        return ResponseEntity.resp(ResponseEnum.OK, lotteryResult);
    }

    // 新增奖品列表
    @PostMapping(value = "/add_prize_list", consumes = "application/json; charset=utf-8")
    public ResponseEntity<String> addPrizeList(@RequestBody AddPrizeListReq req) {
        Long userId = (long) req.getUserId();
        if (userId <= 0) {
            return ResponseEntity.fail();
        }
        lotteryServiceImpl1.addPrizeList(req.getViewPrizeList());
        return ResponseEntity.ok();
    }

    @PostMapping(value = "/import_coupon_cache", consumes = "application/json; charset=utf-8")
    public ResponseEntity<String> ImportCacheCoupon(@RequestBody ImportCacheCouponReq req)  {
        Long userId = (long) req.getUserId();
        if (userId <= 0) {
            return ResponseEntity.fail();
        }
        String code = req.getViewCoupon().getCode();
        Long prizeId = (long) req.getViewCoupon().getPrizeId();
        try {
            lotteryServiceImpl2.importCouponWithCache(prizeId, code);
        } catch (Exception e) {
            log.info("import_coupon_cache err " + e.getMessage());
            return ResponseEntity.fail();
        }
        return ResponseEntity.ok();
    }

}