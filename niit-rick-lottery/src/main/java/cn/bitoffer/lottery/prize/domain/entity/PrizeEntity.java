package cn.bitoffer.lottery.prize.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 奖品表 实体类
 *
 * @Author 李祥生
 * @Date 2024-05-16 16:35:02
 * @Copyright niit-阿升
 */

@Data
@TableName("t_prize")
public class PrizeEntity {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 奖品名称
     */
    private String title;

    /**
     * 奖品数量
     */
    private Integer prizeNum;

    /**
     * 剩余数量
     */
    private Integer leftNum;

    /**
     * 0-9999表示100%，0-0表示万分之一的中奖概率
     */
    private String prizeCode;

    /**
     * 发奖周期
     */
    private Integer prizeTime;

    /**
     * 奖品图片
     */
    private String img;

    /**
     * 位置序号
     */
    private Integer displayOrder;

    /**
     * 奖品类型
     */
    private Integer prizeType;

    /**
     * 奖品扩展数据
     */
    private String prizeProfile;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 发奖计划
     */
    private String prizePlan;

    /**
     * 发奖计划周期的开始
     */
    private Date prizeBegin;

    /**
     * 发奖计划周期的结束
     */
    private Date prizeEnd;

    /**
     * 状态
     */
    private Integer sysStatus;

    /**
     * 创建时间
     */
    private Date sysCreated;

    /**
     * 修改时间
     */
    private Date sysUpdated;

    /**
     * 操作人IP
     */
    private String sysIp;

}