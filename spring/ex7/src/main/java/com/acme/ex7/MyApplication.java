package com.acme.ex7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@SpringBootApplication
@EnableWebSocketMessageBroker
public class MyApplication {

	/*
    @Configuration // méthode 1 : une classe de configuration qui fournit un AbstractWebSocketMessageBrokerConfigurer
    public static class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {

            registry.addEndpoint("/hello").setAllowedOrigins("*").withSockJS();
        }
    }
*/

	
    @Bean // méthode 2 : une méthode productrice qui fournit une implémentation de WebSocketMessageBrokerConfigurer
    public WebSocketMessageBrokerConfigurer webSocketConfig(){
        return new AbstractWebSocketMessageBrokerConfigurer() {
            @Override
            public void registerStompEndpoints(StompEndpointRegistry registry) {
                registry.addEndpoint("/hello").withSockJS();
            }
        };
    }

    /* méthode 1 et méthode 2 conduisent au même résultat.
       la première méthode est à privilégier
       - s'il y a beaucoup de méthodes à redéfinir
       - si la classe de configuration contient elle même des méthodes productrices.
     */



    
    @RestController
    public static class ChatBoker{

        public static class Message {
            public String author, text;
        }

        @SubscribeMapping("/chats/{id}/outgoing-messages")
        public void onSubscribe(@DestinationVariable int id){
            System.out.println("subscription callback for : "+id);

        }

        @SendTo("/chats/{id}/outgoing-messages")
        @MessageMapping("/chats/{id}/incoming-messages")
        public Message onMessage(@DestinationVariable int id, @Payload Message message, @Header String userId) {
            message.text = message.text.toUpperCase();
            return message;
        }
    }
	
	
    public static void main(String[] args) {
    	
        SpringApplication.run(MyApplication.class, args);
    }
}
