def call() {
    def tagDate = sh(
        script: "git show -s --format=%ci | cut -d ':' -f1-2 | tr ' ' 'r' | tr -d - | tr -d :",
        returnStdout: true
    ).trim()

    // Resolve branch name from different possible sources
    def branch = params.BRANCH ?: env.BRANCH ?: BRANCH_NAME ?: env.GIT_BRANCH

    if (!branch || branch.trim() == '') {
        // Fallback for safety
        branch = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
    }

    // Normalize and shorten branch name
    def branchName = branch.contains('/') ? branch.substring(branch.lastIndexOf('/') + 1) : branch
    branchName = branchName.replaceAll('/', '_').take(40)

    def commitHash = sh(
        script: "git rev-parse HEAD | cut -c1-7",
        returnStdout: true
    ).trim()

    def imageTag = "${tagDate}-${branchName}_${commitHash}"

    echo "Generated Docker image tag: ${imageTag}"
    return imageTag
}
