package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.Board;
import com.mycompany.teamproject9.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardMapper boardMapper;

    // 📌 게시글 목록
    @GetMapping
    public String boardList(Model model) {
        List<Board> boardList = boardMapper.findAll();

        // 📌 [디버깅] 가져온 게시글 목록 확인
        for (Board board : boardList) {
            System.out.println("📌 [디버깅] 게시글 ID: " + board.getBoardId() + ", 제목: " + board.getTitle());
        }

        model.addAttribute("boards", boardList);
        return "board/board-list";
    }

    // 📌 게시글 상세 보기
    @GetMapping("/{id}")
    public String boardDetail(@PathVariable("id") int boardId, Model model) {
        Board board = boardMapper.findById(boardId);

        if (board == null) {
            System.out.println("❌ [오류] 해당 ID의 게시글이 없음!");
            return "redirect:/board";
        }

        // ✅ 디버깅 로그 추가
        System.out.println("📌 [디버깅] DB에서 가져온 게시글 ID: " + board.getBoardId());

        model.addAttribute("board", board);
        return "board/board-detail";
    }
// 📌 게시글 수정 페이지 (GET)
@GetMapping("/edit/{id}")
public String editBoard(@PathVariable("id") int boardId, Model model) {
    System.out.println("📌 [디버깅] 수정하려는 게시글 ID: " + boardId);

    Board board = boardMapper.findById(boardId);
    if (board == null) {
        System.out.println("❌ [오류] 해당 ID의 게시글이 없음!");
        return "redirect:/board";  // 게시글이 없으면 목록으로 이동
    }

    model.addAttribute("board", board);
    return "board/board-edit";  // 수정 페이지로 이동
}

// 📌 게시글 수정 요청 처리 (POST)
@PostMapping("/edit/{id}")
public String updateBoard(@PathVariable("id") int boardId, @ModelAttribute Board board) {
    board.setBoardId(boardId);  // ✅ URL에서 받은 boardId 설정

    int updatedRows = boardMapper.update(board);
    if (updatedRows > 0) {
        System.out.println("📌 [디버깅] 게시글 수정 완료! ID: " + boardId);
    } else {
        System.out.println("❌ [디버깅] 게시글 수정 실패! ID: " + boardId);
    }

    return "redirect:/board/" + boardId;  // ✅ 수정 완료 후 상세 페이지로 이동
}


    // 📌 게시글 삭제 기능
@PostMapping("/delete/{id}")
public String deleteBoard(@PathVariable("id") int boardId) {
    System.out.println("📌 [디버깅] 삭제할 게시글 ID: " + boardId);
    int result = boardMapper.delete(boardId);

    if (result > 0) {
        System.out.println("✅ 게시글 삭제 완료!");
    } else {
        System.out.println("❌ 게시글 삭제 실패!");
    }

    return "redirect:/board"; // 삭제 후 목록으로 이동
}



}

