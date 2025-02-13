package com.titlark.track;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * 性能跟踪工具类
 */
public class TimeTrackerTest extends TestCase {

    /**
     * lambda自动处理异常
     * <p>
     * 使用不抛出检查异常的版本（异常被包装为RuntimeException）
     */
    public void test1() {
        TimeTracker.track("简单任务", () -> {
            Thread.sleep(1000);
            return "完成";
        });
    }

    /**
     * lambda显式异常处理
     * <p>
     * 使用可能抛出异常的版本
     */
    public void test2() {
        try {
            TimeTracker.trackThrows("可能失败的任务", () -> {
                if (Math.random() < 0.5) {
                    throw new IOException("模拟IO异常");
                }
                return "成功";
            });
        } catch (Exception e) {
            //处理异常
            e.printStackTrace();
        }
    }

    /**
     * lambda嵌套使用
     * <p>
     * 嵌套使用示例
     */
    public void test3() {
        try {
            TimeTracker.trackThrows("复杂流程", () -> {
                //子任务1：使用不抛出异常的版本
                TimeTracker.track("子任务1", () -> {
                    Thread.sleep(500);
                });

                //子任务2：使用抛出异常的版本
                return TimeTracker.trackThrows("子任务2", () -> {
                    Thread.sleep(500);
                    return "全部完成";
                });
            });
        } catch (Exception e) {
            //处理异常
            e.printStackTrace();
        }
    }

    /**
     * 结合静态方法的try-with-resources
     * <p>
     * try-with-resources示例
     */
    public void test4() {
        try (TimeTracker tracker = TimeTracker.of("资源管理演示")) {
            //模拟资源操作
            performResourceIntensiveTask();
        }
    }

    /**
     * 多资源管理的try-with-resources
     */
    public void test5() {
        try (TimeTracker tracker1 = TimeTracker.of("第一阶段"); TimeTracker tracker2 = TimeTracker.of("第二阶段");
             //可以同时管理其他资源
             CustomResource resource = acquireResource()) {
            processResourcesSequentially(resource);
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
        }
    }

    /**
     * 忽略返回值的try-with-resources
     */
    public void test6() {
        try (TimeTracker ignored = TimeTracker.of("后台任务")) {
            performBackgroundTask();
        }
    }

    //辅助方法（仅作示例）
    private void performResourceIntensiveTask() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("资源密集型任务完成");
    }

    private CustomResource acquireResource() {
        return new CustomResource();
    }

    private void processResourcesSequentially(CustomResource resource) {
        //处理资源的示例方法
        resource.process();
    }

    private void performBackgroundTask() {
        //后台任务示例
        System.out.println("执行后台任务");
    }

    //模拟自定义资源类
    private static class CustomResource implements AutoCloseable {
        public void process() {
            System.out.println("处理资源");
        }

        @Override
        public void close() {
            System.out.println("关闭资源");
        }
    }
}
