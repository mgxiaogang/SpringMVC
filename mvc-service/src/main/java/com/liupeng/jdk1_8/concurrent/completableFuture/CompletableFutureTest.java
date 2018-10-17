package com.liupeng.jdk1_8.concurrent.completableFuture;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //method1();
        method2();
    }

    private static void method1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<String> list = Lists.newArrayList("liupeng", "laosun");
        List<CompletableFuture<String>> result = list.stream().map(str ->
                CompletableFuture
                        .supplyAsync(() -> {
                            return str;
                        }, executorService)
                        .thenApply(String::valueOf)
                        .handle((res, ex) -> {
                            if (ex != null) {
                                System.out.println("发生了错误");
                                return null;
                            }
                            return res;
                        })
        ).collect(Collectors.toList());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(result.toArray(new CompletableFuture[result.size()]));
        CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v -> {
            return result.stream().map(CompletableFuture::join).collect(Collectors.toList());
        });

        List<String> strings = allPageContentsFuture.get();
        System.out.println(strings);
        executorService.shutdown();
    }

    private static void method2() {
        List<String> array = Lists.newArrayList();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<String> list = Lists.newArrayList("liupeng", "laosun");
        List<CompletableFuture<String>> result = list.stream().map(str ->
                CompletableFuture
                        .supplyAsync(() -> {
                            return str;
                        }, executorService)
                        .thenApply(String::valueOf)
                        .handle((res, ex) -> {
                            if (ex != null) {
                                System.out.println("发生了错误");
                                return null;
                            }
                            return res;
                        })
                        .whenComplete((s, e) -> {
                            System.out.println("任务" + s + "完成!result=" + s + "，异常 e=" + e + "," + new Date());
                            array.add(s);
                        })
        ).collect(Collectors.toList());

        CompletableFuture.allOf(result.toArray(new CompletableFuture[result.size()])).join();
        System.out.println(array);
        executorService.shutdown();
    }
}
