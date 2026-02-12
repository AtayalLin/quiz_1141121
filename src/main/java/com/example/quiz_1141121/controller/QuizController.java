package com.example.quiz_1141121.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;// 新增，處理跨域
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_1141121.req.CreateReq;
import com.example.quiz_1141121.req.DeleteReq;
import com.example.quiz_1141121.req.FillinReq;
import com.example.quiz_1141121.res.BasicRes;
import com.example.quiz_1141121.res.CreateRes;
import com.example.quiz_1141121.res.GetQuestionRes;
import com.example.quiz_1141121.res.GetQuizRes;
import com.example.quiz_1141121.res.UpdateRes;
import com.example.quiz_1141121.service.FillinService;
import com.example.quiz_1141121.service.QuizService;

@RestController
//允許 Angular (通常是 4200 埠) 進行跨域請求
@CrossOrigin(origins = "*")
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@Autowired
	private FillinService fillinService;
	
	/**
     * 新增問卷與問題
     * URL: POST http://localhost:8080/quiz/create
     */
	
	@PostMapping("quiz/create")
	public CreateRes create(@RequestBody CreateReq req) {
		return quizService.create(req);
	}
	
	/**
     * 更新問卷與問題 (補上這個 API)
     */
	@PostMapping("quiz/update")
	public UpdateRes update(@RequestBody CreateReq req) {
		return quizService.update(req);
	}
	
	/**
     * 取得所有問卷清單 (不含題目)
     * URL: GET http://localhost:8080/quiz/getAll
     */
	@GetMapping("quiz/getAll")
	public GetQuizRes getQuizList() {
		return quizService.getQuizList();
	}
	
	/*API 的路徑: http://localhost:8080/quiz/get_questions_List?quizId=1 */
	@GetMapping("quiz/get_questions_List")
	public GetQuestionRes getQuestionList(@RequestParam("quizId") int quizId) {
		return quizService.getQuestionList(quizId);
	}
	
	/**
     * 單筆刪除問卷 (保留你原本的需求)
     * URL: GET http://localhost:8080/quiz/delete_single?quizId=1
     */
	@GetMapping("quiz/delete_single")
	public BasicRes deleteSingle(@RequestParam("quizId") int quizId) {
		// 將單個 ID 轉為 List 後傳給 Service
		return quizService.deleteQuiz(Arrays.asList(quizId));
	}
	
	/**
     * 批次刪除問卷
     * URL: POST http://localhost:8080/quiz/delete
     */
	@PostMapping("quiz/delete")
	public BasicRes deleteBatch(@RequestBody DeleteReq req) {
		// 調用 Service 的 deleteQuiz，並傳入 ID 列表
		return quizService.deleteQuiz(req.getQuizIdList());
	}
	
	@PostMapping("quiz/fillin")
	public BasicRes fillin(@RequestBody FillinReq req) {
		return fillinService.fillin(req);
	}
	
	

}
