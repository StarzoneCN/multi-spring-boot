package com.example.demo.multi.springBoot.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.multi.springBoot.entity.Ssq;
import com.example.demo.multi.springBoot.service.ISsqService;
import com.example.demo.multi.springBoot.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
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
    public Map<String, List<Map.Entry<Integer, Integer>>> neatenData(){
        List<Ssq> list = iSsqService.selectList(null);
        Map<Integer, Integer> mapR = new HashMap<>(64);
        Map<Integer, Integer> mapB = new HashMap<>(16);
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

        Map<String, List<Map.Entry<Integer, Integer>>> map = new HashMap<>(2);
        map.put("red", lr);
        map.put("blue", lb);
        return map;
    }

    @RequestMapping("update")
    public String updateData() {
        String responseStr = HttpUtil.get("http://f.apiplus.net/ssq-2.json", Charset.forName("UTF-8"));
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
}
