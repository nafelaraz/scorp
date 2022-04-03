package repository;

import model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    // select * from post where id in(ids)
    public List<Post> getPostsByIds(List<Integer> ids){

        return new ArrayList<Post>();
    }
}
