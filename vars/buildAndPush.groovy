def call(Map args = [:]) {

    def imageName   = args.get('imageName', 'default-app')
    def imageTag    = args.get('imageTag', 'latest')
    def registry    = args.get('registry', '513296374752.dkr.ecr.eu-west-1.amazonaws.com')
    def repoPath    = args.get('repoPath', "trustd/app")
    def imagePath   = "${registry}/${repoPath}/${imageName}:${imageTag}"

    sh "docker build -t ${imagePath} ."
    sh "docker push ${imagePath}"

    echo "Pushed Docker image: ${imagePath}"
    return imagePath
}
