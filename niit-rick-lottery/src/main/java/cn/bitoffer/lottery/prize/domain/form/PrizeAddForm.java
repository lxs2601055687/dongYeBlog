package cn.bitoffer.lottery.prize.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 奖品表 新建表单
 *
 * @Author 李祥生
 * @Date 2024-05-16 16:35:02
 * @Copyright niit-阿升
 */

@Data
public class PrizeAddForm {


    @Schema(description = "奖品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "奖品名称 不能为空")
    @JsonProperty("title")
    private String title;

    @Schema(description = "奖品数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "奖品数量 不能为空")
    @JsonProperty("prizeNum")
    private Integer prizeNum;

    @Schema(description = "剩余数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "剩余数量 不能为空")
    @JsonProperty("leftNum")
    private Integer leftNum;

    @Schema(description = "0-9999表示100%，0-0表示万分之一的中奖概率", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "0-9999表示100%，0-0表示万分之一的中奖概率 不能为空")
    @JsonProperty("prizeCode")
    private String prizeCode;

    @Schema(description = "发奖周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "发奖周期 不能为空")
    @JsonProperty("prizeTime")
    private Integer prizeTime;

    @Schema(description = "奖品图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "奖品图片 不能为空")
    @JsonProperty("img")
    private String img;

    @Schema(description = "位置序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "位置序号 不能为空")
    @JsonProperty("displayOrder")
    private Integer displayOrder;

    @Schema(description = "奖品类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "奖品类型 不能为空")
    @JsonProperty("prizeType")
    private Integer prizeType;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date beginTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date endTime;

    @Schema(description = "发奖计划周期的开始", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date prizeBegin;

    @Schema(description = "发奖计划周期的结束", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date prizeEnd;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date sysCreated;

    @Schema(description = "修改时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date sysUpdated;

}