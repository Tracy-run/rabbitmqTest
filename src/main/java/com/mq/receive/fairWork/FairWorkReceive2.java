package com.mq.receive.fairWork;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 能者多劳。
 * 手动应答
 */
public class FairWorkReceive2 {
    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String[] args)throws Exception{
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        final Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //一次只能发送一个消息
        channel.basicQos(1);

        //创建消费者
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body,"utf-8");
                System.out.println("FairWorkReceive2 receive msg:"+msg);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("done");
                    //手动应答
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //监听队列,不是自动应答
        boolean autoAck=false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}