#!/usr/bin/env groovy

def call() {

    def tagDate = sh(
        script: "git show -s --format=%ci | cut -d ':' -f1-2 | tr ' ' 'T' | tr -d '-' | tr -d ':'", 
        returnStdout: true
    ).trim()
    
    def branchName = "unknown"
    if (env.BRANCH_NAME) {

        def parts = env.BRANCH_NAME.split('/')
        branchName = parts[-1]  // Get last part after splitting
    }
    branchName = branchName.replaceAll('/', '_').take(40)    

    def commitHash = sh(
        script: "git rev-parse HEAD | cut -c1-7", 
        returnStdout: true
    ).trim()
    
    def imageTag = "${tagDate}-${branchName}_${commitHash}"
    
    return imageTag
}
