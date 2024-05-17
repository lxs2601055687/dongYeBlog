package cn.bitoffer.lottery.prize.dao;

import cn.bitoffer.lottery.prize.domain.entity.PrizeEntity;
import cn.bitoffer.lottery.prize.domain.form.PrizeQueryForm;
import cn.bitoffer.lottery.prize.domain.vo.PrizeVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 奖品表 Dao
 *
 * @Author 李祥生
 * @Date 2024-05-16 16:35:02
 * @Copyright niit-阿升
 */

@Mapper
@Component
public interface PrizeDao extends BaseMapper<PrizeEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<PrizeVO> queryPage(Page page, @Param("queryForm") PrizeQueryForm queryForm);
    PrizeEntity get(@Param("id") int id);

    ArrayList<PrizeEntity> getAll();

    void save(@Param("prize") PrizeEntity prize);

    void saveInBatches(@Param("prizeList") ArrayList<PrizeEntity> prizeList);

    void deleteAll();

    void updatePrizePlan(@Param("id") Long id, @Param("prizePlan") String prizePlan);

    void updatePrizePlanWithTime(@Param("id") Long id, @Param("prizePlan") String prizePlan, @Param("prizeBegin") Date prizeBegin, @Param("prizeEnd") Date prizeEnd);

    ArrayList<PrizeEntity> getAllUsefulPrizeList(@Param("now") Date now);

    int decrLeftNum(@Param("id") Long id,@Param("num")int num);


}
