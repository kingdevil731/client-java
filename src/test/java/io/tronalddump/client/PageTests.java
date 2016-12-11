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

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;

/**
 * Tests for {@link Page}.
 *
 * @author Marcel Overdijk
 */
public class PageTests {

    @Test(expected = NullPointerException.class)
    public void testPreventsContentIsNull() {
        new Page<>(null, new Pageable(1, 10), 100);
    }

    @Test(expected = NullPointerException.class)
    public void testPreventsPageableIsNull() {
        new Page<>(new ArrayList<>(), null, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPreventsTotalIsLessThanZero() {
        new Page<>(new ArrayList<>(), new Pageable(1, 10), -1);
    }

    @Test
    public void testNoContent() {
        List<String> list = Collections.emptyList();
        Page<String> page = new Page<>(list, new Pageable(1, 10), 0);
        assertThat(page.getContent(), is(equalTo(list)));
        assertThat(page.getNumber(), is(equalTo(1)));
        assertThat(page.getSize(), is(equalTo(10)));
        assertThat(page.getNumberOfElements(), is(equalTo(0)));
        assertThat(page.getTotalPages(), is(equalTo(1)));
        assertThat(page.getTotalElements(), is(equalTo(0L)));
        assertThat(page.hasNext(), is(equalTo(false)));
        assertThat(page.hasPrevious(), is(equalTo(false)));
        assertThat(page.isFirst(), is(equalTo(true)));
        assertThat(page.isLast(), is(equalTo(true)));
        assertThat(page.nextPageable(), is(equalTo(null)));
        assertThat(page.previousPageable(), is(equalTo(null)));
    }

    @Test
    public void testSinglePage() {
        List<String> list = Arrays.asList("a", "b", "c");
        Page<String> page = new Page<>(list, new Pageable(1, 5), 3);
        assertThat(page.getContent(), is(equalTo(list)));
        assertThat(page.getNumber(), is(equalTo(1)));
        assertThat(page.getSize(), is(equalTo(5)));
        assertThat(page.getNumberOfElements(), is(equalTo(3)));
        assertThat(page.getTotalPages(), is(equalTo(1)));
        assertThat(page.getTotalElements(), is(equalTo(3L)));
        assertThat(page.hasNext(), is(equalTo(false)));
        assertThat(page.hasPrevious(), is(equalTo(false)));
        assertThat(page.isFirst(), is(equalTo(true)));
        assertThat(page.isLast(), is(equalTo(true)));
        assertThat(page.nextPageable(), is(equalTo(null)));
        assertThat(page.previousPageable(), is(equalTo(null)));
    }

    @Test
    public void testFullSinglePage() {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        Page<String> page = new Page<>(list, new Pageable(1, 5), 5);
        assertThat(page.getContent(), is(equalTo(list)));
        assertThat(page.getNumber(), is(equalTo(1)));
        assertThat(page.getSize(), is(equalTo(5)));
        assertThat(page.getNumberOfElements(), is(equalTo(5)));
        assertThat(page.getTotalPages(), is(equalTo(1)));
        assertThat(page.getTotalElements(), is(equalTo(5L)));
        assertThat(page.hasNext(), is(equalTo(false)));
        assertThat(page.hasPrevious(), is(equalTo(false)));
        assertThat(page.isFirst(), is(equalTo(true)));
        assertThat(page.isLast(), is(equalTo(true)));
        assertThat(page.nextPageable(), is(equalTo(null)));
        assertThat(page.previousPageable(), is(equalTo(null)));
    }

    @Test
    public void testMultiplePagesFirst() {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        Page<String> page = new Page<>(list, new Pageable(1, 5), 50);
        assertThat(page.getContent(), is(equalTo(list)));
        assertThat(page.getNumber(), is(equalTo(1)));
        assertThat(page.getSize(), is(equalTo(5)));
        assertThat(page.getNumberOfElements(), is(equalTo(5)));
        assertThat(page.getTotalPages(), is(equalTo(10)));
        assertThat(page.getTotalElements(), is(equalTo(50L)));
        assertThat(page.hasNext(), is(equalTo(true)));
        assertThat(page.hasPrevious(), is(equalTo(false)));
        assertThat(page.isFirst(), is(equalTo(true)));
        assertThat(page.isLast(), is(equalTo(false)));
        assertThat(page.nextPageable(), is(equalTo(new Pageable(2, 5))));
        assertThat(page.previousPageable(), is(equalTo(null)));
    }

    @Test
    public void testMultiplePagesLast() {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        Page<String> page = new Page<>(list, new Pageable(10, 5), 50);
        assertThat(page.getContent(), is(equalTo(list)));
        assertThat(page.getNumber(), is(equalTo(10)));
        assertThat(page.getSize(), is(equalTo(5)));
        assertThat(page.getNumberOfElements(), is(equalTo(5)));
        assertThat(page.getTotalPages(), is(equalTo(10)));
        assertThat(page.getTotalElements(), is(equalTo(50L)));
        assertThat(page.hasNext(), is(equalTo(false)));
        assertThat(page.hasPrevious(), is(equalTo(true)));
        assertThat(page.isFirst(), is(equalTo(false)));
        assertThat(page.isLast(), is(equalTo(true)));
        assertThat(page.nextPageable(), is(equalTo(null)));
        assertThat(page.previousPageable(), is(equalTo(new Pageable(9, 5))));
    }

    @Test
    public void testMultiplePagesMiddle() {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        Page<String> page = new Page<>(list, new Pageable(5, 5), 50);
        assertThat(page.getContent(), is(equalTo(list)));
        assertThat(page.getNumber(), is(equalTo(5)));
        assertThat(page.getSize(), is(equalTo(5)));
        assertThat(page.getNumberOfElements(), is(equalTo(5)));
        assertThat(page.getTotalPages(), is(equalTo(10)));
        assertThat(page.getTotalElements(), is(equalTo(50L)));
        assertThat(page.hasNext(), is(equalTo(true)));
        assertThat(page.hasPrevious(), is(equalTo(true)));
        assertThat(page.isFirst(), is(equalTo(false)));
        assertThat(page.isLast(), is(equalTo(false)));
        assertThat(page.nextPageable(), is(equalTo(new Pageable(6, 5))));
        assertThat(page.previousPageable(), is(equalTo(new Pageable(4, 5))));
    }
}
