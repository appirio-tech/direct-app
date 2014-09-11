-- ------------------------------------------------------------
-- Contains a list of templates.
-- @id - ID of the template;
-- @category - category of the template;
-- @name - name of the template;
-- @creation_date - creation date of the template;
-- @description - description of the template.
-- ------------------------------------------------------------

CREATE TABLE template (
  id INTEGER NOT NULL,
  category INTEGER NOT NULL,
  name VARCHAR(30) NOT NULL,
  creation_date DATETIME YEAR TO SECOND,
  description VARCHAR(255),
  PRIMARY KEY(id)
)

