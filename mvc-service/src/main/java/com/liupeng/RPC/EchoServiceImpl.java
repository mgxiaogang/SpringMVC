package com.liupeng.RPC;

/**
 * Created by liupeng on 2017/6/18.
 */
public class EchoServiceImpl implements EchoService {
    // 测试
    @Override
    public String echo(String ping) {
        int a = 1;
        return ping != null ? ping + "--> i am ok" : "i am ok";
        // sst 11
    }
}
