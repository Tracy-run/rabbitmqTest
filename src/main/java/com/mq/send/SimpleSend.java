package com.mq.send;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class SimpleSend {

    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String [] args) throws Exception{
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //消息
        String strBody = "Hello Mq";

        //发送
        channel.basicPublish("",QUEUE_NAME,null,strBody.getBytes());
        System.out.println("send strBody:" + strBody);

        //关闭连接
        channel.close();
        connection.close();
    }

}
