package com.topcoder.direct.services.kafka;

import java.util.Hashtable;
import com.google.gson.*;
import java.net.*;
import java.io.*;

public class DirectKafkaProducer {

    private static DirectKafkaProducer myProducer = null;
    public static synchronized DirectKafkaProducer getInstance() {
        if (myProducer == null) { myProducer = new DirectKafkaProducer(); }
        return myProducer;
    }

    public final static int CHALLENGE_CREATE = 0;

    private Hashtable<Integer, String> topics = null;

    protected DirectKafkaProducer() {
        topics = new Hashtable<Integer, String>();
        topics.put(CHALLENGE_CREATE, "challenge.notification.create");
    }

    public void postChallengeCreate(long challengeId)
        throws IOException/*, MalformedUrlException*/ {
        CreateChallengeMessage ccm = new CreateChallengeMessage();
        ccm.legacyId = ""+challengeId;
        ccm.topic = topics.get(CHALLENGE_CREATE);
        Gson gson = new Gson();
        String requestBody = gson.toJson(ccm);
        postEvent(requestBody);
    }

    private String getUrl() {
        // get bus api Url from config
        return System.getenv("bus.api.url");
    }

    private void doPost(String url, String json)
        throws IOException {
        // make HTTPS POST call to api
        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection con = null;
        try {
            URL _url = new URL(url);
            con = (HttpURLConnection) _url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            os = con.getOutputStream();
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            // read just to be sure it sent
            is = con.getInputStream();
        } finally {
            if (os != null) { try { os.close(); } catch (Throwable t) {} }
            if (is != null) { try { is.close(); } catch (Throwable t) {} }
        }
    }

    private void postEvent(final String json)
        throws IOException {
        new Thread(new Runnable() {
            public void run() {
                final String url = getUrl();
                try {
                    doPost(url, json);
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }).start();
    }
}