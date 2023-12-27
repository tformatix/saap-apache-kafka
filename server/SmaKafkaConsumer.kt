package server

import shared.SmaKafkaConstants
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*


class SmaKafkaConsumer {
    private val consumer: Consumer<String, String>

    init {
        val properties = Properties().apply {
            setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SmaKafkaConstants.BOOTSTRAP_SERVERS)
            setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString())
            setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        }

        consumer = KafkaConsumer(properties)
    }

    fun subscribe(onRecordArrived: (String?, String?, String?) -> Unit){
        consumer.seekToBeginning(consumer.assignment());
        consumer.subscribe(listOf(SmaKafkaConstants.SMA_TOPIC))

        while (true) {
            val records = consumer.poll(Duration.ofSeconds(1))

            for (record in records) {
                onRecordArrived(record.topic(), record.key(), record.value())
            }
        }
    }
}