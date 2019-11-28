package com.mq.send;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 *轮播
 */
public class RoundWorkSend {
    private static final String QUENE_NAME="test_work_queue";
    public static void main(String[] args) throws Exception {
        //获取一个连接
        Connection connection= ConnectionUtils.getConnection();
        //从连接中获取一个通道
        Channel channel=connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUENE_NAME,false,false,false,null);

        //消息与发送放入for循环
        for (int i=0;i<50;i++){
            String msg="hello "+i;
            System.out.println("RoundWorkSend [send msg]:"+msg);
            channel.basicPublish("",QUENE_NAME,null,msg.getBytes());
            Thread.sleep(i*1);
        }

        //关闭连接
        channel.close();
        connection.close();
    }
}