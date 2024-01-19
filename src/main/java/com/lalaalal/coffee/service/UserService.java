package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.dto.UserDTO;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService extends DataStoreService<String, User> {
    public UserService() {
        super(String.class, User.class, HashMap::new,
                Configurations.getConfiguration("data.user.path"));
    }

    public Result login(String id, String password) {
        if (!data.containsKey(id))
            return Result.failed("user.login.failed.no_such_id", id);

        User user = data.get(id);
        if (user.verify(password))
            // TODO: transl
            return Result.succeed("user.login.succeed", id);
        return Result.failed("user.login.failed.password_not_match");
    }

    public Result signUp(String id, String password) {
        if (data.containsKey(id))
            return Result.failed("user.sign_up.failed.id_exists", id);

        User user = new User(id, password);
        data.put(user.getId(), user);
        save();
        return Result.succeed("user.sign_up.succeed", id);
    }

    public UserDTO getUser(String id) {
        return UserDTO.from(data.get(id));
    }
}
