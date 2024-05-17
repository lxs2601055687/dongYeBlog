package cn.bitoffer.lottery.prize.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 奖品表 列表VO
 *
 * @Author 李祥生
 * @Date 2024-05-16 16:35:02
 * @Copyright niit-阿升
 */

@Data
public class PrizeVO {


    @Schema(description = "id")
    private Integer id;

    @Schema(description = "奖品名称")
    private String title;

    @Schema(description = "奖品数量")
    private Integer prizeNum;

    @Schema(description = "剩余数量")
    private Integer leftNum;

    @Schema(description = "0-9999表示100%，0-0表示万分之一的中奖概率")
    private String prizeCode;

    @Schema(description = "发奖周期")
    private Integer prizeTime;

    @Schema(description = "位置序号")
    private Integer displayOrder;

    @Schema(description = "奖品类型")
    private Integer prizeType;

    @Schema(description = "开始时间")
    private LocalDateTime beginTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "发奖计划")
    private String prizePlan;

    @Schema(description = "状态")
    private Integer sysStatus;

    @Schema(description = "创建时间")
    private LocalDateTime sysCreated;

    @Schema(description = "修改时间")
    private LocalDateTime sysUpdated;

    @Schema(description = "操作人IP")
    private String sysIp;

    @Schema(description = "奖品图片")
    private String img;
}