package com.example.quiz_1141121.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz_1141121.constants.ReplyMessage;
import com.example.quiz_1141121.constants.Type;
import com.example.quiz_1141121.dao.QuestionsDao;
import com.example.quiz_1141121.dao.QuizDao;
import com.example.quiz_1141121.entity.Questions;
import com.example.quiz_1141121.entity.Quiz;
import com.example.quiz_1141121.req.CreateReq;
import com.example.quiz_1141121.res.BasicRes;
import com.example.quiz_1141121.res.CreateRes;
import com.example.quiz_1141121.res.GetQuestionRes;
import com.example.quiz_1141121.res.GetQuizRes;
import com.example.quiz_1141121.res.UpdateRes;

@Service
@Transactional(rollbackFor = Exception.class)
public class QuizService {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionsDao questionsDao;

	/**
	 * 新增問卷與問題
	 * 使用 @Transactional 確保問卷與題目要嘛同時成功，要嘛同時失敗(回滾)
	 */
	@Transactional(rollbackFor = Exception.class)
	public CreateRes create(CreateReq req) {

		/* 1. 檢查前端傳來的參數是否符合規範 */
		CreateRes res = checkParams(req);
		if (res != null) {
			return res; // 若有錯誤，直接回傳錯誤代碼與訊息
		}

		try {
			/* 2. 執行新增問卷主體 (quiz 表) */
			quizDao.insertQuiz(
					req.getTitle(), 
					req.getDescription(), 
					req.getStart_date(),
					req.getEnd_date(), 
					req.isPublished()
			);

			/* 3. 取得剛剛新增的那筆問卷 ID (用於關聯題目) 
			 * 註：如果是使用 JPA 的 save()，可以直接從回傳物件拿到 ID
			 */
			int quizId = quizDao.getQuizmax();

			/* 4. 迴圈新增問題列表 (questions 表) */
			for (Questions item : req.getQuestionList()) {
				questionsDao.insertQuestion(
						quizId,                // 關聯問卷 ID
						item.getQuestion_id(), // 題目序號 (q1, q2...)
						item.getQuestion(),    // 題目內容
						item.getType(),        // 題目類型 (single/multi/text)
						item.isRequired(),     // 是否必填
						item.getOptions()       // 選項內容 (用分號串接)
				);
			}

		} catch (Exception e) {
			// 印出錯誤日誌方便除錯，並拋出例外觸發 @Transactional 回滾
			e.printStackTrace(); 
			throw e;
		}
		
		// 全部成功後回傳成功訊息
		return new CreateRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
	}

	/**
	 * 參數檢查邏輯
	 */
	private CreateRes checkParams(CreateReq req) {
		// 檢查問卷標題
		if (!StringUtils.hasText(req.getTitle())) {
			return new CreateRes(ReplyMessage.TITLE_ERROR.getCode(), ReplyMessage.TITLE_ERROR.getMessage());
		}
		
		// 檢查問卷描述
		if (!StringUtils.hasText(req.getDescription())) {
			return new CreateRes(ReplyMessage.DESCRIPTION_ERROR.getCode(), ReplyMessage.DESCRIPTION_ERROR.getMessage());
		}

		// 檢查開始時間：不能為空，且不能晚於結束時間
		if (req.getStart_date() == null || req.getEnd_date() == null) {
			return new CreateRes(ReplyMessage.START_DATE_ERROR.getCode(), "日期不得為空");
		}
		
		// 邏輯檢查：開始時間不能在結束時間之後
		if (req.getStart_date().isAfter(req.getEnd_date())) {
			return new CreateRes(ReplyMessage.START_DATE_ERROR.getCode(), ReplyMessage.START_DATE_ERROR.getMessage());
		}

		// 檢查結束時間：不能早於今天
		if (req.getEnd_date().isBefore(LocalDate.now())) {
			return new CreateRes(ReplyMessage.END_DATE_ERROR.getCode(), ReplyMessage.END_DATE_ERROR.getMessage());
		}

		// 檢查題目列表：至少要有一題
		if (req.getQuestionList() == null || req.getQuestionList().isEmpty()) {
			return new CreateRes(ReplyMessage.QUESTION_LIST_EMPTY.getCode(), ReplyMessage.QUESTION_LIST_EMPTY.getMessage());
		}

		// 逐題檢查內容
		for (Questions item : req.getQuestionList()) {
			// 檢查題號
			if (item.getQuestion_id() <= 0) {
				return new CreateRes(ReplyMessage.QUESTION_ID_ERROR.getCode(), ReplyMessage.QUESTION_ID_ERROR.getMessage(), item.getQuestion_id());
			}
			// 檢查題目文字
			if (!StringUtils.hasText(item.getQuestion())) {
				return new CreateRes(ReplyMessage.QUESTION_ERROR.getCode(), ReplyMessage.QUESTION_ERROR.getMessage(), item.getQuestion_id());
			}
			// 檢查題目類型 (single/multi/text)
			if (!Type.check(item.getType())) {
				return new CreateRes(ReplyMessage.TYPE_ERROR.getCode(), ReplyMessage.TYPE_ERROR.getMessage(), item.getQuestion_id());
			}
			// 檢查選項內容：若非簡答題 (TEXT)，則選項 (Options) 必須有內容
			if (!item.getType().equalsIgnoreCase(Type.TEXT.getType())) {
				if (!StringUtils.hasText(item.getOptions())) {
					return new CreateRes(ReplyMessage.OPTIONS_ERROR.getCode(), ReplyMessage.OPTIONS_ERROR.getMessage(), item.getQuestion_id());
				}
			}
		}
		return null; // 通過所有檢查
	}

	/**
	 * 查詢所有問卷列表
	 */
	public GetQuizRes getQuizList() {
		return new GetQuizRes(ReplyMessage.SUCCESS.getCode(), 
				ReplyMessage.SUCCESS.getMessage(), quizDao.getAll());
	}

	/**
	 * 根據問卷 ID 查詢題目細節
	 */
	public GetQuestionRes getQuestionList(int quizId) {
		return new GetQuestionRes(ReplyMessage.SUCCESS.getCode(), 
				ReplyMessage.SUCCESS.getMessage(), questionsDao.getByQuizId(quizId));
	}
	
	/**
     * 更新問卷與問題
     */
    @Transactional(rollbackFor = Exception.class)
    public UpdateRes update(CreateReq req) {
        UpdateRes res = checkUpdateParams(req);
        if (res != null) return res;

        try {
            Quiz existingQuiz = quizDao.getById(req.getId());
            if (existingQuiz == null) {
                return new UpdateRes(ReplyMessage.QUIZ_NOT_FOUND.getCode(), ReplyMessage.QUIZ_NOT_FOUND.getMessage());
            }

            // 調用 QuizDao 的 update
            quizDao.update(req.getId(), req.getTitle(), req.getDescription(), req.getStart_date(), req.getEnd_date(), req.isPublished());

            // 修正：調用 QuestionsDao 的 deleteByQuizId (清空該問卷舊題目)
            questionsDao.deleteByQuizId(req.getId());

            for (Questions item : req.getQuestionList()) {
                questionsDao.insertQuestion(req.getId(), item.getQuestion_id(), item.getQuestion(), item.getType(), item.isRequired(), item.getOptions());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return new UpdateRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
    }

	/**
	 * 針對 Update 的參數檢查 (回傳 UpdateRes)
	 */
	private UpdateRes checkUpdateParams(CreateReq req) {
		// 檢查 ID 是否存在於 Request 中
		if (req.getId() <= 0) {
			return new UpdateRes(ReplyMessage.QUIZ_ID_ERROR.getCode(), ReplyMessage.QUIZ_ID_ERROR.getMessage());
		}

		// 呼叫通用檢查
		CreateRes checkResult = checkParams(req);
		if (checkResult != null) {
			// 轉換成 UpdateRes 並回傳 (修正方法名為 getQuestionId)
			return new UpdateRes(checkResult.getCode(), checkResult.getMessage(), checkResult.getQuestionid());
		}
		
		return null;
	}
	
	/**
	 * 刪除問卷及其關聯問題
	 * 參數：List<Integer> ids (支援批次刪除)
	 */
	/**
     * 刪除問卷 (批次)
     */
    @Transactional(rollbackFor = Exception.class)
    public BasicRes deleteQuiz(List<Integer> ids) {
        BasicRes res = checkDeleteParams(ids);
        if (res != null) return res;

        try {
            // 修正：調用 QuestionsDao 的 delete(List<Integer>)
            questionsDao.delete(ids); 
            
            // 調用 QuizDao 的 delete(List<Integer>)
            quizDao.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return new BasicRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
    }
    
    private BasicRes checkDeleteParams(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new BasicRes(ReplyMessage.QUIZ_ID_ERROR.getCode(), "請選擇要刪除的問卷");
        }
        return null;
    }
	

}