package cn.bitoffer.lottery.prize.service;

import cn.bitoffer.lottery.prize.dao.PrizeDao;
import cn.bitoffer.lottery.prize.domain.entity.PrizeEntity;
import cn.bitoffer.lottery.prize.domain.form.PrizeAddForm;
import cn.bitoffer.lottery.prize.domain.form.PrizeQueryForm;
import cn.bitoffer.lottery.prize.domain.form.PrizeUpdateForm;
import cn.bitoffer.lottery.prize.domain.vo.PrizeVO;

import java.util.Date;
import java.util.List;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 奖品表 Service
 *
 * @Author 李祥生
 * @Date 2024-05-16 16:35:02
 * @Copyright niit-阿升
 */

@Service
public class PrizeService {

    @Resource
    private PrizeDao prizeDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<PrizeVO> queryPage(PrizeQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PrizeVO> list = prizeDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(PrizeAddForm addForm) {
        PrizeEntity prizeEntity = SmartBeanUtil.copy(addForm, PrizeEntity.class);
        prizeEntity.setPrizePlan("");
        //根据周期和开始时间计算结束时间
        Integer prizeTime = prizeEntity.getPrizeTime();
        Date beginTime = prizeEntity.getBeginTime();
        //计算结束时间
        Date endTime = new Date(beginTime.getTime() + prizeTime * 24 * 60 * 60 * 1000);
        prizeEntity.setEndTime(endTime);
        prizeDao.insert(prizeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(PrizeUpdateForm updateForm) {
        PrizeEntity prizeEntity = SmartBeanUtil.copy(updateForm, PrizeEntity.class);
        prizeDao.updateById(prizeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        prizeDao.deleteBatchIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Integer id) {
        if (null == id){
            return ResponseDTO.ok();
        }

        prizeDao.deleteById(id);
        return ResponseDTO.ok();
    }
}
