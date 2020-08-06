package cn.fan.testfunction;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class DesignPatternTest {
    @Test
    public void remove() {
        HashMap map = new HashMap();
        map.put("key", "1");
        map.put("key1", "2");
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        new TreeSet<>(new ArrayList<>());
        new ArrayList<>(new TreeSet<>()).toArray();
    }
}
