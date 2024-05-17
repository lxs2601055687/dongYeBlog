package cn.bitoffer.lottery.prize.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 奖品表 更新表单
 *
 * @Author 李祥生
 * @Date 2024-05-16 16:35:02
 * @Copyright niit-阿升
 */

@Data
public class PrizeUpdateForm {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id 不能为空")
    private Integer id;

    @Schema(description = "奖品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "奖品名称 不能为空")
    private String title;

    @Schema(description = "奖品数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "奖品数量 不能为空")
    private Integer prizeNum;

    @Schema(description = "剩余数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "剩余数量 不能为空")
    private Integer leftNum;

    @Schema(description = "0-9999表示100%，0-0表示万分之一的中奖概率", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "0-9999表示100%，0-0表示万分之一的中奖概率 不能为空")
    private String prizeCode;

    @Schema(description = "发奖周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "发奖周期 不能为空")
    private Integer prizeTime;

    @Schema(description = "奖品图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "奖品图片 不能为空")
    private String img;

    @Schema(description = "位置序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "位置序号 不能为空")
    private Integer displayOrder;

    @Schema(description = "奖品类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "奖品类型 不能为空")
    private Integer prizeType;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开始时间 不能为空")
    private Date beginTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结束时间 不能为空")
    private Date endTime;

}