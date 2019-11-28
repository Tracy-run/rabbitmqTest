package com.mq.receive.topic;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 *  topic 路由
 *
 *  #可以匹配一个或者多个字符
 *  *可以匹配一个字符
 */
public class TopicReceive2 {

    private static final String EXCHANGE_NAME = "test_exchange_topic";
    private static final String QUEUE_NAME="test_queue_topic_2";

    public static void  main(String [] args) throws Exception{
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //给通道绑定 good.query.pro    good.#  表示good后有一个或多个字符
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"good.add.*");//NG
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"good.query.*");//OK
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("TopicReceive2 receive" + msg);
                try{
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    System.out.println("[done]");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }


}
