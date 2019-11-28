package com.mq.receive;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 轮播
 */
public class RoundWorkReceive2 {


    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String [] args) throws Exception{

        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //创建消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                String str = new String(body,"utf-8");
                System.out.println("RoundWorkReceive2 receive new  str:" + str);
                try {
                    Thread.sleep(300);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println("done");
                }
            }
        };

        //监听队列
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }

}
