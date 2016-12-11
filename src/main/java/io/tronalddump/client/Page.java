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
 * @param <T> the type of the objects contained in the page
 * @author Marcel Overdijk
 * @since 1.0.0
 */
public class Page<T> implements Iterable, Serializable {

    private static final long serialVersionUID = -2555433043215957877L;

    private List<T> content = new ArrayList<>();
    private Pageable pageable;
    private long total;


    public Page(List<T> content, Pageable pageable, long total) {
        this.content = requireNonNull(content, "'content' must not be null");
        this.pageable = requireNonNull(pageable, "'pageable' must not be null");
        this.total = total;
    }

    @Override
    public Iterator iterator() {
        return content.iterator();
    }

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    public int getNumber() {
        return pageable.getPage();
    }

    public int getSize() {
        return pageable.getSize();
    }

    public int getNumberOfElements() {
        return content.size();
    }

    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    public long getTotalElements() {
        return total;
    }

    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    public boolean isFirst() {
        return !hasPrevious();
    }

    public boolean isLast() {
        return !hasNext();
    }

    public Pageable nextPageable() {
        return hasNext() ? pageable.next() : null;
    }

    public Pageable previousPageable() {
        if (hasPrevious()) {
            return pageable.previousOrFirst();
        }
        return null;
    }

    public Pageable firstPageable() {
        return pageable.first();
    }

    public Pageable lastPageable() {
        return new Pageable(getTotalPages(), pageable.getSize());
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
