package com.assignment.usermanagement.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;
    public static final String USER_EVENTS_TOPIC = "user-events";

    public void sendUserEvent(UserEvent userEvent) {
        kafkaTemplate.send(USER_EVENTS_TOPIC, userEvent);
            log.info("message sent to topic::"+USER_EVENTS_TOPIC+" with content::"+userEvent);
    }
}
