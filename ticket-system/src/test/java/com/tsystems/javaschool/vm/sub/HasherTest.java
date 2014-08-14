package com.tsystems.javaschool.vm.sub;

import org.junit.Assert;
import org.junit.Test;

public class HasherTest {
    @Test
    public void testReverse() throws Exception {
        int [] array1 = {1, 2 , 3};
        int [] array2 = {1, 2 , 3 , 4};
        int[] array3 = {3, 2, 1};
        int[] array4 = {4, 3, 2, 1};
        Hasher.reverseArray(array1);
        Hasher.reverseArray(array2);
        Assert.assertArrayEquals(array3, array1);
        Assert.assertArrayEquals(array4, array2);
    }
}