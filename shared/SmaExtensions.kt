package shared

import org.json.JSONObject

object SmaExtensions {
    fun String.toSmaMeasurement(): SmaMeasurement {
        val jsonObject = JSONObject(this)

        val apiVersion = jsonObject.getString("api_version")
        val name = jsonObject.getString("name")
        val smaTime = jsonObject.getDouble("sma_time")

        val measurementsMap = HashMap<String, SmaMeasurementValue>()

        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val obj = jsonObject[key]
            if (obj is JSONObject) {
                val value = obj.get("value")
                val time = if(obj.has("time")) obj.getLong("time") else null

                measurementsMap[key] = SmaMeasurementValue(value, time)
            }
        }

        return SmaMeasurement(apiVersion, name, smaTime, measurementsMap)
    }
}