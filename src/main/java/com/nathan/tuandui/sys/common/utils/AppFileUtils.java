package com.nathan.tuandui.sys.common.utils;

import cn.hutool.core.util.IdUtil;

public class AppFileUtils {

    /**
     * 根据文件老名字得到新名字
     * @param oldName 文件老名字
     * @return 新名字由32位随机数加图片后缀组成
     */
    public static String createNewFileName(String oldName) {
        //获取文件名后缀
        String stuff=oldName.substring(oldName.lastIndexOf("."), oldName.length());
        //将UUID与文件名后缀进行拼接，生成新的文件名  生成的UUID为32位
        return IdUtil.simpleUUID().toUpperCase()+stuff;
    }
}
