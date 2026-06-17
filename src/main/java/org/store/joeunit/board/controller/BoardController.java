package org.store.joeunit.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.store.joeunit.board.dto.BoardDto;
import org.store.joeunit.board.service.BoardService;
import org.store.joeunit.comment.dto.CommentDto;
import org.store.joeunit.comment.service.CommentService;
import org.store.joeunit.member.dto.MemberDto;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String getBoardList(@RequestParam(defaultValue = "ALL") String type, @RequestParam(required = false) String searchType, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, Model model) {
        int limit = 10;
        int offset = (page - 1) * limit;
        int endRow = page * limit;

        int totalCount = boardService.countBoardList(type.toUpperCase(), searchType, keyword);
        int totalPage = (int) Math.ceil((double) totalCount / limit);
        if (totalPage == 0) totalPage = 1;

        int startPage = ((page - 1) / 5) * 5 + 1;
        int endPage = Math.min(startPage + 4, totalPage);

        model.addAttribute("boardList", boardService.getBoardList(type.toUpperCase(), searchType, keyword, offset, endRow));
        model.addAttribute("boardType", type.toUpperCase());
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);

        return "board/list";
    }

    @GetMapping("/write")
    public String writeForm(HttpSession session) {
        return session.getAttribute("loggedMember") == null ? "redirect:/member/login" : "board/write";
    }

    @PostMapping("/write")
    public String writeProcess(BoardDto boardDto, @RequestParam("files") List<MultipartFile> files, HttpSession session) throws Exception {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) return "redirect:/member/login";
        boardDto.setMemberId(loginMember.getMemberId());

        if (files != null && !files.isEmpty() && !files.get(0).isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/uploads/board/";
            File saveFolder = new File(projectPath);
            if (!saveFolder.exists()) saveFolder.mkdirs();

            int count = 1;
            for (MultipartFile file : files) {
                if (file.isEmpty() || count > 3) continue;
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                file.transferTo(new File(projectPath, fileName));

                if (count == 1) { boardDto.setImageName(fileName); boardDto.setImagePath("/uploads/board/" + fileName); }
                else if (count == 2) { boardDto.setImageName2(fileName); boardDto.setImagePath2("/uploads/board/" + fileName); }
                else if (count == 3) { boardDto.setImageName3(fileName); boardDto.setImagePath3("/uploads/board/" + fileName); }
                count++;
            }
        }
        boardService.insertBoard(boardDto);
        return "redirect:/board/list?type=" + boardDto.getBoardType();
    }

    @GetMapping("/view")
    public String viewBoard(@RequestParam("id") Long id, Model model) {
        BoardDto board = boardService.getBoardDetail(id);
        List<CommentDto> commentList = commentService.getCommentsByBoardId(id);
        model.addAttribute("board", board);
        model.addAttribute("commentList", commentList);
        return "board/view";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("loggedMember") == null) return "redirect:/member/login";
        model.addAttribute("board", boardService.getBoardDetail(id));
        return "board/update";
    }

    @PostMapping("/update")
    public String updateProcess(BoardDto boardDto, @RequestParam(value="files", required=false) List<MultipartFile> files) throws Exception {
        if (files != null && !files.isEmpty() && !files.get(0).isEmpty()) {
            boardDto.setImageChanged(true);
            String projectPath = System.getProperty("user.dir") + "/uploads/board/";
            File saveFolder = new File(projectPath);
            if (!saveFolder.exists()) saveFolder.mkdirs();

            int count = 1;
            for (MultipartFile file : files) {
                if (file.isEmpty() || count > 3) continue;
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                file.transferTo(new File(projectPath, fileName));

                if (count == 1) { boardDto.setImageName(fileName); boardDto.setImagePath("/uploads/board/" + fileName); }
                else if (count == 2) { boardDto.setImageName2(fileName); boardDto.setImagePath2("/uploads/board/" + fileName); }
                else if (count == 3) { boardDto.setImageName3(fileName); boardDto.setImagePath3("/uploads/board/" + fileName); }
                count++;
            }
        } else {
            boardDto.setImageChanged(false);
        }
        boardService.updateBoard(boardDto);
        return "redirect:/board/view?id=" + boardDto.getBoardId();
    }

    @GetMapping("/delete")
    public String deleteProcess(@RequestParam("id") Long id) {
        BoardDto board = boardService.getBoardDetail(id);
        boardService.deleteBoard(id);
        return "redirect:/board/list?type=" + board.getBoardType();
    }
}