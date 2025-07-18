#!/usr/bin/env groovy

def call() {
    // Get timestamp in YYYYMMDD-HHMM format from git commit
    def tagDate = sh(
        script: "git show -s --format=%ci | cut -d ':' -f1-2 | tr ' ' 'T' | tr -d '-' | tr -d ':'", 
        returnStdout: true
    ).trim()
    
    // Process branch name (remove prefix, replace slashes, limit length)
    def branchName = env.BRANCH_NAME.substring(env.BRANCH_NAME.indexOf('/') + 1)
                          .replaceAll('/', '_')
                          .take(40)
    
    // Get short commit hash
    def commitHash = sh(
        script: "git rev-parse HEAD | cut -c1-7", 
        returnStdout: true
    ).trim()
    
    // Combine components into final tag
    def imageTag = "${tagDate}-${branchName}_${commitHash}"
    
    return imageTag
}
