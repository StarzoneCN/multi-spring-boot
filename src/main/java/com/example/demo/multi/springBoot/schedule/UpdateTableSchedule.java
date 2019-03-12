package com.example.demo.multi.springBoot.schedule;

import com.example.demo.multi.springBoot.service.IProbabilityRankService;
import com.example.demo.multi.springBoot.service.ISsqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时更新表格数据
 *
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/2/21 16:11
 * @modefied by
 */
@Component
public class UpdateTableSchedule {

    @Autowired
    private ISsqService ssqService;
    @Autowired
    private IProbabilityRankService probabilityRankService;

    /**
     * 每月1号触发，因为体彩中心的接口最多可查询前20期的数据
     */
    @Transactional
    @Scheduled(cron = "0 18 21 ? * 2,4,7")
    public void updateData(){
        ssqService.updateData();
        probabilityRankService.updatePr();
    }
}
