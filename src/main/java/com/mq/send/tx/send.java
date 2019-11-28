package com.mq.send.tx;


import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

public class send {
    private static final String QUEUE_NAME="test_queue_tx";
    public static void main(String[] args)throws Exception{
        Connection connection= ConnectionUtils.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg="tx msg";
        try {
            channel.txSelect();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("msg:"+msg);
            //营造一个可以回退的语句
            int a=1/0;
            channel.txCommit();
        }catch (Exception e){
            channel.txRollback();
        }
    }
}