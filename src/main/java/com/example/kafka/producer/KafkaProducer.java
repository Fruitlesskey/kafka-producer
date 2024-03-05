package com.example.kafka.producer;

import static java.lang.String.format;

import com.example.kafka.dto.Customer;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendMessage(String message) {
    log.info(format("Sending message to MasterTopic:: %s", message));
    CompletableFuture<SendResult<String, Object>> myTopic = kafkaTemplate.send("MasterTopic1",3, null, message);
    myTopic.whenComplete((result, ex) -> {
      if (ex == null) {
        System.out.println("Sent message = [" + message +
            "] with offset = [" + result.getRecordMetadata().offset() + "]");
      } else {
        System.out.println("Unable to send message = [" +
            message + "] due to : " + ex.getMessage());
      }
    });
  }

  public void sendEvent(Customer customer) {
    try {

      log.info(format("Sending customer to MasterTopic:: %s", customer));
      CompletableFuture<SendResult<String, Object>> myTopic = kafkaTemplate.send("MasterTopic1",
          customer);
      myTopic.whenComplete((result, ex) -> {
        if (ex == null) {
          System.out.println("Sent customer = [" + customer +
              "] with offset = [" + result.getRecordMetadata().offset() + "]");
        } else {
          System.out.println("Unable to send customer = [" +
              customer + "] due to : " + ex.getMessage());
        }
      });
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }

}
