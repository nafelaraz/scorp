package service;

import model.Follow;
import model.Like;
import model.Post;
import model.User;
import model.dto.PostDTO;
import model.dto.UserDTO;
import repository.FollowRepository;
import repository.LikeRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PostService {

    public List<PostDTO> getPosts(int userId, List<Integer> postIds) {
        List<Post> posts = postRepoHelper(postIds);

        // create post lookup using postId as key
        Map<Integer, Post> postLookup = posts.stream().collect(Collectors.toMap(Post::getId, Function.identity()));
        List<Integer> userIds = posts.stream().map(a -> a.getUserId()).collect(Collectors.toList());
        Map<Integer, User> userLookup = userLookupHelper(userIds);

        Map<Integer,Follow> followLookup = followLookupHelper(userIds,userId);

        Map<Integer, Like> likeLookup = likeLookupHelper(postIds, userId);
        List<PostDTO> response = new ArrayList<>();

        for(int i:postIds){
            response.add(postDTOMapper(i,postLookup,userLookup,likeLookup,followLookup));
        }
        return response;

    }


    private PostDTO postDTOMapper(int postId, Map<Integer, Post> postLookup, Map<Integer, User> userLookup,
                                  Map<Integer, Like> likeLookup, Map<Integer,Follow> followLookup){

        Post post = postLookup.get(postId);
        if(post == null){
            return null;
        }
        User user = userLookup.get(post.getUserId());
        Like like = likeLookup.get(postId);
        Follow follow = followLookup.get(post.getUserId());


        PostDTO postDTO = new PostDTO();
        postDTO.setId(postId);
        postDTO.setDescription(post.getDescription());
        postDTO.setOwner(userDTOMapper(user,follow==null?false:true));
        postDTO.setImage(post.getImage());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setLiked(like==null?false:true);
        return postDTO;

    }

    private UserDTO userDTOMapper(User user, boolean follow){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setFollowed(follow);
        return userDTO;
    }

    private List<Post> postRepoHelper(List<Integer> postIds) {
        PostRepository postRepository = new PostRepository();
        return postRepository.getPostsByIds(postIds);

    }

    private Map<Integer, User> userLookupHelper(List<Integer> userIds) {
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.getUsersByIds(userIds);
        // create user lookup using userId as key
        return users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

    private Map<Integer, Follow> followLookupHelper(List<Integer> userIds, int userId) {

        FollowRepository followRepository = new FollowRepository();
        List<Follow> follows = followRepository.getFollowsByFollowingIdsAndFollowerId(userIds,userId);
        // create follow lookup using followingId as key
        return follows.stream().collect(Collectors.toMap(Follow::getFollowingId, Function.identity()));
    }

    private Map<Integer, Like> likeLookupHelper(List<Integer> postIds, int userId) {
        LikeRepository likeRepository = new LikeRepository();
        List<Like> likes = likeRepository.getLikesByPostIdsAndUserId(postIds, userId);
        // create likes lookup using postId as key
        return likes.stream().collect(Collectors.toMap(Like::getPostId, Function.identity()));
    }
}
