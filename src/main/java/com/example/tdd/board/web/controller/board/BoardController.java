package com.example.tdd.board.web.controller.board;

import com.example.tdd.board.service.board.BoardService;
import com.example.tdd.board.web.domain.board.Board;
import com.example.tdd.board.web.dto.board.RequestBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    @GetMapping("/boardList")
    public String boardList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PostMapping("/createBoard")
    public Board createBoard(RequestBoard board, Long groupId) {
        Board entity = RequestBoard.toBoardEntity(board);
        return boardService.createBoard(entity, groupId);
    }

    @PostMapping("/board/{boardId}")
    public Board getBoard(@PathVariable("boardId") Long boardId) {
        return boardService.getBoard(boardId);
    }

    @PatchMapping("/board/{boardId}")
    public Board updateBoard(@PathVariable("boardId") Long boardId, RequestBoard board) {
        Board entity = RequestBoard.toBoardEntity(boardId, board);
        return boardService.updateBoard(entity);
    }
}
