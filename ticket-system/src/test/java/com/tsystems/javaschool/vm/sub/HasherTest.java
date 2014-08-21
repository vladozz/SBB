package com.tsystems.javaschool.vm.sub;

import org.junit.Assert;
import org.junit.Test;

public class HasherTest {
    @Test
    public void testReverse() throws Exception {
        long [] array1 = {1, 2 , 3};
        long [] array2 = {1, 2 , 3 , 4};
        long[] array3 = {3, 2, 1};
        long[] array4 = {4, 3, 2, 1};
        Hasher.reverseArray(array1);
        Hasher.reverseArray(array2);
        Assert.assertArrayEquals(array3, array1);
        Assert.assertArrayEquals(array4, array2);
    }
}