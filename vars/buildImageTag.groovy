def call() {
    def tagDate = sh(script: "git show -s --format=%ci | cut -d ':' -f1-2 | tr ' ' 'r' | tr -d - | tr -d :", returnStdout: true).trim()

    def branch = params.BRANCH ?: env.BRANCH ?: env.BRANCH_NAME
    if (!branch || branch.trim() == '') {
        branch = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
    }

    def branchName = branch.contains('/') ? branch.substring(branch.indexOf('/') + 1) : branch
    branchName = branchName.replaceAll('/', '_').take(40)

    def commitHash = sh(script: "git rev-parse HEAD | cut -c1-7", returnStdout: true).trim()

    def imageTag = "${tagDate}-${branchName}_${commitHash}"

    echo "Generated Docker image tag: ${imageTag}"
    
    return imageTag
}

