package com.rpc.codec;

import org.junit.Test;

import javax.xml.stream.events.EndDocument;

import static org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder = new JSONEncoder();
        TestBean testBean = new TestBean();
        testBean.setAge(20);
        testBean.setName("rpc");
        byte[] bytes = encoder.encode(testBean);
        assertNotNull(bytes);
    }
}