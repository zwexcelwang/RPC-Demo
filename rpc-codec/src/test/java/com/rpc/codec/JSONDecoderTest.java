package com.rpc.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONDecoderTest {

    @Test
    public void decode() {
        Encoder encoder = new JSONEncoder();

        TestBean testBean = new TestBean();
        testBean.setAge(20);
        testBean.setName("rpc");
        byte[] bytes = encoder.encode(testBean);
//        assertNotNull(bytes);
        Decoder decoder = new JSONDecoder();
        TestBean testBean1 = decoder.decode(bytes, TestBean.class);
        assertEquals(testBean.getName(), testBean1.getName());
        assertEquals(testBean.getAge(), testBean1.getAge());

    }
}