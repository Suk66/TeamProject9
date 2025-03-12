package com.mycompany.teamproject9.controller;

import com.mycompany.teamproject9.dto.Board;
import com.mycompany.teamproject9.dto.User;
import com.mycompany.teamproject9.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
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

    // 📌 게시글 작성 페이지 (GET 요청)
@GetMapping("/write")
public String showWriteForm(Model model) {
    model.addAttribute("board", new Board()); // 빈 Board 객체 전달
    return "board/board-write";  // 게시글 작성 페이지
}


// 📌 게시글 작성 요청 처리 (POST)
@PostMapping("/write")
public String insert(@ModelAttribute Board board) {
    System.out.println("📌 [디버깅] 새 게시글 작성! 제목: " + board.getTitle() + ", 비밀번호: " + board.getPassword());

    boardMapper.insert(board);  // ✅ 새 게시글 DB에 저장 (비밀번호 포함)

    return "redirect:/board";  // ✅ 작성 후 게시글 목록으로 이동
}




    // 📌 게시글 상세 보기
@GetMapping("/{id}")
public String boardDetail(@PathVariable("id") int boardId, Model model) {
    System.out.println("📌 [디버깅] 요청된 게시글 ID: " + boardId);

    Board board = boardMapper.findById(boardId);

    if (board == null) {
        System.out.println("❌ [오류] 게시글을 찾을 수 없음! ID: " + boardId);
        return "redirect:/board";
    }

    System.out.println("📌 [디버깅] 조회된 게시글 ID: " + board.getBoardId());

    model.addAttribute("board", board);
    return "board/board-detail";
}

// 📌 게시글 수정 페이지 (GET)
@GetMapping("/edit/{id}")
public String editBoard(
        @PathVariable("id") int boardId,
        @RequestParam(value = "password", required = false) String password,
        HttpSession session,
        Model model) {

    // 📌 게시글 가져오기
    Board board = boardMapper.findById(boardId);

    if (board == null) {
        return "redirect:/board"; // 게시글이 없으면 목록으로 이동
    }

    // ✅ 로그인한 사용자는 비밀번호 확인 필요 없음
    User loggedInUser = (User) session.getAttribute("user");
    if (loggedInUser != null && loggedInUser.getEmail().equals(board.getWriter())) {
        model.addAttribute("board", board);
        return "board/board-edit";
    }

    // ✅ 디버깅 로그 추가
    System.out.println("📌 [디버깅] 입력한 비밀번호: '" + password + "'");
    System.out.println("📌 [디버깅] 저장된 비밀번호: '" + board.getPassword() + "'");

    // ✅ 비로그인 사용자는 비밀번호 입력해야 함
    if (password == null || !password.equals(board.getPassword())) {
        System.out.println("❌ [오류] 비밀번호 불일치!");
        return "redirect:/board?error=invalid_password"; // 🚨 비밀번호가 틀리면 목록으로 이동
    }

    // ✅ 비밀번호가 맞으면 수정 페이지로 이동
    model.addAttribute("board", board);
    return "board/password-check";
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

// 📌 비밀번호 입력 화면으로 이동
@GetMapping("/check-password/{id}")
public String showPasswordCheckPage(@PathVariable("id") int boardId, Model model) {
    model.addAttribute("boardId", boardId);
    return "board/password-check";  // 비밀번호 입력 화면
}

// 📌 비밀번호 확인 후 수정 페이지 이동
@PostMapping("/check-password")
public String checkPassword(
        @RequestParam("boardId") int boardId,
        @RequestParam("password") String password,
        Model model) {

    // 게시글 정보 조회
    Board board = boardMapper.findById(boardId);
    if (board == null) {
        return "redirect:/board?error=not_found";  // 게시글이 없으면 목록으로 이동
    }

    // 비밀번호 검증
    if (!board.getPassword().equals(password)) {
        return "redirect:/board/check-password/" + boardId + "?error=invalid_password";  // 비밀번호 틀림
    }

    // 비밀번호가 맞으면 수정 페이지로 이동
    model.addAttribute("board", board);
    return "board/board-edit";
}




}

