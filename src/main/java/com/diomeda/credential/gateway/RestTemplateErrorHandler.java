package com.diomeda.credential.gateway;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import com.diomeda.credential.util.RestUtil;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {


  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    log.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());
  }

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return RestUtil.isError(response.getStatusCode());
  }
}
