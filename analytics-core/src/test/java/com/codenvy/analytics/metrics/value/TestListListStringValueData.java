/*
 *    Copyright (C) 2013 Codenvy.
 *
 */
package com.codenvy.analytics.metrics.value;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import com.codenvy.analytics.BaseTest;


import java.util.Arrays;
import java.util.Collections;

import org.apache.pig.data.Tuple;
import org.testng.annotations.Test;


/**
 * @author <a href="mailto:abazko@codenvy.com">Anatoliy Bazko</a>
 */
public class TestListListStringValueData extends BaseTest {

    private ValueData expectedValueData =
                                          new ListListStringValueData(
                                                                      Arrays.asList(new ListStringValueData[]{
            new ListStringValueData(
                                    Arrays.asList(new StringValueData[]{new StringValueData("ws1"), new StringValueData("pr1")})),
            new ListStringValueData(
                                    Arrays.asList(new StringValueData[]{new StringValueData("ws1"), new StringValueData("pr2")})),
            new ListStringValueData(
                                    Arrays.asList(new StringValueData[]{new StringValueData("ws2"), new StringValueData("pr1")}))
                                                                      }));

    @Test
    public void testValueDataFromTuple() throws Exception {
        Tuple innerTuple = tupleFactory.newTuple();
        innerTuple.append(tupleFactory.newTuple("ws1"));
        innerTuple.append(tupleFactory.newTuple("pr1"));
        Tuple tupleA = tupleFactory.newTuple(innerTuple);


        innerTuple = tupleFactory.newTuple();
        innerTuple.append(tupleFactory.newTuple("ws1"));
        innerTuple.append(tupleFactory.newTuple("pr2"));
        Tuple tupleB = tupleFactory.newTuple(innerTuple);

        innerTuple = tupleFactory.newTuple();
        innerTuple.append(tupleFactory.newTuple("ws2"));
        innerTuple.append(tupleFactory.newTuple("pr1"));
        Tuple tupleC = tupleFactory.newTuple(innerTuple);

        ValueData valueData =
                              ValueDataFactory.createValueData(ListListStringValueData.class, Arrays.asList(new Tuple[]{tupleA,
                                      tupleB,
                                      tupleC})
                                                                                                   .iterator());

        assertEquals(valueData, expectedValueData);
    }

    @Test
    public void testStoreLoad() throws Exception {
        valueManager.store(expectedValueData, uuid);
        assertEquals(valueManager.load(uuid), expectedValueData);
    }

    @Test
    public void testStoreLoadEmptyValueData() throws Exception {
        ValueData expectedValueData = new ListListStringValueData(Collections.<ListStringValueData> emptyList());

        valueManager.store(expectedValueData, uuid);
        assertEquals(valueManager.load(uuid), expectedValueData);
    }

    @Test
    public void testStoreLoadEmptyCollection() throws Exception {
        ValueData expectedValueData =
                                      new ListListStringValueData(
                                                                  Arrays.asList(new ListStringValueData[]{}));

        valueManager.store(expectedValueData, uuid);
        assertEquals(valueManager.load(uuid), expectedValueData);
    }

    @Test
    public void testStoreLoadEmptyString() throws Exception {
        ValueData expectedValueData =
                                      new ListListStringValueData(
                                                                  Arrays.asList(new ListStringValueData[]{
                                                                          new ListStringValueData(
                                                                                                  Arrays.asList(new StringValueData[]{
                                                                                                          new StringValueData(
                                                                                                                              "")})),

                                                                  }));

        valueManager.store(expectedValueData, uuid);
        assertEquals(valueManager.load(uuid), expectedValueData);
    }

    @Test
    public void testEqualsSameOrder() throws Exception {
        ValueData newValueData =
                                 new ListListStringValueData(
                                                             Arrays.asList(new ListStringValueData[]{
                                                                     new ListStringValueData(
                                                                                             Arrays.asList(new StringValueData[]{
                                                                                                     new StringValueData("ws1"),
                                                                                                     new StringValueData("pr1")})),
                                                                     new ListStringValueData(
                                                                                             Arrays.asList(new StringValueData[]{
                                                                                                     new StringValueData("ws1"),
                                                                                                     new StringValueData("pr2")})),
                                                                     new ListStringValueData(
                                                                                             Arrays.asList(new StringValueData[]{
                                                                                                     new StringValueData("ws2"),
                                                                                                     new StringValueData("pr1")}))
                                                             }));

        assertEquals(newValueData, expectedValueData);
    }

    @Test
    public void testNotEqualsDifferentOrder() throws Exception {
        ValueData newValueData =
                                 new ListListStringValueData(
                                                             Arrays.asList(new ListStringValueData[]{
                                                                     new ListStringValueData(
                                                                                             Arrays.asList(new StringValueData[]{
                                                                                                     new StringValueData("ws1"),
                                                                                                     new StringValueData("pr2")})),
                                                                     new ListStringValueData(
                                                                                             Arrays.asList(new StringValueData[]{
                                                                                                     new StringValueData("ws1"),
                                                                                                     new StringValueData("pr1")})),

                                                                     new ListStringValueData(
                                                                                             Arrays.asList(new StringValueData[]{
                                                                                                     new StringValueData("ws2"),
                                                                                                     new StringValueData("pr1")}))
                                                             }));

        assertNotEquals(newValueData, expectedValueData);
    }

    @Test
    public void testNotEquals() throws Exception {
        ValueData newValueData =
                                 new ListListStringValueData(
                                                             Arrays.asList(new ListStringValueData[]{
                                                                     new ListStringValueData(
                                                                                             Arrays.asList(new StringValueData[]{
                                                                                                     new StringValueData("ws1"),
                                                                                                     new StringValueData("pr2")})),
                                                                     new ListStringValueData(
                                                                                             Arrays.asList(new StringValueData[]{
                                                                                                     new StringValueData("ws2"),
                                                                                                     new StringValueData("pr1")}))
                                                             }));

        assertNotEquals(newValueData, expectedValueData);
    }
}
