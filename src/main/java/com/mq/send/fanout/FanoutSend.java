package com.mq.send.fanout;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅
 */
public class FanoutSend {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String [] args) throws  Exception{

        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //获取一个通道
        Channel channel = connection.createChannel();
        //创建一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //消息
        String msg = "hello world pugsub";

        //发送
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println("send msg : " + msg);

        //关闭连接
        channel.close();
        connection.close();
    }




}
