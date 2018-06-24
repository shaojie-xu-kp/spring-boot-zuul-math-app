package com.shaojie.www.mathapp.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaojie.www.mathapp.MathServiceFactory;
import com.shaojie.www.mathapp.exception.BadRequestException;
import com.shaojie.www.mathapp.model.MathDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MathServiceManager {

    private static final Logger LOG = LoggerFactory.getLogger(MathServiceManager.class);

    @Autowired
    private KafkaTemplate<String, MathDto> kafkaTemplate;

    @Value("${app.topic.calculation}")
    private String topic;

    @Autowired
    private MathServiceFactory serviceFactory;

    /**
     * created as a kind of database to store the calculation math result
     * the record gets inserted only when kafka topic is updated
     */
    private ConcurrentHashMap<Integer, MathDto> mathRepository = new ConcurrentHashMap<>();

    private static final AtomicInteger count = new AtomicInteger(0);

    public MathDto createMath(MathDto math) throws BadRequestException  {

        MathService mathService = serviceFactory.select(math.getAction());
        try {
            mathService.calculateResult(math);
        } catch (IllegalArgumentException illex) {
            throw new BadRequestException(illex.getMessage());
        }
        math.setId(count.get());
        send(math);
        return math;
    }

    public MathDto getMathBy(int id) {
        return mathRepository.get(id);
    }

    @KafkaListener(topics = "${app.topic.calculation}")
    private void updateRepository(@Payload String message) {
        LOG.info("received message='{}'", message);
        ObjectMapper mapper = new ObjectMapper();
        MathDto mathDto = null;
        try {
            mathDto = mapper.readValue(message, MathDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mathRepository.putIfAbsent(mathDto.getId(), mathDto);
        count.getAndIncrement();
    }

    private void send(MathDto message){
        LOG.info("sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }


}
