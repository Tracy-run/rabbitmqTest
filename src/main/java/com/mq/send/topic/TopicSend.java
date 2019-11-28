package com.mq.send.topic;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TopicSend {

    private static  final String EXCHANGE_NAME ="test_exchange_topic";
    public static void main(String [] args) throws  Exception{
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String msg = "hello world";

        //第二个是重点 
        String routingKey = "商品测试";
        channel.basicPublish(EXCHANGE_NAME,"good.query.pro",null,msg.getBytes());
        System.out.println("msg send: " + msg);

        channel.close();
        connection.close();
    }



}
