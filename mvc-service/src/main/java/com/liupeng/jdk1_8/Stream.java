package com.liupeng.jdk1_8;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author fengdao.lp
 * @date 2018/7/10
 */
public class Stream {

    /**
     * Stream流
     */
    public static void stream() {
        List<Employee> list = Arrays.asList(
            new Employee("张三", "上海", 5000, 22),
            new Employee("李四", "北京", 4000, 23),
            new Employee("c五", "日本", 6000, 50),
            new Employee("b七", "香港", 4000, 50),
            new Employee("赵六", "纽约", 1000, 8)
        );

        /**
         * 筛选和切片
         *
         * filter -- 接受Lambda，从流中排除某些元素
         * limit -- 截断流，使其元素不超过某个给定数量
         * skip -- 跳过元素，返回一个扔掉了前n个元素的流，若流中元素不足n个，则返回一个空流，与limit互补。
         * distinct -- 去重，通过hashcode和equals去重。
         */
        list.stream().filter(e -> e.getAge() > 25).limit(5).skip(1).distinct().forEach(System.out::println);

        /**
         * 映射
         * map -- 接受Lambda，将元素转换成其他形式或提取信息，接受一个函数作为参数
         * flatmap -- 接受一个函数做为参数，将流中的每个值都转换成另一个流，然后将所有流连接成一个流，
         */
        list.stream().map(Employee::getName).forEach(System.out::println);

        // 自然排序
        List<String> list1 = Arrays.asList("ddd", "ccc", "ggg", "bbb", "aaa");
        list1.stream().sorted().forEach(System.out::println);
        // 定制排序
        list.stream().sorted((e1, e2) -> {
            if (e1.getSalary() == e2.getSalary()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return e1.getSalary() - e2.getSalary();
            }
        }).forEach(System.out::println);

        /**
         * Stream的终止操作
         */
        boolean a1 = list.stream().allMatch(employee -> employee.getName().equals("张三"));
        boolean a2 = list.stream().anyMatch(employee -> employee.getName().equals("张三"));
        boolean a3 = list.stream().noneMatch(employee -> employee.getName().equals("张三"));
        Employee e1 = list.parallelStream().findAny().orElse(null);
        Employee e2 = list.stream().filter(employee -> employee.getName().equals("张三")).findAny().orElse(null);

        /**
         * 终止操作：
         *
         * 归约
         * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) -- 可以将流中的元素反复结合起来，得到一个值
         */
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
        Integer reduce = list2.stream().reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        Optional<Integer> reduce1 = list.stream().map(Employee::getSalary).reduce(Integer::sum);
        System.out.println(reduce1.get());

        /**
         * 终止操作：收集Collect（很强大）
         */
        // List
        List<String> coList = list.stream().map(Employee::getName).collect(Collectors.toList());
        coList.forEach(System.out::println);
        // Set
        Set<String> coSet = list.stream().map(Employee::getName).collect(Collectors.toSet());
        coSet.forEach(System.out::println);
        // 指定的数据结构LinkedHashSet
        LinkedHashSet<String> liSet = list.stream().map(Employee::getName).collect(
            Collectors.toCollection(LinkedHashSet::new));
        liSet.forEach(System.out::println);

        // 总数
        Long r1 = list.stream().collect(Collectors.counting());
        System.out.println(r1);
        // 平均
        Double r2 = list.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(r2);
        // 总和
        Double r3 = list.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(r3);
        // 最大值
        Optional<Employee> r4 = list.stream().collect(
            Collectors.maxBy((e11, e22) -> Double.compare(e11.getSalary(), e22.getSalary())));

        // 单级分组
        Map<Integer, List<Employee>> m1 = list.stream().collect(Collectors.groupingBy(Employee::getSalary));
        System.out.println(m1);
        // 多级分组
        Map<Integer, Map<String, List<Employee>>> m2 = list.stream().collect(
            Collectors.groupingBy(Employee::getSalary, Collectors.groupingBy(e -> {
                if (e.getAge() < 20) {
                    return "少年";
                } else if (e.getAge() < 30) {
                    return "青年";
                } else {
                    return "中年";
                }
            })));
        System.out.println(m2);

        //分区--满足条件一个区，不满足另一个区
        Map<Boolean, List<Employee>> c1 = list.stream().collect(Collectors.partitioningBy(e -> e.getSalary() > 5000));
        System.out.println(c1);
        System.out.println("-----------------------");
        //收集各种统计数据
        DoubleSummaryStatistics c2 = list.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(c2 + "-----------平均薪水" + c2.getAverage());
        //连接字符串
        String c3 = list.stream().map(Employee::getName).collect(Collectors.joining(",", "-----", "-----"));
        System.out.println(c3);

        /**
         * 需求2：
         * 用reduce和map数一数流中的元素个数
         */
        Optional<Integer> rr = list.stream()
            //巧妙之处
            .map(e -> 1)
            .reduce(Integer::sum);
        System.out.println(rr);
    }

    /**
     * 并行流：https://blog.csdn.net/op134972/article/details/76408237?locationNum=1&fps=1
     */

    /**
     * Optional类
     */
    public static void optional() {
        Optional<Employee> op = Optional.of(new Employee());
        Employee employee = op.get();
        System.out.println(employee);

        // Optional.empty();
        Optional<Employee> op1 = Optional.ofNullable(null);
        if (op1.isPresent()) {
            System.out.println(op1.get());
        } else {
            //op1.orElse(new Employee());
            Object o = op1.orElseGet(Employee::new);
            System.out.println(o);
        }
        System.out.println(op1.orElse(null));

        Optional<Employee> op2 = Optional.ofNullable(new Employee("张三", "上海", 5000, 22));
        Optional<Integer> salary = op2.map(Employee::getSalary);
        System.out.println(salary.get());
    }

    public static void main(String[] args) {
        stream();
        optional();
    }

    static class Employee {
        private String name;
        private String area;
        private int salary;
        private int age;

        public Employee() {}

        public Employee(String name, String area, int salary, int age) {
            this.name = name;
            this.area = area;
            this.salary = salary;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
