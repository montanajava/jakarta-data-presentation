@Library(['build', 'development-jenkins', 'deployment']) _

def defaultPodTemplate = getDefaultPodTemplate()
def namespace="test-sim2"
def serviceAccount="cicd"
initOpenShiftProject(namespace)
initTestContainer(namespace,serviceAccount)
def nodeName="jakarta-data-presentation"
def jdk="jdk21"
podTemplate(
            cloud: defaultPodTemplate.defaultCloud,
            label: nodeName,
            containers: defaultPodTemplate."${jdk}",
            namespace: namespace,
            serviceAccount: serviceAccount,
            volumes: defaultPodTemplate.defaultVolumes
    ) {

        node(nodeName) {
            try {
                stage('Checkout') {
                    doCheckout("https://git.isceco.admin.ch/playground/jakarta-data-presentation.git", '', 'master')
                }

                stage('test'){
                    withEnv(["TESTCONTAINERS_RYUK_DISABLED=true", "TESTCONTAINERS_CHECKS_DISABLE=true", "DOCKER_HOST=tcp://127.0.0.1:2475"]){
                        mvn('clean package')
                    }
                }
            } catch(err) {
                echo err
                input 'click me'
            }
        }

    }
