INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_item_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_comment_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_item_comment_id_seq', 1, 20, 0); 

insert into scorecard_type_lu(scorecard_type_id) values (11);
insert into scorecard_type_lu(scorecard_type_id) values (12);
insert into scorecard_type_lu(scorecard_type_id) values (13);
insert into scorecard_type_lu(scorecard_type_id) values (14);
insert into scorecard_type_lu(scorecard_type_id) values (15);
insert into scorecard_type_lu(scorecard_type_id) values (16);

insert into scorecard(scorecard_id, scorecard_type_id) values (11, 11);
insert into scorecard(scorecard_id, scorecard_type_id) values (12, 12);
insert into scorecard(scorecard_id, scorecard_type_id) values (13, 13);
insert into scorecard(scorecard_id, scorecard_type_id) values (14, 14);
insert into scorecard(scorecard_id, scorecard_type_id) values (15, 15);
insert into scorecard(scorecard_id, scorecard_type_id) values (16, 16);

insert into scorecard_question(scorecard_question_id) values (11);
insert into scorecard_question(scorecard_question_id) values (12);
insert into scorecard_question(scorecard_question_id) values (13);
insert into scorecard_question(scorecard_question_id) values (14);
insert into scorecard_question(scorecard_question_id) values (15);

insert into resource(resource_id, project_id) values(11, null);
insert into resource(resource_id, project_id) values(12, null);
insert into resource(resource_id, project_id) values(13, null);
insert into resource(resource_id, project_id) values(14, null);
insert into resource(resource_id, project_id) values(15, null);
insert into resource(resource_id, project_id) values(16, null);
insert into resource(resource_id, project_id) values(17, null);
insert into resource(resource_id, project_id) values(18, null);
insert into resource(resource_id, project_id) values(19, null);

insert into submission(submission_id) values(11);
insert into submission(submission_id) values(12);
insert into submission(submission_id) values(13);
insert into submission(submission_id) values(14);
insert into submission(submission_id) values(15);

insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (11, 'stress_type11', 'the comment type 11', 'stress_review', DATE('1998-1-10'), 'tcdev11', DATE('1998-1-10'));
 insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (12, 'stress_type12', 'the comment type 12', 'stress_review', DATE('1998-1-10'), 'tcdev1', DATE('1998-1-10'));
 insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (13, 'stress_type13', 'the comment type 13', 'stress_review', DATE('1998-1-10'), 'tcdev1', DATE('1998-1-10'));
 insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (14, 'stress_type14', 'the comment type 14', 'stress_review', DATE('1998-1-10'), 'tcdev1', DATE('1998-1-10'));
 insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (15, 'stress_type15', 'the comment type 15', 'stress_review', DATE('1998-1-10'), 'tcdev1', DATE('1998-1-10'));
 insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (16, 'stress_type16', 'the comment type 16', 'stress_review', DATE('2006-10-10'), 'tcdev1', DATE('2006-10-10'));
 insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (17, 'stress_type17', 'the comment type 17', 'stress_review', DATE('2006-10-10'), 'tcdev1', DATE('2006-10-10'));
 insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (18, 'stress_type18', 'the comment type 18', 'stress_review', DATE('2006-10-10'), 'tcdev1', DATE('2006-10-10'));
 insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
 values (19, 'stress_type19', 'the comment type 19', 'stress_review', DATE('2006-10-10'), 'tcdev1', DATE('2006-10-10'));