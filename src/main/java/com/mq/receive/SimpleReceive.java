package com.mq.receive;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class SimpleReceive {

    private static final String QUEUE_NAME="test_simple_queue";

    public static void main1(String[] args) throws  Exception{
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //定义消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);

        while(true){
            QueueingConsumer.Delivery  delivery = consumer.nextDelivery();
            String strbody = new String(delivery.getBody());
            System.out.println("receive strBody:" + strbody);
        }
    }


    public static void main(String [] args)throws  Exception{
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
                System.out.println("receive new  str:" + str);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }


}
