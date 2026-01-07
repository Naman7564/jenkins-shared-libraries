def call(Map config = [:]) {

    String imageName   = config.imageName
    String dockerUser  = config.dockerUser
    String backupTag   = config.backupTag
    String latestTag   = config.latestTag ?: 'latest'
    String composeCmd  = config.composeCmd ?: 'docker compose'

    if (!imageName || !dockerUser || !backupTag) {
        error "rollbackIfFailed: imageName, dockerUser, and backupTag are required"
    }

    script {
        echo "🔍 Checking container status for rollback decision"

        def status = sh(
            script: "docker inspect -f '{{.State.Status}}' ${imageName} 2>/dev/null || echo notfound",
            returnStdout: true
        ).trim()

        if (status != "running") {
            echo "❌ Container status: ${status} → Rolling back"

            sh """
            ${composeCmd} down || true
            docker pull ${dockerUser}/${imageName}:${backupTag}
            docker tag ${dockerUser}/${imageName}:${backupTag} \
                       ${dockerUser}/${imageName}:${latestTag}
            ${composeCmd} up -d
            """

            echo "✅ Rollback completed successfully"
        } else {
            echo "ℹ️ Container is running — rollback skipped"
        }
    }
}
