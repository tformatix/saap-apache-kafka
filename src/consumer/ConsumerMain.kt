package consumer

fun main() {
    val smaKafkaConsumer = SmaKafkaConsumer()
    smaKafkaConsumer.subscribe{ topic: String?, key: String?, value: String? ->
        println("Kafka: $topic / $key / $value")
    }
}