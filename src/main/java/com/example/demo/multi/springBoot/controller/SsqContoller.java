package com.example.demo.multi.springBoot.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.demo.multi.springBoot.entity.ProbabilityRank;
import com.example.demo.multi.springBoot.entity.Ssq;
import com.example.demo.multi.springBoot.service.IProbabilityRankService;
import com.example.demo.multi.springBoot.service.ISsqService;
import com.example.demo.multi.springBoot.util.StringUtils;
import com.example.demo.multi.springBoot.vo.CodeSortedVo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/16 0:00
 * @modefied:
 */
@RestController
@RequestMapping("ssq")
public class SsqContoller {
    @Autowired
    private ISsqService iSsqService;
    @Autowired
    private IProbabilityRankService iProbabilityRankService;

    /**
     * 初始化数据，获取第1期到最新一期所有的开奖结果
     *
     * @return
     */
    @RequestMapping("init")
    public String initData() {
        iSsqService.initSsqData();
        return "success";
    }

    /**
     * 将所有期的号码拼接成r0-r1-r2-r3-r4-r5+b0形式，并保存到数据库
     *
     * @return
     */
    @RequestMapping("fs")
    public String fillStr() {
        List<Ssq> list = iSsqService.selectList(null);
        for (Ssq ssq : list) {
            ssq.setStr(StringUtils.assemblySsq2Str(ssq));
        }
        iSsqService.updateBatchById(list);
        return "success";
    }

    /**
     * 直接返回号码的排序 如： 1:20/7/11 表示：#20/#7/#11球出现了1次
     *
     * @return
     */
    @RequestMapping("nd")
    public Map<String, TreeMap<Integer, String>> neatenData() {
        List<Ssq> list = iSsqService.selectList(null);
        CodeSortedVo vo = sortCode(list);
        Map<String, TreeMap<Integer, String>> result = new HashMap<String, TreeMap<Integer, String>>() {{
            this.put("red", new TreeMap());
            this.put("blue", new TreeMap());
        }};
        vo.getRedSortedList().stream().forEach(entry -> {
            Map<Integer, String> redMap = result.get("red");
            if (redMap.get(entry.getValue()) == null)
                redMap.put(entry.getValue(), entry.getKey().toString());
            else
                redMap.put(entry.getValue(), redMap.get(entry.getValue()) + "/" + entry.getKey());

            Map<Integer, String> blueMap = result.get("blue");
            if (blueMap.get(entry.getValue()) == null)
                blueMap.put(entry.getValue(), entry.getKey().toString());
            else
                blueMap.put(entry.getValue(), blueMap.get(entry.getValue()) + "/" + entry.getKey());
        });
        return result;
    }

    /**
     * @return 每个号码的开出次数，如1:343-表示#1球开出343次；
     */
    @RequestMapping("count")
    @Deprecated
    public Map<String, Map<Integer, Integer>> count() {
        List<Ssq> list = iSsqService.selectList(null);
        HashMap<String, Map<Integer, Integer>> result = list.stream().reduce(new HashMap<String, Map<Integer, Integer>>() {{
            this.put("red", new HashMap<>());
            this.put("blue", new HashMap<>());
        }}, (m, ssq) -> {
            Map<Integer, Integer> redMap = m.get("red");
            Map<Integer, Integer> blueMap = m.get("blue");
            redMap.put(ssq.getR0(), redMap.get(ssq.getR0()) == null ? 1 : redMap.get(ssq.getR0()) + 1);
            redMap.put(ssq.getR1(), redMap.get(ssq.getR1()) == null ? 1 : redMap.get(ssq.getR1()) + 1);
            redMap.put(ssq.getR2(), redMap.get(ssq.getR2()) == null ? 1 : redMap.get(ssq.getR2()) + 1);
            redMap.put(ssq.getR3(), redMap.get(ssq.getR3()) == null ? 1 : redMap.get(ssq.getR3()) + 1);
            redMap.put(ssq.getR4(), redMap.get(ssq.getR4()) == null ? 1 : redMap.get(ssq.getR4()) + 1);
            redMap.put(ssq.getR5(), redMap.get(ssq.getR5()) == null ? 1 : redMap.get(ssq.getR5()) + 1);

            blueMap.put(ssq.getB0(), blueMap.get(ssq.getB0()) == null ? 1 : blueMap.get(ssq.getB0()) + 1);
            return m;
        }, (m1, m2) -> {
            m1.get("red").entrySet().stream().forEach((entry) -> {
                Map<Integer, Integer> red2 = m2.get("red");
                red2.put(entry.getKey(), red2.get(entry.getKey()) + entry.getValue());
            });
            m1.get("blue").entrySet().stream().forEach((entry) -> {
                Map<Integer, Integer> red2 = m2.get("blue");
                red2.put(entry.getKey(), red2.get(entry.getKey()) + entry.getValue());
            });
            return m2;
        });
        return result;
    }

    /**
     * 更新最新20条数据
     *
     * @return
     */
    @RequestMapping("update")
    public String updateData() {
        iSsqService.updateData();
        return "success";
    }

    /**
     * 排序处理，初始化ProbabilityRank表
     *
     * @param rowFrom 开始插入位置：ssq_id
     * @return
     */
    @RequestMapping("ipr/{from}")
    @Deprecated
    public String initProbabilityRank(@PathVariable("from") String rowFrom) {
        rowFrom = StringUtils.isNotBlank(rowFrom) ? rowFrom : "11118";
        List<Ssq> list = iSsqService.selectList(null);
        List<ProbabilityRank> pRanks = new ArrayList<>();
        CodeSortedVo vo;
        for (int i = 0; i < list.size(); i++) {
            Ssq ssq = list.get(i);
            ProbabilityRank pr = new ProbabilityRank();
            pr.setSsqId(ssq.getId());
            vo = sortCode(list.subList(0, i + 1));
            pr = setTimesCodeOpened(vo, pr);

            Map<Integer, Integer> rankingMapR = new TreeMap<>();
            Map<Integer, Integer> rankingMapB = new TreeMap<>();
            Map<Integer, Integer> codeSortMapR = new TreeMap<>();
            Map<Integer, Integer> codeSortMapB = new TreeMap<>();
            for (int j = 1; j <= vo.getRedSortedList().size(); j++) {
                if (j <= 16) {
                    rankingMapB.put(j, vo.getBlueSortedList().get(j - 1).getKey());
                    codeSortMapB.put(vo.getBlueSortedList().get(j - 1).getKey(), j);
                }
                rankingMapR.put(j, vo.getRedSortedList().get(j - 1).getKey());
                codeSortMapR.put(vo.getRedSortedList().get(j - 1).getKey(), j);
            }
            pr = setRanking4Pr(pr, rankingMapR, rankingMapB);

            pr = setCodeSort4Pr(pr, codeSortMapR, codeSortMapB);

            pr = getOpenCodeSort(ssq, pr, codeSortMapR, codeSortMapB);
            if (rowFrom.compareTo(pr.getSsqId()) <= 0) {
                pRanks.add(pr);
            }
        }
        iProbabilityRankService.insertOrUpdateBatch(pRanks);
        return "success";
    }

    /**
     * 统计概率分布，例：1:20 表示号码1排序20
     *
     * @param ssqId
     * @return
     */
    @RequestMapping("cocs")
    @Deprecated
    public String computeOpenCodeSort(String ssqId) {
        Map<String, Map<String, Integer>> map = getComputedOpenCodeSort(ssqId);
        return JSONUtil.toJsonStr(map);
    }

    /**
     * 排序后的号码，例：330:20,  表示：号码20总出现次数为330
     *
     * @param ssqId
     * @return
     */
    @RequestMapping("rcocs")
    @Deprecated
    public String reverseComputedOpenCodeSort(String ssqId) {
        Map<String, Map<String, Integer>> map = getComputedOpenCodeSort(ssqId);
        Map<Integer, Integer> mapR = new HashMap<>();
        Map<Integer, Integer> mapB = new HashMap<>();
        for (Map.Entry<String, Integer> e : map.get("red").entrySet()) {
            mapR.put(e.getValue(), Integer.parseInt(e.getKey()));
        }
        for (Map.Entry<String, Integer> entry : map.get("blue").entrySet()) {
            mapB.put(entry.getValue(), Integer.parseInt(entry.getKey()));
        }
        Map<String, Map<Integer, Integer>> returnMap = new HashMap<>(2);
        returnMap.put("red", mapR);
        returnMap.put("blue", mapB);
        return JSONUtil.toJsonStr(returnMap);
    }

    private Map<String, Map<String, Integer>> getComputedOpenCodeSort(String ssqId) {
        EntityWrapper<ProbabilityRank> ew = null;
        if (StringUtils.isNotBlank(ssqId)) {
            ew = new EntityWrapper();
            ew.le("ssq_id", ssqId);
        }
        List<ProbabilityRank> prs = iProbabilityRankService.selectList(ew);
        Map<String, Integer> mapR = new TreeMap<>();
        Map<String, Integer> mapB = new TreeMap<>();
        for (int i = 1; i <= 33; i++) {
            if (i <= 16) {
                mapB.put(Integer.toString(i), 0);
            }
            mapR.put(Integer.toString(i), 0);
        }
        for (ProbabilityRank pr : prs) {
            String openCodeSort = pr.getOpenCodeSort();
            String[] redProbs = openCodeSort.substring(0, openCodeSort.lastIndexOf('+')).split("-");
            for (String redProb : redProbs) {
                mapR.put(redProb, mapR.get(redProb) + 1);
            }
            String blueProb = openCodeSort.substring(openCodeSort.lastIndexOf('+') + 1);
            mapB.put(blueProb, mapB.get(blueProb) + 1);
        }

        Map<String, Map<String, Integer>> map = new HashMap<>(2);
        map.put("red", mapR);
        map.put("blue", mapB);
        return map;
    }

    private CodeSortedVo sortCode(List<Ssq> list) {
        CodeSortedVo vo = new CodeSortedVo();
        Map<Integer, Integer> mapR = new TreeMap();
        Map<Integer, Integer> mapB = new TreeMap();
        for (int i = 1; i < 34; i++) {
            if (i < 17) {
                mapB.put(i, 0);
            }
            mapR.put(i, 0);
        }
        list.stream().forEach(ssq -> {
            Integer r0 = ssq.getR0();
            mapR.put(r0, mapR.get(r0) + 1);
            Integer r1 = ssq.getR1();
            mapR.put(r1, mapR.get(r1) + 1);
            Integer r2 = ssq.getR2();
            mapR.put(r2, mapR.get(r2) + 1);
            Integer r3 = ssq.getR3();
            mapR.put(r3, mapR.get(r3) + 1);
            Integer r4 = ssq.getR4();
            mapR.put(r4, mapR.get(r4) + 1);
            Integer r5 = ssq.getR5();
            mapR.put(r5, mapR.get(r5) + 1);
            Integer b0 = ssq.getB0();
            mapB.put(b0, mapB.get(b0) + 1);
        });
        List<Map.Entry<Integer, Integer>> lr = new ArrayList<>(mapR.entrySet());
        Comparator<Map.Entry<Integer, Integer>> comparator = (o1, o2) -> {
            return o1.getValue() - o2.getValue();
        };
        lr.sort(comparator);
        List<Map.Entry<Integer, Integer>> lb = new ArrayList<>(mapB.entrySet());
        lb.sort(comparator);

        vo.setMapR(mapR);
        vo.setMapB(mapB);
        vo.setRedSortedList(lr);
        vo.setBlueSortedList(lb);
        return vo;
    }

    private ProbabilityRank setTimesCodeOpened(CodeSortedVo vo, ProbabilityRank pr) {
        Map<String, Map<Integer, Integer>> codeOpenTimesMap = new HashMap<>();
        codeOpenTimesMap.put("red", vo.getMapR());
        codeOpenTimesMap.put("blue", vo.getMapB());
        pr.setTimesCodeOpened(JSONUtil.toJsonStr(codeOpenTimesMap));
        return pr;
    }

    private ProbabilityRank setCodeSort4Pr(ProbabilityRank pr, Map<Integer, Integer> codeSortMapR, Map<Integer, Integer> codeSortMapB) {
        Map<String, Map<Integer, Integer>> codeSortMap = new HashMap<>();
        codeSortMap.put("red", codeSortMapR);
        codeSortMap.put("blue", codeSortMapB);
        pr.setCodeSort(JSONUtil.toJsonStr(codeSortMap));
        return pr;
    }

    private ProbabilityRank setRanking4Pr(ProbabilityRank pr, Map<Integer, Integer> rankingMapR, Map<Integer, Integer> rankingMapB) {
        Map<String, Map<Integer, Integer>> rankingMap = new HashMap<>();
        rankingMap.put("red", rankingMapR);
        rankingMap.put("blue", rankingMapB);
        pr.setRanking(JSONUtil.toJsonStr(rankingMap));
        return pr;
    }

    private ProbabilityRank getOpenCodeSort(Ssq ssq, ProbabilityRank pr, Map<Integer, Integer> codeSortMapR, Map<Integer, Integer> codeSortMapB) {
        Set<Integer> openCodeSort = new TreeSet<>();
        openCodeSort.add(codeSortMapR.get(ssq.getR0()));
        openCodeSort.add(codeSortMapR.get(ssq.getR1()));
        openCodeSort.add(codeSortMapR.get(ssq.getR2()));
        openCodeSort.add(codeSortMapR.get(ssq.getR3()));
        openCodeSort.add(codeSortMapR.get(ssq.getR4()));
        openCodeSort.add(codeSortMapR.get(ssq.getR5()));
        pr.setOpenCodeSort(CollectionUtil.join(openCodeSort, "-") + "+" + codeSortMapB.get(ssq.getB0()));
        pr.setSortSum(openCodeSort.stream().reduce(0, (pre, i) -> {
            return pre + i;
        }));
        return pr;
    }

    /**
     * 某概率排序值出现的概率（百分比）
     *
     * @return
     */
    @GetMapping("rta")
    @Deprecated
    public Map<String, Map<Integer, String>> realTimeAnalyze() {
        int totalR = 0;
        int totalB = 0;
        Map<Integer, Integer> mapR = new HashMap<>();
        Map<Integer, Integer> mapB = new HashMap<>();
        List<ProbabilityRank> prs = iProbabilityRankService.selectList(null);
        for (int i = 1; i <= 33; i++) {
            mapR.put(i, 0);
            if (i < 17) {
                mapB.put(i, 0);
            }
        }
        for (ProbabilityRank pr : prs) {
            String openCodeSort = pr.getOpenCodeSort();
            String[] codes = openCodeSort.split("-");
            for (int i = 0; i < codes.length; i++) {
                if (i < 5) {
                    int key = Integer.parseInt(codes[i]);
                    mapR.put(key, mapR.get(key) + 1);
                } else {
                    String[] rAndB = codes[i].split("\\+");
                    int keyR5 = Integer.parseInt(rAndB[0]);
                    int keyB0 = Integer.parseInt(rAndB[1]);
                    mapR.put(keyR5, mapR.get(keyR5) + 1);
                    mapB.put(keyB0, mapB.get(keyB0) + 1);
                }
                totalR++;
            }
            totalB++;
        }
        Map<Integer, String> mapPercentR = new HashMap<>();
        Map<Integer, String> mapPercentB = new HashMap<>();
        DecimalFormat df = new DecimalFormat("######0.00");
        for (Map.Entry<Integer, Integer> entry : mapR.entrySet()) {
            double percent = ((double) entry.getValue()) / totalR;
            mapPercentR.put(entry.getKey(), df.format(percent * 100) + "%");
        }
        for (Map.Entry<Integer, Integer> entry : mapB.entrySet()) {
            double percent = ((double) entry.getValue()) / totalB;
            mapPercentB.put(entry.getKey(), df.format(percent * 100) + "%");
        }
        Map<String, Map<Integer, String>> result = new HashMap<>(2);
        result.put("red", mapPercentR);
        result.put("blue", mapPercentB);
        return result;
    }

    /**
     * 某概率排序值出现的概率+排序
     *
     * @return
     */
    @GetMapping("rta/sort")
    @Deprecated
    public Map<String, Map<String, Integer>> realTimeAnalyzeAndSort(String rowFrom) {
        rowFrom = StringUtils.isNotBlank(rowFrom) ? rowFrom : "03001";
        int totalR = 0;
        int totalB = 0;
        Map<Integer, Integer> mapR = new HashMap<>();
        Map<Integer, Integer> mapB = new HashMap<>();
        Wrapper<ProbabilityRank> wrapper = Condition.create();
        wrapper.ge("ssq_id", rowFrom);
        List<ProbabilityRank> prs = iProbabilityRankService.selectList(wrapper);
        for (int i = 1; i <= 33; i++) {
            mapR.put(i, 0);
            if (i < 17) {
                mapB.put(i, 0);
            }
        }
        for (ProbabilityRank pr : prs) {
            String openCodeSort = pr.getOpenCodeSort();
            String[] codes = openCodeSort.split("-");
            for (int i = 0; i < codes.length; i++) {
                if (i < 5) {
                    int key = Integer.parseInt(codes[i]);
                    mapR.put(key, mapR.get(key) + 1);
                } else {
                    String[] rAndB = codes[i].split("\\+");
                    int keyR5 = Integer.parseInt(rAndB[0]);
                    int keyB0 = Integer.parseInt(rAndB[1]);
                    mapR.put(keyR5, mapR.get(keyR5) + 1);
                    mapB.put(keyB0, mapB.get(keyB0) + 1);
                }
                totalR++;
            }
            totalB++;
        }
        Map<String, Integer> mapPercentR = new TreeMap<>();
        Map<String, Integer> mapPercentB = new TreeMap<>();
        DecimalFormat df = new DecimalFormat("######0.00");
        for (Map.Entry<Integer, Integer> entry : mapR.entrySet()) {
            double percent = ((double) entry.getValue()) / totalR;
            mapPercentR.put(df.format(percent * 100) + "%", entry.getKey());
        }
        for (Map.Entry<Integer, Integer> entry : mapB.entrySet()) {
            double percent = ((double) entry.getValue()) / totalB;
            mapPercentB.put(df.format(percent * 100) + "%", entry.getKey());
        }
        Map<String, Map<String, Integer>> result = new HashMap<>(2);
        result.put("red", mapPercentR);
        result.put("blue", mapPercentB);
        return result;
    }

    /**
     * 初始化probability_rank表
     * @return
     */
    @RequestMapping("pr/init")
    public String initPr(){
        iProbabilityRankService.initPr();
        return "success";
    }

    /**
     * 更新probability_rank表
     * @return
     */
    @RequestMapping("pr/update")
    public String updatePr(){
        iProbabilityRankService.updatePr();
        return "success";
    }
}
