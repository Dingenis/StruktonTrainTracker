package com.rsdt.anl;

import com.android.internal.util.Predicate;
import com.rsdt.anl.WebRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 22-6-2016
 * Description...
 */
public class RequestPool implements WebRequest.OnWebRequestsCompletedCallback{

    protected ArrayList<ExtendedRequestPoolListener> listeners = new ArrayList<>();

    protected ArrayList<WebRequest> queued = new ArrayList<>();

    public void addListener(ExtendedRequestPoolListener listener)
    {
        listeners.add(listener);
    }

    public void removeListener(ExtendedRequestPoolListener listener)
    {
        listeners.remove(listener);
    }

    public void query(WebRequest request)
    {
        queued.add(request);
    }

    public void executeAsync()
    {
        WebRequest[] requests = new WebRequest[queued.size()];
        queued.toArray(requests);
        new WebRequestTask(this).execute(requests);
    }

    @Override
    public void onWebRequestsCompleted(List<WebResponse> responses) {
        WebResponse currentResponse;
        ExtendedRequestPoolListener listener;
        for(int i = 0; i < responses.size(); i++)
        {
            currentResponse = responses.get(i);

            for(int l = 0; l < listeners.size(); l++)
            {
                listener = listeners.get(l);

                Predicate<WebResponse> condition = listener.getCondition();
                if(condition != null && condition.apply(currentResponse))
                {
                    listener.onWebRequestCompleted(currentResponse);
                }
            }
        }
    }

    public abstract static class ExtendedRequestPoolListener implements RequestPoolListener, WebRequest.OnWebRequestCompletedCallback
    {
        public ExtendedRequestPoolListener()
        {
            this.condtion = new Predicate<WebResponse>() {
                @Override
                public boolean apply(WebResponse response) {
                    return true;
                }
            };
        }

        public ExtendedRequestPoolListener(Predicate<WebResponse> condtion) { this.condtion = condtion; }

        Predicate<WebResponse> condtion;

        @Override
        public Predicate<WebResponse> getCondition() {
            return condtion;
        }
    }

    public interface RequestPoolListener
    {
        Predicate<WebResponse> getCondition();
    }


}
