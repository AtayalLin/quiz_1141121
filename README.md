# quiz_1141121
動態問卷(後端)

# ⚙️ QSDemo Backend — quiz_1141121
### Spring Boot RESTful API Server

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.x-6DB33F.svg?style=for-the-badge&logo=springboot)
![Java](https://img.shields.io/badge/Java-17+-ED8B00.svg?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1.svg?style=for-the-badge&logo=mysql)
![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A.svg?style=for-the-badge&logo=gradle)
![Status](https://img.shields.io/badge/Status-Fully_Integrated-success.svg?style=for-the-badge)

本專案為 QSDemo 問卷管理系統的**後端服務**，基於 Spring Boot 3 建構，提供完整的 RESTful API，負責問卷建立、題目管理、使用者填答、歷史紀錄查詢與身份驗證等核心業務邏輯。

> 🔗 前端 Angular 專案請見：
> **[👉 Angular-qsdemo (前端 Repository)](https://github.com/AtayalLin/Angular-qsdemo)**

---

## 🗂️ 專案結構 (Project Structure)

```
src/main/java/com/example/quiz_1141121/
│
├── Quiz1141121Application.java          # Spring Boot 啟動入口
│
├── config/
│   └── WebConfig.java                   # CORS 跨域設定（允許前端 localhost:4200 存取）
│
├── constants/
│   ├── ReplyMessage.java                # 統一回應訊息常數（如 "success", "not found"）
│   ├── Type.java                        # 題目類型常數（single / multi / text）
│   └── ValidationMsg.java              # 資料驗證錯誤訊息常數
│
├── controller/
│   └── QuizController.java              # ★ 所有 API 端點定義於此，路徑前綴 /quiz
│
├── dao/
│   ├── FillinDao.java                   # 填答資料存取（JPA Repository）
│   ├── QuestionsDao.java                # 題目資料存取
│   ├── QuizDao.java                     # 問卷資料存取
│   └── UserDao.java                     # 使用者資料存取
│
├── entity/
│   ├── Quiz.java                        # 問卷主表（id, title, type, published, dates...）
│   ├── Questions.java                   # 題目表（quiz_id, question, type, options...）
│   ├── QuestionId.java                  # Questions 複合主鍵
│   ├── Fillin.java                      # 填答記錄表（quiz_id, email, answers...）
│   ├── FillinId.java                    # Fillin 複合主鍵
│   └── User.java                        # 使用者表（email, name, phone, avatar...）
│
├── exception/
│   └── GlobalExceptionHandler.java      # 全域例外處理（統一回傳錯誤格式）
│
├── req/                                 # 前端送入的請求物件 (Request Body)
│   ├── AnswerVo.java                    # 單題答案結構（question + answer）
│   ├── CreateReq.java                   # 新增問卷請求
│   ├── UpdateReq.java                   # 更新問卷請求
│   ├── DeleteReq.java                   # 批次刪除請求（quizIdList）
│   ├── FeedbackReq.java                 # 查詢填答紀錄請求（quizId + email）
│   ├── FillinReq.java                   # 提交填答請求（quizId + answerVoList）
│   └── RegisterReq.java                 # 使用者註冊請求
│
├── res/                                 # 後端回傳的回應物件 (Response Body)
│   ├── BasicRes.java                    # 基礎回應（code + message）
│   ├── LoginRes.java                    # 登入回應（code + role + user）
│   ├── GetQuizRes.java                  # 問卷清單回應（quizList）
│   ├── GetQuestionRes.java              # 題目清單回應（questionList）
│   ├── CreateRes.java                   # 新增問卷回應
│   ├── UpdateRes.java                   # 更新問卷回應
│   ├── FeedbackRes.java                 # 填答內容回應（name, phone, email, answerVoList）
│   ├── FeedbackUserVo.java              # 填答使用者資訊 VO
│   └── GetFeedbackUserRes.java          # 歷史填答清單回應
│
└── service/
    ├── QuizService.java                 # 問卷相關業務邏輯（建立、編輯、發佈、刪除）
    ├── FillinService.java               # 填答相關業務邏輯（提交、查詢歷史、feedback）
    └── UserService.java                 # 使用者業務邏輯（登入、註冊、改密碼、頭像）
```

---

## 📡 API 端點清單 (API Reference)

所有端點路徑前綴為 `/quiz`，後端預設運行於 `http://localhost:8080`。

### 🔐 使用者與身份驗證

| 方法 | 路徑 | 說明 | 參數位置 |
|------|------|------|----------|
| POST | `/quiz/login` | 使用者登入，回傳角色與基本資料 | Query Params |
| POST | `/quiz/register` | 使用者註冊 | Request Body (RegisterReq) |
| POST | `/quiz/update_profile` | 更新個人資料（姓名、電話） | Request Body (User) |
| POST | `/quiz/change_password` | 修改密碼（需驗證舊密碼） | Query Params |
| POST | `/quiz/upload_avatar` | 上傳頭像（轉 Base64 存入 DB） | MultipartFile + email |

### 📋 問卷管理

| 方法 | 路徑 | 說明 | 參數位置 |
|------|------|------|----------|
| GET | `/quiz/getAll` | 取得所有問卷清單 | — |
| GET | `/quiz/get_questions_List` | 取得指定問卷的題目清單 | Query Param: `quizId` |
| POST | `/quiz/create` | 新增問卷（含題目） | Request Body (CreateReq) |
| POST | `/quiz/update` | 更新問卷（含題目） | Request Body (UpdateReq) |
| POST | `/quiz/publish` | 發佈問卷 | Query Param: `id` |
| POST | `/quiz/unpublish` | 取消發佈問卷 | Query Param: `id` |
| GET | `/quiz/delete_single` | 單筆刪除問卷 | Query Param: `quizId` |
| POST | `/quiz/delete` | 批次刪除問卷 | Request Body (DeleteReq) |

### ✏️ 填答與歷史

| 方法 | 路徑 | 說明 | 參數位置 |
|------|------|------|----------|
| POST | `/quiz/fillin` | 提交問卷填答 | Request Body (FillinReq) |
| POST | `/quiz/feedback` | 取得指定使用者的填答內容 | Request Body (FeedbackReq) |
| GET | `/quiz/get_history` | 取得會員填答歷史清單 | Query Param: `email` |

---

## 🗄️ 資料庫設定 (Database Setup)

### 建立資料庫

```sql
CREATE DATABASE quiz_1141121
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

### 主要資料表結構

**`quiz`（問卷主表）**
```sql
CREATE TABLE quiz (
  id            INT AUTO_INCREMENT PRIMARY KEY,
  title         VARCHAR(255)   NOT NULL,
  type          VARCHAR(50),
  intro         TEXT,
  start_date    DATE,
  end_date      DATE,
  is_published  BOOLEAN        DEFAULT FALSE,
  participants  INT            DEFAULT 0,
  collect_name  BOOLEAN        DEFAULT FALSE,
  collect_phone BOOLEAN        DEFAULT FALSE,
  collect_email BOOLEAN        DEFAULT FALSE,
  require_age   BOOLEAN        DEFAULT FALSE
);
```

**`questions`（題目表）**
```sql
CREATE TABLE questions (
  quiz_id       INT            NOT NULL,
  question_id   INT            NOT NULL,
  question      VARCHAR(500),
  type          VARCHAR(20),           -- single / multi / text
  options       TEXT,                  -- 選項以分號 ';' 串接
  required      BOOLEAN        DEFAULT FALSE,
  is_dependent  BOOLEAN        DEFAULT FALSE,
  parent_id     INT,
  PRIMARY KEY (quiz_id, question_id),
  FOREIGN KEY (quiz_id) REFERENCES quiz(id)
);
```

**`user`（使用者表）**
```sql
CREATE TABLE user (
  email     VARCHAR(255)   PRIMARY KEY,
  name      VARCHAR(100),
  phone     VARCHAR(20),
  password  VARCHAR(255),
  role      VARCHAR(20)    DEFAULT 'user',  -- 'user' 或 'admin'
  avatar    LONGTEXT,      -- ⚠️ 必須為 LONGTEXT，Base64 圖片字串較長
  join_date DATE
);
```

**`fillin`（填答記錄表）**
```sql
CREATE TABLE fillin (
  quiz_id      INT            NOT NULL,
  question_id  INT            NOT NULL,
  email        VARCHAR(255)   NOT NULL,
  answer       TEXT,
  name         VARCHAR(100),
  phone        VARCHAR(20),
  age          INT,
  PRIMARY KEY (quiz_id, question_id, email),
  FOREIGN KEY (quiz_id) REFERENCES quiz(id)
);
```

> ⚠️ **重要**：若已建立 `user` 表但 `avatar` 欄位為 `VARCHAR`，請執行以下 SQL 修正，否則上傳頭像時會發生截斷錯誤：
> ```sql
> ALTER TABLE user MODIFY COLUMN avatar LONGTEXT;
> ```

---

## 🚀 本地端啟動 (Local Setup)

### 前置需求

| 工具 | 建議版本 |
|------|----------|
| Java | 17 以上 |
| Gradle | 8.x（專案內附 Gradle Wrapper，不需另外安裝） |
| MySQL | 8.0 |

### Step 1 — 設定資料庫連線

編輯 `src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quiz_1141121?useSSL=false&serverTimezone=Asia/Taipei
spring.datasource.username=你的MySQL帳號
spring.datasource.password=你的MySQL密碼
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### Step 2 — 啟動後端

```bash
# 使用 Gradle Wrapper 啟動（不需安裝 Gradle）
./gradlew bootRun

# Windows 使用
gradlew.bat bootRun
```

或直接在 IntelliJ IDEA 中執行 `Quiz1141121Application.java` 的 `main()` 方法。

後端成功啟動後，終端機會顯示：
```
Tomcat started on port 8080 (http)
Started Quiz1141121Application
```

### Step 3 — 確認 CORS 設定

`WebConfig.java` 預設允許來自 `http://localhost:4200` 的請求。若前端使用其他 port，請修改：

```java
// WebConfig.java
config.addAllowedOrigin("http://localhost:4200"); // 改為實際前端網址
```

---

## 📦 請求 / 回應格式範例

### 登入 (POST /quiz/login)
```
Request: ?email=user@example.com&password=123456

Response:
{
  "code": 200,
  "message": "success",
  "role": "user",
  "user": {
    "email": "user@example.com",
    "name": "林曉齊",
    "phone": "0912-345-678",
    "avatar": "data:image/png;base64,..."
  }
}
```

### 提交填答 (POST /quiz/fillin)
```json
{
  "quizId": 1,
  "email": "user@example.com",
  "name": "林曉齊",
  "phone": "0912-345-678",
  "age": 25,
  "answerVoList": [
    {
      "question": {
        "quiz_id": 1,
        "question_id": 1,
        "question": "滿意度如何？",
        "type": "single",
        "required": true,
        "options": "非常滿意;滿意;普通;不滿意",
        "is_dependent": false,
        "parent_id": null
      },
      "answer": "非常滿意"
    }
  ]
}
```

### 標準回應格式
```json
{
  "code": 200,
  "message": "success"
}
```
```json
{
  "code": 404,
  "message": "User Not Found"
}
```

---

## 📅 版本紀錄 (Changelog)

- **v2.5.0 (2026-03-10)**
  - **[Docs]** 新增完整 README（API 清單、資料表結構、啟動說明）
  - **[Fix]** 修正 `FeedbackReq.java` getter 寫死回傳值導致永遠回傳 `quizId=0, email=null` 的問題
  - **[Fix]** 確認 `user.avatar` 欄位型別為 `LONGTEXT`，修正 Base64 截斷錯誤

- **v2.0.0**
  - 完成前後端 API 全面整合
  - 新增 `GlobalExceptionHandler` 統一錯誤處理

---

© 2026 QSDemo Engineering Team. Developer: AtayalLin. All Rights Reserved.
