package io.swagger.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.client.WebResource.Builder;

import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import java.net.URLEncoder;

import java.io.IOException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.DataInputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import io.swagger.client.auth.Authentication;
import io.swagger.client.auth.HttpBasicAuth;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.auth.OAuth;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-09-23T12:52:56.012+08:00")
public class ApiClient {
  private Map<String, Client> hostMap = new HashMap<String, Client>();
  private Map<String, String> defaultHeaderMap = new HashMap<String, String>();
  private boolean debugging = false;
  private String basePath = "http://petstore.swagger.io/v2";
  private JSON json = new JSON();

  private Map<String, Authentication> authentications;

  private int statusCode;
  private Map<String, List<String>> responseHeaders;

  private DateFormat dateFormat;

  public ApiClient() {
    // Use ISO 8601 format for date and datetime.
    // See https://en.wikipedia.org/wiki/ISO_8601
    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    // Use UTC as the default time zone.
    this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

    // Set default User-Agent.
    setUserAgent("Java-Swagger");

    // Setup authentications (key: authentication name, value: authentication).
    authentications = new HashMap<String, Authentication>();
    authentications.put("api_key", new ApiKeyAuth("header", "api_key"));
    authentications.put("petstore_auth", new OAuth());
    // Prevent the authentications from being modified.
    authentications = Collections.unmodifiableMap(authentications);
  }

  public String getBasePath() {
    return basePath;
  }

  public ApiClient setBasePath(String basePath) {
    this.basePath = basePath;
    return this;
  }

  /**
   * Gets the status code of the previous request
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * Gets the response headers of the previous request
   */
  public Map<String, List<String>> getResponseHeaders() {
    return responseHeaders;
  }

  /**
   * Get authentications (key: authentication name, value: authentication).
   */
  public Map<String, Authentication> getAuthentications() {
    return authentications;
  }

  /**
   * Get authentication for the given name.
   *
   * @param authName The authentication name
   * @return The authentication, null if not found
   */
  public Authentication getAuthentication(String authName) {
    return authentications.get(authName);
  }

  /**
   * Helper method to set username for the first HTTP basic authentication.
   */
  public void setUsername(String username) {
    for (Authentication auth : authentications.values()) {
      if (auth instanceof HttpBasicAuth) {
        ((HttpBasicAuth) auth).setUsername(username);
        return;
      }
    }
    throw new RuntimeException("No HTTP basic authentication configured!");
  }

  /**
   * Helper method to set password for the first HTTP basic authentication.
   */
  public void setPassword(String password) {
    for (Authentication auth : authentications.values()) {
      if (auth instanceof HttpBasicAuth) {
        ((HttpBasicAuth) auth).setPassword(password);
        return;
      }
    }
    throw new RuntimeException("No HTTP basic authentication configured!");
  }

  /**
   * Helper method to set API key value for the first API key authentication.
   */
  public void setApiKey(String apiKey) {
    for (Authentication auth : authentications.values()) {
      if (auth instanceof ApiKeyAuth) {
        ((ApiKeyAuth) auth).setApiKey(apiKey);
        return;
      }
    }
    throw new RuntimeException("No API key authentication configured!");
  }

  /**
   * Helper method to set API key prefix for the first API key authentication.
   */
  public void setApiKeyPrefix(String apiKeyPrefix) {
    for (Authentication auth : authentications.values()) {
      if (auth instanceof ApiKeyAuth) {
        ((ApiKeyAuth) auth).setApiKeyPrefix(apiKeyPrefix);
        return;
      }
    }
    throw new RuntimeException("No API key authentication configured!");
  }

  /**
   * Set the User-Agent header's value (by adding to the default header map).
   */
  public ApiClient setUserAgent(String userAgent) {
    addDefaultHeader("User-Agent", userAgent);
    return this;
  }

  /**
   * Add a default header.
   *
   * @param key The header's key
   * @param value The header's value
   */
  public ApiClient addDefaultHeader(String key, String value) {
    defaultHeaderMap.put(key, value);
    return this;
  }

  /**
   * Check that whether debugging is enabled for this API client.
   */
  public boolean isDebugging() {
    return debugging;
  }

  /**
   * Enable/disable debugging for this API client.
   *
   * @param debugging To enable (true) or disable (false) debugging
   */
  public ApiClient setDebugging(boolean debugging) {
    this.debugging = debugging;
    return this;
  }

  /**
   * Get the date format used to parse/format date parameters.
   */
  public DateFormat getDateFormat() {
    return dateFormat;
  }

  /**
   * Set the date format used to parse/format date parameters.
   */
  public ApiClient getDateFormat(DateFormat dateFormat) {
    this.dateFormat = dateFormat;
    return this;
  }

  /**
   * Parse the given string into Date object.
   */
  public Date parseDate(String str) {
    try {
      return dateFormat.parse(str);
    } catch (java.text.ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Format the given Date object into string.
   */
  public String formatDate(Date date) {
    return dateFormat.format(date);
  }

  /**
   * Format the given parameter object into string.
   */
  public String parameterToString(Object param) {
    if (param == null) {
      return "";
    } else if (param instanceof Date) {
      return formatDate((Date) param);
    } else if (param instanceof Collection) {
      StringBuilder b = new StringBuilder();
      for(Object o : (Collection)param) {
        if(b.length() > 0) {
          b.append(",");
        }
        b.append(String.valueOf(o));
      }
      return b.toString();
    } else {
      return String.valueOf(param);
    }
  }

  /*
    Format to {@code Pair} objects.
  */
  public List<Pair> parameterToPairs(String collectionFormat, String name, Object value){
    List<Pair> params = new ArrayList<Pair>();

    // preconditions
    if (name == null || name.isEmpty() || value == null) return params;

    Collection valueCollection = null;
    if (value instanceof Collection) {
      valueCollection = (Collection) value;
    } else {
      params.add(new Pair(name, parameterToString(value)));
      return params;
    }

    if (valueCollection.isEmpty()){
      return params;
    }

    // get the collection format
    collectionFormat = (collectionFormat == null || collectionFormat.isEmpty() ? "csv" : collectionFormat); // default: csv

    // create the params based on the collection format
    if (collectionFormat.equals("multi")) {
      for (Object item : valueCollection) {
        params.add(new Pair(name, parameterToString(item)));
      }

      return params;
    }

    String delimiter = ",";

    if (collectionFormat.equals("csv")) {
      delimiter = ",";
    } else if (collectionFormat.equals("ssv")) {
      delimiter = " ";
    } else if (collectionFormat.equals("tsv")) {
      delimiter = "\t";
    } else if (collectionFormat.equals("pipes")) {
      delimiter = "|";
    }

    StringBuilder sb = new StringBuilder() ;
    for (Object item : valueCollection) {
      sb.append(delimiter);
      sb.append(parameterToString(item));
    }

    params.add(new Pair(name, sb.substring(1)));

    return params;
  }

  /**
   * Select the Accept header's value from the given accepts array:
   *   if JSON exists in the given array, use it;
   *   otherwise use all of them (joining into a string)
   *
   * @param accepts The accepts array to select from
   * @return The Accept header to use. If the given array is empty,
   *   null will be returned (not to set the Accept header explicitly).
   */
  public String selectHeaderAccept(String[] accepts) {
    if (accepts.length == 0) return null;
    if (StringUtil.containsIgnoreCase(accepts, "application/json")) return "application/json";
    return StringUtil.join(accepts, ",");
  }

  /**
   * Select the Content-Type header's value from the given array:
   *   if JSON exists in the given array, use it;
   *   otherwise use the first one of the array.
   *
   * @param contentTypes The Content-Type array to select from
   * @return The Content-Type header to use. If the given array is empty,
   *   JSON will be used.
   */
  public String selectHeaderContentType(String[] contentTypes) {
    if (contentTypes.length == 0) return "application/json";
    if (StringUtil.containsIgnoreCase(contentTypes, "application/json")) return "application/json";
    return contentTypes[0];
  }

  /**
   * Escape the given string to be used as URL query value.
   */
  public String escapeString(String str) {
    try {
      return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
    } catch (UnsupportedEncodingException e) {
      return str;
    }
  }

  /**
   * Serialize the given Java object into string according the given
   * Content-Type (only JSON is supported for now).
   */
  public String serialize(Object obj, String contentType) throws ApiException {
    if (contentType.startsWith("application/json")) {
      return json.serialize(obj);
    } else {
      throw new ApiException(400, "can not serialize object into Content-Type: " + contentType);
    }
  }

  /**
   * Deserialize response body to Java object according to the Content-Type.
   */
  public <T> T deserialize(ClientResponse response, TypeRef returnType) throws ApiException {
    String contentType = null;
    List<String> contentTypes = response.getHeaders().get("Content-Type");
    if (contentTypes != null && !contentTypes.isEmpty())
      contentType = contentTypes.get(0);
    if (contentType == null)
      throw new ApiException(500, "missing Content-Type in response");

    String body;
    if (response.hasEntity())
      body = (String) response.getEntity(String.class);
    else
      body = "";

    if (contentType.startsWith("application/json")) {
      return json.deserialize(body, returnType);
    } else if (returnType.getType().equals(String.class)) {
      // Expecting string, return the raw response body.
      return (T) body;
    } else {
      throw new ApiException(
        500,
        "Content type \"" + contentType + "\" is not supported for type: "
          + returnType.getType()
      );
    }
  }

  private ClientResponse getAPIResponse(String path, String method, List<Pair> queryParams, Object body, byte[] binaryBody, Map<String, String> headerParams, Map<String, Object> formParams, String accept, String contentType, String[] authNames) throws ApiException {

    if (body != null && binaryBody != null){
      throw new ApiException(500, "either body or binaryBody must be null");
    }

    updateParamsForAuth(authNames, queryParams, headerParams);

    Client client = getClient();

    StringBuilder b = new StringBuilder();
    b.append("?");
    if (queryParams != null){
      for (Pair queryParam : queryParams){
        if (!queryParam.getName().isEmpty()) {
          b.append(escapeString(queryParam.getName()));
          b.append("=");
          b.append(escapeString(queryParam.getValue()));
          b.append("&");
        }
      }
    }

    String querystring = b.substring(0, b.length() - 1);

    Builder builder;
    if (accept == null)
      builder = client.resource(basePath + path + querystring).getRequestBuilder();
    else
      builder = client.resource(basePath + path + querystring).accept(accept);

    for (String key : headerParams.keySet()) {
      builder = builder.header(key, headerParams.get(key));
    }
    for (String key : defaultHeaderMap.keySet()) {
      if (!headerParams.containsKey(key)) {
        builder = builder.header(key, defaultHeaderMap.get(key));
      }
    }

    String encodedFormParams = null;
    if (contentType.startsWith("multipart/form-data")) {
      FormDataMultiPart mp = new FormDataMultiPart();
      for (Entry<String, Object> param: formParams.entrySet()) {
        if (param.getValue() instanceof File) {
          File file = (File) param.getValue();
          mp.field(param.getKey(), file.getName());
          mp.bodyPart(new FileDataBodyPart(param.getKey(), file, MediaType.MULTIPART_FORM_DATA_TYPE));
        } else {
          mp.field(param.getKey(), parameterToString(param.getValue()), MediaType.MULTIPART_FORM_DATA_TYPE);
        }
      }
      body = mp;
    } else if (contentType.startsWith("application/x-www-form-urlencoded")) {
      encodedFormParams = this.getXWWWFormUrlencodedParams(formParams);
    }

    ClientResponse response = null;

    if ("GET".equals(method)) {
      response = (ClientResponse) builder.get(ClientResponse.class);
    } else if ("POST".equals(method)) {
      if (encodedFormParams != null) {
        response = builder.type(contentType).post(ClientResponse.class, encodedFormParams);
      } else if (body == null) {
        if(binaryBody == null)
            response = builder.post(ClientResponse.class, null);
        else
            response = builder.type(contentType).post(ClientResponse.class, binaryBody);
      } else if (body instanceof FormDataMultiPart) {
        response = builder.type(contentType).post(ClientResponse.class, body);
      } else {
        response = builder.type(contentType).post(ClientResponse.class, serialize(body, contentType));
      }
    } else if ("PUT".equals(method)) {
      if (encodedFormParams != null) {
        response = builder.type(contentType).put(ClientResponse.class, encodedFormParams);
      } else if(body == null) {
        if(binaryBody == null)
            response = builder.put(ClientResponse.class, null);
        else
            response = builder.type(contentType).put(ClientResponse.class, binaryBody);
      } else {
        response = builder.type(contentType).put(ClientResponse.class, serialize(body, contentType));
      }
    } else if ("DELETE".equals(method)) {
      if (encodedFormParams != null) {
        response = builder.type(contentType).delete(ClientResponse.class, encodedFormParams);
      } else if(body == null) {
        if(binaryBody == null)
            response = builder.delete(ClientResponse.class);
        else
            response = builder.type(contentType).delete(ClientResponse.class, binaryBody);
      } else {
        response = builder.type(contentType).delete(ClientResponse.class, serialize(body, contentType));
      }
    } else {
      throw new ApiException(500, "unknown method type " + method);
    }
    return response;
  }

  /**
   * Invoke API by sending HTTP request with the given options.
   *
   * @param path The sub-path of the HTTP URL
   * @param method The request method, one of "GET", "POST", "PUT", and "DELETE"
   * @param queryParams The query parameters
   * @param body The request body object - if it is not binary, otherwise null
   * @param binaryBody The request body object - if it is binary, otherwise null
   * @param headerParams The header parameters
   * @param formParams The form parameters
   * @param accept The request's Accept header
   * @param contentType The request's Content-Type header
   * @param authNames The authentications to apply
   * @return The response body in type of string
   */
   public <T> T invokeAPI(String path, String method, List<Pair> queryParams, Object body, byte[] binaryBody, Map<String, String> headerParams, Map<String, Object> formParams, String accept, String contentType, String[] authNames, TypeRef returnType) throws ApiException {

    ClientResponse response = getAPIResponse(path, method, queryParams, body, binaryBody, headerParams, formParams, accept, contentType, authNames);

    statusCode = response.getStatusInfo().getStatusCode();
    responseHeaders = response.getHeaders();

    if(response.getStatusInfo() == ClientResponse.Status.NO_CONTENT) {
      return null;
    } else if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
      if (returnType == null)
        return null;
      else
        return deserialize(response, returnType);
    } else {
      String message = "error";
      String respBody = null;
      if (response.hasEntity()) {
        try {
          respBody = String.valueOf(response.getEntity(String.class));
          message = respBody;
        } catch (RuntimeException e) {
          // e.printStackTrace();
        }
      }
      throw new ApiException(
        response.getStatusInfo().getStatusCode(),
        message,
        response.getHeaders(),
        respBody);
    }
  }
 /**
   * Invoke API by sending HTTP request with the given options - return binary result
   *
   * @param path The sub-path of the HTTP URL
   * @param method The request method, one of "GET", "POST", "PUT", and "DELETE"
   * @param queryParams The query parameters
   * @param body The request body object - if it is not binary, otherwise null
   * @param binaryBody The request body object - if it is binary, otherwise null
   * @param headerParams The header parameters
   * @param formParams The form parameters
   * @param accept The request's Accept header
   * @param contentType The request's Content-Type header
   * @param authNames The authentications to apply
   * @return The response body in type of string
   */
 public byte[] invokeBinaryAPI(String path, String method, List<Pair> queryParams, Object body, byte[] binaryBody, Map<String, String> headerParams, Map<String, Object> formParams, String accept, String contentType, String[]authNames) throws ApiException {

    ClientResponse response = getAPIResponse(path, method, queryParams, body, binaryBody, headerParams, formParams, accept, contentType, authNames);

    if(response.getStatusInfo() == ClientResponse.Status.NO_CONTENT) {
      return null;
    }
    else if(response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
      if(response.hasEntity()) {
    	DataInputStream stream = new DataInputStream(response.getEntityInputStream());
    	byte[] data = new byte[response.getLength()];
    	try {
    	  stream.readFully(data);
    	} catch (IOException ex) {
    	  throw new ApiException(500, "Error obtaining binary response data");
    	}
        return data;
      }
      else {
        return new byte[0];
      }
    }
    else {
      String message = "error";
      if(response.hasEntity()) {
        try{
          message = String.valueOf(response.getEntity(String.class));
        }
        catch (RuntimeException e) {
          // e.printStackTrace();
        }
      }
      throw new ApiException(
                response.getStatusInfo().getStatusCode(),
                message);
    }
  }

  /**
   * Update query and header parameters based on authentication settings.
   *
   * @param authNames The authentications to apply
   */
  private void updateParamsForAuth(String[] authNames, List<Pair> queryParams, Map<String, String> headerParams) {
    for (String authName : authNames) {
      Authentication auth = authentications.get(authName);
      if (auth == null) throw new RuntimeException("Authentication undefined: " + authName);
      auth.applyToParams(queryParams, headerParams);
    }
  }

  /**
   * Encode the given form parameters as request body.
   */
  private String getXWWWFormUrlencodedParams(Map<String, Object> formParams) {
    StringBuilder formParamBuilder = new StringBuilder();

    for (Entry<String, Object> param : formParams.entrySet()) {
      String keyStr = param.getKey();
      String valueStr = parameterToString(param.getValue());
      try {
        formParamBuilder.append(URLEncoder.encode(param.getKey(), "utf8"))
            .append("=")
            .append(URLEncoder.encode(valueStr, "utf8"));
        formParamBuilder.append("&");
      } catch (UnsupportedEncodingException e) {
        // move on to next
      }
    }

    String encodedFormParams = formParamBuilder.toString();
    if (encodedFormParams.endsWith("&")) {
      encodedFormParams = encodedFormParams.substring(0, encodedFormParams.length() - 1);
    }

    return encodedFormParams;
  }

  /**
   * Get an existing client or create a new client to handle HTTP request.
   */
  private Client getClient() {
    if(!hostMap.containsKey(basePath)) {
      Client client = Client.create();
      if (debugging)
        client.addFilter(new LoggingFilter());
      hostMap.put(basePath, client);
    }
    return hostMap.get(basePath);
  }
}
