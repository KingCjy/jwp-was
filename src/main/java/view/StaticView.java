package view;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

public class StaticView implements View {

  private byte[] body;

  public StaticView(byte[] body) {
    this.body = body;
  }

  @Override
  public void render(HttpRequest request, HttpResponse response) {
    if (body == null) {
      response.error(HttpStatus.NOT_FOUND);
      return;
    }
    response.setHttpVersion("HTTP1/1");
    response.setHttpStatus(HttpStatus.OK);
    response.setContentType(getContentType(request));
    response.setContentLength(body.length);
    response.setBody(body);
    response.render();
  }

  private String getContentType(HttpRequest httpRequest) {
    return httpRequest.getAccept();
  }

}
