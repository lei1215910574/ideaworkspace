package com.test.mpdemo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.mpdemo.entity.User;
import com.test.mpdemo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CRUDTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
        System.out.println("----- selectAll method test ------");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out :: println);
    }

    @Test
    public void testInsert(){

        User user = new User();
        user.setName("oo");
        user.setAge(12);
        user.setEmail("111@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }

    @Test
    public void testUpdateById(){
        User user = new User();
        user.setId(1280794296891678722L);
        user.setName("小h");

        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    /**
     * 测试乐观锁成功
     */
    @Test
    public void testOptimisticLocker(){
        User user = userMapper.selectById(1280800611504463873L);

        user.setName("haha");
        user.setEmail("123456@163.com");

        userMapper.updateById(user);
    }

    /**
     * 测试乐观锁失败
     */
    @Test
    public void testOptimisticLockerFail(){
        //查询 数据库中version=2
        User user = userMapper.selectById(1280800611504463873L);
        //修改数据
        user.setName("xixi");
        user.setEmail("789@163.com");
        //模拟数据库中实际存储的version比取出来的version大。即已被其他线程修改并更新了version
        user.setVersion(user.getVersion() - 1);
        //更新
        userMapper.updateById(user);
    }

    @Test
    public void testSelectById(){
        User user = userMapper.selectById("1L");
        System.out.println(user);
    }

    @Test
    public void testSelectBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));

        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap(){
        //map中的key是表的列名，比如数据库中user_id,实体类是userId,key是user_id
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","haha");
        map.put("age",18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectPage(){
        Page<User> page = new Page<>(2, 3);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        //当前页码
        System.out.println(page.getCurrent());
        //总页数
        System.out.println(page.getPages());
        //每页的总数
        System.out.println(page.getSize());
        //数据的总条数
        System.out.println(page.getTotal());
        //判断是否有下一页
        System.out.println(page.hasNext());
        //判断是否有上一页
        System.out.println(page.hasPrevious());
    }

    @Test
    public void testSelectMapsPage(){
        Page<User> page = new Page<>(1, 3);
        IPage<Map<String, Object>> IPage = userMapper.selectMapsPage(page, null);
        IPage.getRecords().forEach(System.out::println);
        //当前页码
        System.out.println(page.getCurrent());
        //总页数
        System.out.println(page.getPages());
        //每页的总数
        System.out.println(page.getSize());
        //数据的总条数
        System.out.println(page.getTotal());
        //判断是否有下一页
        System.out.println(page.hasNext());
        //判断是否有上一页
        System.out.println(page.hasPrevious());
    }

    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(1280794296891678722L);
        System.out.println(result);
    }

    @Test
    public void testDeleteBatchIds(){
        int result = userMapper.deleteBatchIds(Arrays.asList(1280791953437581314L, 1280031040124190722L));
        System.out.println(result);
    }

    @Test
    public void testDeleteByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "小王");
        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }

    @Test
    public void testLogicDelete(){
        int result = userMapper.deleteById(1281134195092197377L);
        System.out.println(result);
    }

    @Test
    public void testLogicDeleteSelect(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testPerformance(){
        User user = new User();
        user.setName("我是Helen");
        user.setEmail("helen@sina.com");
        user.setAge(18);
        userMapper.insert(user);
    }
}
