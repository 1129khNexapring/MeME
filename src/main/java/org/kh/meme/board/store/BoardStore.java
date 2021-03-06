package org.kh.meme.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.kh.meme.board.domain.Board;
import org.kh.meme.board.domain.BoardFile;
import org.kh.meme.board.domain.Comment;
import org.kh.meme.board.domain.Recommend;
import org.kh.meme.common.PageInfo;

public interface BoardStore {
	
	//페이징 처리
	public int selectListCount(SqlSession sqlSession);
	
	//게시판 리스트
	public List<Board> selectAllBoard(SqlSession sqlSession, PageInfo pi);
	

	//게시글 등록
	public int insertBoard(SqlSession sqlSession, Board board);
	public int insertBoardFile(SqlSession sqlSession, BoardFile boardFile);

	//게시글 수정

	public int updateBoard(SqlSession sqlSession, Board board);
	public int updateBoardFile(SqlSession sqlSession, BoardFile boardFile);

	//게시글 삭제
	public int deleteBoard(SqlSession sqlSession, Integer boardNo);
	//게시글 지워질 때 첨부파일, 댓글, 추천 기록 같이 삭제
	public int deleteBoardFile(SqlSession sqlSession, Integer boardNo);
	public void deleteCommentBoardNo(SqlSession sqlSession, Integer boardNo);
	public void deleteRecommendBoardNo(SqlSession sqlSession, Integer boardNo);
	
	
	//게시판 상세 페이지
	public Board selectBoardOneById(SqlSession sqlSession, Integer boardNo);
	public BoardFile selectBoardFileOneByNo(SqlSession sqlSession, int boardNo);	//첨부파일

	//댓글
	public int insertComment(SqlSession sqlSession, Comment comment);

	public List<Comment> selectAllComment(SqlSession sqlSession, int boardNo);

	public int updateComment(SqlSession sqlSession, Comment comment);

	public int deleteComment(SqlSession sqlSession, int commentNo);
	
	
	//조회 수
	public int updateBoardCount(SqlSession sqlSession, Integer boardNo);

	//추천 수
	//recommend table insert
	public int insertBoardLike(SqlSession sqlSession, Recommend recommend);
	// board table update
	public int updateBoardLike(SqlSession sqlSession, Recommend recommend);
	
	//신고 수
	public int updateBoardReport(SqlSession sqlSession, int boardNo);



	//관리자 : 신고 게시글 숨기기
	public int updateBoardReportManagerToN(SqlSession sqlSession, int boardNo);
	//관리자 : 신고 게시글 보이기
	public int updateBoardReportManagerToY(SqlSession sqlSession, int boardNo);












}
