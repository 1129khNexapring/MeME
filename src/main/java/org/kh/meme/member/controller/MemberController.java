package org.kh.meme.member.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kh.meme.board.domain.Board;
import org.kh.meme.board.domain.Comment;
import org.kh.meme.common.PageInfo;
import org.kh.meme.common.Pagination;
import org.kh.meme.member.domain.Member;
import org.kh.meme.member.service.MemberService;
import org.kh.meme.quiz.domain.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	// 회원 로그인
	@RequestMapping(value="/member/login.me", method=RequestMethod.POST)
	public String memberLogin(HttpServletRequest request
			,@RequestParam("memberId") String memberId
			,@RequestParam("memberPw") String memberPw) {
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
//		String referer = request.getHeader("Referer");
		
		try {
			Member loginMember = mService.loginMember(member);
			if(loginMember != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginMember", loginMember);
				session.setAttribute("memberId", memberId);
				return "redirect:/";
			}else {
				request.setAttribute("msg", "로그인 실패!");
				return ".tilesHead/common/errorPage";
			}
		}catch (Exception e) {
			request.setAttribute("msg", e.toString());
			return ".tilesHead/common/errorPage";
		}
	}
	
	// 로그아웃
	@RequestMapping(value="/member/logout.me", method=RequestMethod.GET)
	public String memberLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session != null) {
			session.invalidate();
			return "redirect:/";
		}else {
			request.setAttribute("msg", "로그아웃 실패!");
			return ".tilesHead/common/errorPage";
		}
	}
	
	// 회원가입 페이지 이동
	@RequestMapping(value="/member/join.me", method=RequestMethod.GET)
	public String memberJoin(Model model) {
		model.addAttribute("title", "회원가입");
		return ".tilesHead/member/memberJoin";
	}
	
	// 회원 가입
	@RequestMapping(value="/member/join.me", method=RequestMethod.POST)
	public String memberRegister(
			Model model
			, @ModelAttribute Member member) throws UnsupportedEncodingException {
		try {
			int result = mService.registerMember(member);
			if(result > 0) {
				return ".tilesHead/member/joinSuccess";
			}else {
				model.addAttribute("msg", "회원가입 실패");
				return ".tilesHead/common/errorPage";
			}			
		}catch(Exception e) {
			model.addAttribute("msg", e.toString());
			return ".tilesHead/common/errorPage";
		}
	}
	
	// 아이디 찾기 페이지 이동
	@RequestMapping(value="/member/findId.me", method=RequestMethod.GET)
	public String memberFindIdRedirect(Model model) {
		model.addAttribute("title", "아이디 찾기");
		return ".tilesHead/member/findId";
	}
	
	// 아이디 찾기
	@RequestMapping(value="/member/findId.me", method=RequestMethod.POST)
	public String memberFindId(HttpServletRequest request
			, Model model
			, @ModelAttribute Member member) {
		try {
			Member memberOne = mService.findMemberId(member);
			if(memberOne != null) {
				model.addAttribute("member", memberOne);
				return ".tilesHead/member/findIdPrint";
			}else {
				request.setAttribute("msg", "아이디 찾기 실패");
				return ".tilesHead/common/errorPage";
			}
		}catch(Exception e) {
			request.setAttribute("msg", e.toString());
			return ".tilesHead/common/errorPage";
		}
	}
	
	// 비밀번호 찾기 페이지 이동
	@RequestMapping(value="/member/findPw.me", method=RequestMethod.GET)
	public String memberFindPwRedirect(Model model) {
		model.addAttribute("title", "비밀번호 찾기");
		return ".tilesHead/member/findPw";
	}
	
	// 비밀번호 찾기
	@RequestMapping(value="/member/findPw.me", method=RequestMethod.POST)
	public String memberFindPw(HttpServletRequest request
		, Model model
		, @ModelAttribute Member member) {
		try {
			Member memberOne = mService.findMemberPw(member);
			if(memberOne != null) {
				model.addAttribute("member", memberOne);
				model.addAttribute("title", "비밀번호 재설정");
				return ".tilesHead/member/pwReset";
			}else {
				request.setAttribute("msg", "비밀번호 찾기 실패");
				return ".tilesHead/common/errorPage";
			}
		}catch(Exception e) {
			request.setAttribute("msg", e.toString());
			return ".tilesHead/common/errorPage";
			}
		}
	
	// 비밀번호 재설정
	@RequestMapping(value="/member/pwReset.me", method=RequestMethod.POST)
	public String memberPwReset(HttpServletRequest request
		, Model model
		, @ModelAttribute Member member) {
		try {
			int result = mService.memberPwReset(member);
			if(result > 0) {
				return ".tilesHead/member/pwResetSuccess";
			}else {
				request.setAttribute("msg", "비밀번호 재설정 실패");
				return ".tilesHead/common/errorPage";
			}
		}catch(Exception e) {
			request.setAttribute("msg", e.toString());
			return ".tilesHead/.tilesHead/common/errorPage";
		}
	}
	
	// 회원 정보 수정 페이지 이동
	@RequestMapping(value="/member/modifyMember.me", method=RequestMethod.GET)
	public String memberModifyMemberRedirect(HttpServletRequest request
			,Model model) {
		HttpSession session = request.getSession();
		Member memberOne = (Member)session.getAttribute("loginMember");
		if(memberOne == null) {
			return "redirect:/login.me";
		}
		Member member = mService.selectById(memberOne.getMemberId());
		model.addAttribute("member", member);
		model.addAttribute("title", "마이페이지");
		return ".tilesHead/member/modifyMember";
	}
	
	// 회원 정보 수정
	@RequestMapping(value="/member/modifyMember.me", method=RequestMethod.POST)
	public String memberModifyMember(HttpServletRequest request
			,Model model
			,@ModelAttribute Member member) {
		int result = mService.modifyMember(member);
		if(result > 0) {
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", member);
			return "redirect:/member/modifyMember.me";
		}
		return ".tilesHead/member/modifyMember";
	}
	
	// 회원 탈퇴 페이지 이동
	@RequestMapping(value="/member/deleteMember.me", method=RequestMethod.GET)
	public String memberDeleteMemberRedirect(Model model) {
		model.addAttribute("title", "마이페이지");
		return ".tilesHead/member/deleteMember";
	}
	
	// 회원 탈퇴
	@RequestMapping(value="/member/remove.me", method=RequestMethod.GET)
	public String memberRemove(HttpServletRequest request) {
		String memberId = request.getParameter("memberId");
		int result = mService.memberRemove(memberId);
		if(result > 0) {
			HttpSession session = request.getSession();
			session.invalidate();
			return ".tilesHead/member/removeSuccess";
		}else {
			request.setAttribute("msg", "회원 탈퇴 실패!");
			return ".tilesHead/common/errorPage";
		}
	}
	
	// 마이페이지 - 내가 쓴 글
	@RequestMapping(value="/myPage.me", method=RequestMethod.GET)
	public String myPage(HttpServletRequest request
			,Model model
			, @RequestParam(value="page", required=false) Integer page) {
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("loginMember");
		
		if(member == null) {
			return "redirect:/login.me";
		}
		
		if(member.getmGrade().equals("A")) {
			return "redirect:/admin/adminHome.me";
		}
		
		int currentPage = (page != null) ? page : 1;
		int totalCount = mService.getMyPageListCount(member.getMemberId());
		PageInfo pi = Pagination.getPageInfo(currentPage, totalCount);
		model.addAttribute("pi", pi);
		
		
		List<Board> myBoardList = mService.printMyBoard(pi, member.getMemberId());
		model.addAttribute("myBoardList", myBoardList);
		model.addAttribute("title", "마이페이지");
		return ".tilesHead/member/myPage";
	}
	
	// 마이페이지 - 내가 만든 퀴즈
	@RequestMapping(value="/member/myQuiz.me", method=RequestMethod.GET)
	public String memberMyQuizRedirect(HttpServletRequest request
			,Model model
			,@RequestParam(value="page", required=false) Integer page) {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("loginMember");
		
		if(member == null) {
			return "redirect:/login.me";
		}
		if(member.getmGrade().equals("A")) {
			return "redirect:/admin/manageQuiz.me";
		}
		
		int currentPage = (page != null) ? page : 1;
		int totalCount = mService.getMyQuizListCount(member.getMemberId());
		PageInfo pi = Pagination.getPageInfo(currentPage, totalCount);
		model.addAttribute("pi", pi);
		

		List<Quiz> myQuizList = mService.printMyQuiz(pi, member.getMemberId());
		model.addAttribute("myQuizList", myQuizList);
		model.addAttribute("title", "마이페이지");
		return ".tilesHead/member/myQuiz";
	}
	
	// 마이페이지 - 내가 쓴 댓글
	@RequestMapping(value="/member/myComment.me", method=RequestMethod.GET)
	public String memberMyCommentRedirect(HttpServletRequest request
			,Model model
			,@RequestParam(value="page", required=false) Integer page) {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("loginMember");
		
		if(member == null) {
			return "redirect:/login.me";
		}
		
		int currentPage = (page != null) ? page : 1;
		int totalCount = mService.getMyCommentListCount(member.getMemberId());
		PageInfo pi = Pagination.getPageInfo(currentPage, totalCount);
		model.addAttribute("pi", pi);
		

		List<Comment> myCommentList = mService.printMyComment(pi, member.getMemberId());
		model.addAttribute("myCommentList", myCommentList);
		model.addAttribute("title", "마이페이지");
		return ".tilesHead/member/myComment";
	}
	
	
	
}
