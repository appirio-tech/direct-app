INSERT INTO 'informix'.user (user_id, first_name, last_name, handle, status, activation_code, middle_name, password) VALUES(1, 'user1', 'last1', 'user1', 'T', 'abcd', 'efg', 'pass');
INSERT INTO 'informix'.user (user_id, first_name, last_name, handle, status, activation_code, middle_name, password) VALUES(2, 'user2', 'last2', 'user2', 'T', 'abcd', 'efg', 'pass');

INSERT INTO 'informix'.FileTypeIcon (id, file_type, file_type_category, icon_path) VALUES(1, 'jpg', 'jpg_cate1', 'test_files/acc/1.jpg');

INSERT INTO 'informix'.Category (id, name, container_type, container_id) VALUES(1, 'jpg_cate1', 'container_type1', 1);
INSERT INTO 'informix'.Category (id, name, container_type, container_id) VALUES(2, 'gif_cate2', 'container_type2', 2);

INSERT INTO 'informix'.Asset (id, name, current_version_id, container_type, container_id, public) VALUES(1, 'assert1', NULL,'container_type1', 1, 'F');
INSERT INTO 'informix'.Asset (id, name, current_version_id, container_type, container_id, public) VALUES(2, 'assert2', NULL,'container_type2', 2, 'F');
INSERT INTO 'informix'.Asset (id, name, current_version_id, container_type, container_id, public) VALUES(3, 'assert3', NULL,'container_type3', 3, 't');
INSERT INTO 'informix'.Asset (id, name, current_version_id, container_type, container_id, public) VALUES(4, 'assert4', NULL,'container_type3', 3, 't');
INSERT INTO 'informix'.Asset (id, name, current_version_id, container_type, container_id, public) VALUES(5, 'assert5', NULL,'container_type3', 3, 'F');
INSERT INTO 'informix'.Asset (id, name, current_version_id, container_type, container_id, public) VALUES(6, 'assert6', NULL,'container_type2', 3, 't');
INSERT INTO 'informix'.Asset (id, name, current_version_id, container_type, container_id, public) VALUES(7, 'assert7', NULL,'container_type3', 4, 't');

INSERT INTO 'informix'.AssetVersion (id, version, file_name, file_type, file_size_bytes, uploader_id, upload_time, asset_id,preview_image_path,file_path) VALUES(
1, 'assetversion1', '1.jpeg', 'jpeg', 3327, 1, current, 1, 'previewpath', 'test_files/acc/1.jpeg');
INSERT INTO 'informix'.AssetVersion (id, version, file_name, file_type, file_size_bytes, uploader_id, upload_time, asset_id,preview_image_path,file_path) VALUES(
2, 'assetversion2', '2.jpeg', 'jpeg', 3327, 1, current, 2, 'previewpath', 'test_files/acc/2.jpeg');
INSERT INTO 'informix'.AssetVersion (id, version, file_name, file_type, file_size_bytes, uploader_id, upload_time, asset_id,preview_image_path,file_path) VALUES(
3, 'assetversion3', '3.jpeg', 'jpeg', 3327, 2, current, 3, 'previewpath', 'test_files/acc/3.jpeg');

update 'informix'.Asset set current_version_id = 1 where id = 1;
update 'informix'.Asset set current_version_id = 2 where id = 2;
update 'informix'.Asset set current_version_id = 3 where id = 3;


INSERT INTO 'informix'.AssetPermission (asset_id, user_id) VALUES(1, 1);
INSERT INTO 'informix'.AssetPermission (asset_id, user_id) VALUES(3, 1);
INSERT INTO 'informix'.AssetPermission (asset_id, user_id) VALUES(4, 1);
INSERT INTO 'informix'.AssetPermission (asset_id, user_id) VALUES(5, 1);

