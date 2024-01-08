package shared

data class SmaMeasurement(
        val apiVersion: String,
        val name: String,
        val smaTime: Double,
        val measurements: HashMap<String, SmaMeasurementValue>
)