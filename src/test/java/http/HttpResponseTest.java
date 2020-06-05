package http;

import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    public void setHeaderTest() {
        MultiValueMap<String, String> testHeaders = HttpHeaders.emptyHeaders();
        testHeaders.add(HttpHeaders.USER_AGENT, "Chrome/1.1");
        testHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(new ByteArrayOutputStream()));
        httpResponse.setContentType("application/json");
        httpResponse.setHeader(HttpHeaders.USER_AGENT, "Chrome/1.1");

        assertThat(httpResponse.getHeaderMap()).isEqualTo(testHeaders);
    }

    @Test
    public void writeHeaderTest() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));
        httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpResponse.setHeader(HttpHeaders.ACCEPT, "text/html");
        httpResponse.setHeader(HttpHeaders.USER_AGENT, "Chrome/1.1");

        String result = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Accept: text/html\r\n" +
                "User-Agent: Chrome/1.1\r\n" +
                System.lineSeparator();


        httpResponse.writeHeader();;
        httpResponse.getDataOutputStream().flush();

        String httpResult = new String(byteArrayOutputStream.toByteArray());

        assertThat(httpResult).isEqualTo(result);
    }

    @Test
    public void sendRedirectTest() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));
        httpResponse.sendRedirect("/index.html");

        String result = "HTTP/1.1 302 Found\r\n" +
                "Location: /index.html\r\n" +
                System.lineSeparator();

        String httpResult = new String(byteArrayOutputStream.toByteArray());

        assertThat(httpResult).isEqualTo(result);
    }

    @Test
    public void addCookieTest() {
        String resultString = "name=value; Path=/";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));

        httpResponse.addCookie(new Cookie("name", "value"));

        assertThat(httpResponse.getHeader(HttpHeaders.SET_COOKIE)).isEqualTo(resultString);
        assertThat(httpResponse.getCookies()).contains(new Cookie("name", "value"));
    }
}
