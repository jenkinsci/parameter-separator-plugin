pipeline {
    agent {
        label('test-agent')
    }
	parameters {
		separator(name: "building")
		choice(name: "java_vendor", choices: "Corretto")
		choice(name: "java_version", choices: "11")
		separator(name: "testing")
		choice(name: "browser", choices: "chrome")
		separator(name: "end")
	}
    stages {
        stage('Stage') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
