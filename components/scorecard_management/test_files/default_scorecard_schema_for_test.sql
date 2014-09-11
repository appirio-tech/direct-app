/*
Note: This schema used just for test but not the real schema for default_scorecard table.
*/

CREATE TABLE default_scorecard (
  default_scorecard_id          INTEGER                         NOT NULL,
  scorecard_type_id             INTEGER                         NOT NULL,
  scorecard_id                  INTEGER                         NOT NULL,
  project_category_id           INTEGER                         NOT NULL,
  PRIMARY KEY(default_scorecard_id),
  FOREIGN KEY(scorecard_type_id)
    REFERENCES scorecard_type_lu(scorecard_type_id),
  FOREIGN KEY(scorecard_id)
    REFERENCES scorecard(scorecard_id),
  FOREIGN KEY(project_category_id)
    REFERENCES project_category_lu(project_category_id)
);