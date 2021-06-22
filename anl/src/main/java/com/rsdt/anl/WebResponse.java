package com.rsdt.anl;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 22-6-2016
 * Description...
 */
public class WebResponse {


    protected WebResponse(WebRequest request, String data, int responseCode)
    {
        this.request = request;
        this.data = data;
        this.responseCode = responseCode;
    }

    private WebRequest request;

    public WebRequest getRequest() {
        return request;
    }

    private String data;

    public String getData() {
        return data;
    }

    private int responseCode;

    public int getResponseCode() {
        return responseCode;
    }
}
