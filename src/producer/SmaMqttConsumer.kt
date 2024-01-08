package producer

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class SmaMqttConsumer() {
    companion object {
        private const val SERVER_URI = "tcp://localhost:1883"
        private const val CLIENT_ID = "subscribe_client"
        private const val TOPIC = "sma"
        private const val CONNECTION_TIMEOUT = 60
        private const val KEEP_ALIVE_INTERVAL = 60
        private const val QOS = 0
    }

    fun subscribe(onMessageArrived: (String) -> Unit) {
        MqttClient(SERVER_URI, CLIENT_ID, MemoryPersistence()).apply {
            setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable) {}
                override fun deliveryComplete(token: IMqttDeliveryToken) {}

                override fun messageArrived(topic: String, message: MqttMessage) {
                    onMessageArrived(String(message.payload))
                }
            })
            connect(MqttConnectOptions().apply {
                connectionTimeout = CONNECTION_TIMEOUT
                keepAliveInterval = KEEP_ALIVE_INTERVAL
            })
            subscribe(TOPIC, QOS)
        }
    }
}