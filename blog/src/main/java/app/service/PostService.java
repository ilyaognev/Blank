package app.service;

import app.model.Post;
import app.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Optional<Post> getById(Long id) {
        return postRepository.findById(id);
    }

    public Collection<Post> getAll() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    public Post save(Post post) {
        return postRepository.saveAndFlush(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
