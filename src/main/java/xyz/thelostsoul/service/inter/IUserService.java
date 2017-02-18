package xyz.thelostsoul.service.inter;

import xyz.thelostsoul.bean.User;

import java.util.List;

/**
 * Created by jamie on 17-2-15.
 */
public interface IUserService {
    User getUserById(int id);

    List<User> allUsers();

    int addUser(User user);
}
