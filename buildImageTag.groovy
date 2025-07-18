def call() {
    def tagDate = sh(script: "git show -s --format=%ci | cut -d ':' -f1-2 | tr ' ' 'r' | tr -d - | tr -d :", returnStdout: true).trim()

    def branchName = env.BRANCH_NAME
    if (branchName.contains('/')) {
        branchName = branchName.substring(branchName.indexOf('/') + 1)
    }
    branchName = branchName.replaceAll('/', '_').take(40)

    def commitHash = sh(script: "git rev-parse HEAD | cut -c1-7", returnStdout: true).trim()

    return "${tagDate}-${branchName}_${commitHash}"
}

