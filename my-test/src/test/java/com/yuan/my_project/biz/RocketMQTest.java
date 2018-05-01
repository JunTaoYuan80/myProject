package com.yuan.my_project.biz;

import com.yuan.my_project.BaseServiceTest;
import com.yuan.my_project.mq.rocketmq.TestProducer;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yuanjuntao
 * @date 2018/4/30 20:36
 */
@Component
public class RocketMQTest extends BaseServiceTest {

    @Resource
    private TestProducer testProducer;

    @Test
    public void sendMsg(){
        testProducer.sendMsg("tag100","hello");
    }
}
