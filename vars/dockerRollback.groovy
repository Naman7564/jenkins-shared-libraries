def call(String imageName = 'bakery', String backupTag = 'backup', String latestTag = 'latest') {
    sh """
        echo "Rolling back Docker image..."
        docker tag ${imageName}:${backupTag} ${imageName}:${latestTag}
        docker compose down
        docker compose up -d
        echo "Rollback completed"
    """
}
