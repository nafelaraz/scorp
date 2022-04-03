package repository;

import model.Follow;

import java.util.ArrayList;
import java.util.List;

public class FollowRepository {
    // select * from follow where follower_id=userId and  following_id in (ids)
    public List<Follow> getFollowsByFollowingIdsAndFollowerId(List<Integer> ids, int userId){

        return new ArrayList<Follow>();
    }
}
