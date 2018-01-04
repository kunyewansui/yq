/**
 *
 */
package com.xiaosuokeji.server.util;

import java.util.*;

/**
 * 集合相关工具类
 *
 * Created with IntelliJ IDEA.
 * User: guobaikun
 * Date: 2017/12/8
 * Time: 14:00
 */
public final class CollectionUtils {

    public static <T> List<T> toList(T... elements) {
        List<T> list = new ArrayList<T>();
        Collections.addAll(list, elements);
        return list;
    }

    public static <T> Set<T> toSet(T... elements) {
        Set<T> set = new HashSet<T>();
        Collections.addAll(set, elements);
        return set;
    }

    public static <K, V> Map<K, V> toMap(K[] keys, V[] values) {

        if (keys.length != values.length) {
            throw new IllegalArgumentException("The length of keys and values are not equal.");
        }

        Map<K, V> map = new HashMap<K, V>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    public static <T> Map<T, T> toMap(T... elements) {

        if (elements.length % 2 != 0) {
            throw new IllegalArgumentException("Length is illegal.");
        }

        Map<T, T> map = new HashMap<T, T>();
        for (int i = 0; i < elements.length; i++) {
            map.put(elements[i], elements[++i]);
        }
        return map;
    }

    /**
     * 判断集合（Collection和Map，不包括数组）是否为空
     */
    public static <T> boolean isBlank(T t){
        if(t==null)
            return true;

        if(t instanceof Collection){
            return ((Collection)t).isEmpty();
        }else if(t instanceof Map){
            return ((Map)t).isEmpty();
        }
        throw new IllegalArgumentException("not collection class.");
    }

    public static void main(String[] args) {
        System.out.println(toList("1", "2", "3"));
        System.out.println(toSet(1, 2, 3));
        System.out.println(toMap("a", "aa", "b", "bb", "c", "cc"));
        System.out.println(isBlank(null));
        List list = new ArrayList();
        System.out.println("a1-->"+isBlank(list));
        list.add("gg");
        System.out.println("a2-->"+isBlank(list));
        Set set = new HashSet();
        System.out.println("a3-->"+isBlank(set));
        set.add("yy");
        System.out.println("a4-->"+isBlank(set));
        Map map = new HashMap();
        System.out.println("a5-->"+isBlank(map));
        map.put("key","nihao");
        System.out.println("a6-->"+isBlank(map));
    }


}
