package com.example.kafka.controller;

import com.example.kafka.dto.Customer;
import com.example.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

  private final KafkaProducer kafkaProducer;

  @GetMapping("/{message}")
  public ResponseEntity<?> sendMessage(
      @PathVariable String message) {
    try {
      for (int i = 0; i < 1_00; i++) {

        kafkaProducer.sendMessage(message + " : " + i);
      }
      return ResponseEntity.ok("Message queued successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .build();
    }
  }

  @PostMapping("/send")
  public void sendEvents(@RequestBody Customer customer) {
    kafkaProducer.sendEvent(customer);

  }

}

