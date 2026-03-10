package com.example.quiz_1141121.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.quiz_1141121.constants.ReplyMessage;
import com.example.quiz_1141121.constants.Type;
import com.example.quiz_1141121.dao.FillinDao;
import com.example.quiz_1141121.dao.QuizDao;
import com.example.quiz_1141121.entity.Quiz;
import com.example.quiz_1141121.dao.QuestionsDao;
import com.example.quiz_1141121.dao.UserDao;
import com.example.quiz_1141121.entity.Fillin;
import com.example.quiz_1141121.entity.Questions;
import com.example.quiz_1141121.entity.User;
import com.example.quiz_1141121.req.AnswerVo;
import com.example.quiz_1141121.req.FeedbackReq;
import com.example.quiz_1141121.req.FillinReq;
import com.example.quiz_1141121.res.BasicRes;
import com.example.quiz_1141121.res.FeedbackRes;
import com.example.quiz_1141121.res.FeedbackUserVo;
import com.example.quiz_1141121.res.GetFeedbackUserRes;

@Service
@Transactional(rollbackFor = Exception.class)
public class FillinService {

	@Autowired
	private FillinDao fillinDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionsDao questionsDao;

	@Transactional(rollbackFor = Exception.class)
	public BasicRes fillin(FillinReq req) {
		BasicRes res = checkParams(req);
		if (res != null) {
			return res;
		}
		/* 新增資料 */
		try {
			for (AnswerVo vo : req.getAnswerVoList()) {
				fillinDao.insert(req.getQuizId(), vo.getQuestion().getQuestion_id(), req.getEmail(), //
						vo.getAnswer());
			}
		} catch (Exception e) {
			throw e;
		}

		return new BasicRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());

	}

	private BasicRes checkParams(FillinReq req) {
		if (req.getQuizId() <= 0) {
			return new BasicRes(ReplyMessage.QUIZ_ID_ERROR.getCode(), //
					ReplyMessage.QUIZ_ID_ERROR.getMessage());
		}
		 List<Fillin> existing = fillinDao.getByQuizIdAndEmail(req.getQuizId(), req.getEmail());
		    if (existing != null && !existing.isEmpty()) {
		        return new BasicRes(400, "您已填寫過此問卷，無法重複提交");
		    }
		if (!StringUtils.hasText(req.getEmail())) {
			return new BasicRes(ReplyMessage.USER_EMAIL_ERROR.getCode(), //
					ReplyMessage.USER_EMAIL_ERROR.getMessage());

		}
		if (!StringUtils.hasText(req.getName())) {
			return new BasicRes(ReplyMessage.USER_NAME_ERROR.getCode(), //
					ReplyMessage.USER_NAME_ERROR.getMessage());

		}
		Quiz quiz = quizDao.getById(req.getQuizId());
		if (quiz != null && quiz.isRequireAge() && req.getAge() < 18) {
			return new BasicRes(ReplyMessage.USER_AGE_ERROR.getCode(), //
					ReplyMessage.USER_AGE_ERROR.getMessage());
		}
		for (AnswerVo vo : req.getAnswerVoList()) {
			/* 檢查必填但沒有答案(沒有選項編號或選項) */
			if (vo.getQuestion().isRequired()) {
				if (!StringUtils.hasText(vo.getAnswer())) {
					return new BasicRes(ReplyMessage.ANSWER_REQUIRED.getCode(), "題目：" +
						       vo.getQuestion().getQuestion() + " 為必填");
				}
			}
		}
		return null;
	}

	public GetFeedbackUserRes getAllFillinUsers(int quizId) {
		if (quizId <= 0) {
			return new GetFeedbackUserRes(ReplyMessage.QUIZ_ID_ERROR.getCode(), //
					ReplyMessage.QUIZ_ID_ERROR.getMessage());
		}
		List<Fillin> list = fillinDao.getByQuizId(quizId);
		Map<String, FeedbackUserVo> map = new HashMap<>();
		
		List<FeedbackUserVo> userVoList = new ArrayList<>();
		Map<String, List<AnswerVo>> answerVomap = new HashMap<>();

		List<Questions> QuestionsList = questionsDao.getByQuizId(quizId);

		for (Fillin fillin : list) {
			String email = fillin.getUserEmail();
			if(!map.containsKey(email)) {
				/* 使用 map 可以防止同一位使用者有多題的作答時，會產生多個 FeedbackUserVo，
				 * 因為是同一位使用者對同一張問卷作答多題
				 * 新的使用者必須要重置 List<AnswerVo> answerVoLis*/
				User user = userDao.getByEmail(email);
				if (user == null) continue;
				List<AnswerVo> answerVoList = new ArrayList<>();
			    FeedbackUserVo userVo = new FeedbackUserVo(user.getName(), user.getPhone(), //
			    		user.getEmail(), user.getAge(), fillin.getFillinDate(), answerVoList);
			    userVoList.add(userVo);
			    map.put(email, userVo);
			    answerVomap.put(email, answerVoList);

			    
			}
			
			for (Questions questions : QuestionsList) {
				/* 比對 question_id: 一樣的就把答案放到 AnswerVo */
				if (fillin.getQuestionId() == questions.getQuestion_id()) {
					AnswerVo answerVo = new AnswerVo(questions, fillin.getAnswer());
					 map.get(email).getAnswerVoList().add(answerVo);
					 break;
//					/* 取出特定使用者舊的 answerVoList */
//					answerVoList = answerVomap.get(fillin.getUserEmail());
//					/* 增加新的 answerVo*/
//					answerVoList.add(answerVo);
//					/* 把新的 answerVoList 放回到 answerVomap 中 */
//					answerVomap.put(fillin.getUserEmail(), answerVoList);
					
				}
			}
		}
		return new GetFeedbackUserRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), userVoList);
	}
	
	public FeedbackRes feedback(FeedbackReq req) {
		/* 參數檢查 */
		/* 透過 quizId 取得所有問題*/
		List<Questions> questionsList = questionsDao. getByQuizId(req.getQuizId());
		List<Fillin> fillinList = fillinDao.getByQuizIdAndEmail(req.getQuizId(), req.getEmail());
		User user = userDao.getByEmail(req.getEmail());
		
		if(user == null) {
			return new FeedbackRes(ReplyMessage.USER_NOT_FOUND.getCode(), //
					ReplyMessage.USER_NOT_FOUND.getMessage());
		}
		
		List<AnswerVo> answerVoList = new ArrayList<>(); // answerVoList > 參考記憶體位置
		
		
		/* 把答案匹配到對應的問題上 */
		for(Questions qu : questionsList) {
			AnswerVo vo = new AnswerVo(qu); // vo 中一定會包含 Questions，但不一定會有使用者的填答。
			for(Fillin fillin : fillinList) {
				if(qu.getQuestion_id() == fillin.getQuestionId()) {
					/* 把 andser 放到 vo 中*/
					vo.setAnswer(fillin.getAnswer());
					/* 有匹配到相同的題號，就可以跳過剩下 fillin 的比對 */
					break;
				}
			}
			answerVoList.add(vo);
		}
		return new FeedbackRes (ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), req.getQuizId(), req.getEmail(), user.getName(), //
				user.getPhone(), user.getAge(), answerVoList);
		
	}
	
    /**
     * [新增] 獲取該會員的填答歷史清單
     */
       public List<Quiz> getUserHistory(String email) {
           // 1. 從 fillin 表找出該 email 填過的所有 quiz_id (不重複)
           List<Integer> quizIds = fillinDao.findQuizIdsByEmail(email);
  
           // 2. 根據 ID 清單，去 quiz 表抓取對應的問卷實體
           List<Quiz> history = new ArrayList<>();
           for (Integer id : quizIds) {
               quizDao.findById(id).ifPresent(history::add);
           }
  
           return history;
       }
}
