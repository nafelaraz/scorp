package repository;

import model.Like;

import java.util.ArrayList;
import java.util.List;

public class LikeRepository {
    // select * from like where user_id=userId and  post_id in (ids)
    public List<Like> getLikesByPostIdsAndUserId(List<Integer> ids, int userId){

        return new ArrayList<Like>();
    }
}
