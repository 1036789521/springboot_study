package com.example.demo.util;


import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * 自定义id生成规则
 */
public  class UUIDGenerateUtil {


    /** 
    * @Description: 生成带时间的uuid
    */ 
    public  static String getNextId() {
        UUID uuid=UUID.randomUUID();
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer str = new StringBuffer();
        str.append(f.format(new Date()));

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        str.append(Base58Util.encode(bb.array()).toLowerCase());
        return str.toString();
    }

    public static void main(String[] args) {
        for (int i=1;i<100;i++){
            System.out.println(getNextId());
        }
    }
}
