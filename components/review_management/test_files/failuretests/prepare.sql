INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_item_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_comment_id_seq', 1, 20, 0);
INSERT INTO id_sequences(name, next_block_start, block_size, exhausted) VALUES('review_item_comment_id_seq', 1, 20, 0); 

insert into scorecard_type_lu(scorecard_type_id) values (1);

insert into scorecard(scorecard_id, scorecard_type_id) values (1, 1);

insert into scorecard_question(scorecard_question_id) values (1);

insert into resource(resource_id, project_id) values(1, null);

insert into submission(submission_id) values(1);

insert into comment_type_lu (comment_type_id, name, description, create_user, create_date, modify_user, modify_date)
    values (1, 'type1', 'the comment type 1', 'admin', CURRENT, 'admin', CURRENT);

insert into review(review_id, resource_id, submission_id, scorecard_id, committed, score, create_user, create_date, modify_user, modify_date)
    values (1, 1, 1, 1, 0, 80, 'admin', CURRENT, 'admin', CURRENT);
 