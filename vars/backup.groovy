def call(String imageName = 'bakery', String sourceTag = 'latest', String backupTag = 'backup') {
    sh """
        echo "Creating Backup of Previous Image..."
        if docker image inspect ${imageName}:${sourceTag} > /dev/null 2>&1; then
            docker tag ${imageName}:${sourceTag} ${imageName}:${backupTag}
            echo "Backup created successfully"
        else
            echo "No previous image found, skipping backup"
        fi
    """
}
