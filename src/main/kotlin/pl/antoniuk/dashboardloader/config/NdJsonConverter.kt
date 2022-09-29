//package pl.antoniuk.dashboardloader.config
//
//import org.springframework.http.converter.AbstractHttpMessageConverter
//
//class NdJsonConverter :
//    AbstractHttpMessageConverter<List<String>>(
//        // set target content type
//        MediaType("application", "x-ndjson")
//    ) {
//
//    private val objectMapper = jacksonObjectMapper()
//
//    override fun supports(clazz: Class<*>): Boolean {
//        return List::class.java.isAssignableFrom(clazz)
//    }
//
//    override fun writeInternal(t: List<Todo>, outputMessage: HttpOutputMessage) {
//        // If you use request only, nothing to implement convert code. Or you can throw an exception.
//        val responses = t.joinToString("\n") { objectMapper.writeValueAsString(it) }
//        outputMessage.body.bufferedWriter().use { it.write(responses) }
//    }
//
//    override fun readInternal(clazz: Class<out List<Todo>>, inputMessage: HttpInputMessage): List<Todo> {
//        val requestBody = inputMessage.body.bufferedReader().use { it.readLines() }
//        val requests = requestBody.map {
//            objectMapper.readValue<Todo>(it)
//        }
//        return requests
//    }
//}