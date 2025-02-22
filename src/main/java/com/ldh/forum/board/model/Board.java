package com.ldh.forum.board.model;

import com.ldh.forum.comment.model.Comment;
import com.ldh.forum.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private String author;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false)
    private Integer views;

    @ManyToMany
    @JoinTable(name = "board_likes",
               joinColumns = @JoinColumn(name = "board_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedUsers = new HashSet<>();

    public Board(String title, String body, String author) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.likes = 0;
        this.views = 0;
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setBoard(null);
    }
}
