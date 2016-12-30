package com.paulhoang;

import com.google.gson.Gson;
import com.paulhoang.data.AppConfig;
import com.paulhoang.data.ShareData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Controller
@EnableAutoConfiguration
public class CollectController {

    private static final Logger LOG = LoggerFactory.getLogger(CollectController.class);
    private static AppConfig appConfig;
    private static KafkaProducer<String, String> PRODUCER;
    private static final Gson GSON = new Gson();

    @RequestMapping(value = "/")
    @ResponseBody
    public String home() {
        return "Hello, you reached the shares collector app. The endpoint your interested in is /collect as POST";
    }

    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    @ResponseBody
    public String collect(@RequestBody List<ShareData> sharePrices) {
        sharePrices.parallelStream().forEach(shareData -> {
            LOG.info("GOT SOME SHARE DATA: {}", shareData);
            final String recordString = GSON.toJson(shareData);
            final ProducerRecord record = new ProducerRecord<String, String>(appConfig.getTopic(), recordString);
            PRODUCER.send(record, ((metadata, exception) -> {
                if(exception != null){
                    LOG.error("exception occurred: {}", exception);
                }
            }));
        });
        return "thanks!";
    }

    public static void main(String[] args) throws Exception {
        setupKafkaProducer();
        SpringApplication.run(CollectController.class, args);
    }

    private static void setupKafkaProducer(){
        appConfig = loadConfiguration();

        Properties properties = new Properties();
        properties.put("bootstrap.servers", appConfig.getMessageBusLocation());
        properties.put("acks", "0");
        properties.put("retries", 0);
        properties.put("batch.size", 10);
        properties.put("linger.ms", 1);
        properties.put("client.id", "sharescollector");
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        PRODUCER = new KafkaProducer(properties);
    }

    private static AppConfig loadConfiguration() {
        final Yaml yaml = new Yaml();
        InputStream configInputStream;
            configInputStream = CollectController.class.getClassLoader()
                    .getResourceAsStream("appConfig.yml");

        return yaml.loadAs(configInputStream, AppConfig.class);
    }
}