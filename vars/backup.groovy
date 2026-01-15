stage('Backup') {
    steps {
        echo "Creating Backup of Previous Image..."
        sh '''
            if docker image inspect bakery:latest > /dev/null 2>&1; then
                docker tag bakery:latest bakery:backup
                echo "Backup created successfully"
            else
                echo "No previous image found, skipping backup"
            fi
        '''
    }
}
