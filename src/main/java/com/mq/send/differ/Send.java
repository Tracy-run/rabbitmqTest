package com.mq.send.differ;

import com.mq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;


public class Send {

    private static final String QUEUE_NAME = "test_queue_confirm_asyn";

    public static void main(String [] args) throws  Exception{
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmSelect将channel设置为nconfirm模式
        channel.confirmSelect();

        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        channel.addConfirmListener(new ConfirmListener() {
            //没有问题
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("handleAck multiple");
                    confirmSet.headSet(deliveryTag +1).clear();
                }else{
                    System.out.println("handleAck false");
                    confirmSet.remove(deliveryTag);
                }
            }
            //有问题
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("handleAck multiple");
                    confirmSet.headSet(deliveryTag +1).clear();
                }else{
                    System.out.println("handleAck false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });
        String msg = "success";
        while(true){
            long equNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(equNo);
        }
    }


}
