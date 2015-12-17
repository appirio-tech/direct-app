grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        //grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        mavenRepo "http://repo.grails.org/grails/plugins"
        mavenRepo "http://repo.grails.org/grails/repo"
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
    }
}

grails.war.resources = { stagingDir, args ->
	delete {
		fileset(dir: "${stagingDir}/WEB-INF/lib", includes: "jta-1.1.jar")
		fileset(dir: "${stagingDir}/WEB-INF/lib", includes: "shared.jar")
		fileset(dir: "${stagingDir}/WEB-INF/lib", includes: "tcwebcommon.jar")
		fileset(dir: "${stagingDir}/WEB-INF/lib", includes: "security.jar")
		fileset(dir: "${stagingDir}/WEB-INF/lib", includes: "base_exception.jar")
		fileset(dir: "${stagingDir}/WEB-INF/lib", includes: "log4j-1.2.16.jar")
                fileset(dir: "${stagingDir}/css", includes: "**/*")
                fileset(dir: "${stagingDir}/js", includes: "**/*") 
                fileset(dir: "${stagingDir}/images", includes: "**/*")
	}
}
