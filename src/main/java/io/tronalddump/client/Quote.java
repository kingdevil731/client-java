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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The class representing a quote.
 *
 * @author Marcel Overdijk
 * @since 1.0.0
 */
public class Quote {

    private String id;
    private String value;
    private String sourceUrl;
    private Date date;
    private List<String> tags = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        getTags().add(tag);
    }

    public void addTags(List<String> tags) {
        getTags().addAll(tags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote = (Quote) o;
        if (id != null ? !id.equals(quote.id) : quote.id != null) {
            return false;
        }
        if (value != null ? !value.equals(quote.value) : quote.value != null) {
            return false;
        }
        if (sourceUrl != null ? !sourceUrl.equals(quote.sourceUrl) : quote.sourceUrl != null) {
            return false;
        }
        if (date != null ? !date.equals(quote.date) : quote.date != null) {
            return false;
        }
        return tags != null ? tags.equals(quote.tags) : quote.tags == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (sourceUrl != null ? sourceUrl.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", date='" + date + '\'' +
                ", tags=" + tags +
                '}';
    }
}
