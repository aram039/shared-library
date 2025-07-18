import org.yourorg.docker.Builder

def call(Map args = [:]) {
    def builder = new Builder(this)
    builder.buildAndPushImage(args)
}
