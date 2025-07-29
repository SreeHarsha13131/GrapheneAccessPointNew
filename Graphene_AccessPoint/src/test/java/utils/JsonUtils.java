package utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public WebDriver driver;

    public JsonUtils(WebDriver driver) {
        this.driver = driver;
    }


    public List<String> getMetaNamesFromJson(String fileName) {
        List<String> metaNames = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("File not found in resources: " + fileName);
            }

            JsonNode rootNode = mapper.readTree(inputStream);
            JsonNode namesNode = rootNode.path("metaNames");
            if (namesNode.isArray()) {
                for (JsonNode name : namesNode) {
                    metaNames.add(name.asText());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metaNames;
    }
}
