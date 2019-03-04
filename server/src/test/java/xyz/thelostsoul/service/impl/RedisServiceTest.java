package xyz.thelostsoul.service.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.thelostsoul.bean.User;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
public class RedisServiceTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private Set<User> users = new HashSet<User>();


    @CachePut(value = "user", key = "'User:'+#user.id")
    public User addUser(User user) {
        users.add(user);
        redisTemplate.restore("USER"+user.getId(), user.toString().getBytes(), 1000, TimeUnit.MINUTES);
        return user;
    }

    @Cacheable(value = "user", key = "'User:'+#id")
    public User addUser(Integer id, String name, String password) {
        User user = new User(id, name, password);
        users.add(user);
        return user;
    }

    @Cacheable(value = "user", key = "'User:'+#id")
    public User getStudent(Integer id) {
        System.out.println("not in redis cache");
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
