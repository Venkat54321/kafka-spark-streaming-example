package com;

import com.test.beans.RecordBean;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.ForeachWriter;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.datasources.jdbc.JDBCOptions;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.Trigger;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;


public class StreamTesting {

    public static void main(String[] args) throws Exception{

        SparkSession sparkSession = SparkSession.builder()
                                        .appName("Test").config("kafka.bootstrap.servers","localhost:9092")
                                        .master("local[1]")
                                        .getOrCreate();
       String jdbcUrl = "jdbc:mysql://localhost:3306/dashboard_test";
       String username = "root";
       String pwd = "Root@123";



       while (true) {
           Dataset<RecordBean> dataset = KafkaConsumer1.createKafkaConsumer(sparkSession, KafkaConsumerEnum.DASHBOARD);
          // StreamingQuery query = dataset.writeStream().outputMode("append").format("console").start();

           //query.awaitTermination();

    /*       StreamingQuery query =  dataset.writeStream()
                   .outputMode("append")
                   .outputMode(OutputMode.Append())
                   .option(JDBCOptions.JDBC_URL(),jdbcUrl)
                   .option(JDBCOptions.JDBC_TABLE_NAME(),"events")
                   .option(JDBCOptions.JDBC_DRIVER_CLASS(), "com.mysql.jdbc.Driver")
                   .option(JDBCOptions.JDBC_BATCH_INSERT_SIZE(), "5")
                   .option("user", username)
                   .option("password", pwd)
                   .trigger(Trigger.ProcessingTime("10 seconds"))
                   .start();*/

          // query.awaitTermination();

          StreamingQuery query =  dataset.writeStream().foreach(new ForeachWriter<RecordBean>() {
               @Override
               public boolean open(long l, long l1) {
                   System.out.println("Started............");
                   return true;
               }

               @Override
               public void process(RecordBean recordBean) {
                     System.out.println("Recode Bean " + recordBean.getType().name() + " value is  " + recordBean.getValue());
               }

               @Override
               public void close(Throwable throwable) {

               }
           }).start();
          query.awaitTermination();
       }

    }
}
