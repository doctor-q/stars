package cc.doctor.stars.crawler.utils;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenzhi03
 */
public class CollectUtils {
    public static <K, V> MapBuilder<K, V> mapBuilder() {
        return new MapBuilder<>();
    }

    public static class MapBuilder<K, V> {
        private final LinkedHashMap<K, V> kvs;

        public MapBuilder() {
            kvs = new LinkedHashMap<>();
        }

        public MapBuilder<K, V> entry(K k, V v) {
            kvs.put(k, v);
            return this;
        }

        public MapBuilder<K, V> entry(boolean b, K k, V v) {
            if (b) {
                kvs.put(k, v);
            }
            return this;
        }

        public HashMap<K, V> toHashMap() {
            return new HashMap<>(kvs);
        }

        public LinkedHashMap<K, V> toLinkedHashMap() {
            return new LinkedHashMap<>(kvs);
        }
    }

    public static String join(Collection<?> list) {
        return join(list, ",");
    }

    public static String join(Collection<?> list, String separator) {
        if (list == null) {
            return "";
        }
        return list.stream().map(Object::toString).collect(Collectors.joining(separator));
    }

    public static String join(String separator, Object... list) {
        if (list == null) {
            return "";
        }
        return join(Arrays.stream(list).filter(Objects::nonNull).collect(Collectors.toList()), separator);
    }

    public static List<Integer> splitToInts(String s) {
        if (!StringUtils.isEmpty(s)) {
            return Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static List<String> splitToStrings(String s) {
        return splitToStrings(s, ",");
    }

    public static List<String> splitToStrings(String s, String split) {
        if (!StringUtils.isEmpty(s)) {
            return Arrays.stream(s.split(split)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @SafeVarargs
    public static <T> List<T> concat(List<T>... lists) {
        List<T> ts = new ArrayList<>();
        for (List<T> list : lists) {
            if (list != null) {
                ts.addAll(list);
            }
        }
        return ts;
    }

    /**
     * 并集，t1||t2
     */
    @SafeVarargs
    public static <T> Set<T> union(Collection<T>... sets) {
        Set<T> set = new HashSet<>();
        for (Collection<T> ts : sets) {
            set.addAll(ts);
        }
        return set;
    }

    public static <T> Set<T> union(Collection<Collection<T>> sets) {
        Set<T> set = new HashSet<>();
        for (Collection<T> ts : sets) {
            set.addAll(ts);
        }
        return set;
    }

    /**
     * 交集，t1&t2
     */
    @SafeVarargs
    public static <T> Set<T> intersection(Collection<T>... sets) {
        return intersection(Arrays.asList(sets));
    }

    public static <T> Set<T> intersection(Collection<Collection<T>> sets) {
        if (sets.size() == 0) {
            return Collections.emptySet();
        }
        Set<T> union = union(sets);
        Set<T> intersection = new HashSet<>();
        for (T t : union) {
            boolean intersect = true;
            for (Collection<T> set : sets) {
                if (!set.contains(t)) {
                    intersect = false;
                    break;
                }
            }
            if (intersect) {
                intersection.add(t);
            }
        }
        return intersection;
    }

    /**
     * 求交集，忽略null
     *
     * @return null如果所有集合都是空
     */
    @SafeVarargs
    public static <T> Set<T> intersectionIgnoreNull(Collection<T>... sets) {
        if (sets.length == 0) {
            return Collections.emptySet();
        }
        List<Collection<T>> nonNulls = Arrays.stream(sets).filter(Objects::nonNull).collect(Collectors.toList());
        if (nonNulls.isEmpty()) {
            return null;
        }
        return intersection(nonNulls);
    }

    /**
     * 求并集，忽略null
     *
     * @return null如果所有集合都是空
     */
    @SafeVarargs
    public static <T> Set<T> unionIgnoreNull(Collection<T>... sets) {
        if (sets.length == 0) {
            return Collections.emptySet();
        }
        List<Collection<T>> nonNulls = Arrays.stream(sets).filter(Objects::nonNull).collect(Collectors.toList());
        if (nonNulls.isEmpty()) {
            return null;
        }
        return union(nonNulls);
    }

    /**
     * 差集，l1-l2
     * eg: 1l:[1,2,3],l2:[2,3,4],difference(l1,l2)=[1],difference(l2,l1)=[4]
     */
    public static <T> List<T> difference(Collection<T> l1, Collection<T> l2) {
        if (org.springframework.util.CollectionUtils.isEmpty(l1)) {
            return Collections.emptyList();
        }
        if (org.springframework.util.CollectionUtils.isEmpty(l2)) {
            return new ArrayList<>(l1);
        }
        List<T> difference = new ArrayList<>();
        for (T t : l1) {
            if (!l2.contains(t)) {
                difference.add(t);
            }
        }
        return difference;
    }

    public static <T> boolean containsAny(Collection<T> l1, Collection<T> l2) {
        for (T t : l2) {
            if (l1.contains(t)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1, 2, 3);
        List<Integer> l2 = Arrays.asList(2, 3, 4);
        System.out.println(difference(l1, l2));
        System.out.println(difference(l2, l1));
    }

    public static <T> boolean equalSet(Set<T> s1, Set<T> s2) {
        return difference(s1, s2).isEmpty() && difference(s2, s1).isEmpty();
    }

    public static <T> List<List<T>> splitCollection(List<T> lst, int pageSize) {
        if (null == lst || lst.isEmpty()) {
            return new ArrayList<>();
        }

        int sumSize = lst.size();
        if (pageSize <= 0 || sumSize <= pageSize) {
            List<List<T>> tmp = new ArrayList<>(1);
            tmp.add(lst);
            return tmp;
        }

        int pages = (sumSize + pageSize - 1) / pageSize;
        List<List<T>> result = new ArrayList<>(pages);
        for (int i = 0; i < pages; i++) {
            List<T> r;
            if (i != pages - 1) {
                r = lst.subList(i * pageSize, (i + 1) * pageSize);
            } else {
                r = lst.subList(i * pageSize, lst.size());
            }
            result.add(r);
        }
        return result;
    }

    public interface CollectionFunction<T, R> {
        List<R> apply(Collection<T> ts);
    }

}
