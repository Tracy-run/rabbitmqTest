package com.mq.send.fairWork;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class FairWorkSend {

    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String [] args) throws Exception{
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //限制发送一个消费者不得超过一条
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        //消息与发送放入for循环中
        for(int i =0;i<50;i++){
            String msg = "hello " + i;
            System.out.println("[send msg] :" + msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*1);
        }

        //关闭连接
        channel.close();
        connection.close();
    }


}
