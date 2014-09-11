-- ------------------------------------------------------------
-- Contains a list of phase types.
-- @id - global unique phase type id;
-- @template_if - ID of the template in which this type is defined;
-- @type_id - internal ID of the type in the template;
-- @name - name of the phase type.
-- ------------------------------------------------------------

CREATE TABLE phase_type (
  id INTEGER NOT NULL,
  template_id INTEGER NOT NULL,
  type_id INTEGER NOT NULL,
  name VARCHAR(30) NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(template_id)
    REFERENCES template(id)
)

