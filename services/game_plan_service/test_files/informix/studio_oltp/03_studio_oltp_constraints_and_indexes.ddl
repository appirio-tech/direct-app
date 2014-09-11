database studio_oltp;

alter table "informix".command add constraint (foreign key (command_group_id) 
    references "informix".command_group_lu  constraint "informix"
    .command_command_group_fk);
alter table "informix".contest add constraint (foreign key (contest_status_id) 
    references "informix".contest_status_lu  constraint "informix"
    .contest_conteststatuslu_fk);
alter table "informix".contest add constraint (foreign key (contest_channel_id) 
    references "informix".contest_channel_lu  constraint "informix"
    .contest_contestchannel_fk);
alter table "informix".contest add constraint (foreign key (contest_type_id) 
    references "informix".contest_type_lu  constraint "informix"
    .contest_contesttype_fk);
alter table "informix".submission_review add constraint (foreign 
    key (review_status_id) references "informix".review_status_lu 
     constraint "informix".submissionreview_reviewstatus_fk);
alter table "informix".contest_registration add constraint (foreign 
    key (contest_id) references "informix".contest  constraint 
    "informix".contestreg_contest_fk);
alter table "informix".document add constraint (foreign key (path_id) 
    references "informix".path  constraint "informix".document_path_fk);
    
alter table "informix".document add constraint (foreign key (document_type_id) 
    references "informix".document_type_lu  constraint "informix"
    .document_doctype_fk);
alter table "informix".document add constraint (foreign key (mime_type_id) 
    references "informix".mime_type_lu  constraint "informix".document_mimetype_fk);
    
alter table "informix".contest_document_xref add constraint (foreign 
    key (contest_id) references "informix".contest  constraint 
    "informix".contestdocumentxref_contest_fk);
alter table "informix".contest_document_xref add constraint (foreign 
    key (document_id) references "informix".document  constraint 
    "informix".contestdocumentxref_document_fk);
alter table "informix".prize add constraint (foreign key (prize_type_id) 
    references "informix".prize_type_lu  constraint "informix".prize_type_fk);
    
alter table "informix".contest_prize_xref add constraint (foreign 
    key (contest_id) references "informix".contest  constraint 
    "informix".contestprizexref_contest_fk);
alter table "informix".contest_prize_xref add constraint (foreign 
    key (prize_id) references "informix".prize  constraint "informix"
    .contestprizexref_prize_fk);
alter table "informix".contest_file_type_xref add constraint 
    (foreign key (contest_id) references "informix".contest  constraint 
    "informix".contestfiletypexref_contest_fk);
alter table "informix".contest_file_type_xref add constraint 
    (foreign key (file_type_id) references "informix".file_type_lu 
     constraint "informix".contestfiletypexref_file_type_fk);
alter table "informix".mime_type_lu add constraint (foreign key 
    (file_type_id) references "informix".file_type_lu  constraint 
    "informix".mimetype_filetype_fk);
alter table "informix".contest_result add constraint (foreign 
    key (contest_id) references "informix".contest  constraint 
    "informix".contestresult_contest_fk);
alter table "informix".contest_result add constraint (foreign 
    key (submission_id) references "informix".submission  constraint 
    "informix".contestresult_submission_fk);
alter table "informix".command_execution add constraint (foreign 
    key (command_id) references "informix".command  constraint 
    "informix".commandexecution_command_fk);
alter table "informix".contest_config add constraint (foreign 
    key (contest_id) references "informix".contest  constraint 
    "informix".contestconfg_contest_fk);
alter table "informix".contest_config add constraint (foreign 
    key (property_id) references "informix".contest_property_lu 
     constraint "informix".contestconfg_contestproperty_fk);
alter table "informix".submission_prize_xref add constraint (foreign 
    key (submission_id) references "informix".submission  constraint 
    "informix".submissionprizexref_submission_fk);
alter table "informix".submission_prize_xref add constraint (foreign 
    key (prize_id) references "informix".prize  constraint "informix"
    .submissionprizexref_prize_fk);
alter table "informix".contest_medium_xref add constraint (foreign 
    key (contest_id) references "informix".contest  constraint 
    "informix".contestmediumxref_contest_fk);
alter table "informix".contest_medium_xref add constraint (foreign 
    key (medium_id) references "informix".medium_lu  constraint 
    "informix".contestmediumxref_medium_fk);
alter table "informix".submission_payment add constraint (foreign 
    key (payment_status_id) references "informix".payment_status_lu 
    );
alter table "informix".submission_payment add constraint (foreign 
    key (sale_type_id) references "informix".sale_type_lu  constraint 
    "informix".submission_payment_sale_type_lu_fk);
alter table "informix".submission_image add constraint (foreign 
    key (submission_id) references "informix".submission  constraint 
    "informix".submissionimage_submission_fk);
alter table "informix".contest_type_file_type add constraint 
    (foreign key (file_type_id) references "informix".file_type_lu 
     constraint "informix".contest_type_file_type_file_type_lu_fk);
    
alter table "informix".contest_type_file_type add constraint 
    (foreign key (contest_type_id) references "informix".contest_type_lu 
     constraint "informix".contest_type_file_type_contest_type_lu_fk);
    
alter table "informix".contest_type_config add constraint (foreign 
    key (property_id) references "informix".contest_property_lu 
     constraint "informix".contest_type_config_contest_property_lu_fk);
    
alter table "informix".contest_type_config add constraint (foreign 
    key (contest_type_id) references "informix".contest_type_lu 
     constraint "informix".contest_type_config_contest_type_fk);
    
alter table "informix".contest_detailed_status_lu add constraint 
    (foreign key (contest_status_id) references "informix".contest_status_lu 
     constraint "informix".contestdetailedstatuslu_conteststatuslu_fk);
    
alter table "informix".contest_payment add constraint (foreign 
    key (payment_status_id) references "informix".payment_status_lu 
     constraint "informix".contest_payment_status_fk);
alter table "informix".contest_payment add constraint (foreign 
    key (contest_id) references "informix".contest  constraint 
    "informix".contest_payment_contest_fk);
alter table "informix".contest_payment add constraint (foreign 
    key (sale_type_id) references "informix".sale_type_lu  constraint 
    "informix".contest_payment_sale_type_lu_fk);
alter table "informix".contest_change_history add constraint 
    (foreign key (contest_id) references "informix".contest  constraint 
    "informix".contest_contestchangehistory_fk);
alter table "informix".submission add constraint (foreign key 
    (contest_id) references "informix".contest  constraint "informix"
    .submission_contest_fk);
alter table "informix".submission add constraint (foreign key 
    (path_id) references "informix".path  constraint "informix"
    .submission_path_fk);
alter table "informix".submission add constraint (foreign key 
    (submission_type_id) references "informix".submission_type_lu 
     constraint "informix".submission_submissiontypelu_fk);
alter table "informix".submission add constraint (foreign key 
    (mime_type_id) references "informix".mime_type_lu  constraint 
    "informix".submission_mimetype_fk);

ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_milestone_prize_id)
        REFERENCES contest_milestone_prize(contest_milestone_prize_id) CONSTRAINT contest_milestone_prize_fk );
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_multi_round_information_id)
        REFERENCES contest_multi_round_information(contest_multi_round_information_id) CONSTRAINT contest_multi_round_fk );
ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_specifications_id)
        REFERENCES contest_specifications(contest_specifications_id) CONSTRAINT contest_spec_fk );

ALTER TABLE contest
        ADD CONSTRAINT ( FOREIGN KEY(contest_general_info_ID)
        REFERENCES contest_general_info(contest_general_info_ID) CONSTRAINT contest_general_fk );

create unique index "informix".inputlu_inputcode_idx on "informix"
    .input_lu (input_code) using btree  in ol_topcoder ;
create index "informix".submitter_contest_idx on "informix".submission 
    (submitter_id,contest_id) using btree  in ol_topcoder ;

alter table 'informix'.rboard_user add constraint foreign key 
	 (status_id) 
     references 'informix'.rboard_status_lu 
     (status_id) 
     constraint rboard_user_status_fk;

alter table 'informix'.contest_milestone_prize add constraint foreign key 
	 (prize_type_id)  
     REFERENCES prize_type_lu
     (prize_type_id) 
     CONSTRAINT contest_milstone_prize_prize_type_lu_fk;

alter table 'informix'.resource add constraint foreign key 
	  (resource_role_id)	
      references 'informix'.resource_role_lu	
      (resource_role_id) 
      constraint res_res_role_fk;


alter table 'informix'.submission_milestone_prize_xref add constraint foreign key 
	   (submission_id) 
       REFERENCES 'informix'.submission
       (submission_id) 
       CONSTRAINT sub_milestone_prize_submission_fk;



alter table 'informix'.submission_milestone_prize_xref add constraint foreign key 
	  (contest_milestone_prize_id) 
      REFERENCES 'informix'.contest_milestone_prize
      (contest_milestone_prize_id) 
      CONSTRAINT sub_milestone_prize_contest_prize_fk;



alter table 'informix'.contest_resource_xref add constraint foreign key 
	  (contest_id)  
      REFERENCES 'informix'.contest(contest_id) 
      CONSTRAINT 
      contest_resource_xref_contest_fk;


alter table 'informix'.contest_resource_xref add constraint foreign key 
	  (resource_id) 
      REFERENCES 'informix'.resource(resource_id) 
      CONSTRAINT contest_resource_xref_resource_fk;
   
alter table 'informix'.studio_competition_pipeline_info add constraint foreign key 
	  (competition_id)	
      references 'informix'.contest
      (contest_id) 
      constraint pipeline_contest_fk;

alter table 'informix'.studio_competition_change_history add constraint foreign key 
	 (studio_competition_pipeline_info_id) 
     references 
     'informix'.studio_competition_pipeline_info 
     (id) 
     constraint pipeline_history_pipeline_fk;


 alter table 'informix'.studio_competition_pipeline_resources add constraint foreign key 
    (studio_competition_pipeline_info_id) 
    references 'informix'.studio_competition_pipeline_info 
    (id)
    constraint pipeline_resource_pipeline_fk;


 alter table 'informix'.studio_competition_pipeline_resources add constraint foreign key 
    (resource_id) 
    references 'informix'.resource 
    (resource_id) 
    constraint pipeline_resource_res_fk;

 alter table 'informix'.electronic_affirmation add constraint foreign key 
    (submission_id) 
    references 'informix'.submission 
    (submission_id) 
    constraint electronic_affirmation_fk;



