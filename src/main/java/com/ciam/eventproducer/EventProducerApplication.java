package com.ciam.eventproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@EnableSchemaRegistryClient
@EnableBinding(EventBinding.class)
public class EventProducerApplication {

	Logger logger = LoggerFactory.getLogger(EventProducerApplication.class);

	public static void main(String[] args){
		SpringApplication.run(EventProducerApplication.class, args);
	}

	@Bean
	public SchemaRegistryClient schemaRegistryClient(@Value("${spring.cloud.stream.schema-registry-client.endpoint}") String endpoint){
		ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
		client.setEndpoint(endpoint);
		return client;
	}
	@InboundChannelAdapter(channel = EventBinding.EVENT_OUT, poller = @Poller(fixedRate = "2000"))
	public Message<?> generate() {
		Event eventSend=randomEvent ();
		if(logger.isTraceEnabled ())
			logger.trace ("Send Message " + eventSend);
		logger.info ("send Message");
		return MessageBuilder.withPayload(eventSend)
				.build();
	}

	private Event randomEvent() {
		return  Event.newBuilder ().setType ("user_created").setUserBuilder (User.newBuilder ()
				.setEmail ("a.boucetta@hotmail.fr").setEmailVerified (true).setId ("123").setExternalId ("test").setHasPassword (true)).build ();
	};


}