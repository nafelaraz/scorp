package repository;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    // select * from user where id in(ids)
    public List<User> getUsersByIds(List<Integer> ids){

        return new ArrayList<User>();
    }
}
