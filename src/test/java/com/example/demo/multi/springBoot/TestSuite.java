package com.example.demo.multi.springBoot;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 测试套件
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/29 11:02:49
 */
@RunWith(Categories.class)
@Categories.ExcludeCategory({TestRule.class})
@Suite.SuiteClasses({EasyTester.class, EasyTester2.class, TestParameterized.class, TestRule.class})
public class TestSuite {

}
