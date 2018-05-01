package com.yuan.my_project.mq.rocketmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @author yuanjuntao
 * @date 2018/4/30 18:15
 */
public class TestConsumer {


    private DefaultMQPushConsumer defaultMQPushConsumer;

    private String topic;

    private String namesrvAddr;

    private String consumerGroup;

    private int reconsumeTimes;




    public void init() throws MQClientException {
        this.defaultMQPushConsumer = new DefaultMQPushConsumer(this.consumerGroup);
        this.defaultMQPushConsumer.setNamesrvAddr(this.namesrvAddr);
        this.defaultMQPushConsumer.subscribe(topic,"*");
        this.defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        this.defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        this.defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {

            MessageExt msg = msgs.get(0);
            try {

                String tags = msg.getTags();
                String body = new String(msg.getBody());

                System.out.println("msg:"+JSON.toJSON(msg));
                System.out.println("context:"+JSON.toJSONString(context));
            }catch (Exception e){
                if(msg.getReconsumeTimes()<reconsumeTimes){
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        this.defaultMQPushConsumer.start();
    }



    public void destroy(){

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

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public int getReconsumeTimes() {
        return reconsumeTimes;
    }

    public void setReconsumeTimes(int reconsumeTimes) {
        this.reconsumeTimes = reconsumeTimes;
    }
}
