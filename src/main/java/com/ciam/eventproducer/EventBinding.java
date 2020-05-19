package com.ciam.eventproducer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;



public interface EventBinding {

    String EVENT_OUT = "eventCiam";
    @Output(EVENT_OUT)
    MessageChannel eventOut();
}

