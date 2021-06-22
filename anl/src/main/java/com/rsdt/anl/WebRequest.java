package com.rsdt.anl;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 22-6-2016
 * Description...
 */
public class WebRequest {

    public static final String REQUEST_ID_UNKNOWN = "REQUEST_ID_UNKNOWN";

    /**
     * The identifier of the request(optional).
     * */
    private String id = REQUEST_ID_UNKNOWN;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * The url of the request.
     * */
    private URL url;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * The data that should be send(optional).
     * */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * The method of the request, post or get.
     * */
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * The date of execution.
     * */
    private Date executionDate;

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    protected WebRequest() {}

    public boolean hasData()
    {
        return (data != null || !data.isEmpty());
    }


    public void executeAsync(OnWebRequestCompletedCallback callback)
    {
        new WebRequestTask(new CallbackHolder(callback) {
            @Override
            public void onWebRequestsCompleted(List<WebResponse> responses) {
                OnWebRequestCompletedCallback callback = this.callback.get();
                if(callback != null)
                {
                    if(responses.size() > 0) {
                        callback.onWebRequestCompleted(responses.get(0));
                    }
                }
            }
        }).execute(this);
    }

    private abstract class CallbackHolder implements OnWebRequestsCompletedCallback
    {
        protected WeakReference<OnWebRequestCompletedCallback> callback;

        public CallbackHolder(OnWebRequestCompletedCallback callback)
        {
            this.callback = new WeakReference<>(callback);
        }
    }

    public interface OnWebRequestCompletedCallback
    {
        void onWebRequestCompleted(WebResponse response);
    }

    public interface OnWebRequestsCompletedCallback
    {
        void onWebRequestsCompleted(List<WebResponse> responses);
    }

    public static class Builder
    {

        WebRequest buffer = new WebRequest();

        public Builder setId(String id)
        {
            buffer.id = id;
            return this;
        }

        public Builder setUrl(URL url)
        {
            buffer.url = url;
            return this;
        }

        public Builder setData(String data)
        {
            buffer.data = data;
            return this;
        }

        public Builder setMethod(String method)
        {
            buffer.method = method;
            return this;
        }

        public WebRequest create()
        {
            return buffer;
        }

    }

}
