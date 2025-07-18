def call(Map args) {
    def imageTag   = args.imageTag
    def contextDir = args.contextDir ?: '.'
    def imageName  = args.imageName
    def dockerfile = args.dockerfile ?: 'Dockerfile'
    def awsRegion  = args.awsRegion ?: 'eu-west-1'
    def accountId  = args.accountId ?: '513296374752'
    def preBuild   = args.preBuild ?: ''
    def fullImageName = "${imageName}:${imageTag}"
    def ecrImage = "${accountId}.dkr.ecr.${awsRegion}.amazonaws.com/${imageName}:${imageTag}"

    sh """
        set +x
        cd ${contextDir}

        echo "Running pre-build commands (if any)..."
        ${preBuild}

        echo "Building Docker image: ${fullImageName}"
        docker build -f ${dockerfile} -t ${fullImageName} .
        

        echo "Tagging image for ECR: ${ecrImage}"
        docker tag ${fullImageName} ${ecrImage}

        echo "Pushing image to ECR: ${ecrImage}"
        docker push ${ecrImage}
    """
}
// docker build -f ${dockerfile} -t ${fullImageName} ${contextDir}
//        
// def call(Map args) {
//     def imageTag   = args.imageTag
//     def contextDir = args.contextDir ?: '.'
//     def imageName  = args.imageName
//     def dockerfile = args.dockerfile ?: 'Dockerfile'
//     def awsRegion  = args.awsRegion ?: 'eu-west-1'
//     def accountId  = args.accountId ?: '513296374752'
//     def preBuild   = args.preBuild ?: ''
//     def fullImageName = "${imageName}:${imageTag}"
//     def ecrImage = "${accountId}.dkr.ecr.${awsRegion}.amazonaws.com/${imageName}:${imageTag}"

//     sh """
//         set +x

//         echo "Running pre-build commands (if any)..."
//         ${preBuild}

//         echo "Building Docker image: ${fullImageName}"
//         docker build -f ${contextDir}/${dockerfile} -t ${fullImageName} ${contextDir}

//         echo "Tagging image for ECR: ${ecrImage}"
//         docker tag ${fullImageName} ${ecrImage}

//         echo "Pushing image to ECR: ${ecrImage}"
//         docker push ${ecrImage}
//     """
// }
