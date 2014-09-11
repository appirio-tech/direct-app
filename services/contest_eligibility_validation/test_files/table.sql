create table 'informix'.user_group_xref (
    user_group_id DECIMAL(12,0) not null,
    login_id DECIMAL(12,0),
    group_id DECIMAL(12,0),
    create_user_id DECIMAL(12,0),
    security_status_id DECIMAL(3,0),
    create_date DATETIME YEAR TO FRACTION default CURRENT YEAR TO FRACTION
);

create table 'informix'.contest_eligibility (
contest_eligibility_id DECIMAL(10, 0) not null,
contest_id DECIMAL(10,0) not null,
is_studio SMALLINT NOT NULL,
PRIMARY KEY (contest_eligibility_id) constraint contest_eligibility_pk
)

create table 'informix'.group_contest_eligibility (
contest_eligibility_id DECIMAL(10, 0) not null,
group_id DECIMAL(10,0) not null,
PRIMARY KEY (contest_eligibility_id) constraint group_contest_eligibility_pk,
FOREIGN KEY (contest_eligibility_id) REFERENCES contest_eligibility(contest_eligibility_id) constraint contest_eligibility_fk
)


CREATE SEQUENCE CONTEST_ELIGIBILITY_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1;