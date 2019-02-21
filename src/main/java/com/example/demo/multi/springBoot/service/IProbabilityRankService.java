package com.example.demo.multi.springBoot.service;

import com.example.demo.multi.springBoot.entity.ProbabilityRank;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 概率排序 服务类
 * </p>
 *
 * @author LiHongxing
 * @since 2018-08-08
 */
public interface IProbabilityRankService extends IService<ProbabilityRank> {

    void initPr();

    void updatePr();

    String queryMaxSsqId();
}
