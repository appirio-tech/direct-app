dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            // url = "jdbc:hsqldb:mem:devDB"
            // dbCreate = "create-drop"

            driverClassName = "com.informix.jdbc.IfxDriver"
            url = "jdbc:informix-sqli://localhost:2021/tcs_catalog:INFORMIXSERVER=informixoltp_tcp;DB_LOCALE=en_us.utf8"
            username = "informix"
            password = "1nf0rm1x"
        }
    }
    test {
        dataSource {
            // Please DONOT change the data source for test environment because the review table in production database has
            // many foreign keys
            dbCreate = "create-drop"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            driverClassName = "com.informix.jdbc.IfxDriver"
            url = "jdbc:informix-sqli://localhost:2021/tcs_catalog:INFORMIXSERVER=informixoltp_tcp;DB_LOCALE=en_us.utf8"
            username = "informix"
            password = "1nf0rm1x"
        }
    }
}
