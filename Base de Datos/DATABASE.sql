-- SQLINES DEMO *** le SQL Developer Data Modeler 21.2.0.183.1957
-- SQLINES DEMO *** -10-30 13:52:30 COT
-- SQLINES DEMO ***  Server 2012
-- SQLINES DEMO *** Server 2012



-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE classroom 
    (
     id_classroom BIGSERIAL NOT NULL , 
     code_class VARCHAR (10) , 
     date_registration_class TIMESTAMP(3) , 
     class_description TEXT , 
     class_section VARCHAR (50) , 
     name_class VARCHAR (50) , 
     classroom_number VARCHAR (10) , 
     class_state CHAR (1) , 
     person_id_person BIGINT NOT NULL , 
     period_id_period BIGINT NOT NULL 
    );

ALTER TABLE classroom ADD CONSTRAINT classroom_PK PRIMARY KEY (id_classroom)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE evaluation 
    (
     id_evaluation BIGSERIAL NOT NULL , 
     name_evaluation VARCHAR (50) , 
     description_evaluation TEXT , 
     state_evaluation CHAR (1) , 
     date_registration_evaluation TIMESTAMP(3) , 
     date_finish_evaluation TIMESTAMP(3) , 
     file_route_evaluation TEXT , 
     external_resource TEXT , 
     classroom_id_classroom BIGINT NOT NULL 
    );

ALTER TABLE evaluation ADD CONSTRAINT evaluation_PK PRIMARY KEY (id_evaluation)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE evaluation_categories 
    (
     id_categorie BIGSERIAL NOT NULL , 
     name_categorie VARCHAR (30) , 
     state_categorie CHAR (1) 
    );

ALTER TABLE evaluation_categories ADD CONSTRAINT evaluation_categories_PK PRIMARY KEY (id_categorie)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE evaluation_questions 
    (
     id_evaluationquestions BIGSERIAL NOT NULL , 
     qualification NUMERIC , 
     date_register_evaluation_question TIMESTAMP(3) ,
     comment_answer TEXT , 
     result_answer TEXT , 
     evaluation_id_evaluation BIGINT NOT NULL , 
     questions_id_questions BIGINT NOT NULL 
    );

ALTER TABLE evaluation_questions ADD CONSTRAINT evaluation_questions_PK PRIMARY KEY (id_evaluationquestions)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE evaluation_reponse 
    (
     id_evaluationresponse BIGSERIAL NOT NULL , 
     evaluation_questions_id_evaluationquestions BIGINT NOT NULL , 
     person_id_person BIGINT NOT NULL , 
     qualification NUMERIC , 
     date_register_evaluation_response TIMESTAMP(3) , 
     state_response CHAR (1) 
    );

ALTER TABLE evaluation_reponse ADD CONSTRAINT evaluation_reponse_PK PRIMARY KEY (id_evaluationresponse)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE period 
    (
     id_period BIGSERIAL NOT NULL , 
     name_period VARCHAR (20) , 
     date_registration_period TIMESTAMP(3) , 
     state_period CHAR (1) , 
     person_id_person BIGINT NOT NULL 
    );

ALTER TABLE period ADD CONSTRAINT period_PK PRIMARY KEY (id_period)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE person 
    (
     id_person BIGSERIAL NOT NULL , 
     name_person VARCHAR (30) , 
     lastname_person VARCHAR (30) , 
     pathimg_person VARCHAR (75) , 
     codeverifitacion_person VARCHAR (15) , 
     type_person CHAR (1), 
     email_person VARCHAR (75) , 
     password_person VARCHAR (64) , 
     datereg_person TIMESTAMP(3) , 
     provider_person VARCHAR (25) , 
     id_city VARCHAR (20) 
    );

ALTER TABLE person ADD CONSTRAINT person_PK PRIMARY KEY (id_person)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE question_group 
    (
     id_questiongroup BIGSERIAL NOT NULL , 
     group_name VARCHAR (30) , 
     group_description TEXT , 
     date_registration_group TIMESTAMP(3) , 
     state_group CHAR (1) , 
     person_id_person BIGINT NOT NULL 
    );

ALTER TABLE question_group ADD CONSTRAINT question_group_PK PRIMARY KEY (id_questiongroup)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE questions 
    (
     id_questions BIGSERIAL NOT NULL , 
     name_question TEXT , 
     description_question TEXT , 
     state_question CHAR , 
     date_registration_question TIMESTAMP(3) , 
     required_question CHAR (1) , 
     file_question JSON , 
     question_group_id_questiongroup BIGINT NOT NULL , 
     evaluation_categories_id_categorie BIGINT NOT NULL 
    );

ALTER TABLE questions ADD CONSTRAINT questions_PK PRIMARY KEY (id_questions)
     ;
 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE responses 
    (
     id_response BIGSERIAL NOT NULL , 
     state_response CHAR (1) , 
     date_register_response TIMESTAMP(3) , 
     answer_options JSON , 
     questions_id_questions BIGINT NOT NULL 
    );

ALTER TABLE responses ADD CONSTRAINT responses_PK PRIMARY KEY (id_response)
     ;
 

ALTER TABLE classroom 
    ADD CONSTRAINT classroom_period_FK FOREIGN KEY 
    ( 
     period_id_period
    ) 
    REFERENCES period 
    ( 
     id_period 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE classroom 
    ADD CONSTRAINT classroom_person_FK FOREIGN KEY 
    ( 
     person_id_person
    ) 
    REFERENCES person 
    ( 
     id_person 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE evaluation 
    ADD CONSTRAINT evaluation_classroom_FK FOREIGN KEY 
    ( 
     classroom_id_classroom
    ) 
    REFERENCES classroom 
    ( 
     id_classroom 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE evaluation_questions 
    ADD CONSTRAINT evaluation_questions_evaluation_FK FOREIGN KEY 
    ( 
     evaluation_id_evaluation
    ) 
    REFERENCES evaluation 
    ( 
     id_evaluation 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE evaluation_questions 
    ADD CONSTRAINT evaluation_questions_questions_FK FOREIGN KEY 
    ( 
     questions_id_questions
    ) 
    REFERENCES questions 
    ( 
     id_questions 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE evaluation_reponse 
    ADD CONSTRAINT evaluation_reponse_evaluation_questions_FK FOREIGN KEY 
    ( 
     evaluation_questions_id_evaluationquestions
    ) 
    REFERENCES evaluation_questions 
    ( 
     id_evaluationquestions 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE evaluation_reponse 
    ADD CONSTRAINT evaluation_reponse_person_FK FOREIGN KEY 
    ( 
     person_id_person
    ) 
    REFERENCES person 
    ( 
     id_person 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE period 
    ADD CONSTRAINT period_person_FK FOREIGN KEY 
    ( 
     person_id_person
    ) 
    REFERENCES person 
    ( 
     id_person 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE question_group 
    ADD CONSTRAINT question_group_person_FK FOREIGN KEY 
    ( 
     person_id_person
    ) 
    REFERENCES person 
    ( 
     id_person 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE questions 
    ADD CONSTRAINT questions_evaluation_categories_FK FOREIGN KEY 
    ( 
     evaluation_categories_id_categorie
    ) 
    REFERENCES evaluation_categories 
    ( 
     id_categorie 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE questions 
    ADD CONSTRAINT questions_question_group_FK FOREIGN KEY 
    ( 
     question_group_id_questiongroup
    ) 
    REFERENCES question_group 
    ( 
     id_questiongroup 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 

ALTER TABLE responses 
    ADD CONSTRAINT responses_questions_FK FOREIGN KEY 
    ( 
     questions_id_questions
    ) 
    REFERENCES questions 
    ( 
     id_questions 
    ) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION; 
 



-- SQLINES DEMO *** n de Oracle SQL Developer Data Modeler: 
-- 
-- SQLINES DEMO ***                        10
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                        22
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO *** DY                      0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO *** IEW                     0
-- SQLINES DEMO *** EGMENT                  0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0
-- SQLINES DEMO *** FUNCTION                0
-- SQLINES DEMO *** SCHEME                  0
-- 
-- SQLINES DEMO ***                         0
-- 
-- SQLINES DEMO ***                         0
-- SQLINES DEMO ***                         0