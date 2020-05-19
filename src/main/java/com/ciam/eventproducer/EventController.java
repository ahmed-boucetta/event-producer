package com.ciam.eventproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.ciam.eventproducer.EventProducerApplication.randomEvent;

@RestController
public class EventController {

    private final MessageChannel eventCiam;

    Logger logger = LoggerFactory.getLogger(EventProducerApplication.class);

    public EventController(MessageChannel eventCiam ) {
        this.eventCiam = eventCiam;
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public void sendMessage() {
        try {
            Event eventSend=randomEvent ();
            eventCiam.send ((MessageBuilder.withPayload(eventSend)
                    .build()));
            logger.info ("sendMessage");
        }catch (Exception e){
            throw new RuntimeException (e);
        }

    }
}
