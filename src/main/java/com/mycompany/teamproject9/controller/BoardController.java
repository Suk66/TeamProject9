package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.Board;
import com.mycompany.teamproject9.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardMapper boardMapper;

    // ✅ 게시판 목록 조회
    @GetMapping("/board")
public String boardList(Model model) {
    List<Board> boardList = boardMapper.findAll();

    // ✅ 작성일 디버깅 로그 출력
    for (Board board : boardList) {
        System.out.println("📌 [디버깅] 게시글 ID: " + board.getBoardId() + ", 작성일: " + board.getCreatedAt());
    }

    model.addAttribute("boards", boardList);
    return "board-list"; // 📌 게시판 목록 페이지
}


    // ✅ 게시글 작성 페이지
    @GetMapping("/board/write")
    public String showWriteForm(Model model) {
        model.addAttribute("board", new Board()); // 빈 객체 전달
        return "board-write"; // 📌 게시글 작성 페이지
    }

    // ✅ 게시글 등록 처리
    @PostMapping("/board/write")
    public String saveBoard(@ModelAttribute Board board) {
        boardMapper.insert(board);
        return "redirect:/board"; // 저장 후 게시판 목록으로 이동
    }
}
