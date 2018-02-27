package com.example.demo.multi.springBoot;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

/**
 * 测试list
 *
 * @author: LiHongxing
 * @date: Create in 2018-02-13 10:24
 * @modefied:
 */
public class ListTest {
    private ArrayList<String> list ;

    @Before
    public void startUp() {
        this.list = new ArrayList<>();
    }

    @Test
    public void testIndexInsert() {
        this.list.add("one");
        this.list.add("two");
        this.list.add(2, "ten");
        System.out.println(list);
    }

    @Test
    public void testSpliterator() {
        this.list.add("1");
        this.list.add("2");
        this.list.add("3");
        this.list.add("4");
        this.list.add("5");
        this.list.add("6");
        this.list.add("7");
        this.list.add("8");
        this.list.add("9");
        this.list.add("10");
        this.list.add("before");
        Spliterator spliterator = this.list.spliterator();
        this.list.add("after");
        Spliterator<String> spliterator1 = spliterator.trySplit();
        Spliterator<String> spliterator2 = spliterator1.trySplit();

        spliterator.forEachRemaining(
                each -> {
                    System.out.println("origin : " + each);
                    System.out.println(Thread.currentThread().getId());
                }
        );

        spliterator1.forEachRemaining(
                each -> {
                    System.out.println("1 : " + each);
                    System.out.println(Thread.currentThread().getId());
                }
        );

        spliterator2.forEachRemaining(
                each -> {
                    System.out.println("2 : " + each);
                    System.out.println(Thread.currentThread().getId());
                }
        );


    }
}
