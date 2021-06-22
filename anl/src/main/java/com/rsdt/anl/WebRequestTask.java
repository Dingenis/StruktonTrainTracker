package com.rsdt.anl;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 22-6-2016
 * Description...
 */
class WebRequestTask extends AsyncTask<WebRequest, Integer, List<WebResponse>>
{

    WeakReference<WebRequest.OnWebRequestsCompletedCallback> callback;

    public WebRequestTask(WebRequest.OnWebRequestsCompletedCallback callback)
    {
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected List<WebResponse> doInBackground(WebRequest... params) {
        ArrayList<WebResponse> responses = new ArrayList<>();
        WebRequest current;
        for(int i = 0; i < params.length; i++)
        {
            current = params[i];

            TRYCATCH:
            try
            {
                if(current.getUrl() == null) break TRYCATCH;
                HttpURLConnection connection = (HttpURLConnection)current.getUrl().openConnection();

                switch (current.getMethod())
                {
                    case WebRequestMethod.POST:
                        if(current.hasData())
                        {
                            connection.setDoOutput(true);
                            connection.setRequestMethod(WebRequestMethod.POST);

                            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
                            streamWriter.write(current.getData());
                            streamWriter.flush();
                            streamWriter.close();
                        }
                        break;
                }

                InputStream response;
                if(connection.getResponseCode() == 200)
                {
                         /*
                        * Get the response stream.
                        * */
                    response = connection.getInputStream();
                }
                else
                {
                        /*
                        * Get the error stream.
                        * */
                    response = connection.getErrorStream();
                }

                /**
                 * Read the stream.
                 * */
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                bufferedReader.close();

                /**
                 * Create a response
                 * */
                responses.add(new WebResponse(current, builder.toString(), connection.getResponseCode()));

                current.setExecutionDate(new Date());

                connection.disconnect();

            }
            catch(Exception e)
            {
                /**
                 * Log a error.
                 * */
                Log.e("WebRequestTask", e.getMessage(), e);
            }
        }
        return responses;
    }

    @Override
    protected void onPostExecute(List<WebResponse> responses) {
        super.onPostExecute(responses);

        WebRequest.OnWebRequestsCompletedCallback callback = this.callback.get();
        if(callback != null)
        {
            callback.onWebRequestsCompleted(responses);
        }
    }
}