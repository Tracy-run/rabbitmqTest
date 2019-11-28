package com.mq.receive.differ;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;


public class Receive {

    private static final String QUEUE_NAME = "test_queue_confirm_asyn";

    public static void main(String [] args) throws  Exception{
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"utf-8"));
                try {
                    Thread.sleep(300);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    System.out.println("done");
                }
            }
        });

    }


}
