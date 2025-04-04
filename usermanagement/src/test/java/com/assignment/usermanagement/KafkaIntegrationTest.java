// package com.assignment.usermanagement;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ContextConfiguration;

// import com.assignment.usermanagement.Entity.Role;
// import com.assignment.usermanagement.Kafka.KafkaProducerService;
// import com.assignment.usermanagement.Kafka.UserEvent;

// @SpringBootTest
// @ContextConfiguration(classes = KafkaTestContainerConfig.class)
// public class KafkaIntegrationTest {

//     @Autowired
//     private KafkaProducerService kafkaProducerService;

//     @Test
//     void shouldPublishKafkaEventSuccessfully() {
//         UserEvent event = UserEvent.builder()
//                 .id(100L)
//                 .username("akshay")
//                 .email("akshay@example.com")
//                 .role(Role.USER)
//                 .action("CREATED")
//                 .build();

//         kafkaProducerService.sendUserEvent(event);

//         // Add Kafka consumer logic later to verify
//     }
// }
