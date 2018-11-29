package tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTool {

	private final static Logger log = LoggerFactory.getLogger(JsonTool.class);

	public static <T> String toJSON(T obj) {

		String jsonStr;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			log.error("Object to Json error", e);
			throw new RuntimeException(e);
		}
		return jsonStr;
	}

	public static <T> T toObject(String jsonText, Class<T> type) {
		T obj;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			obj = objectMapper.readValue(jsonText, type);
		} catch (Exception e) {
			log.error("Json to Object error", e);
			throw new RuntimeException(e);
		}
		return obj;
	}
}
