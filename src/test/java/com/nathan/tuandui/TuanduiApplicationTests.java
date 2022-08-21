package com.nathan.tuandui;

import cn.hutool.core.util.IdUtil;
import com.nathan.tuandui.sys.common.consts.Constast;
import freemarker.template.SimpleHash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@SpringBootTest
class TuanduiApplicationTests {
    @DateTimeFormat(pattern = Constast.TIME_FORM)
    private Date addTime;
    private static final boolean storedCredentialsHexEncoded = true;

    @Test
    void contextLoads() {


        String salt = IdUtil.simpleUUID().toUpperCase();
        Md5Hash hash = new Md5Hash("123456","1E4B45B052D94D05BB242F8A8B3A748C",2);

        System.out.println(hash.toBase64());


    }

}
