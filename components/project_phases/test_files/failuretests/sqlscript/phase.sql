-- ------------------------------------------------------------
-- Contains a list of phases.
-- @id - global ID of the phase;
-- @template_id - ID of template which contains this phase;
-- @type_id - global type ID of the phase;
-- @phase_id -  ID of the phase inside the template;
-- @time_length - length of the phase in milliseconds.
-- ------------------------------------------------------------
CREATE TABLE phase (
  id INTEGER NOT NULL,
  phase_type_id INTEGER NOT NULL,
  template_id INTEGER NOT NULL,
  phase_id INTEGER NOT NULL,
  time_length INT8 NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(phase_type_id)
    REFERENCES phase_type(id),
  FOREIGN KEY(template_id)
    REFERENCES template(id)
);