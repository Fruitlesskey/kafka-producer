package com.example.kafka;

import static org.awaitility.Awaitility.await;

import com.example.kafka.dto.Customer;
import com.example.kafka.producer.KafkaProducer;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
@Slf4j
class KafkaProducerApplicationTests {

  @Autowired
  private KafkaProducer kafkaProducer;

  @Container
  static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

  @DynamicPropertySource
  public static void initKafkaProperties(DynamicPropertyRegistry registry) {
    log.info("Init initKafkaProperties started...");
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    log.info("Init initKafkaProperties ended...");

  }

  @Test
   void testSendEvent() {
    kafkaProducer.sendEvent(new Customer(111, "test user", "test@test", 1134144));
    await().pollInterval(Duration.ofSeconds(3))
        .atMost(10, TimeUnit.SECONDS)
        .untilAsserted(() ->{
      //assert statement
    });
  }

}
