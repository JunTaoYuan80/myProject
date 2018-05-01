package com.yuan.my_project.mq.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Component;

/**
 * @author yuanjuntao
 * @date 2018/4/30 19:35
 */
@Component
public class TestSendCallback implements SendCallback {

    private SendResult sendResult;

    @Override
    public void onSuccess(SendResult sendResult) {
        //插入本地事物，并发送commit/rollback消息
        System.out.println("send success 插入本地事物，并发送commit/rollback消息");
        this.sendResult = sendResult;
    }

    @Override
    public void onException(Throwable e) {
        System.out.println("send exception:"+e.getMessage());
    }


    public SendResult getSendResult() {
        return sendResult;
    }

    public void setSendResult(SendResult sendResult) {
        this.sendResult = sendResult;
    }
}
