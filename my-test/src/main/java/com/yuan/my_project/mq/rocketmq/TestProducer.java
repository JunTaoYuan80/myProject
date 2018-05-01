package com.yuan.my_project.mq.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author yuanjuntao
 * @date 2018/4/30 18:15
 */
public class TestProducer {

    @Resource
    private TestSendCallback testSendCallback;

    private DefaultMQProducer producer;

    private String topic;
    private String namesrvAddr;
    private String producerGroup;
    private int retryTimesWhenSendFailed;
    private int sendMsgTimeout;



    public SendResult sendMsg(String tag, String msg){
        SendResult sendResult = new SendResult();
        try {
            Message message = new Message(topic, tag, msg.getBytes("UTF-8"));
            System.out.println(this.producer.getCreateTopicKey());
            //this.producer.setCreateTopicKey(topic);
            //this.producer.createTopic("100",topic,0);
            this.producer.send(message, testSendCallback);
            //this.producer.send(message);
            System.out.println("TestProducer sendMsg end");

            sendResult = testSendCallback.getSendResult();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        return sendResult;
    }


    public void init() throws MQClientException {
        this.producer = new DefaultMQProducer(this.producerGroup);
        this.producer.setNamesrvAddr(this.namesrvAddr);
        this.producer.setProducerGroup(this.producerGroup);
        this.producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
        this.producer.setSendMsgTimeout(this.sendMsgTimeout);
        this.producer.setInstanceName(UUID.randomUUID().toString());

        this.producer.start();
        System.out.println("TestProducer init start end");
    }



    public void destroy(){
        this.producer.shutdown();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public int getRetryTimesWhenSendFailed() {
        return retryTimesWhenSendFailed;
    }

    public void setRetryTimesWhenSendFailed(int retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }

    public int getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(int sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }
}
