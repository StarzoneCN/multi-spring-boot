package com.example.demo.multi.springBoot.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.multi.springBoot.entity.ProbabilityRank;
import com.example.demo.multi.springBoot.entity.Ssq;
import com.example.demo.multi.springBoot.mapper.ProbabilityRankMapper;
import com.example.demo.multi.springBoot.service.IProbabilityRankService;
import com.example.demo.multi.springBoot.service.ISsqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * <p>
 * 概率排序 服务实现类
 * </p>
 *
 * @author LiHongxing
 * @since 2018-08-08
 */
@Service
public class ProbabilityRankServiceImpl extends ServiceImpl<ProbabilityRankMapper, ProbabilityRank> implements IProbabilityRankService {

    @Autowired
    private ISsqService ssqService;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private ProbabilityRankMapper probabilityRankMapper;

    /**
     * 初始化probability_rank表
     */
    @Override
    public void initPr() {
        List<Ssq> ssqs = ssqService.selectList(null);
        Map<String, Map<Integer, Integer>> timesCodeOpen = new HashMap<String, Map<Integer, Integer>>() {{
            this.put("red", new TreeMap<>());
            this.put("blue", new TreeMap<>());
        }};
        assembleDataAndSave(ssqs, timesCodeOpen);

    }


    private void assembleDataAndSave(List<Ssq> ssqs, Map<String, Map<Integer, Integer>> timesCodeOpen) {
        List<ProbabilityRank> prs = new ArrayList<>();
        /*处理times_code_opened字段*/
        ssqs.stream().forEach(ssq -> {
            ProbabilityRank probabilityRank = new ProbabilityRank();
            probabilityRank.setSsqId(ssq.getId());
            Map<Integer, Integer> redMap = timesCodeOpen.get("red");
            redMap.put(ssq.getR0(), redMap.get(ssq.getR0()) == null ? 1 : redMap.get(ssq.getR0()) + 1);
            redMap.put(ssq.getR1(), redMap.get(ssq.getR1()) == null ? 1 : redMap.get(ssq.getR1()) + 1);
            redMap.put(ssq.getR2(), redMap.get(ssq.getR2()) == null ? 1 : redMap.get(ssq.getR2()) + 1);
            redMap.put(ssq.getR3(), redMap.get(ssq.getR3()) == null ? 1 : redMap.get(ssq.getR3()) + 1);
            redMap.put(ssq.getR4(), redMap.get(ssq.getR4()) == null ? 1 : redMap.get(ssq.getR4()) + 1);
            redMap.put(ssq.getR5(), redMap.get(ssq.getR5()) == null ? 1 : redMap.get(ssq.getR5()) + 1);

            Map<Integer, Integer> blueMap = timesCodeOpen.get("blue");
            blueMap.put(ssq.getB0(), blueMap.get(ssq.getB0()) == null ? 1 : blueMap.get(ssq.getB0()) + 1);

            probabilityRank.setTimesCodeOpened(JSONUtil.toJsonStr(timesCodeOpen));

            /*处理code_sort字段*/
            List<Integer> redTimesSortedList = redMap.entrySet().stream()
                    .map(entry -> entry.getValue())
                    .sorted(Comparator.reverseOrder())
                    .collect(toList());
            List<String> redCodeOrderList = new ArrayList<>();
            redMap.keySet().stream().forEach(key -> {
                redCodeOrderList.add("#" + key + " - (" + (redTimesSortedList.indexOf(redMap.get(key)) + 1) + ")");
            });
            List<Integer> blueTimesSortedList = blueMap.entrySet().stream()
                    .map(entry -> entry.getValue())
                    .sorted(Comparator.reverseOrder())
                    .collect(toList());
            List<String> blueCodeOrderList = new ArrayList<>();
            blueMap.keySet().stream().forEach(key -> {
                blueCodeOrderList.add("#" + key + " - (" + (redTimesSortedList.indexOf(redMap.get(key)) + 1) + ")");
            });
            Map<String, List<String>> codeSortResult = new HashMap<>();
            codeSortResult.put("red", redCodeOrderList);
            codeSortResult.put("blue", blueCodeOrderList);
            probabilityRank.setCodeSort(JSONUtil.toJsonStr(codeSortResult));

            /*处理ranking字段*/
            double sum = redTimesSortedList.stream().collect(summingDouble(t -> (double) t));
            Map<String, List<String>> rankingResult = new HashMap<>();
            rankingResult.put("red", redMap.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue())
                    .map(entry -> NumberUtil.formatPercent(entry.getValue() / sum, 2) + " - (" + entry.getKey() + ")")
                    .collect(toList()));
            rankingResult.put("blue", blueMap.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue())
                    .map(entry -> NumberUtil.formatPercent(entry.getValue() / sum, 2) + " - (" + entry.getKey() + ")")
                    .collect(toList()));
            probabilityRank.setRanking(JSONUtil.toJsonStr(rankingResult));

            /*处理open_code_sort字段*/
            int sortSum = 0;
            StringBuilder openCodeSortSb = new StringBuilder();
            int r0Sort = redTimesSortedList.indexOf(redMap.get(ssq.getR0())) + 1;
            openCodeSortSb.append(r0Sort);
            openCodeSortSb.append("-");
            int r1Sort = redTimesSortedList.indexOf(redMap.get(ssq.getR1())) + 1;
            openCodeSortSb.append(r1Sort);
            openCodeSortSb.append("-");
            int r2Sort = redTimesSortedList.indexOf(redMap.get(ssq.getR2())) + 1;
            openCodeSortSb.append(r2Sort);
            openCodeSortSb.append("-");
            int r3Sort = redTimesSortedList.indexOf(redMap.get(ssq.getR3())) + 1;
            openCodeSortSb.append(r3Sort);
            openCodeSortSb.append("-");
            int r4Sort = redTimesSortedList.indexOf(redMap.get(ssq.getR4())) + 1;
            openCodeSortSb.append(r4Sort);
            openCodeSortSb.append("-");
            int r5Sort = redTimesSortedList.indexOf(redMap.get(ssq.getR5())) + 1;
            openCodeSortSb.append(r5Sort);
            sortSum += r0Sort + r1Sort + r2Sort + r3Sort + r4Sort + r5Sort;
            openCodeSortSb.append("+");
            openCodeSortSb.append(blueTimesSortedList.indexOf(blueMap.get(ssq.getB0())) + 1);
            probabilityRank.setOpenCodeSort(openCodeSortSb.toString());

            /*处理sort_sum字段*/
            probabilityRank.setSortSum(sortSum);

            /*处理update_time字段*/
            probabilityRank.setUpdateTime(new Date());

            prs.add(probabilityRank);
        });
        insertOrUpdateBatch(prs);
    }

    /**
     * 更新probability_rank表
     */
    @Transactional
    @Override
    public void updatePr() {
        String maxSsqId = queryMaxSsqId();
        ProbabilityRank latestPr = selectById(maxSsqId);
        Condition condition = new Condition();
        condition.gt("id", maxSsqId);
        condition.orderBy("id", true);
        List ssqs = ssqService.selectList(condition);
        JSONObject jsonObject = JSONUtil.parseObj(latestPr.getTimesCodeOpened());
        Map<String, Map<Integer, Integer>> timesCodeOpen = new HashMap<String, Map<Integer, Integer>>(){{
            this.put("red", new TreeMap());
            this.put("blue", new TreeMap());
        }};
        jsonObject.keySet().stream().forEach(k -> {
            JSONObject subJsonObj = (JSONObject)jsonObject.get(k);
            subJsonObj.keySet().stream().forEach(key -> {
                timesCodeOpen.get(k).put(Integer.parseInt(key.toString()), (Integer)subJsonObj.get(key));
            });
        });
        assembleDataAndSave(ssqs, timesCodeOpen);

    }

    /**
     * 获取最大ssqId
     * @return
     */
    @Override
    public String queryMaxSsqId() {
        return probabilityRankMapper.queryMaxId();
    }
}
