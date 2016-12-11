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

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.tronalddump.client.TronaldClient.DATE_FORMAT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link TronaldClient}.
 *
 * @author Marcel Overdijk
 */
public class TronaldClientTests {

    private TronaldClient client;

    @Before
    public void setUp() {
        this.client = new TronaldClient();
    }

    @Test
    public void testGetTags() {
        List<String> tags = client.getTags();
        assertThat(tags, is(notNullValue()));
        assertThat(tags, hasSize(greaterThan(0)));
        assertThat(tags, hasItems(equalTo("Hillary Clinton"), equalTo("Barack Obama")));
    }

    @Test
    public void testGetQuote() {
        String id = "wAgIgzV1S9OARKhfun3f0A";
        Quote quote = client.getQuote(id);
        assertThat(quote.getId(), is(equalTo(id)));
        assertThat(quote.getValue(), is(equalTo("26,000 unreported sexual assults in the military-only 238 convictions. What did these geniuses expect when they put men & women together?")));
        assertThat(quote.getSourceUrl(), is(equalTo("https://twitter.com/realDonaldTrump/status/331907383771148288")));
        assertThat(DATE_FORMAT.format(quote.getDate()), is(equalTo("2013-05-08T00:00:00")));
        assertThat(quote.getTags(), hasItems(equalTo("Sexual Assults"), equalTo("Military")));
    }

    @Test
    public void testGetRandomQuote() {
        Quote quote = client.getRandomQuote();
        assertThat(quote.getId(), is(notNullValue()));
        assertThat(quote.getValue(), is(notNullValue()));
        assertThat(quote.getSourceUrl(), is(notNullValue()));
        assertThat(quote.getDate(), is(notNullValue()));
        assertThat(quote.getTags(), is(notNullValue()));
    }

    @Test
    public void testGetRandomQuoteWithTag() {
        Quote quote = client.getRandomQuote("Hillary Clinton");
        assertThat(quote.getId(), is(notNullValue()));
        assertThat(quote.getValue(), is(notNullValue()));
        assertThat(quote.getSourceUrl(), is(notNullValue()));
        assertThat(quote.getDate(), is(notNullValue()));
        assertThat(quote.getTags(), is(notNullValue()));
    }

    @Test
    public void testGetRandomQuoteWithUnknownTag() {
        try {
            client.getRandomQuote("foo");
            fail();
        } catch (TronaldHttpException e) {
            assertThat(e.getHttpStatus(), is(equalTo(404)));
            assertThat(e.getMessage(), is(equalTo("Could not find a random quote.")));
        }
    }

    @Test
    public void testSearch() {
        Page<Quote> page = client.search("Hillary Clinton");
        assertThat(page.getNumber(), is(equalTo(1)));
        assertThat(page.getSize(), is(equalTo(25)));
        assertThat(page.getNumberOfElements(), is(equalTo(25)));
        assertThat(page.getTotalElements(), is(greaterThan(25L)));
        assertThat(page.getTotalPages(), is(greaterThan(1)));
        assertThat(page.getContent(), hasSize(equalTo(25)));
    }

    @Test
    public void testSearchNoResults() {
        Page<Quote> page = client.search("foobar");
        assertThat(page.getNumber(), is(equalTo(1)));
        assertThat(page.getSize(), is(equalTo(25)));
        assertThat(page.getNumberOfElements(), is(equalTo(0)));
        assertThat(page.getTotalElements(), is(equalTo(0L)));
        assertThat(page.getTotalPages(), is(equalTo(0)));
        assertThat(page.getContent(), hasSize(equalTo(0)));
    }
}
