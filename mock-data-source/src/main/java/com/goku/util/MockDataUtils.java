package com.goku.util;

import com.goku.producer.MockData;

public class MockDataUtils {

    public static void mockOrderStreamData(boolean orderedFlag,int sleepTime,int unOrderedNum){
        MockData mockData = new MockData();
        mockData.setNumber(100,1000,100);
        mockData.mockData(orderedFlag,sleepTime,unOrderedNum);
    }


}
