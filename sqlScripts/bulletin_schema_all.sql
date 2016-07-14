------------------------------------------------------------
--        Script Postgre 
------------------------------------------------------------
CREATE SCHEMA bulletin
  AUTHORIZATION postgres;

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
	label         INT   ,
	CONSTRAINT prk_constraint_competence PRIMARY KEY (competence_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: ponderation
------------------------------------------------------------
CREATE TABLE bulletin.ponderation(
	pond_id       SERIAL NOT NULL ,
	maxpoints     INT   ,
	eval_id       INT   ,
	competence_id INT   ,
	CONSTRAINT prk_constraint_ponderation PRIMARY KEY (pond_id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: resultats
------------------------------------------------------------
CREATE TABLE bulletin.resultats(
	res_id            SERIAL NOT NULL ,
	value             INT   ,
	pond_id           INT   ,
	corrected_copy_id INT   ,
	CONSTRAINT prk_constraint_resultats PRIMARY KEY (res_id)
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


------------------------------------------------------------
-- Table: competence_eg
------------------------------------------------------------
CREATE TABLE bulletin.competence_eg(
	competence_id INT  NOT NULL ,
	eg_id         INT  NOT NULL ,
	CONSTRAINT prk_constraint_competence_eg PRIMARY KEY (competence_id,eg_id)
)WITHOUT OIDS;



ALTER TABLE bulletin.educational_goal_mix ADD CONSTRAINT FK_educational_goal_mix_timespan_id FOREIGN KEY (timespan_id) REFERENCES bulletin.timespan(timespan_id);
ALTER TABLE bulletin.evaluation_mix ADD CONSTRAINT FK_evaluation_mix_evaluation_type_id FOREIGN KEY (evaluation_type_id) REFERENCES bulletin.evaluation_type(evaluation_type_id);
ALTER TABLE bulletin.corrected_copy ADD CONSTRAINT FK_corrected_copy_eval_id FOREIGN KEY (eval_id) REFERENCES bulletin.evaluation_mix(eval_id);
ALTER TABLE bulletin.ponderation ADD CONSTRAINT FK_ponderation_eval_id FOREIGN KEY (eval_id) REFERENCES bulletin.evaluation_mix(eval_id);
ALTER TABLE bulletin.ponderation ADD CONSTRAINT FK_ponderation_competence_id FOREIGN KEY (competence_id) REFERENCES bulletin.competence(competence_id);
ALTER TABLE bulletin.resultats ADD CONSTRAINT FK_resultats_pond_id FOREIGN KEY (pond_id) REFERENCES bulletin.ponderation(pond_id);
ALTER TABLE bulletin.resultats ADD CONSTRAINT FK_resultats_corrected_copy_id FOREIGN KEY (corrected_copy_id) REFERENCES bulletin.corrected_copy(corrected_copy_id);
ALTER TABLE bulletin.educational_goal_evaluation ADD CONSTRAINT FK_educational_goal_evaluation_eval_id FOREIGN KEY (eval_id) REFERENCES bulletin.evaluation_mix(eval_id);
ALTER TABLE bulletin.educational_goal_evaluation ADD CONSTRAINT FK_educational_goal_evaluation_eg_id FOREIGN KEY (eg_id) REFERENCES bulletin.educational_goal_mix(eg_id);
ALTER TABLE bulletin.student_corrected_copy ADD CONSTRAINT FK_student_corrected_copy_user_id FOREIGN KEY (user_id) REFERENCES bulletin.student(user_id);
ALTER TABLE bulletin.student_corrected_copy ADD CONSTRAINT FK_student_corrected_copy_corrected_copy_id FOREIGN KEY (corrected_copy_id) REFERENCES bulletin.corrected_copy(corrected_copy_id);
ALTER TABLE bulletin.student_score ADD CONSTRAINT FK_student_score_user_id FOREIGN KEY (user_id) REFERENCES bulletin.student(user_id);
ALTER TABLE bulletin.student_score ADD CONSTRAINT FK_student_score_eg_id FOREIGN KEY (eg_id) REFERENCES bulletin.educational_goal_mix(eg_id);
ALTER TABLE bulletin.competence_eg ADD CONSTRAINT FK_competence_eg_competence_id FOREIGN KEY (competence_id) REFERENCES bulletin.competence(competence_id);
ALTER TABLE bulletin.competence_eg ADD CONSTRAINT FK_competence_eg_eg_id FOREIGN KEY (eg_id) REFERENCES bulletin.educational_goal_mix(eg_id);


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
INSERT INTO bulletin.evaluation_mix (eval_id,visible,label,type,occurrence,evaluation_type_id) 
VALUES (3,TRUE,'examen final GIF500',1,now(),1);

----------EDUCATIONAL_GOAL_EVALUATION--
-- inserts a faire automatiquement avec Triggers 
INSERT INTO bulletin.educational_goal_evaluation (eval_id,eg_id) 
VALUES (1,2);
INSERT INTO bulletin.educational_goal_evaluation (eval_id,eg_id) 
VALUES (2,2);
INSERT INTO bulletin.educational_goal_evaluation (eval_id,eg_id) 
VALUES (3,1);
-----------CORRECTED_COPY---------------------
INSERT INTO bulletin.corrected_copy (corrected_copy_id,eval_id) 
VALUES (1,1);
INSERT INTO bulletin.corrected_copy (corrected_copy_id,eval_id) 
VALUES (2,2);
INSERT INTO bulletin.corrected_copy (corrected_copy_id,eval_id) 
VALUES (3,3);
INSERT INTO bulletin.corrected_copy (corrected_copy_id,eval_id) 
VALUES (4,1);
INSERT INTO bulletin.corrected_copy (corrected_copy_id,eval_id) 
VALUES (5,3);
-----------STUDENT_CORRECTED_COPY-------------
INSERT INTO bulletin.student_corrected_copy (user_id,corrected_copy_id) 
VALUES (1,1);
INSERT INTO bulletin.student_corrected_copy (user_id,corrected_copy_id)  
VALUES (1,2);
INSERT INTO bulletin.student_corrected_copy (user_id,corrected_copy_id)  
VALUES (1,3);
INSERT INTO bulletin.student_corrected_copy (user_id,corrected_copy_id)  
VALUES (2,4);
INSERT INTO bulletin.student_corrected_copy (user_id,corrected_copy_id)  
VALUES (2,5);


----------COMPETENCE--------------
INSERT INTO bulletin.competence (competence_id,label) 
VALUES (1,1);
INSERT INTO bulletin.competence (competence_id,label)
VALUES (2,2);
INSERT INTO bulletin.competence (competence_id,label)
VALUES (3,3);


----------competence_eg(Relation competence-eg)--------------
INSERT INTO bulletin.competence_eg (competence_id,eg_id)
VALUES (1,1);
INSERT INTO bulletin.competence_eg (competence_id,eg_id)
VALUES (2,1);
INSERT INTO bulletin.competence_eg (competence_id,eg_id)
VALUES (3,1);
INSERT INTO bulletin.competence_eg (competence_id,eg_id)
VALUES (1,2);
INSERT INTO bulletin.competence_eg (competence_id,eg_id)
VALUES (2,2);
INSERT INTO bulletin.competence_eg (competence_id,eg_id)
VALUES (1,3);
INSERT INTO bulletin.competence_eg (competence_id,eg_id)
VALUES (2,3);
INSERT INTO bulletin.competence_eg (competence_id,eg_id)
VALUES (3,3);

----------PONDERATION--------------
INSERT INTO bulletin.ponderation(pond_id,maxpoints,eval_id,competence_id) 
VALUES (1,50,1,1);
INSERT INTO bulletin.ponderation(pond_id,maxpoints,eval_id,competence_id) 
VALUES (2,60,1,2);
INSERT INTO bulletin.ponderation(pond_id,maxpoints,eval_id,competence_id) 
VALUES (3,35,1,3);
INSERT INTO bulletin.ponderation(pond_id,maxpoints,eval_id,competence_id) 
VALUES (4,50,3,1);
INSERT INTO bulletin.ponderation(pond_id,maxpoints,eval_id,competence_id) 
VALUES (5,50,3,2);


----------RESULTATS--------------
--Resultat de elsf2301
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (1,45,1,1);
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (2,33,2,1);
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (3,NULL,3,1);
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (4,0,4,3);
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (5,32,5,3);

--Represente les resultats de fria1501
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (6,33,1,4);
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (7,53,2,4);
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (8,30,3,4);
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (9,NULL,4,5);
INSERT INTO bulletin.resultats(res_id,value, pond_id,corrected_copy_id) 
VALUES (10,45,5,5);




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
  bulletin.student_score,
  bulletin.competence_eg
 WHERE
  bulletin.student.user_id = bulletin.student_score.user_id AND
  bulletin.student_score.eg_id = bulletin.educational_goal_mix.eg_id AND
  bulletin.competence_eg.eg_id = bulletin.educational_goal_mix.eg_id AND
  bulletin.competence_eg.competence_id = bulletin.competence.competence_id;
 
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
	--  CREATE OR REPLACE VIEW bulletin.v_notes_evaluations_competences_eg AS 
	-- 
	-- SELECT 
	--   student.student_id, 
	--   competence.competence_id, 
	--   competence.label AS label_competence, 
	--   criterion.label AS label_criterion, 
	--   criterion.value AS note, 
	--   criterion.weighting AS ponderation,
	--   evaluation_mix.eval_id, 
	--   evaluation_mix.label AS label_eval,
	--   educational_goal_mix.label AS eg_label
	-- FROM 
	--   bulletin.competence, 
	--   bulletin.criterion, 
	--   bulletin.corrected_copy, 
	--   bulletin.student_corrected_copy, 
	--   bulletin.student, 
	--   bulletin.rubric, 
	--   bulletin.evaluation_mix,
	--   bulletin.educational_goal_evaluation,
	--   bulletin.educational_goal_mix
	-- WHERE 
	--   criterion.competence_id = competence.competence_id AND
	--   criterion.rubric_id = rubric.rubric_id AND
	--   corrected_copy.corrected_copy_id = student_corrected_copy.corrected_copy_id AND
	--   student_corrected_copy.user_id = student.user_id AND
	--   student_corrected_copy.corrected_copy_id = criterion.corrected_copy_id AND
	--   rubric.eval_id = evaluation_mix.eval_id AND
	--   educational_goal_evaluation.eval_id = evaluation_mix.eval_id AND
	--   educational_goal_mix.eg_id = educational_goal_evaluation.eg_id;
	--   
	-- ALTER TABLE bulletin.v_notes_evaluations_competences_eg
	--   OWNER TO superopus;

--view montrant les resultat d'un etudiant par EG,evaluation et competence
CREATE OR REPLACE VIEW bulletin.v_resultats_eg_ev_comp AS
SELECT 
  timespan.label AS Session, 
  student.student_id, 
  educational_goal_mix.label AS AP, 
  evaluation_mix.label AS travail, 
  competence.label AS competence, 
  resultats.value AS note,
  ponderation.maxpoints
FROM 
  bulletin.resultats, 
  bulletin.student, 
  bulletin.student_corrected_copy, 
  bulletin.ponderation, 
  bulletin.evaluation_mix, 
  bulletin.educational_goal_mix, 
  bulletin.educational_goal_evaluation, 
  bulletin.corrected_copy, 
  bulletin.competence, 
  bulletin.timespan
WHERE 
  resultats.pond_id = ponderation.pond_id AND
  student.user_id = student_corrected_copy.user_id AND
  student_corrected_copy.corrected_copy_id = corrected_copy.corrected_copy_id AND
  ponderation.competence_id = competence.competence_id AND
  evaluation_mix.eval_id = corrected_copy.eval_id AND
  evaluation_mix.eval_id = educational_goal_evaluation.eval_id AND
  educational_goal_mix.eg_id = educational_goal_evaluation.eg_id AND
  educational_goal_mix.timespan_id = timespan.timespan_id AND
  corrected_copy.corrected_copy_id = resultats.corrected_copy_id;

  ALTER TABLE bulletin.v_resultats_eg_ev_comp
  OWNER TO superopus;




--------------------------------------TYPE RETOUR----------------------------------
-----------------------------------------------------------------------------------

-- type de var: retour de function f_getEgByStudent, store educational_goal(Activite pedag.) pour 1 student
--CREATE TYPE t_eg_student_results AS (label VARCHAR(25),description VARCHAR(25));
--CREATE TYPE t_competence_eg_student AS (label_comp VARCHAR(25), eg VARCHAR(25));
--CREATE TYPE t_tr_eg_student_results AS (label VARCHAR(25));

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
  LANGUAGE plpgsql;
-- get average by AP and competence and evaluation
CREATE FUNCTION bulletin.f_getavgforcompeg(
    eg character varying,
    ev character varying,
    comp integer)
  RETURNS integer AS
$BODY$
DECLARE average integer;

BEGIN
 SELECT AVG(note) into average FROM bulletin.v_resultats_eg_ev_comp 
 WHERE 
 v_resultats_eg_ev_comp.AP = eg AND
 v_resultats_eg_ev_comp.travail = ev AND
 v_resultats_eg_ev_comp.competence = comp;
 
 RETURN average;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
  
-- get ecart type by AP and competence and evaluation

CREATE FUNCTION bulletin.f_getecarttypeforcompeg(
    eg character varying,
    ev character varying,
    comp integer)
  RETURNS integer AS
$BODY$
DECLARE et integer;

BEGIN
 SELECT stddev(note) into et FROM bulletin.v_resultats_eg_ev_comp 
 WHERE 
 v_resultats_eg_ev_comp.ap = eg AND
 v_resultats_eg_ev_comp.travail = ev AND
 v_resultats_eg_ev_comp.competence = comp;

  IF et = NULL THEN
    et := 0;
 END IF;
 
 RETURN et;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
  
 -- view montrant les moyennes et ecart type des competences des travaux des ap
 
 CREATE OR REPLACE VIEW bulletin.v_avg_ecarttype_comp_ap AS 
 SELECT educational_goal_mix.label AS label_eg,
    evaluation_mix.label AS label_eval,
    competence.label AS label_comp,
    bulletin.f_getavgforcompeg(educational_goal_mix.label, evaluation_mix.label, competence.label) AS average,
    bulletin.f_getecarttypeforcompeg(educational_goal_mix.label, evaluation_mix.label, competence.label) AS ecarttype
   FROM bulletin.competence,
    bulletin.educational_goal_mix,
    bulletin.educational_goal_evaluation,
    bulletin.competence_eg,
    bulletin.evaluation_mix
  WHERE educational_goal_mix.eg_id = educational_goal_evaluation.eg_id AND 
  educational_goal_mix.eg_id = competence_eg.eg_id AND
  competence.competence_id = competence_eg.competence_id AND
  evaluation_mix.eval_id = educational_goal_evaluation.eval_id;

ALTER TABLE bulletin.v_avg_ecarttype_comp_ap
  OWNER TO superopus;
  
 --
