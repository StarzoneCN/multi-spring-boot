package com.example.demo.multi.springBoot.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.multi.springBoot.entity.Ssq;
import com.example.demo.multi.springBoot.mapper.SsqMapper;
import com.example.demo.multi.springBoot.service.ISsqService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.multi.springBoot.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hongxing
 * @since 2018-04-16
 */
@Service
public class SsqServiceImpl extends ServiceImpl<SsqMapper, Ssq> implements ISsqService {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private SsqMapper ssqMapper;

    @Override
    public String queryMaxId() {
        return ssqMapper.queryMaxId();
    }

    /**
     * 更新最新20条数据
     * @return
     */
    @Override
    public void updateData() {
        String responseStr = HttpUtil.get("http://f.apiplus.net/ssq-20.json", Charset.forName("UTF-8"));
        JSONObject jsonObject = JSONUtil.parseObj(responseStr);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Ssq> listToUpdate = new ArrayList<>(32);
        String maxId = ssqMapper.queryMaxId();
        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject) obj;
            String expectStr = jsonObj.getStr("expect");
            if (StringUtils.isBlank(expectStr)
                    || StringUtils.isNotBlank(maxId) && expectStr.substring(2).compareTo(maxId) <= 0) {
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
        if (listToUpdate.size() > 0) {
            insertOrUpdateBatch(listToUpdate);
        }
    }


    /**
     * 初始化ssq表
     */
    @Override
    public void initSsqData(){
        String htmlStr = HttpUtil.get("http://kaijiang.500.com/shtml/ssq/19019.shtml", Charset.forName("UTF-8"));
        Document doc = Jsoup.parse(htmlStr);
        Elements elements = doc.getElementsByClass("iSelectList");

        for (Element element : elements) {
            Elements aChildren = element.children();
            for (Element child : aChildren) {
                String href = child.attr("href");
                parseAndSaveData(href);
            }
        }
    }

    private void parseAndSaveData(String url) {
        Ssq ssq = new Ssq();
        for (String s : StringUtils.splitToArray(url, '/')) {
            int index = s.indexOf(".shtml");
            if (index > 0) {
                String id = s.substring(0, index);
                ssq.setId(id);
            }
        }

        /*红色*/
        Elements redElements = getElesByClass(url, "ball_red");
        for (int i = 0; i < 6; i++) {
            Element ele = redElements.get(i);
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
                default:
                    break;
            }
        }

        /*蓝色*/
        Elements blueElements = getElesByClass(url, "ball_blue");
        ssq.setB0(Integer.parseInt(blueElements.get(0).text()));

        /*str字段*/
        ssq.setStr(StringUtils.assemblySsq2Str(ssq));
        insertOrUpdate(ssq);
    }

    private Elements getElesByClass(String url, String tagName) {
        String htmlStr = HttpUtil.get(url);
        Document doc = Jsoup.parse(htmlStr);
        return doc.getElementsByClass(tagName);
    }
}
