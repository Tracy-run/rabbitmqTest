package spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringMain {

    public static void main(String [] args) throws Exception{

        ApplicationContext context = new GenericXmlApplicationContext("classpath:rabbit-context.xml");

        //RabbitMQ模板
        RabbitTemplate template = context.getBean(RabbitTemplate.class);

        //发送消息
        template.convertAndSend("spring.MyConsumer");
        Thread.sleep(200);
    }
}
