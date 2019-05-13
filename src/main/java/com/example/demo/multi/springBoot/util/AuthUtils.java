package com.example.demo.multi.springBoot.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AuthUtils {

    public static VarificationCodeAndImageVo createVarificationCode(){
        // 在内存中创建图象
        int width = 90, height = 25;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        //生成随机类
        Random random = new Random();

        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        //设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        //画边框
        //g.setColor(new Color());
        //g.drawRect(0,0,width-1,height-1);

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字)
        StringBuffer codeSb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            codeSb.append(rand);
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 21 * i + 6, 20);//13   16   调整图片数字的位置
        }

        // 图象生效
        g.dispose();
        VarificationCodeAndImageVo vo = new VarificationCodeAndImageVo();
        vo.setImage(image);
        vo.setCode(codeSb.toString());
        return vo;
    }

    private static Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public static class VarificationCodeAndImageVo {
        private BufferedImage image;
        private String code;

        public BufferedImage getImage() {
            return image;
        }

        public void setImage(BufferedImage image) {
            this.image = image;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
