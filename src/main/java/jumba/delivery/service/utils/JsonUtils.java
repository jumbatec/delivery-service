package jumba.delivery.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

@Slf4j
public class JsonUtils {
    static final ObjectMapper objMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static Object mapJsonToClass(String rawDataString, Class<?> clazz) throws InternalServerErrorException {
        Object obj;
        try {
            obj = objMapper.readValue(rawDataString, clazz);
        } catch (JsonProcessingException e) {
            log.error("Error occurred mapping " + clazz.getName() + " object.", e);
            throw new InternalServerErrorException(
                    "An error occurred mapping json string to " + clazz.getName() + " . Error: {}",
                    e.getLocalizedMessage());
        }
        return obj;
    }

    /**
     * Converts json string into json Object
     *
     * @param jsonString Json String to be converted to json object
     * @return {@link JSONObject}
     * @throws InternalServerErrorException if an error occurs during parsing json string
     */
    public static JSONObject getJsonObject(String jsonString) throws InternalServerErrorException {
        String result;
        int i = jsonString.indexOf("{");
        result = jsonString.substring(i);
        log.info("Deserialization result {}", result);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            log.error("Error parsing json string. ", e);
            throw new InternalServerErrorException(
                    "An error occurred paring sale order string to json object. Error: {}. Json String: {}",
                    e.getLocalizedMessage(),
                    jsonString
            );
        }
        return jsonObject;
    }
}
