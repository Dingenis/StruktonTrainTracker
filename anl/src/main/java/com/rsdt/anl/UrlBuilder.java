package com.rsdt.anl;

import java.net.URL;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 22-6-2016
 * Description...
 */
public class UrlBuilder {

    private String url = "";

    public UrlBuilder append(String arg)
    {
        url += arg;
        return this;
    }

    public URL build()
    {
        try
        {
            return new URL(url);
        } catch (Exception e)
        {
            return null;
        }
    }

}
