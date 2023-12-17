package com.download.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author：三玖
 * @date: 2023/12/17
 */
@Configuration
public class RabbitmqConfig {

    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";
    public static final String QUEUE_INFORM_DOWNLOAD = "queue_inform_download";
    public static final String ROUTINGKEY_DOWNLOAD = "inform.#.download.#";

    //声明交换机
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange exchangeTopicsInform() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    //声明队列
    @Bean(QUEUE_INFORM_DOWNLOAD)
    public Queue queueInformDownload() {
        return new Queue(QUEUE_INFORM_DOWNLOAD);
    }

    //RoutingKey绑定队列与交换机
    @Bean
    public Binding routingkeyDownload(@Qualifier(QUEUE_INFORM_DOWNLOAD) Queue queue,
                                      @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_DOWNLOAD).noargs();
    }


}
