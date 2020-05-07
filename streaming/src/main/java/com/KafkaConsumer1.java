package com;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;


public class KafkaConsumer1 {

    public static <T> Dataset<T> createKafkaConsumer(SparkSession sparkSession, KafkaConsumerEnum consumer){

        Dataset<byte[]> session = sparkSession
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "127.0.0.1:9092")
                .option("subscribe", "dashboard")
                .option("kafkaConsumer.pollTimeoutMs",1000)
                .option("auto.offset.reset", "earliest")
                .option("failOnDataLoss", false)
                .load()
                .selectExpr("CAST(value AS BINARY)")
                .as(Encoders.BINARY());
        return session.map((MapFunction<byte[], Object>) value ->  consumer.getObjectMapper().reader().forType(consumer.getTypeReference()).readValue(value), Encoders.kryo(consumer.getEncoderClass()));

    }
}
