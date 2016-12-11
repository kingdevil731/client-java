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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The class representing a quote.
 *
 * @author Marcel Overdijk
 * @since 1.0.0
 */
public class Quote implements Serializable {

    private static final long serialVersionUID = -2502900563432123970L;

    private String id;
    private String value;
    private String sourceUrl;
    private Date date;
    private List<String> tags = new ArrayList<>();

    /**
     * Returns the unique id of the quote.
     *
     * @return the unique id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique id of the quote.
     *
     * @param id the unique id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the actual quote.
     *
     * @return the quote
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the actual quote.
     *
     * @param value the quote value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the source url of the quote.
     *
     * @return the source url
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * Sets the source url of the quote.
     *
     * @param sourceUrl the source url
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    /**
     * Returns the date of the quote.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the quote.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the tags associated with the quote.
     *
     * @return the tags, not null
     */
    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }

    /**
     * Sets the tags associated with the quote.
     *
     * @param tags the tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Adds a tag to the list of tags associated with the quote.
     *
     * @param tag the tag
     */
    public void addTag(String tag) {
        getTags().add(tag);
    }

    /**
     * Adds the tags to the list of tags associated with the quote.
     *
     * @param tags the tags
     */
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
        Quote other = (Quote) o;
        if (id != null ? !id.equals(other.id) : other.id != null) {
            return false;
        }
        if (value != null ? !value.equals(other.value) : other.value != null) {
            return false;
        }
        if (sourceUrl != null ? !sourceUrl.equals(other.sourceUrl) : other.sourceUrl != null) {
            return false;
        }
        if (date != null ? !date.equals(other.date) : other.date != null) {
            return false;
        }
        return tags != null ? tags.equals(other.tags) : other.tags == null;
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
