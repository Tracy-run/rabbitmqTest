package com.mq.send;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * routing路由模式
 */
public class RoutingSend {

    private static final String EXCHANGE_NAME = "test_exchange_direct";
    //private static final String QUEUE_NAME = "test_exchange_direct_1";

    public static void main(String [] args) throws  Exception{
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String msg = "hello world .";
        //重点 第二个参数routingKey  receive的EXCHANGE_NAME 相同时才能接收到
        String routingKey = "info";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("send msg :" + msg);

        channel.close();
        connection.close();
    }

}
