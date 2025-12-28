def call(String image, String user, String tag) {
    sh "docker tag ${image} ${user}/${image}:${tag}"
    sh "docker push ${user}/${image}:${tag}"
}
