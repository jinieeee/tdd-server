package com.example.tdd.board.service.board;

import com.example.tdd.board.repository.board.BoardGroupRepository;
import com.example.tdd.board.web.domain.board.BoardGroup;
import com.example.tdd.board.web.domain.board.GroupJoin;
import com.example.tdd.board.web.domain.users.Users;

import com.example.tdd.board.repository.board.GroupJoinRepository;
import com.example.tdd.board.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardGroupService {

    private final BoardGroupRepository boardGroupRepository;

    private final UserRepository userRepository;

    private final GroupJoinRepository groupJoinRepository;

    private final SecurityContext securityContext = SecurityContextHolder.getContext();

    @Transactional
    public BoardGroup createGroup(BoardGroup boardGroup) {
        String email = securityContext.getAuthentication().getName();
        Users user = userRepository.findByUserEmail(email).orElseThrow();

        GroupJoin groupJoin = GroupJoin.builder()
                .userId(user.getUserId())
                .build();
        groupJoin.changeBoardGroup(boardGroup);

        BoardGroup saveBoardGroup = boardGroupRepository.save(boardGroup);
        groupJoinRepository.save(groupJoin);

        return saveBoardGroup;
    }

    @Transactional
    public List<BoardGroup> allBoardGroup() {
        return boardGroupRepository.findAll();
    }

    @Transactional
    public List<BoardGroup> findByUserId(Long userId) {
        return boardGroupRepository.findByUserId(userId).orElseThrow();
    }
}
