package http;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parameters extends LinkedMultiValueMap<String, String> {

    private Parameters(MultiValueMap<String, String> parameters) {
        super(parameters);
    }

    public static Parameters from(String queryString) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Arrays.stream(queryString.split("&"))
                .map(parameter -> parameter.split("="))
                .forEach(keyValue -> parameters.put(keyValue[0], keyValue.length == 2 ? Arrays.asList(keyValue[1].split(",")) : new ArrayList<>()));

        return new Parameters(parameters);
    }

    public static Parameters emptyParameters() {
        return new Parameters(new LinkedMultiValueMap<>());
    }

    public String getParameter(String name) {
        List<String> values = get(name);

        return values == null ? null : String.join(", ", values.toArray(new String[] {}));
    }

    public String[] getParameterValues(String name) {
        return get(name).toArray(new String[]{});
    }
}
