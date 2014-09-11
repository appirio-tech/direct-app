-- catalogs;
insert into catalog (catalog_id, catalog_name) values (1, 'catalog 1');
insert into catalog (catalog_id, catalog_name) values (2, 'catalog 2');

-- categories;
insert into categories (category_name, description, status_id, viewable, 
parent_category_id, category_id) values ('1', '1', 1, 1, null, 1);
insert into categories (category_name, description, status_id, viewable, 
parent_category_id, category_id) values ('2', '2', 2, 1, 1, 2);
insert into categories (category_name, description, status_id, viewable, 
parent_category_id, category_id) values ('3', '3', 1, 1, 2, 3);
insert into categories (category_name, description, status_id, viewable, 
parent_category_id, category_id) values ('4', '4', 2, 0, 3, 4);

-- category-2-catalog associations;
insert into category_catalog (category_id, catalog_id) values (1, 1);
insert into category_catalog (category_id, catalog_id) values (2, 2);
insert into category_catalog (category_id, catalog_id) values (3, 1);
insert into category_catalog (category_id, catalog_id) values (4, 2);

-- technologies;
insert into technology_types (technology_type_id, technology_name, description, status_id)
values (1, 'Java', 'Java 1.5', 1);
insert into technology_types (technology_type_id, technology_name, description, status_id)
values (2, 'Informix', 'Informix 10', 1);
insert into technology_types (technology_type_id, technology_name, description, status_id)
values (3, 'Hibernate', 'Pure Hibernate', 0);

-- phases;
insert into phase (phase_id, description) values (1, 'Registration');
insert into phase (phase_id, description) values (2, 'Submission');
insert into phase (phase_id, description) values (3, 'Review');
insert into phase (phase_id, description) values (4, 'Final Fix');
insert into phase (phase_id, description) values (111, 'Collaboration');

-- client;
insert into client (client_id) values (1);
insert into client (client_id) values (2);
insert into client (client_id) values (3);

-- user_client;
insert into user_client (client_id, user_id) values (1, 1);
insert into user_client (client_id, user_id) values (1, 2);
insert into user_client (client_id, user_id) values (1, 3);
insert into user_client (client_id, user_id) values (2, 1);
insert into user_client (client_id, user_id) values (3, 2);

insert into doc_types (document_type_id, description, status_id) values (300, 'type1', 1111);
insert into doc_types (document_type_id, description, status_id) values (301, 'type2', 1111);
