-- ------------------------------------------------------------
-- Contains pairs of catagory and default templates.
-- @id - ID or the pair;
-- @template_id - global ID of the template;
-- @category - category of the template.
-- ------------------------------------------------------------

CREATE TABLE default_template (
  id SERIAL NOT NULL,
  template_id INTEGER NOT NULL,
  category INTEGER,
  PRIMARY KEY(id),
  FOREIGN KEY(template_id)
    REFERENCES template(id)
)