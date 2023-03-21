package com.cwq.codespringbootstarter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class CodeUtil {
    private static final int CODE_LENGTH = 4;
    private static final int CODE_WIDTH = 200;
    private static final int CODE_HEIGHT = 40;
    private static final int CODE_WIDTH_RANDOM = 25;
    private static final int CODE_HEIGHT_RANDOM = 10;
    private static final int BASE_FONT_SIZE = 19;
    private static final int FONT_SIZE_RANDOM = 5;

    private static final int LINE_SIZE = 10;

    private static final Color[] COLORS = {Color.RED, Color.BLACK, Color.CYAN, Color.GRAY, Color.BLUE,
            Color.MAGENTA, Color.PINK, Color.ORANGE, Color.GREEN};
    private String[] AUTH_CODES= new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9","a","b","c","d","e","f","g","h","i"
            ,"j","k","m","n","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","P","Q","R",
            "S","T","U","V","W","X","Y","Z"};
    private String code = "";
    public byte[] codes(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Random random = new Random();
        for(int i = 0; i < CODE_LENGTH; i ++) {
            code += AUTH_CODES[random.nextInt(AUTH_CODES.length)];
        }
        System.out.println("验证码：" + code);
//        session.setAttribute("code",code);
        BufferedImage bi = new BufferedImage(CODE_WIDTH, CODE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CODE_WIDTH, CODE_HEIGHT);

        for(int i = 0; i < CODE_LENGTH; i ++) {
            g.setFont(new Font("微软雅黑", Font.BOLD, BASE_FONT_SIZE + random.nextInt(FONT_SIZE_RANDOM)));
            g.setColor(COLORS[random.nextInt(COLORS.length)]);
            g.drawString(code.charAt(i) + "",
                    (CODE_WIDTH/CODE_LENGTH)*i + random.nextInt(CODE_WIDTH_RANDOM),
                    CODE_HEIGHT/2 + random.nextInt(CODE_HEIGHT_RANDOM));
        }

        for(int i = 0; i < LINE_SIZE; i++) {
            g.setColor(COLORS[random.nextInt(COLORS.length)]);
            g.drawLine(
                    random.nextInt(CODE_WIDTH) ,   random.nextInt(CODE_HEIGHT) ,
                    random.nextInt(CODE_WIDTH) ,   random.nextInt(CODE_HEIGHT));
        }

        try {
            ImageIO.write(bi, "jpg",byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public String verCode(){
//        return Base64Util.encode(code);
        return  code;
    }
}
