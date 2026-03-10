package com.example.quiz_1141121.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz_1141121.entity.Fillin;
import com.example.quiz_1141121.entity.FillinId;

@Repository
public interface FillinDao extends JpaRepository<Fillin, FillinId> {

	      /**
	         * 新增填答紀錄
	        * [修正] 確保插入時對應到 MySQL 的 user_email 欄位
	        */
	        @Modifying
	        @Transactional
	        @Query(value = "insert into fillin (quiz_id, question_id, user_email, answer, fillin_date) "      
	                + " values (?1, ?2, ?3, ?4, CURDATE())", nativeQuery = true)
	        public void insert(int quizId, int questionId, String userEmail, String answer);
	   
	        @Query(value = "select * from fillin where quiz_id = ?1", nativeQuery = true)
	        public List<Fillin> getByQuizId(int quizId);
	
	
	@Query(value = "select * from fillin where quiz_id = ?1 and user_email = ?2", nativeQuery = true)
	public List<Fillin> getByQuizIdAndEmail(int quizId, String email);
	
	 /**
	   * [新增] 根據使用者的 Email 找出所有不重複的填答問卷 ID
	   * 功用：協助會員中心顯示「填過哪些問卷」的清單
	   */
	   @Query(value = "SELECT DISTINCT quiz_id FROM fillin WHERE user_email = ?1", nativeQuery = true)   
	   public List<Integer> findQuizIdsByEmail(String userEmail);
}
