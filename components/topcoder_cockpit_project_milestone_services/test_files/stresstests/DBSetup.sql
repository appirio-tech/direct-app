-- -----------------------------------------------------
-- Table project_milestone
-- -----------------------------------------------------
create table project_milestone (
    project_milestone_id Serial not null,
    name VARCHAR(80) not null,
    description VARCHAR(250) not null,
    due_date DATETIME YEAR TO FRACTION not null,                   
    send_notifications boolean not null,
    completed boolean not null,
    project_id INT not null,
    primary key (project_milestone_id))
extent size 1000 next size 1000
lock mode row;   

-- -----------------------------------------------------
-- Table project_milestone_owner
-- -----------------------------------------------------
create table project_milestone_owner (
    project_milestone_owner_id Serial NOT NULL,
    user_id INT NOT NULL,
    user_handle VARCHAR(45) NOT NULL,
    project_milestone_id INT NOT NULL,
    PRIMARY KEY (project_milestone_owner_id),
    FOREIGN KEY (project_milestone_id) REFERENCES project_milestone (project_milestone_id))
extent size 1000 next size 1000
lock mode row; 