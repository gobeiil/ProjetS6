------------------------------------------------------------
--        Script Postgre 
------------------------------------------------------------
CREATE SCHEMA bulletin
  AUTHORIZATION postgres;

COMMENT ON SCHEMA bulletin
  IS 'Fonctions et tables personnalise pour le module bulletin';
------------------------------------------------------------
--        Script Postgre 
------------------------------------------------------------



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
	competence_id     INT   ,
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
-- Table: competence
------------------------------------------------------------
CREATE TABLE bulletin.competence(
	competence_id SERIAL NOT NULL ,
	label         VARCHAR (25)  ,
	eg_id         INT   ,
	CONSTRAINT prk_constraint_competence PRIMARY KEY (competence_id)
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
ALTER TABLE bulletin.criterion ADD CONSTRAINT FK_criterion_competence_id FOREIGN KEY (competence_id) REFERENCES bulletin.competence(competence_id);
ALTER TABLE bulletin.rubric ADD CONSTRAINT FK_rubric_eval_id FOREIGN KEY (eval_id) REFERENCES bulletin.evaluation_mix(eval_id);
ALTER TABLE bulletin.corrected_copy ADD CONSTRAINT FK_corrected_copy_eval_id FOREIGN KEY (eval_id) REFERENCES bulletin.evaluation_mix(eval_id);
ALTER TABLE bulletin.competence ADD CONSTRAINT FK_competence_eg_id FOREIGN KEY (eg_id) REFERENCES bulletin.educational_goal_mix(eg_id);
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

----------EVALUATION_TYPE--------------
INSERT INTO bulletin.evaluation_type (evaluation_type_id,label) 
VALUES (1,'examen');
INSERT INTO bulletin.evaluation_type (evaluation_type_id,label) 
VALUES (2,'validation');
INSERT INTO bulletin.evaluation_type (evaluation_type_id,label) 
VALUES (3,'rapport');
INSERT INTO bulletin.evaluation_type (evaluation_type_id,label) 
VALUES (4,'laboratoire');

----------EVALUATION_MIX--------------
INSERT INTO bulletin.evaluation_mix (eval_id,visible,label,type,occurrence,evaluation_type_id) 
VALUES (1,TRUE,'examen GIF501',1,now(),1);
INSERT INTO bulletin.evaluation_mix (eval_id,visible,label,type,occurrence,evaluation_type_id) 
VALUES (2,TRUE,'rapport GIF501',1,now(),3);

----------EDUCATIONAL_GOAL_EVALUATION--
-- inserts a faire automatiquement avec Triggers 
INSERT INTO bulletin.educational_goal_evaluation (eval_id,eg_id) 
VALUES (1,2);
INSERT INTO bulletin.educational_goal_evaluation (eval_id,eg_id) 
VALUES (2,2);

-----------RUBRIC---------------------
INSERT INTO bulletin.rubric (rubric_id,label,eval_id) 
VALUES (1,'Question 1', 1);
INSERT INTO bulletin.rubric (rubric_id,label,eval_id) 
VALUES (2,'Question 2', 1);
INSERT INTO bulletin.rubric (rubric_id,label,eval_id) 
VALUES (3,'Resolution problematique', 2);

-----------CORRECTED_COPY---------------------
INSERT INTO bulletin.corrected_copy (corrected_copy_id,eval_id) 
VALUES (1,1);
INSERT INTO bulletin.corrected_copy (corrected_copy_id,eval_id) 
VALUES (2,2);

-----------STUDENT_CORRECTED_COPY-------------
INSERT INTO bulletin.student_corrected_copy (user_id,corrected_copy_id) 
VALUES (1,1);
INSERT INTO bulletin.student_corrected_copy (user_id,corrected_copy_id)  
VALUES (1,2);

----------COMPETENCE--------------
INSERT INTO bulletin.competence (competence_id,label,eg_id) 
VALUES (1,'C1 GIF 501',2);
INSERT INTO bulletin.competence (competence_id,label,eg_id) 
VALUES (2,'C2 GIF 501',2);
INSERT INTO bulletin.competence (competence_id,label,eg_id) 
VALUES (3,'C3 GIF 501',2);

-----------CRITERION---------------------
INSERT INTO bulletin.criterion (criterion_id,label,weighting,NT_maxpoints,rubric_id,value,NT_comment,corrected_copy_id,competence_id) 
VALUES (1,'Resultat A',10,100,1,1,'Pointage resultat A',1,1);
INSERT INTO bulletin.criterion (criterion_id,label,weighting,NT_maxpoints,rubric_id,value,NT_comment,corrected_copy_id,competence_id) 
VALUES (2,'Demarche A',10,100,1,1,'Pointage demarche A',1,2);
INSERT INTO bulletin.criterion (criterion_id,label,weighting,NT_maxpoints,rubric_id,value,NT_comment,corrected_copy_id,competence_id) 
VALUES (3,'Resultat B',10,100,1,3,'Pointage du B',1,3);
INSERT INTO bulletin.criterion (criterion_id,label,weighting,NT_maxpoints,rubric_id,value,NT_comment,corrected_copy_id,competence_id) 
VALUES (4,'Resultat 1',10,100,3,5,'Resolution probleme',2,1);


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
  
--view montrant les competences d'un ap d'un etudiant
 CREATE VIEW bulletin.v_competence_students_eg (student_id, eg_id, label_eg, competence_id, label_comp)
 AS
 SELECT
  bulletin.student.student_id,
  bulletin.educational_goal_mix.eg_id,
  bulletin.educational_goal_mix.label,
  bulletin.competence.competence_id,
  bulletin.competence.label
 FROM
  bulletin.student, 
  bulletin.educational_goal_mix,
  bulletin.competence,
  bulletin.student_score
 WHERE
  bulletin.student.user_id = bulletin.student_score.user_id AND
  bulletin.student_score.eg_id = bulletin.educational_goal_mix.eg_id AND
  bulletin.educational_goal_mix.eg_id = bulletin.competence.eg_id;
 
-- view montrant les travaux (evaluation_mix) pour un ap d'un etudiant
CREATE OR REPLACE VIEW bulletin.v_travaux_students_eg AS 
 SELECT student.student_id,
	evaluation_mix.eval_id,
	evaluation_mix.label AS label_eval,
	educational_goal_mix.eg_id,
	educational_goal_mix.label AS label_eg,
	evaluation_type.label AS label_evaltype
	
 FROM bulletin.student,
    bulletin.student_score,
    bulletin.evaluation_mix,
    bulletin.educational_goal_mix,
    bulletin.educational_goal_evaluation,
    bulletin.evaluation_type
    
  WHERE student.user_id = student_score.user_id 
  AND student_score.eg_id = educational_goal_mix.eg_id
  AND educational_goal_evaluation.eg_id = educational_goal_mix.eg_id
  AND evaluation_mix.eval_id = educational_goal_evaluation.eval_id
  AND evaluation_type.evaluation_type_id = evaluation_mix.evaluation_type_id;
ALTER TABLE bulletin.v_travaux_students_eg
  OWNER TO superopus;
 
 --view montrant les notes pour les travaux et competences des eg
 CREATE OR REPLACE VIEW bulletin.v_notes_evaluations_competences_eg AS 

SELECT 
  student.student_id, 
  competence.competence_id, 
  competence.label AS label_competence, 
  criterion.label AS label_criterion, 
  criterion.value AS note, 
  criterion.weighting AS ponderation,
  evaluation_mix.eval_id, 
  evaluation_mix.label AS label_eval,
  educational_goal_mix.label AS eg_label
FROM 
  bulletin.competence, 
  bulletin.criterion, 
  bulletin.corrected_copy, 
  bulletin.student_corrected_copy, 
  bulletin.student, 
  bulletin.rubric, 
  bulletin.evaluation_mix,
  bulletin.educational_goal_evaluation,
  bulletin.educational_goal_mix
WHERE 
  criterion.competence_id = competence.competence_id AND
  criterion.rubric_id = rubric.rubric_id AND
  corrected_copy.corrected_copy_id = student_corrected_copy.corrected_copy_id AND
  student_corrected_copy.user_id = student.user_id AND
  student_corrected_copy.corrected_copy_id = criterion.corrected_copy_id AND
  rubric.eval_id = evaluation_mix.eval_id AND
  educational_goal_evaluation.eval_id = evaluation_mix.eval_id AND
  educational_goal_mix.eg_id = educational_goal_evaluation.eg_id;
  
ALTER TABLE bulletin.v_notes_evaluations_competences_eg
  OWNER TO superopus;

--------------------------------------TYPE RETOUR----------------------------------
-----------------------------------------------------------------------------------

-- type de var: retour de function f_getEgByStudent, store educational_goal(Activite pedag.) pour 1 student
CREATE TYPE t_eg_student_results AS (label VARCHAR(25),description VARCHAR(25));
CREATE TYPE t_competence_eg_student AS (label_comp VARCHAR(25), eg VARCHAR(25));
CREATE TYPE t_tr_eg_student_results AS (label VARCHAR(25));

--------------------------------------FUNCTIONS------------------------------------
-----------------------------------------------------------------------------------

-- get educational_goal by student
CREATE OR REPLACE FUNCTION bulletin.f_getEgByStudent(
in cip bulletin.student.student_id%TYPE)
RETURNS SETOF t_eg_student_results AS
$BODY$
DECLARE
query text;

BEGIN
	query := 'SELECT label, description
		  FROM bulletin.v_students_eg WHERE student_id = ''' || cip || '''';
        RETURN query EXECUTE query;
END;
$BODY$
LANGUAGE plpgsql;

-- get competences by educational_goal by student
CREATE OR REPLACE FUNCTION bulletin.f_getCompByEgByStudent(
in cip bulletin.student.student_id%TYPE)
RETURNS SETOF t_competence_eg_student AS
$BODY$
DECLARE
query text;

BEGIN
	query := 'SELECT label_eg, label_comp
		  FROM bulletin.v_competence_students_eg WHERE student_id = ''' || cip || '''';
        RETURN query EXECUTE query;
END;
$BODY$
LANGUAGE plpgsql;

-- get travaux by educational_goal by student
CREATE OR REPLACE FUNCTION bulletin.f_gettravauxbyegbystudent(cip character varying,eg character varying)
  RETURNS SETOF t_tr_eg_student_results AS
$BODY$
DECLARE
query text;

BEGIN
	query := 'SELECT label_eval
		  FROM bulletin.v_travaux_students_eg  WHERE student_id = ''' || cip || ''' AND label_eg = ''' || eg || '''';
        RETURN query EXECUTE query;
END;
$BODY$
  LANGUAGE plpgsql




