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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The class representing a page.
 *
 * Inspired on Spring Data's Page (http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Page.html).
 *
 * @param <T> the type of the objects contained in the page
 * @author Marcel Overdijk
 * @since 1.0.0
 */
public class Page<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = -2555433043215957877L;

    private List<T> content = new ArrayList<>();
    private Pageable pageable;
    private long total;

    /**
     * Creates a new {@code Page} with the given content, pageable and total count.
     *
     * @param content the content
     * @param pageable the pageable
     * @param total the total, must not be less than 0
     */
    public Page(List<T> content, Pageable pageable, long total) {
        this.content = requireNonNull(content, "'content' must not be null");
        this.pageable = requireNonNull(pageable, "'pageable' must not be null");
        if (total < 0) {
            throw new IllegalArgumentException("'total' must not be less than 0");
        }
        this.total = total;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    /**
     * Returns whether the page has content.
     */
    public boolean hasContent() {
        return !content.isEmpty();
    }

    /**
     * Returns the page content as {@link List}.
     *
     * @return the page content
     */
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    /**
     * Returns the page number.
     *
     * @return the page number
     */
    public int getNumber() {
        return pageable.getPage();
    }

    /**
     * Returns the page size.
     *
     * @return the page size
     */
    public int getSize() {
        return pageable.getSize();
    }

    /**
     * Returns the number of elements currently on this {@link Page}.
     *
     * @return the number of elements
     */
    public int getNumberOfElements() {
        return content.size();
    }

    /**
     * Returns the number of total pages.
     *
     * @return the number of total pages
     */
    public int getTotalPages() {
        int val = (int) Math.ceil((double) total / (double) getSize());
        return val == 0 ? 1 : val;
    }

    /**
     * Returns the total amount of elements.
     *
     * @return the total amount of elements
     */
    public long getTotalElements() {
        return total;
    }

    /**
     * Returns whether there is a next {@link Page}.
     */
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    /**
     * Returns whether there is a previous {@link Page}.
     */
    public boolean hasPrevious() {
        return getNumber() > 1;
    }

    /**
     * Returns whether the current {@link Page} is the first one.
     */
    public boolean isFirst() {
        return !hasPrevious();
    }

    /**
     * Returns whether the current {@link Page} is the last one.
     */
    public boolean isLast() {
        return !hasNext();
    }

    /**
     * Returns the {@link Pageable} to request the next {@link Page}.
     *
     * @return the next pageable, or {@code null} if there is no next page
     */
    public Pageable nextPageable() {
        if (hasNext()) {
            return new Pageable(pageable.getPage() + 1, pageable.getSize());
        }
        return null;
    }

    /**
     * Returns the {@link Pageable} to request the previous {@link Page}.
     *
     * @return the previous pageable, or {@code null} if there is no previous page
     */
    public Pageable previousPageable() {
        if (hasPrevious()) {
            return new Pageable(pageable.getPage() - 1, pageable.getSize());
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Page<?> other = (Page<?>) o;
        if (total != other.total) {
            return false;
        }
        if (content != null ? !content.equals(other.content) : other.content != null) {
            return false;
        }
        return pageable != null ? pageable.equals(other.pageable) : other.pageable == null;

    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (pageable != null ? pageable.hashCode() : 0);
        result = 31 * result + (int) (total ^ (total >>> 32));
        return result;
    }

    @Override public String toString() {
        return "Page{" +
                "content=" + content +
                ", pageable=" + pageable +
                ", total=" + total +
                '}';
    }
}
