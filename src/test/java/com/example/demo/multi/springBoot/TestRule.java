package com.example.demo.multi.springBoot;

import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExternalResource;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/29 12:04:02
 */
@Category({TestRule.class})
public class TestRule {
    int a = 0;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    @Ignore
    public void testErrorRule() {
        collector.addError(new Throwable("first  error"));
        collector.addError(new Throwable("second error"));
    }

    @Rule
    public ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            System.out.println("before(defined in resource) ....");
        }
        @Override
        protected void after() {
            System.out.println("after(defined in resource) ...");
        }
    };

    @ClassRule
    public static ExternalResource resourceOfClass = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            System.out.println("before class(defined in resource) ....");
        }
        @Override
        protected void after() {
            System.out.println("after class(defined in resource) ...");
        }
    };

    @Test
    public void testFoo() {
        System.out.println("fdafdsa");
    }

    @Test
    public void test2() {
        System.out.println("test2()");
    }

    @BeforeClass
    public static void beforeClazz(){
        System.out.println("注解@BeforeClass ...");
    }

    @Before
    public void before(){
        System.out.println("注解@Before ...");
    }

    @AfterClass
    public static void afterClazz(){
        System.out.println("注解@AfterClass ...");
    }

    @After
    public void after(){
        System.out.println("注解@After ...");
    }
}
