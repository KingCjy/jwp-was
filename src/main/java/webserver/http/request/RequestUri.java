package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

public class RequestUri {

    private static final String REQUEST_PATH_DELIMITER = "\\?";
    private static final int REQUEST_PATH_VALUE_COUNT = 2;

    private String path;
    private QueryParameter queryParameter;

    private RequestUri(String path, QueryParameter queryParameter) {
        this.path = path;
        this.queryParameter = queryParameter;
    }

    public static RequestUri parse(String requestPathValue) {
        String [] values = requestPathValue.split(REQUEST_PATH_DELIMITER);

        String path = values[0];
        String queryString = StringUtils.EMPTY;

        if (hasQueryString(values))
            queryString = values[1];

        return new RequestUri(path, new QueryParameter(queryString));
    }

    private static boolean hasQueryString(String[] values) {
        return values.length == REQUEST_PATH_VALUE_COUNT;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return queryParameter.getParameter(key);
    }

    @Override
    public String toString() {
        return "RequestUri{" +
                "path='" + path + '\'' +
                ", queryParameter=" + queryParameter +
                '}';
    }
}