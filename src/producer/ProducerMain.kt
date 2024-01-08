package producer

import shared.SmaExtensions.toSmaMeasurement

fun main() {
    val smaKafkaProducer = SmaKafkaProducer()

    SmaMqttConsumer().subscribe {
        println("MQTT: $it")
        smaKafkaProducer.produce(it, it.toSmaMeasurement())
    }
}
