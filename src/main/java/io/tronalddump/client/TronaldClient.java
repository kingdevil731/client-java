/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.tronalddump.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * The official {@code https://api.tronalddump.io} Java client.
 *
 * @author Marcel Overdijk
 * @since 1.0.0
 */
public class TronaldClient {

    public static final String BASE_URL = "https://api.tronalddump.io";

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Returns a list of available tags.
     *
     * @return the list of available tags
     * @throws TronaldException in case an error occurs while retrieving the tags
     */
    public List<String> getTags() {
        try {
            HttpURLConnection conn = createConnection(BASE_URL + "/tags");
            int respCode = conn.getResponseCode();
            if (respCode == HttpURLConnection.HTTP_OK) {
                List<String> tags = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(new JSONTokener(conn.getInputStream()));
                JSONArray jsonTags = jsonObject.getJSONArray("_embedded");
                for (int i = 0; i < jsonTags.length(); i++) {
                    tags.add(jsonTags.getString(i));
                }
                return tags;
            } else {
                String errorMessage = getErrorMessage(conn);
                throw new TronaldException("Error retrieving tags: (#" + respCode + ") " + errorMessage);
            }
        } catch (IOException e) {
            throw new TronaldException("Error retrieving tags", e);
        }
    }

    /**
     * Returns the quote for the given id.
     *
     * @param id the unique quote id
     * @return the quote
     * @throws TronaldException in case an error occurs while retrieving the quote
     */
    public Quote getQuote(String id) {
        try {
            HttpURLConnection conn = createConnection(BASE_URL + "/quote/" + urlEncode(id));
            int respCode = conn.getResponseCode();
            if (respCode == HttpURLConnection.HTTP_OK) {
                JSONObject jsonObject = new JSONObject(new JSONTokener(conn.getInputStream()));
                return parseQuote(jsonObject);
            } else {
                String errorMessage = getErrorMessage(conn);
                throw new TronaldException("Error retrieving quote: (#" + respCode + ") " + errorMessage);
            }
        } catch (IOException e) {
            throw new TronaldException("Error retrieving quote", e);
        }
    }

    /**
     * Returns a random quote.
     *
     * @return the quote
     * @throws TronaldException in case an error occurs while retrieving the random quote
     */
    public Quote getRandomQuote() {
        return getRandomQuote(null);
    }

    /**
     * Returns a random quote with the given tag.
     *
     * @param tag the tag
     * @return the quote
     * @throws TronaldException in case an error occurs while retrieving the random quote
     */
    public Quote getRandomQuote(String tag) {
        try {
            HttpURLConnection conn;
            if (tag == null) {
                conn = createConnection(BASE_URL + "/random/quote");
            } else {
                conn = createConnection(BASE_URL + "/random/quote?tag=" + urlEncode(tag));
            }
            int respCode = conn.getResponseCode();
            if (respCode == HttpURLConnection.HTTP_OK) {
                JSONObject jsonObject = new JSONObject(new JSONTokener(conn.getInputStream()));
                return parseQuote(jsonObject);
            } else {
                String errorMessage = getErrorMessage(conn);
                throw new TronaldException("Error retrieving random quote: (#" + respCode + ") " + errorMessage);
            }
        } catch (IOException e) {
            throw new TronaldException("Error retrieving random quote", e);
        }
    }

    /**
     * Creates a new {@link HttpURLConnection} for the given url. Also sets the user agent.
     */
    private HttpURLConnection createConnection(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "tronalddump-io/client-java-" + getVersion());
        return conn;
    }

    /**
     * Returns the version string or {@code null} if it cannot be determined.
     * 
     * @see Package#getImplementationVersion()
     */
    private String getVersion() {
        Package pkg = TronaldClient.class.getPackage();
        return (pkg != null ? pkg.getImplementationVersion() : null);
    }

    /**
     * Parses the {@code JSONObject} and converts it to a {@link Quote} object.
     */
    private Quote parseQuote(JSONObject jsonObject) {
        Quote quote = new Quote();
        quote.setId(jsonObject.optString("quote_id"));
        quote.setValue(jsonObject.optString("value"));
        JSONObject jsonEmbedded = jsonObject.optJSONObject("_embedded");
        if (jsonEmbedded != null) {
            JSONArray jsonSources = jsonEmbedded.optJSONArray("source");
            if (jsonSources != null && jsonSources.length() > 0) {
                JSONObject jsonSource = jsonSources.optJSONObject(0);
                quote.setSourceUrl(jsonSource.optString("url"));
            }
        }
        String appearedAt = jsonObject.optString("appeared_at");
        if (appearedAt != null && appearedAt.length() > 0) {
            try {
                quote.setDate(DATE_FORMAT.parse(appearedAt));
            } catch (ParseException ignore) {
            }
        }
        JSONArray jsonTags = jsonObject.optJSONArray("tags");
        if (jsonTags != null) {
            for (int i = 0; i < jsonTags.length(); i++) {
                quote.addTag(jsonTags.getString(i));
            }
        }
        return quote;
    }

    private String getErrorMessage(HttpURLConnection conn) throws IOException {
        JSONObject jsonObject = new JSONObject(new JSONTokener(conn.getErrorStream()));
        return jsonObject.optString("message");
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new TronaldException("Unable to url encode string: " + s, e);
        }
    }
}
