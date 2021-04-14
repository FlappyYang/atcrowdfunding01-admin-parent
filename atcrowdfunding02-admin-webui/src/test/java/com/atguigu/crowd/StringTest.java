package com.atguigu.crowd;

import com.atguigu.crowd.utils.CrowdUtil;
import org.junit.Test;

/**
 * @author 杨鹏炜
 * @data 2021/4/222:32:51
 */
public class StringTest {
    @Test
    public void testMD5(){
        String source = "123123";
        String encoed = CrowdUtil.md5(source);
        System.out.println(encoed);
    }
}
