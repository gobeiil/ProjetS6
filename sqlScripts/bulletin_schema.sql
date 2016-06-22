------------------------------------------------------------
--        Script Postgre 
------------------------------------------------------------
CREATE SCHEMA bulletin
  AUTHORIZATION opus;

COMMENT ON SCHEMA bulletin
  IS 'Fonctions et tables personnalise pour le module bulletin';


------------------------------------------------------------
-- Table: student
------------------------------------------------------------
CREATE TABLE bulletin.student(
	user_id    SERIAL NOT NULL ,
	student_id VARCHAR (25)  ,
	CONSTRAINT prk_constraint_student PRIMARY KEY (user_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: educational_goal_mix
------------------------------------------------------------
CREATE TABLE bulletin.educational_goal_mix(
	eg_id             SERIAL NOT NULL ,
	label             VARCHAR (25)  ,
	description       VARCHAR (25)  ,
	short_description VARCHAR (25)  ,
	timespan_id       INT   ,
	CONSTRAINT prk_constraint_educational_goal_mix PRIMARY KEY (eg_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: timespan
------------------------------------------------------------
CREATE TABLE bulletin.timespan(
	timespan_id SERIAL NOT NULL ,
	start_date  DATE   ,
	end_date    DATE   ,
	label       VARCHAR (25)  ,
	CONSTRAINT prk_constraint_timespan PRIMARY KEY (timespan_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: evaluation_mix
------------------------------------------------------------
CREATE TABLE bulletin.evaluation_mix(
	eval_id            SERIAL NOT NULL ,
	visible            BOOL   ,
	label              VARCHAR (25)  ,
	type               INT   ,
	occurrence         DATE   ,
	evaluation_type_id INT   ,
	CONSTRAINT prk_constraint_evaluation_mix PRIMARY KEY (eval_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: criterion
------------------------------------------------------------
CREATE TABLE bulletin.criterion(
	criterion_id      SERIAL NOT NULL ,
	label             VARCHAR (25)  ,
	weighting         INT   ,
	NT_maxpoints      INT   ,
	rubric_id         INT   ,
	value             DOUBLE PRECISION   ,
	NT_comment        VARCHAR (25)  ,
	corrected_copy_id INT   ,
	CONSTRAINT prk_constraint_criterion PRIMARY KEY (criterion_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: rubric
------------------------------------------------------------
CREATE TABLE bulletin.rubric(
	rubric_id SERIAL NOT NULL ,
	label     VARCHAR (25)  ,
	eval_id   INT   ,
	CONSTRAINT prk_constraint_rubric PRIMARY KEY (rubric_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Notif_eval
------------------------------------------------------------
CREATE TABLE bulletin.Notif_eval(
	id_notif    SERIAL NOT NULL ,
	id_etudiant VARCHAR (25)  ,
	id_eval     INT   ,
	type_notif  CHAR (25)   ,
	CONSTRAINT prk_constraint_Notif_eval PRIMARY KEY (id_notif)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Tuiles
------------------------------------------------------------
CREATE TABLE bulletin.Tuiles(
	id_tuile SERIAL NOT NULL ,
	couleur  INT   ,
	position INT   ,
	CONSTRAINT prk_constraint_Tuiles PRIMARY KEY (id_tuile)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: evaluation_type
------------------------------------------------------------
CREATE TABLE bulletin.evaluation_type(
	evaluation_type_id SERIAL NOT NULL ,
	label              VARCHAR (25)  ,
	CONSTRAINT prk_constraint_evaluation_type PRIMARY KEY (evaluation_type_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: corrected_copy
------------------------------------------------------------
CREATE TABLE bulletin.corrected_copy(
	corrected_copy_id SERIAL NOT NULL ,
	eval_id           INT   ,
	CONSTRAINT prk_constraint_corrected_copy PRIMARY KEY (corrected_copy_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: educational_goal_evaluation
------------------------------------------------------------
CREATE TABLE bulletin.educational_goal_evaluation(
	eval_id INT  NOT NULL ,
	eg_id   INT  NOT NULL ,
	CONSTRAINT prk_constraint_educational_goal_evaluation PRIMARY KEY (eval_id,eg_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: student_corrected_copy
------------------------------------------------------------
CREATE TABLE bulletin.student_corrected_copy(
	user_id           INT  NOT NULL ,
	corrected_copy_id INT  NOT NULL ,
	CONSTRAINT prk_constraint_student_corrected_copy PRIMARY KEY (user_id,corrected_copy_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: student_score
------------------------------------------------------------
CREATE TABLE bulletin.student_score(
	user_id INT  NOT NULL ,
	eg_id   INT  NOT NULL ,
	CONSTRAINT prk_constraint_student_score PRIMARY KEY (user_id,eg_id)
)WITHOUT OIDS;



ALTER TABLE bulletin.educational_goal_mix ADD CONSTRAINT FK_educational_goal_mix_timespan_id FOREIGN KEY (timespan_id) REFERENCES bulletin.timespan(timespan_id);
ALTER TABLE bulletin.evaluation_mix ADD CONSTRAINT FK_evaluation_mix_evaluation_type_id FOREIGN KEY (evaluation_type_id) REFERENCES bulletin.evaluation_type(evaluation_type_id);
ALTER TABLE bulletin.criterion ADD CONSTRAINT FK_criterion_rubric_id FOREIGN KEY (rubric_id) REFERENCES bulletin.rubric(rubric_id);
ALTER TABLE bulletin.criterion ADD CONSTRAINT FK_criterion_corrected_copy_id FOREIGN KEY (corrected_copy_id) REFERENCES bulletin.corrected_copy(corrected_copy_id);
ALTER TABLE bulletin.rubric ADD CONSTRAINT FK_rubric_eval_id FOREIGN KEY (eval_id) REFERENCES bulletin.evaluation_mix(eval_id);
ALTER TABLE bulletin.corrected_copy ADD CONSTRAINT FK_corrected_copy_eval_id FOREIGN KEY (eval_id) REFERENCES bulletin.evaluation_mix(eval_id);
ALTER TABLE bulletin.educational_goal_evaluation ADD CONSTRAINT FK_educational_goal_evaluation_eval_id FOREIGN KEY (eval_id) REFERENCES bulletin.evaluation_mix(eval_id);
ALTER TABLE bulletin.educational_goal_evaluation ADD CONSTRAINT FK_educational_goal_evaluation_eg_id FOREIGN KEY (eg_id) REFERENCES bulletin.educational_goal_mix(eg_id);
ALTER TABLE bulletin.student_corrected_copy ADD CONSTRAINT FK_student_corrected_copy_user_id FOREIGN KEY (user_id) REFERENCES bulletin.student(user_id);
ALTER TABLE bulletin.student_corrected_copy ADD CONSTRAINT FK_student_corrected_copy_corrected_copy_id FOREIGN KEY (corrected_copy_id) REFERENCES bulletin.corrected_copy(corrected_copy_id);
ALTER TABLE bulletin.student_score ADD CONSTRAINT FK_student_score_user_id FOREIGN KEY (user_id) REFERENCES bulletin.student(user_id);
ALTER TABLE bulletin.student_score ADD CONSTRAINT FK_student_score_eg_id FOREIGN KEY (eg_id) REFERENCES bulletin.educational_goal_mix(eg_id);





--------------------------------------INSERTS--------------------------------------
-----------------------------------------------------------------------------------


-----------TIMESPAN (sessions)-------------
INSERT INTO bulletin.timespan (timespan_id,start_date,end_date,label) 
VALUES (1,'2016-01-01','2016-01-02','S1');

INSERT INTO bulletin.timespan (timespan_id,start_date,end_date,label) 
VALUES (2,'2016-02-01','2016-02-02','S2');

INSERT INTO bulletin.timespan (timespan_id,start_date,end_date,label) 
VALUES (3,'2016-03-01','2016-03-02','S3');

-----------EDUCATIONAL GOAL (AP ou APP)-------------
INSERT INTO bulletin.educational_goal_mix (eg_id,label,description,short_description,timespan_id) 
VALUES (1,'GIF500','Systemes de multiplexage','Reseau 2',1);

INSERT INTO bulletin.educational_goal_mix (eg_id,label,description,short_description,timespan_id) 
VALUES (2,'GIF501','Systemes de commutation','Reseau 3',1);

INSERT INTO bulletin.educational_goal_mix (eg_id,label,description,short_description,timespan_id) 
VALUES (3,'GIF502','Comprendre OPUS 101','OP',1);


-----------STUDENT--------------
INSERT INTO bulletin.student (user_id,student_id) 
VALUES (1,'elsf2301');

INSERT INTO bulletin.student (user_id,student_id) 
VALUES (2,'fria1501');

-----------STUDENT SCORE (Assign eg_id to student_id--------------
INSERT INTO bulletin.student_score (user_id,eg_id) 
VALUES (1,1);
INSERT INTO bulletin.student_score (user_id,eg_id) 
VALUES (1,2);
INSERT INTO bulletin.student_score (user_id,eg_id) 
VALUES (1,3);
INSERT INTO bulletin.student_score (user_id,eg_id) 
VALUES (2,1);
INSERT INTO bulletin.student_score (user_id,eg_id) 
VALUES (2,2);


--------------------------------------VIEW-----------------------------------------
-----------------------------------------------------------------------------------

CREATE VIEW bulletin.v_students_eg (student_id,label,description)
AS
SELECT 
  bulletin.student.student_id, 
  bulletin.educational_goal_mix.label, 
  bulletin.educational_goal_mix.description
FROM 
  bulletin.student, 
  bulletin.educational_goal_mix, 
  bulletin.student_score
WHERE 
  bulletin.student.user_id = bulletin.student_score.user_id AND
  bulletin.student_score.eg_id = bulletin.educational_goal_mix.eg_id;


--------------------------------------TYPE RETOUR----------------------------------
-----------------------------------------------------------------------------------

-- type de var: retour de function f_getEgByStudent, store educational_goal(Activite pedag.) pour 1 student
CREATE TYPE bulletin.t_eg_student_results AS (label VARCHAR(25),description VARCHAR(25));

--------------------------------------FUNCTIONS------------------------------------
-----------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION bulletin.f_getEgByStudent(
in cip bulletin.student.student_id%TYPE)
RETURNS SETOF bulletin.t_eg_student_results AS
$BODY$
DECLARE
query text;

BEGIN
	query := 'SELECT label, description
		  FROM bulletin.v_students_eg WHERE student_id = ''' || cip || '''';
        RETURN query EXECUTE query;
END;
$BODY$
LANGUAGE plpgsql
