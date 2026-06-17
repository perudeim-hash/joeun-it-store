package org.store.joeunit.board.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.store.joeunit.board.dto.BoardDto;

import java.util.List;

@Mapper
public interface BoardDao {

    // =======================================================
    // 1. 조회 기능 (Read)
    // =======================================================

    /** 전체 게시글 목록 조회 */
    List<BoardDto> getList();

    /** 게시글 상세 조회 (글번호 기준) */
    BoardDto getById(Long boardNo);

    /** 검색 키워드에 따른 목록 조회 */
    List<BoardDto> search(String keyword);

    /** 특정 상품 전용 게시글 목록 조회 */
    List<BoardDto> getCategoryList(Integer productId);


    // =======================================================
    // 2. 데이터 변경 기능 (CUD)
    // =======================================================

    /** 게시글 등록 */
    int insert(BoardDto boardDto);

    /** 게시글 수정 */
    int update(BoardDto boardDto);

    /** 게시글 삭제 */
    int delete(Long boardNo);


    // =======================================================
    // 3. 페이징 및 집계 (Pagination & Stats)
    // =======================================================

    /** 페이징 처리된 목록 조회 */
    List<BoardDto> getPageList(@Param("startRow") int startRow,
                               @Param("endRow") int endRow);

    /** 상품 상세페이지용 페이징 목록 조회 */
    List<BoardDto> getCategoryPageList(@Param("productId") Integer productId,
                                       @Param("startRow") int startRow,
                                       @Param("endRow") int endRow);

    /** 전체 게시글 개수 반환 */
    int getTotalCount();

    /** 특정 상품 관련 게시글 개수 반환 */
    int getCategoryTotalCount(Integer productId);


    // =======================================================
    // 4. 부가 기능
    // =======================================================

    /** 조회수 1 증가 */
    int updateHit(Long boardNo);
}