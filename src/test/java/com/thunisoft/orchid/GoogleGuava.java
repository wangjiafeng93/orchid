package com.thunisoft.orchid;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.units.qual.K;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.collect.Table;
import com.google.common.io.Files;
import com.google.common.io.Resources;


/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-08-06 下午 15:31
 */
public class GoogleGuava {

    public static void main(String[] args) {
        testTime();
    }

    static void testCollection() {
        List<String> list = Lists.newArrayList();
        Set<String> set = Sets.newHashSet();
        Map<String, String> map = Maps.newHashMap();
        // 不变Collection的创建
        ImmutableList<String> iList = ImmutableList.of("a", "b", "c");
        ImmutableSet<String> iSet = ImmutableSet.of("e1", "e2");
        ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2");

    }

    static void testCollection2() {
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        map.put("aa", list);
        System.out.println(map.get("aa"));//[1, 2]
        //而现在
        Multimap<String, Integer> map1 = ArrayListMultimap.create();
        map1.put("aa", 1);
        map1.put("aa", 2);
        System.out.println(map1.get("aa"));  //[1, 2]
        //MultiSet: 无序+可重复   count()方法获取单词的次数  增强了可读性+操作简单
        Multiset<String> set = HashMultiset.create();
        set.add("a");
        set.add("b");
        set.add("a");
        set.add("a");
        System.out.println(set.count("a"));
        //Multimap: key-value  key可以重复
        Multimap<String, String> teachers = ArrayListMultimap.create();
        teachers.put("a", "1");
        teachers.put("b", "2");
        teachers.put("a", "3");

        Collection<String> a = teachers.get("a");
        System.out.println(a.stream().filter(e -> StringUtils.equals(e, "3")).collect(Collectors.toList()).get(0));
        System.out.println(a.stream().limit(1).findAny());
        //BiMap: 双向Map(Bidirectional Map) 键与值都不能重复
        BiMap<String, String> biMap = HashBiMap.create();
        //Table: 双键的Map Map--> Table-->rowKey+columnKey+value  //和sql中的联合主键有点像
        Table<String, String, Integer> tables = HashBasedTable.create();
    }

    static void testTrunCollect() {
        List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            str = str + "-" + list.get(i);
        }
        //use guava
        List<String> list1 = new ArrayList<String>();
        list1.add("aa");
        list1.add("bb");
        list1.add("cc");
        String result = Joiner.on("-").join(list1);
        //result为  aa-bb-cc
        //把map集合转换为特定规则的字符串
        Map<String, Integer> map = Maps.newHashMap();
        map.put("xiaoming", 12);
        map.put("xiaohong", 13);
        String result1 = Joiner.on(",").withKeyValueSeparator("=").join(map);

        //将String转换为特定的集合
        //use java
        List<String> list2 = new ArrayList<String>();
        String a = "1-2-3-4-5-6";
        String[] strs = a.split("-");
        for (int i = 0; i < strs.length; i++) {
            list2.add(strs[i]);
        }
        //use guava
        String str3 = "1-2-3-4-5-6";
        List<String> list3 = Splitter.on("-").splitToList(str);
        //list为  [1, 2, 3, 4, 5, 6]
        //guava还可以使用 omitEmptyStrings().trimResults() 去除空串与空格
        String str4 = "1-2-3-4-  5-  6   ";
        List<String> list4 = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(str4);
        System.out.println(list4);

        //将String转换为map
        String str5 = "xiaoming=11,xiaohong=23";
        Map<String, String> map5 = Splitter.on(",").withKeyValueSeparator("=").split(str5);
        //guava还支持多个字符切割，或者特定的正则分隔
        String input = "aa.dd,,ff,,.";
        List<String> result6 = Splitter.onPattern("[.|,]").omitEmptyStrings().splitToList(input);
    }

    static void testFilter() {
        // 判断匹配结果
        boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('b'); //true
        System.out.println(result);
        // 保留数字文本  CharMatcher.digit() 已过时   retain 保留
        //String s1 = CharMatcher.digit().retainFrom("abc 123 efg"); //123
        String s1 = CharMatcher.inRange('0', '9').retainFrom("abc 123 efg"); // 123

        // 删除数字文本  remove 删除
        // String s2 = CharMatcher.digit().removeFrom("abc 123 efg");    //abc  efg
        String s2 = CharMatcher.inRange('0', '9').removeFrom("abc 123 efg"); // abc  efg

        //按照条件过滤
        ImmutableList<String> names = ImmutableList.of("begin", "code", "Guava", "Java");
        Iterable<String> fitered = Iterables.filter(names, Predicates.or(Predicates.equalTo("Guava"), Predicates.equalTo("Java")));
        System.out.println(fitered); // [Guava, Java]

        //自定义过滤条件   使用自定义回调方法对Map的每个Value进行操作
        ImmutableMap<String, Integer> m = ImmutableMap.of("begin", 12, "code", 15);
        // Function<F, T> F表示apply()方法input的类型，T表示apply()方法返回类型
        Map<String, Integer> m2 = Maps.transformValues(m, new Function<Integer, Integer>() {
            public Integer apply(Integer input) {
                if(input>12){
                    return input;
                }else{
                    return input+1;
                }
            }
        });
        System.out.println(m2);   //{begin=13, code=15}
    }

    static void testSet(){
        HashSet<Integer> setA = Sets.newHashSet(1, 2, 3, 4, 5);
        HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);

        SetView<Integer> union = Sets.union(setA, setB);
        System.out.println("union:");
        for (Integer integer : union)
            System.out.println(integer);           //union 并集:12345867

        SetView<Integer> difference = Sets.difference(setA, setB);
        System.out.println("difference:");
        for (Integer integer : difference)
            System.out.println(integer);        //difference 差集:123

        SetView<Integer> intersection = Sets.intersection(setA, setB);
        System.out.println("intersection:");
        for (Integer integer : intersection)
            System.out.println(integer);  //intersection 交集:45
    }

    static void testMap(){
        HashMap<String, Integer> mapA = Maps.newHashMap();
        mapA.put("a", 1);mapA.put("b", 2);mapA.put("c", 3);

        HashMap<String, Integer> mapB = Maps.newHashMap();
        mapB.put("b", 20);mapB.put("c", 3);mapB.put("d", 4);

        MapDifference differenceMap = Maps.difference(mapA, mapB);
        differenceMap.areEqual();
        Map entriesDiffering = differenceMap.entriesDiffering();
        Map entriesOnlyLeft = differenceMap.entriesOnlyOnLeft();
        Map entriesOnlyRight = differenceMap.entriesOnlyOnRight();
        Map entriesInCommon = differenceMap.entriesInCommon();

        System.out.println(entriesDiffering);   // {b=(2, 20)}
        System.out.println(entriesOnlyLeft);    // {a=1}
        System.out.println(entriesOnlyRight);   // {d=4}
        System.out.println(entriesInCommon);    // {c=3}
    }
    static void testParam(){
        List<String> list = Lists.newArrayList("2","3");
        //use java
        if(list!=null && list.size()>0){

        }
        Preconditions.checkNotNull(list);
        String str = "";
        if(str!=null && str.length()>0)
        if(str !=null && !str.isEmpty())
//use guava
        if(!Strings.isNullOrEmpty(str)){

        }
        int count = 1;
//use java
        if (count <= 0) {
            throw new IllegalArgumentException("must be positive: " + count);
        }
//use guava
        Preconditions.checkArgument(count > 0, "must be positive: %s", count);
    }
    static void testOther(){
//        Person person = new Person("aa",11);
//        String str = MoreObjects.toStringHelper("Person").add("age", person.getAge()).toString();
//        System.out.println(str);
//输出Person{age=11}

    }
    static  void testOrder(){
        Ordering.natural();
//        natural()   对可排序类型做自然排序，如数字按大小，日期按先后排序
//        usingToString() 按对象的字符串形式做字典排序[lexicographical ordering]
//        from(Comparator)    把给定的Comparator转化为排序器
//        reverse()   获取语义相反的排序器
//        nullsFirst()    使用当前排序器，但额外把null值排到最前面。
//        nullsLast() 使用当前排序器，但额外把null值排到最后面。
//        compound(Comparator)    合成另一个比较器，以处理当前排序器中的相等情况。
//        lexicographical()   基于处理类型T的排序器，返回该类型的可迭代对象Iterable<T>的排序器。
//        onResultOf(Function)    对集合中元素调用Function，再按返回值用当前排序器排序。
//        Person person = new Person("aa",14);  //String name  ,Integer age
//        Person ps = new Person("bb",13);
//        Ordering<Person> byOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<Person,String>(){
//            public String apply(Person person){
//                return person.age.toString();
//            }
//        });
//        byOrdering.compare(person, ps);
//        System.out.println(byOrdering.compare(person, ps)); //1      person的年龄比ps大 所以输出1
    }

    static void testTime(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        for(int i=0; i<100000; i++){
            // do some thing
        }
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);
    }
    static void testFiles(){
        File file = new File("test.txt");
        List<String> list = null;
        try {
            list = Files.readLines(file, Charsets.UTF_8);
        } catch (Exception e) {
        }

//        Files.copy(from,to);  //复制文件
//        Files.deleteDirectoryContents(File directory); //删除文件夹下的内容(包括文件与子文件夹)
//        Files.deleteRecursively(File file); //删除文件或者文件夹
//        Files.move(File from, File to); //移动文件
        URL url = Resources.getResource("abc.xml"); //获取classpath根下的abc.xml文件url
    }
    static void testGuava(){
        LoadingCache<String,String> cahceBuilder= CacheBuilder
                .newBuilder()
                // 设置并发级别，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(10)
                // 设置缓存过期时间
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 设置缓存容器的初始容量
                .initialCapacity(10)
                // 设置缓存最大容量，超过之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(500)
                // 设置缓存的移除通知
                .removalListener(new RemovalListener<Object, Object>() {
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
//                        LOGGER.warn(notification.getKey() + " was removed, cause is " + notification.getCause());
                    }
                })
                .build(new CacheLoader<String, String>(){
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue="hello "+key+"!";
                        return strProValue;
                    }
                });
        System.out.println(cahceBuilder.apply("begincode"));  //hello begincode!
//        System.out.println(cahceBuilder.get("begincode")); //hello begincode!
//        System.out.println(cahceBuilder.get("wen")); //hello wen!     //获取缓存值，callable实现缓存回调
        System.out.println(cahceBuilder.apply("wen")); //hello wen!   //请求缓存值
        System.out.println(cahceBuilder.apply("da"));//hello da!
        cahceBuilder.put("begin", "code");   //设置缓存内容
//        System.out.println(cahceBuilder.get("begin")); //code
        cahceBuilder.invalidateAll();    //清除缓存
    }
}
