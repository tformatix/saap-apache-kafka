package producer

import shared.SmaKafkaConstants
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import shared.SmaMeasurement
import java.util.*


class SmaKafkaProducer {
    private val producer: Producer<String, String>

    init {
        val properties = Properties().apply {
            setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SmaKafkaConstants.BOOTSTRAP_SERVERS)
            setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SmaKafkaConstants.BOOTSTRAP_SERVERS)
            setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        }

        producer = KafkaProducer(properties)
    }

    fun produce(json: String, smaMeasurement: SmaMeasurement){
        val record = ProducerRecord(SmaKafkaConstants.SMA_TOPIC, smaMeasurement.name, json)
        producer.send(record)
    }
}