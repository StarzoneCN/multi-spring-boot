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

    @RequestMapping("init")
    public String initData() {
        String htmlStr = HttpUtil.get("http://kaijiang.500.com/shtml/ssq/18042.shtml", Charset.forName("UTF-8"));
        Document doc = Jsoup.parse(htmlStr);
        Elements elements = doc.getElementsByClass("iSelectList");

        for (Element element : elements) {
            Elements aChildren = element.children();
            for (Element child : aChildren) {
                String href = child.attr("href");
                parseAndSaveData(href);
            }
        }

        return "success";
    }

    @RequestMapping("fs")
    public String fillStr(){
        List<Ssq> list = iSsqService.selectList(null);
        for (Ssq ssq : list) {
            ssq.setStr(StringUtils.assemblySsq2Str(ssq));
        }
        iSsqService.updateBatchById(list);
        return "success";
    }

    @RequestMapping("nd")
    public CodeSortedVo neatenData(){
        List<Ssq> list = iSsqService.selectList(null);
        return sortCode(list);
    }

    @RequestMapping("update")
    public String updateData() {
        String responseStr = HttpUtil.get("http://f.apiplus.net/ssq-20.json", Charset.forName("UTF-8"));
        JSONObject jsonObject = JSONUtil.parseObj(responseStr);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Ssq> listToUpdate = new ArrayList<>(32);
        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject)obj;
            String expectStr = jsonObj.getStr("expect");
            if (StringUtils.isBlank(expectStr)) {
                continue;
            }
            Ssq ssq = new Ssq();
            ssq.setId(expectStr.substring(2));

            String opencode = jsonObj.getStr("opencode");
            String[] opencodes = StringUtils.splitToArray(opencode, ',');

            ssq.setR0(Integer.parseInt(opencodes[0]));
            ssq.setR1(Integer.parseInt(opencodes[1]));
            ssq.setR2(Integer.parseInt(opencodes[2]));
            ssq.setR3(Integer.parseInt(opencodes[3]));
            ssq.setR4(Integer.parseInt(opencodes[4]));

            String lastRedAndBlue = opencodes[5];
            ssq.setR5(Integer.parseInt(lastRedAndBlue.substring(0, 2)));
            ssq.setB0(Integer.parseInt(lastRedAndBlue.substring(3)));

            ssq.setStr(StringUtils.assemblySsq2Str(ssq));
            listToUpdate.add(ssq);
        }
        iSsqService.insertOrUpdateBatch(listToUpdate);
        return "success";
    }

    /**
     * 排序处理
     * @param rowFrom 开始插入位置：ssq_id
     * @return
     */
    @RequestMapping("ipr/{from}")
    public String initProbabilityRank(@PathVariable("from") String rowFrom) {
        rowFrom = StringUtils.isNotBlank(rowFrom)? rowFrom : "11118";
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
            for (int j=1; j <= vo.getRedSortedList().size(); j++) {
                if (j <= 16) {
                    rankingMapB.put(j, vo.getBlueSortedList().get(j-1).getKey());
                    codeSortMapB.put(vo.getBlueSortedList().get(j-1).getKey(), j);
                }
                rankingMapR.put(j, vo.getRedSortedList().get(j-1).getKey());
                codeSortMapR.put(vo.getRedSortedList().get(j-1).getKey(), j);
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
     * 统计概率分布
     * @param ssqId
     * @return
     */
    @RequestMapping("cocs")
    public String computeOpenCodeSort(String ssqId) {
        Map<String, Map<String, Integer>> map = getComputedOpenCodeSort(ssqId);
        return JSONUtil.toJsonStr(map);
    }

    @RequestMapping("rcocs")
    public String reverseComputedOpenCodeSort(String ssqId) {
        Map<String, Map<String, Integer>> map = getComputedOpenCodeSort(ssqId);
        Map<Integer,Integer> mapR = new HashMap<>();
        Map<Integer,Integer> mapB = new HashMap<>();
        for (Map.Entry<String, Integer> e : map.get("red").entrySet()) {
            mapR.put(e.getValue(), Integer.parseInt(e.getKey()));
        }
        for (Map.Entry<String, Integer> entry : map.get("blue").entrySet()) {
            mapB.put(entry.getValue(), Integer.parseInt(entry.getKey()));
        }
       Map<String, Map<Integer,Integer>> returnMap = new HashMap<>(2);
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
        for (int i=1; i<=33; i++) {
            if (i<=16) {
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

    private void parseAndSaveData(String url){
        Ssq ssq = new Ssq();
        for (String s : StringUtils.splitToArray(url, '/')) {
            int index = s.indexOf(".shtml");
            if ( index > 0) {
                String id = s.substring(0, index);
                if (id.compareTo("17001") >= 0)
                    return;
                ssq.setId(id);
            }
        }


        Elements elements = getElesByClass(url, "ball_box01");
        for (int i = 0; i < 7; i++) {
            Element ele = elements.get(i);
            switch (i) {
                case 0:
                    ssq.setR0(Integer.parseInt(ele.text()));
                    break;
                case 1:
                    ssq.setR1(Integer.parseInt(ele.text()));
                    break;
                case 2:
                    ssq.setR2(Integer.parseInt(ele.text()));
                    break;
                case 3:
                    ssq.setR3(Integer.parseInt(ele.text()));
                    break;
                case 4:
                    ssq.setR4(Integer.parseInt(ele.text()));
                    break;
                case 5:
                    ssq.setR5(Integer.parseInt(ele.text()));
                    break;
                case 6:
                    ssq.setB0(Integer.parseInt(ele.text()));
                    break;
                default:
                    break;
            }
        }
        iSsqService.insertOrUpdate(ssq);
    }

    private Elements getElesByClass(String url, String tagName) {
        String htmlStr = HttpUtil.get(url);
        Document doc = Jsoup.parse(htmlStr);
        Elements elements = doc.getElementsByClass(tagName);
        if (elements != null) {
            return elements.first().child(0).children();
        }
        return null;
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
        for (Ssq ssq : list) {
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
        }
        List<Map.Entry<Integer, Integer>> lr = new ArrayList<>(mapR.entrySet());
        lr.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        List<Map.Entry<Integer, Integer>> lb = new ArrayList<>(mapB.entrySet());
        lb.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });

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
        return pr;
    }

    /**
     * 某概率排序值出现的概率
     * @return
     */
    @GetMapping("rta")
    public Map<String, Map<Integer, String>> realTimeAnalyze(){
        int totalR = 0;
        int totalB = 0;
        Map<Integer, Integer> mapR = new HashMap<>();
        Map<Integer, Integer> mapB = new HashMap<>();
        List<ProbabilityRank> prs = iProbabilityRankService.selectList(null);
        for (int i=1; i<=33; i++) {
            mapR.put(i, 0);
            if (i<17){
                mapB.put(i, 0);
            }
        }
        for (ProbabilityRank pr : prs) {
            String openCodeSort = pr.getOpenCodeSort();
            String[] codes = openCodeSort.split("-");
            for (int i=0; i<codes.length; i++){
                if (i<5){
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
            double percent = ((double)entry.getValue()) / totalR;
            mapPercentR.put(entry.getKey(), df.format(percent * 100) + "%");
        }
        for (Map.Entry<Integer, Integer> entry : mapB.entrySet()) {
            double percent = ((double)entry.getValue()) / totalB;
            mapPercentB.put(entry.getKey(), df.format(percent * 100) + "%");
        }
        Map<String, Map<Integer, String>> result = new HashMap<>(2);
        result.put("red", mapPercentR);
        result.put("blue", mapPercentB);
        return result;
    }

    /**
     * 某概率排序值出现的概率+排序
     * @return
     */
    @GetMapping("rta/sort")
    public Map<String, Map<String, Integer>> realTimeAnalyzeAndSort(String rowFrom){
        rowFrom = StringUtils.isNotBlank(rowFrom)? rowFrom : "03001";
        int totalR = 0;
        int totalB = 0;
        Map<Integer, Integer> mapR = new HashMap<>();
        Map<Integer, Integer> mapB = new HashMap<>();
        Wrapper<ProbabilityRank> wrapper = Condition.create();
        wrapper.ge("ssq_id", rowFrom);
        List<ProbabilityRank> prs = iProbabilityRankService.selectList(wrapper);
        for (int i=1; i<=33; i++) {
            mapR.put(i, 0);
            if (i<17){
                mapB.put(i, 0);
            }
        }
        for (ProbabilityRank pr : prs) {
            String openCodeSort = pr.getOpenCodeSort();
            String[] codes = openCodeSort.split("-");
            for (int i=0; i<codes.length; i++){
                if (i<5){
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
            double percent = ((double)entry.getValue()) / totalR;
            mapPercentR.put(df.format(percent * 100) + "%", entry.getKey());
        }
        for (Map.Entry<Integer, Integer> entry : mapB.entrySet()) {
            double percent = ((double)entry.getValue()) / totalB;
            mapPercentB.put(df.format(percent * 100) + "%", entry.getKey());
        }
        Map<String, Map<String, Integer>> result = new HashMap<>(2);
        result.put("red", mapPercentR);
        result.put("blue", mapPercentB);
        return result;
    }
}
