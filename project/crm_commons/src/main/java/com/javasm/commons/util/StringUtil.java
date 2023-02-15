package com.javasm.commons.util;

/**
 * @author:SG
 * @classname: StringUtil
 * @description:
 * @date: 2022/9/13 22:45
 * @version:0.1
 * @since:jdk11
 */
public class StringUtil {
    public static String randomValicode(){
        int max=999999;
        int min=111111;
        return String.valueOf((int)(Math.random()*(max-min))+min);
    }


}
