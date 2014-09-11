-- ------------------------------------------------------------
-- Contains a list project phases dependencies
-- @id - global ID of the dependency;
-- @dependent_id - global ID of the dependent phase;
-- @dependency_id - global ID of the dependency phase;
-- @dependent_start - indicates whether dependent phase start time or end time depends on other phase;
-- @dependency_start - indicates dependent phase depends on start time or end time of dependency phase;
-- @lag_time - time in milliseconds between dependency phase start/end and dependent phase start/end.
-- ------------------------------------------------------------

CREATE TABLE dependency (
  id INTEGER NOT NULL,
  dependent_id INTEGER NOT NULL,
  dependency_id INTEGER NOT NULL,
  dependent_start BOOLEAN,
  dependency_start BOOLEAN,
  lag_time INT8,
  PRIMARY KEY(id),

  FOREIGN KEY(dependency_id)
    REFERENCES phase(id),
  FOREIGN KEY(dependent_id)
    REFERENCES phase(id)
)