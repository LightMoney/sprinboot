package cn.fan.testfunction;

import cn.fan.testfunction.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamTest {


    //测试stream
    @Test
    public void testStream() {
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流 主线程 单线程
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流  多线程
//        通过这个方法可以修改这个值，这是全局属性。
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");
//        有些操作使用并发流的性能会比顺序流的性能更差，比如limit，findFirst ， 依赖元素顺序的操作在并发流中是极其消耗性能的 。findAny的性能就会好很多，应为不依赖顺序。
//-----流一旦被其对应方法操作过后会关闭
        //-------------------------使用 Pattern.splitAsStream() 方法，将字符串分隔成流--------------------------------------
        Pattern pattern = Pattern.compile(",");
        Stream<String> stringStream = pattern.splitAsStream("1,2,3,4");
//        stringStream.forEach(System.out::println);
        List<Integer> collect = stringStream.map(x -> Integer.parseInt(x)).collect(Collectors.toList());
        System.out.println(collect);

    }

    /**
     * filter：过滤出流中的某些元素(得到是满足过滤条件的数据)
     * limit(n)：获取n个元素
     * skip(n)：跳过n元素，配合limit(n)可实现分页
     * distinct：通过流中元素的 hashCode() 和 equals() 去除重复元素
     * <p>
     * 注意执行顺序不同效果不同
     */
    @Test
    public void testStream1() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 6, 6, 77);
        Stream<Integer> skip = integerStream
                .filter(x -> x > 3)
                .skip(2)
                .limit(6)
                .distinct();
        skip.forEach(System.out::println);
    }

    /**
     * 映射
     * map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
     */
    @Test
    public void testStream2() {
        List<String> list = Arrays.asList("a,b,c,d", "1,2,3");

//将每个元素转成一个新的且不带逗号的元素
        Stream<String> s1 = list.stream().map(s -> s.replaceAll(",", ""));
        s1.forEach(System.out::println); // abc  123

        Map<Integer, Object> collect = list.stream().collect(Collectors.toMap(x -> x.length(), x -> x));

        Stream<String> s3 = list.stream().flatMap(s -> {
            //将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        });
        s3.forEach(System.out::println); // a b c 1 2 3
    }


    /**
     * 排序
     * sorted()：自然排序，流中元素需实现Comparable接口
     * sorted(Comparator com)：定制排序，自定义Comparator排序器
     */
    @Test
    public void testStream3() {
        List<String> list = Arrays.asList("aa", "ff", "dd");
//String 类自身已实现Compareable接口
        list.stream().sorted().forEach(System.out::println);// aa dd ff
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("aa", 30);
        Student s4 = new Student("dd", 40);
        List<Student> studentList = Arrays.asList(s1, s2, s3, s4);
//自定义排序：先按姓名升序，姓名相同则按年龄升序
        studentList.stream().sorted(
                (o1, o2) -> {
                    if (o1.getName().equals(o2.getName())) {
                        return o1.getAge() - o2.getAge();
                    } else {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
        ).forEach(System.out::println);


    }

    /**
     * peek：如同于map，能得到流中的每一个元素。但map接收的是一个Function表达式，有返回值；而peek接收的是Consumer表达式，没有返回值。
     */
    @Test
    public void testStream4() {
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        List<Student> studentList = Arrays.asList(s1, s2);

        studentList.stream()
                .peek(o -> o.setAge(100))
                .forEach(System.out::println);
    }

    /**
     * allMatch：接收一个 Predicate 函数，当流中每个元素都符合该断言时才返回true，否则返回false
     * 　　noneMatch：接收一个 Predicate 函数，当流中每个元素都不符合该断言时才返回true，否则返回false
     * 　　anyMatch：接收一个 Predicate 函数，只要流中有一个元素满足该断言则返回true，否则返回false
     * 　　findFirst：返回流中第一个元素
     * 　　findAny：返回流中的任意元素
     * 　　count：返回流中元素的总个数
     * 　　max：返回流中元素最大值
     * 　　min：返回流中元素最小值
     */
    @Test
    public void testStream5() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        boolean allMatch = list.stream().allMatch(e -> e > 10); //false
        boolean noneMatch = list.stream().noneMatch(e -> e > 10); //true
        boolean anyMatch = list.stream().anyMatch(e -> e > 4); //true

        Integer findFirst = list.stream().findFirst().get(); //1
        Integer findAny = list.stream().findAny().get(); //1

        long count = list.stream().count(); //5
        Integer max1 = list.stream().max(Integer::compareTo).get(); //5
        Integer min = list.stream().min(Integer::compareTo).get(); //1　　

        Student s1 = new Student("aa", 30);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("aa", 10);
        Student s4 = new Student("dd", 40);
        List<Student> studentList = Arrays.asList(s1, s2, s3, s4);
//        Optional<Student> max = studentList.stream().max(Comparator.comparingInt(Student::getAge));
        Optional<Student> max = studentList.stream().min(Comparator.comparing(Student::getName));
        System.out.println(max);
    }

    /**
     * reduce 操作可以实现从一组值中生成一个值
     */
    @Test
    public void testStream6() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4);
        Integer reduce = integerStream.reduce(0, (acc, x) -> acc + x);
        log.info("" + reduce);
    }

    /**
     * 常用的流操作是将其分解成两个集合，Collectors.partitioningBy帮我们实现了，接收一个Predicate函数式接口。
     */
    @Test
    public void testStream7() {
        //省略List<student> students的初始化
        Student s1 = new Student("aa", 30);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("aa", 10);
        Student s4 = new Student("dd", 40);
        List<Student> students = Arrays.asList(s1, s2, s3, s4);
        Map<Boolean, List<Student>> listMap = students.stream().collect(
                Collectors.partitioningBy(student -> student.getAge()>20));
//                        student.getName().
//                        contains("aa")));
//将接收的list分为满足条件的true 和未满足条件的false 的两个部分
        log.info(listMap.toString());
    }

    /**
     * 根据类型分组 同sql中 group  by
     */
    @Test
    public void testStream8(){
        //省略List<student> students的初始化
        Student s1 = new Student("aa", 30);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("aa", 10);
        Student s4 = new Student("dd", 40);
        List<Student> students = Arrays.asList(s1, s2, s3, s4);
        Map<String, List<Student>> collect = students.stream().collect(Collectors.groupingBy(student -> student.getName()));
        log.info(""+collect);
    }

    /**
     *  字符串拼接
     * Collectors.joining()和map（）匹配使用
     * joining接收三个参数，第一个是分界符，第二个是前缀符，第三个是结束符。也可以不传入参数Collectors.joining()，这样就是直接拼接。
     */
    @Test
    public void testStream9(){
        Student s1 = new Student("aa", 30);
        Student s2 = new Student("bb", 20);
        Student s4 = new Student("dd", 40);
        List<Student> students = Arrays.asList(s1, s2, s4);
        String collect = students.stream().map(Student::getName).collect(Collectors.joining(",", "[", "]"));
        log.info(""+collect);
    }
}
