INSERT INTO 'informix'.user (user_id, handle, status) VALUES(1, 'user1', 'a');
INSERT INTO 'informix'.user (user_id, handle, status) VALUES(2, 'user2', 'a');

INSERT INTO 'informix'.Asset (id, name,container_type,container_id, public) VALUES(1, 'Asset1', 'type1', 1, 't');

INSERT INTO 'informix'.Category (id, name, container_type, container_id) VALUES(100, 'Category1', 'containerType1', 1);
INSERT INTO 'informix'.Category (id, name, container_type, container_id) VALUES(101, 'Category2', 'containerType2', 2);
INSERT INTO 'informix'.Category (id, name, container_type, container_id) VALUES(102, 'Category3', 'containerType3', 3);

INSERT INTO 'informix'.AssetPermission (asset_id, user_id) VALUES(1, 1);