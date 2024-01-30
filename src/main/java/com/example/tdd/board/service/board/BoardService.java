package com.example.tdd.board.service.board;

import com.example.tdd.board.repository.board.BoardGroupRepository;
import com.example.tdd.board.repository.board.BoardRepository;
import com.example.tdd.board.web.domain.board.Board;
import com.example.tdd.board.web.domain.board.BoardGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    private final BoardGroupRepository boardGroupRepository;

    @Transactional
    public Board createBoard(Board board, Long groupId) {
        BoardGroup boardGroup = boardGroupRepository.findByGroupId(groupId).orElseThrow();
        board.changeBoardGroup(boardGroup);

        Board saveBoard = boardRepository.save(board);
        return saveBoard;
    }
}
