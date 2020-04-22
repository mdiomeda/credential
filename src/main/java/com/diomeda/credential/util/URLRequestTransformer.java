package com.diomeda.credential.util;

import org.apache.http.client.methods.RequestBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

import com.diomeda.credential.gateway.ApiGatewayProperties;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

public class URLRequestTransformer extends ProxyRequestTransformer {

  private ApiGatewayProperties apiGatewayProperties;

  public URLRequestTransformer() {
	  
  }
  public URLRequestTransformer(ApiGatewayProperties apiGatewayProperties) {
    this.apiGatewayProperties = apiGatewayProperties;
  }

  @Override
  public RequestBuilder transform(HttpServletRequest request) throws Exception, URISyntaxException {
    String requestURI = request.getRequestURI();
    URI uri;
    if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
      uri = new URI(getServiceUrl(requestURI, request) + "?" + request.getQueryString());
    } else {
      uri = new URI(getServiceUrl(requestURI, request));
    }

    RequestBuilder rb = RequestBuilder.create(request.getMethod());
    rb.setUri(uri);
    return rb;
  }

  private String getServiceUrl(String requestURI, HttpServletRequest httpServletRequest) throws Exception {

    ApiGatewayProperties.Endpoint endpoint =
            apiGatewayProperties.getEndpoints().stream()
                    .filter(e ->
                                    requestURI.matches(e.getPath()) && e.getMethod() == RequestMethod.valueOf(httpServletRequest.getMethod())
                    )
                    .findFirst().orElseThrow(() -> new Exception());
    return endpoint.getLocation() + requestURI;
  }
}
