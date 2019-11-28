package com.mq.receive.fanout;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 订阅
 */
public class FanoutReceive1 {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    private static final String QUEUE_NAME = "test_fanout_queue_email";

    public static void main(String [] args) throws  Exception{

        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        final Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,""); // public com.rabbitmq.client.AMQP.Queue.BindOk queueBind(String queue, String exchange, String routingKey)

        //一次只能发送一个消息
        channel.basicQos(1);

        //创建消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("FanoutReceive1 receive msg ：" + msg );
                try{
                    Thread.sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println("done");
                    //手动应答
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }

            }
        };
        //监听队列，不是自动应答
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }



}
