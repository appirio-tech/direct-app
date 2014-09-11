DELETE FROM AuditRecord;
DELETE FROM AssetPermission;
DELETE FROM AssetCategoryMapping;
update 'informix'.Asset set current_version_id = NULL;
DELETE FROM Asset;
DELETE FROM AssetVersion;
DELETE FROM Category;
DELETE FROM FileTypeIcon;
DELETE FROM user;