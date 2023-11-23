package com.ssafy.vue.board.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.vue.board.model.BoardCommentDto;
import com.ssafy.vue.board.model.CommentBDto;
import com.ssafy.vue.comment.controller.CommentController;
import com.ssafy.vue.comment.model.CommentDto;
import com.ssafy.vue.comment.model.service.CommentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.BoardListDto;
import com.ssafy.vue.board.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러  API V1")
@Slf4j
public class BoardController {

	private BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping
	public ResponseEntity<?> writeArticle(
			@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto) {
		log.info("writeArticle boardDto - {}", boardDto);
		try {
			boardService.writeArticle(boardDto);
//			return ResponseEntity.ok().build();
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<?> listArticle(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		log.info("listArticle map - {}", map);
		try {
			BoardListDto boardListDto = boardService.listArticle(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<?> getArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int count = countComment(articleno);

		if (count == 0) {
			BoardDto board = boardService.getArticle(articleno);
			map.put("resultData", board);
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} else {
			BoardCommentDto boardComment = boardService.getArticleComment(articleno);
			boardComment.setCommentBDto(listComment(articleno));
			map.put("resultData", boardComment);
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "게시글의 댓글개수", notes = "게시글마다 댓글의 개수를 나타냄", response = CommentDto.class)
	@GetMapping("/{articleNo}/count")
	public int countComment(
			@PathVariable("articleNo") @ApiParam(value = "댓글 개수를 조회할 게시판 번호", required = true) int articleno
	) throws Exception {
		int count = boardService.countComment(articleno);
		log.info("countComment - 호출 : " + count);

		return count;
	}

	@ApiOperation(value = "댓글 리스트 호출", notes = "게시글의 댓글을 보여준다")
	@GetMapping("/{articleno}/comment")
	public List<CommentBDto> listComment(
			@PathVariable("articleno") @ApiParam(value = "게시글 번호") int articleno
	) throws Exception {
		log.info("listCommet 호출 : " + articleno);
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<CommentBDto> comm = boardService.listComment(articleno);

		return comm;
	}

	@ApiOperation(value = "글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/modify/{articleno}")
	public ResponseEntity<BoardDto> getModifyArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		log.info("getModifyArticle - 호출 : " + articleno);
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
		log.info("modifyArticle - 호출 {}", boardDto);

		boardService.modifyArticle(boardDto);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") @ApiParam(value = "삭제할 글의 글번호.", required = true) int articleno) throws Exception {
		log.info("deleteArticle - 호출");
		boardService.deleteArticle(articleno);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "게시글 총 개수", notes = "게시글의 총 개수를 나타냄", response = BoardDto.class)
	@GetMapping("/count")
	public ResponseEntity<?> countArticle() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int count = boardService.countArticle();
		log.info("countArticle - 호출 : " + count);
		map.put("resultData", count);

		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}


	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}