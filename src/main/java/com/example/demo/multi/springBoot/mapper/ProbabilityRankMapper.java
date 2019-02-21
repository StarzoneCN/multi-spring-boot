package com.example.demo.multi.springBoot.mapper;

import com.example.demo.multi.springBoot.entity.ProbabilityRank;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 概率排序 Mapper 接口
 * </p>
 *
 * @author LiHongxing
 * @since 2018-08-08
 */
@Mapper
public interface ProbabilityRankMapper extends BaseMapper<ProbabilityRank> {

    String queryMaxId();
}
