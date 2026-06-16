package org.store.joeunit.board.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.store.joeunit.board.dto.BoardDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.store.joeunit.board.service.BoardService;
import org.store.joeunit.board.service.ReplyService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    /**
     * 📌 [추가] 1. 게시판 리스트 조회 (404 에러 해결 주범!)
     * 주소창에 http://localhost:8080/board 또는 http://localhost:8080/board/ 입력 시 호출됩니다.
     */
    @GetMapping({"", "/"})
    public String boardList(Model model) {
        // 서비스에서 전체 게시글 목록을 가져옴
        List<BoardDto> list = boardService.list();

        // 타임리프 html 템플릿에 "list"라는 이름으로 데이터를 전달
        model.addAttribute("list", list);

        return "board/list"; // src/main/resources/templates/board/list.html 실행
    }

    /**
     * 2. Q&A 글쓰기 화면 출력
     * 주소창에 http://localhost:8080/board/write 입력 시 호출됩니다.
     */
    @GetMapping("/write")
    public String writeForm() {
        return "board/write"; // src/main/resources/templates/board/write.html 실행
    }

    /**
     * 3. Q&A 글등록 처리 후 리스트 화면으로 리다이렉트
     * 글쓰기 화면에서 [등록하기] 버튼을 누르면 이쪽으로 데이터가 넘어옵니다.
     */
    @PostMapping("/write")
    public String writePro(BoardDto boardDto) {
        // 1) DB에 게시글 저장 실행
        boardService.register(boardDto);

        // 2) 저장 완료 후 위의 boardList() 메서드로 강제 이동시킴 (주소창이 /board로 변경됨)
        return "redirect:/board";
    }
    // BoardController.java 안의 view 메서드를 이렇게 수정하세요
    @GetMapping("/view")
    public String view(@RequestParam("boardNo") Long boardNo, Model model) {
        // 1. 조회수 증가
        boardService.increaseViewCount(boardNo);

        // 2. 게시글 상세 정보 담기
        model.addAttribute("board", boardService.getById(boardNo));

        // 3. (추가) 댓글 리스트 가져와서 담기
        model.addAttribute("replyList", replyService.getList(boardNo));

        return "board/view";
    }
}