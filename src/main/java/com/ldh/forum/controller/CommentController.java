package com.ldh.forum.controller;

import com.ldh.forum.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Update comment
    @PostMapping("/{id}/edit")
    public String editComment(@PathVariable("id") Long id,
                              @RequestParam("content") String content,
                              Authentication auth) {

        commentService.updateComment(id, content, auth.getName());
        Long boardId = commentService.getBoardIdByCommentId(id);
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable("id") Long id,
                                Authentication auth) {

        commentService.deleteComment(id, auth.getName());
        Long boardId = commentService.getBoardIdByCommentId(id);
        return "redirect:/boards/" + boardId;
    }
}
