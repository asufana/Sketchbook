package com.github.asufana.typetalk;

import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import org.apache.http.util.*;
import org.junit.*;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class TypetalkAPITest {
    
    @Ignore
    @Test
    public void testname() throws Exception {
        
        final String clientId = "xxx";
        final String clientSecret = "xxx";
        
        final HttpClient client = HttpClientBuilder.create().build();
        
        final List<NameValuePair> tokenParams = new ArrayList<NameValuePair>();
        tokenParams.add(new BasicNameValuePair("client_id", clientId));
        tokenParams.add(new BasicNameValuePair("client_secret", clientSecret));
        tokenParams.add(new BasicNameValuePair("grant_type",
                                               "client_credentials"));
        tokenParams.add(new BasicNameValuePair("scope", "my"));
        
        final HttpPost tokenPost = new HttpPost("https://typetalk.in/oauth2/access_token");
        tokenPost.setEntity(new UrlEncodedFormEntity(tokenParams));
        
        final HttpResponse response = client.execute(tokenPost);
        final Map<String, String> json = new Gson().fromJson(EntityUtils.toString(response.getEntity(),
                                                                                  "UTF-8"),
                                                             new TypeToken<Map<String, String>>() {}.getType());
        final String accessToken = json.get("access_token");
        
        final HttpGet request = new HttpGet("https://typetalk.in/api/v1/profile");
        request.addHeader("Authorization", "Bearer " + accessToken);
        final HttpResponse profileResponse = client.execute(request);
        System.out.println(EntityUtils.toString(profileResponse.getEntity(),
                                                "UTF-8"));
    }
}
