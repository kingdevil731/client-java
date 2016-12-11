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
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The class representing pagination information.
 *
 * @author Marcel Overdijk
 * @since 1.0.0
 */
public class Pageable implements Serializable {

    private static final long serialVersionUID = -6728660557581983504L;

    private int page;
    private int size;

    public Pageable(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("Page index must not be less than one!");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public Pageable next() {
        return new Pageable(page + 1, size);
    }

    public Pageable previous() {
        return page == 1 ? this : new Pageable(page - 1, size);
    }

    public Pageable first() {
        return new Pageable(1, size);
    }

    public Pageable previousOrFirst() {
        return page > 1 ? previous() : first();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pageable other = (Pageable) o;
        if (page != other.page) {
            return false;
        }
        return size == other.size;
    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + size;
        return result;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
