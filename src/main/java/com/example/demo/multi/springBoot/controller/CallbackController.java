package com.example.demo.multi.springBoot.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.multi.springBoot.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * 接收回调的接口
 *
 * @author: LiHongxing
 * @date: Create in 2018-02-27 14:45
 * @modefied:
 */
@RestController
@RequestMapping("/cb")
public class CallbackController {
    private static final String SUCCESS = "success";
    private static final String FAILED = "failed";

    private static final String LOG_DIR = System.getProperty("user.dir");
    private static final String FILE_NAME_OF_UPLOAD_CB = "upload_cb.log";  // TYPE = 1
    private static final String FILE_NAME_OF_WATER_MARK = "water_mark_cb.log";  // TYPE = 2
    private static final String FILE_NAME_OF_THUMBNAIL = "thumbnail_cb.log";  // TYPE = 3
    private static final String FILE_NAME_OF_HLS = "hls_cb.log";  // TYPE = 4

    @RequestMapping
    public String receive(@RequestBody String callbackContent) throws IOException {
        if (StringUtils.isBlank(callbackContent))
            return FAILED;
        JSONObject jsonObj = JSONObject.parseObject(callbackContent);
        JSONObject dataJsonObj = jsonObj.getJSONObject("data");
        if (!Boolean.TRUE.equals(jsonObj.getBoolean("isSuccess")) || dataJsonObj == null || dataJsonObj.size() == 0)
            return FAILED;
        String filePath = LOG_DIR;
        Integer type = jsonObj.getInteger("type");
        if (type == null)
            return FAILED;
        switch (type) {
            case 1:
                filePath = filePath + File.separator + FILE_NAME_OF_UPLOAD_CB;
                break;
            case 2:
                filePath = filePath + File.separator + FILE_NAME_OF_WATER_MARK;
                break;
            case 3:
                filePath = filePath + File.separator + FILE_NAME_OF_THUMBNAIL;
                break;
            case 4:
                filePath = filePath + File.separator + FILE_NAME_OF_HLS;
                break;
            default:
                return FAILED;
        }
        File logFile = FileUtil.createNewFile(filePath, false);
        String dataStrToAppend = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") +
                dataJsonObj.toJSONString();
        FileWriter fileWriter = new FileWriter(logFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            bufferedWriter.append(dataStrToAppend);
            bufferedWriter.newLine();
        } finally {
            bufferedWriter.close();
        }
        return SUCCESS;
    }
}
