package com.mq.send.tx;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class send2 {

    private static final String QUEUE_NAME="test_queue_confirm";
    public static void main(String [] args) throws  Exception{

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.confirmSelect();
        String msg = "confirm msg";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        if(!channel.waitForConfirms()){
            System.out.println("message send failed");
        }else {
            System.out.println("message send success");
        }

    }



}
