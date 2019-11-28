package com.mq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

    /**
     * 获取connection连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        //定义一个工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //设置AMQP端口
        factory.setPort(5672);
        //vhost
        factory.setVirtualHost("/");
        //用户名
        factory.setUsername("fx");
        //密码
        factory.setPassword("123456");

        return factory.newConnection();
    }


}
