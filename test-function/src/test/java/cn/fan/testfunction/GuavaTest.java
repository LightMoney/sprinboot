package cn.fan.testfunction;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * guava
 *
 * @date 2021/2/6 13:46
 */

public class GuavaTest {
    /**
     * object  equal 方法  避免null指针异常
     */
    @Test
    public void equalTest() {
        System.out.println(Objects.equal("a", "a"));
        System.out.println(Objects.equal(null, "a"));
        System.out.println(Objects.equal("a", null));
        System.out.println(Objects.equal(null, null));
        java.util.Objects.equals("a","b");
    }



    /**
     *返回 不为null 的字段
     *
     */
    @Test
    public void firstNonNullTest() {
        //name不为空
        String name = "张三";
        String nann = MoreObjects.firstNonNull(name, "哈哈");
        System.out.println(nann);

        //sex为null
        String sex = null;
        String sexx = MoreObjects.firstNonNull(sex, "哈哈");
        System.out.println(sexx);
    }

    /**
     * Guava中不可变对象和Collections工具类的unmodifiableSet/List/Map/etc的区别：
     *
     * 当Collections创建的不可变集合的wrapper类改变的时候，不可变集合也会改变，而Guava的Immutable集合保证确实是不可变的。
     * 这里只列举部分不可见集合  其余请自行查找
     */

    @Test
    public void testGuavaImmutable(){

        List<String> list=new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        ImmutableList<String> imlist=ImmutableList.copyOf(list);
        System.out.println("imlist："+imlist);

        ImmutableList<String> imOflist=ImmutableList.of("peida","jerry","harry");
        System.out.println("imOflist："+imOflist);

        ImmutableSortedSet<String> imSortList=ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");//固定遍历顺序
        System.out.println("imSortList："+imSortList);

        list.add("baby");
        //关键看这里是否imlist也添加新元素了
        System.out.println("list添加新元素之后看imlist:"+imlist);

        ImmutableSet<Color> imColorSet =
                ImmutableSet.<Color>builder()
                        .add(new Color(0, 255, 255))
                        .add(new Color(0, 191, 255))
                        .build();

        System.out.println("imColorSet:"+imColorSet);
    }

    /**
     * copyof 方法会在合适的情况下避免拷贝元素的操作
     */
    @Test
    public void testCopyOf(){
        ImmutableSet<String> imSet=ImmutableSet.of("peida","jerry","harry","lisa");
        System.out.println("imSet："+imSet);

        //set直接转list  2
        ImmutableList<String> imlist=ImmutableList.copyOf(imSet);
        System.out.println("imlist："+imlist);

        //list直接转SortedSet
        ImmutableSortedSet<String> imSortSet=ImmutableSortedSet.copyOf(imSet);
        System.out.println("imSortSet："+imSortSet);

        List<String> list=new ArrayList<String>();
        for(int i=0;i<=10;i++){
            list.add(i+"x");
        }
        System.out.println("list："+list);

        //截取集合部分元素
        ImmutableList<String> imInfolist=ImmutableList.copyOf(list.subList(2, 8));
        System.out.println("imInfolist："+imInfolist);
    }
}


