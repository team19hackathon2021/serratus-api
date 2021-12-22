# Setup serratus-api development environment on MacOSX or Linux (Fedora, RHEL, CentOS)

## Install Ansible dependencies on Linux

```bash
pkcon install -y git
pkcon install -y python3-pip
pkcon install -y python3-virtualenv
```

## Install Ansible dependencies on MacOSX

```bash
brew install git python gnu-tar
pip3 install virtualenv openshift
```

## Install the latest Python and setup a new Python virtualenv

```bash
# This step might be virtualenv-3 for you. 
virtualenv ~/python

source ~/python/bin/activate
echo "source ~/python/bin/activate" | tee -a ~/.bash_profile
```

## Install the latest Ansible

```bash
pip install setuptools_rust wheel
pip install --upgrade pip
```

## Install dependencies on Linux

```bash
pkcon install -y maven
pkcon install -y gcc
pkcon install -y make
pkcon install -y git
pkcon install -y bison
pkcon install -y flex
pkcon install -y readline-devel
pkcon install -y zlib-devel
pkcon install -y systemd-devel
pkcon install -y libxml2-devel
pkcon install -y libxslt-devel
pkcon install -y openssl-devel
pkcon install -y perl-core
pkcon install -y libselinux-devel
pkcon install -y container-selinux
pkcon install -y java-1.8.0-openjdk
pkcon install -y java-11-openjdk
```

## Install dependencies on MacOSX

```bash
brew install maven
```

# Setup Ansible

## Install python3 application dependencies

```bash
pip3 install psycopg2-binary openshift
```

## Setup the directory for the project and clone the git repository into it 

```bash
install -d ~/.local/src/serratus-api
git clone git@github.com:team19hackathon2021/serratus-api.git ~/.local/src/serratus-api
```

## Setup the Ansible Galaxy roles for installing the complete project locally. 

```bash
install -d ~/.ansible/roles
git clone git@github.com:computate-org/computate_postgres.git ~/.ansible/roles/computate.computate_postgres
git clone git@github.com:computate-org/computate_zookeeper.git ~/.ansible/roles/computate.computate_zookeeper
git clone git@github.com:computate-org/computate_solr.git ~/.ansible/roles/computate.computate_solr
git clone git@github.com:computate-org/computate_project.git ~/.ansible/roles/computate.computate_project
```

## Run the Ansible Galaxy roles to install the complete project locally. 

```bash

ansible-playbook ~/.ansible/roles/computate.computate_postgres/install.yml

ansible-playbook ~/.ansible/roles/computate.computate_zookeeper/install.yml

ansible-playbook ~/.ansible/roles/computate.computate_solr/install.yml

ansible-playbook ~/.ansible/roles/computate.computate_project/install.yml -e SITE_NAME=serratus-api -e ENABLE_CODE_GENERATION_SERVICE=true
```

## About the code generation

Much of the getters, setters, initialization code, API code, and page code is generated using this open source library: 

https://github.com/computate-org/computate

It can be confusing how it works, so you can disable it by removing ` -e ENABLE_CODE_GENERATION_SERVICE=true` in the command above. If you do wish to use the code generation service, then you will need to run the `touch.sh` script 4 times to initialize the code records in the Solr search engine. Every piece of the code is read and stored in the search engine, and the code *learns* about itself as it is modified. In order to link together the generated code between 2 foreign key related classes, first all of the base classes must be read into the search engine which might take a couple of passes of running the `touch.sh` script, followed by one of the model classes, followed by the other model class. 

So to see the code generation working, open one terminal and watch the logs: 

```
journalctl -f --user-unit watch-serratus-api
```

And in the other terminal, run the touch script at least 4 times, waiting for the logs to stop each time, until there are no more errors in the logs, or in the code: 

```
~/.local/src/serratus-api/bin/touch.sh
```

You can view the code in the Solr search engine here: 

http://localhost:8983/solr/#/computate/query?q=*:*&q.op=OR

For questions, please reach out with work-in-progress pull requests in Github in the project here. 

# Configure Eclipse

## Install the Maven plugin for Eclipse

* In Eclipse, go to Help -> Eclipse Marketplace...
* Install "Maven Integration for Eclipse"

## Import the serratus-api project into Eclipse

* In Eclipse, go to File -> Import...
* Select Maven -> Existing Maven Projects
* Click [ Next > ]
* Browse to the directory: ~/.local/src/serratus-api
* Click [ Finish ]

## Setup an Eclipse Debug/Run configuration to run and debug serratus-api

* In Eclipse, go to File -> Debug Configurations...
* Right click on Java Application -> New Configuration
* Name: serratus-api QuarkusApp
* Project: serratus-api
* Main class: io.serratus.api.enus.vertx.QuarkusApp

### In the "Arguments" tab

Setup the following VM arguments to disable caching for easier web development: 

```
-DfileResolverCachingEnabled=false -Dvertx.disableFileCaching=true
```

### In the Environment tab

Setup the following variables to setup the Vert.x verticle. 

* CLUSTER_PORT: 13081
* CONFIG_PATH: ~/.local/src/serratus-api/config/serratus-api.yml
* SITE_INSTANCES: 5
* VERTXWEB_ENVIRONMENT: dev
* WORKER_POOL_SIZE: 2
* ZOOKEEPER_HOST_NAME: localhost
* ZOOKEEPER_PORT: 2181

Click [ Apply ] and [ Debug ] to debug the application. 

# Deploy serratus-api to OpenShift with Ansible

To deploy serratus-api to OpenShift with Ansible, you will want to follow the instructions to install Ansible on your system first above "Install Ansible dependencies on Linux". 

## Setup ~/.ansible/roles directory

A default place to install Ansible roles from Ansible Galaxy is in ~/.ansible/roles. Make sure this directory exists: 

```bash
install -d ~/.ansible/roles
```

## Clone the Ansible roles for deploying the applications to OpenShift

```bash
git clone git@github.com:computate-org/computate_postgres_openshift.git ~/.ansible/roles/computate.computate_postgres_openshift
git clone git@github.com:computate-org/computate_zookeeper_openshift.git ~/.ansible/roles/computate.computate_zookeeper_openshift
git clone git@github.com:computate-org/computate_solr_openshift.git ~/.ansible/roles/computate.computate_solr_openshift
git clone git@github.com:computate-org/computate_project_openshift.git ~/.ansible/roles/computate.computate_project_openshift
```

## Create an ansible vault for your OpenShift.

You can create and edit an encrypted ansible vault with a password for the host secrets for your shared OpenShift inventory to deploy serratus-api.
It will have you create a password when you save the file for the first time, like using vim to exit. 

```bash
sudo install -d -o $USER ~/.local/src/serratus-api-ansible
install -d ~/.local/src/serratus-api-ansible/vaults/$USER-staging/vault
ansible-vault create ~/.local/src/serratus-api-ansible/vaults/$USER-staging/vault
ansible-vault edit ~/.local/src/serratus-api-ansible/vaults/$USER-staging/vault
```

The contents of the vault will contain the secrets needed to override any default values you want to change in the app defaults defined here.

https://github.com/team19hackathon2021/serratus-api/blob/main/openshift/defaults.yml

Here is an example of a vault that I have used to deploy the serratus-api application. 
You will want to update these values to reflect your OpenShift environment, like the REDHAT_OPENSHIFT_TOKEN which you will need to obtain after logging into OpenShift. 
Or the REDHAT_OPENSHIFT_STORAGE_CLASS_NAME which might be different than gp2 for you. 
If so, try creating a persistent volume in the UI to figure out a good storage class for your environment: 

```yaml
SITE_NAME: serratus-api

REDHAT_OPENSHIFT_HOST: https://api.rh-us-east-1.openshift.com
REDHAT_OPENSHIFT_TOKEN: OcrtrXzKNKVj0riR2FvfqORgGfnURx98G8zRPd2MUvs
REDHAT_OPENSHIFT_NAMESPACE: rh-impact
REDHAT_OPENSHIFT_STORAGE_CLASS_NAME: gp2

POSTGRES_DB_NAME: sampledb
POSTGRES_DB_USER: computate
POSTGRES_DB_PASSWORD: qVTaaa23aIkLmw
POSTGRES_VOLUME_SIZE: 1Gi
POSTGRES_STORAGE_CLASS_NAME: gp2

ZOOKEEPER_VOLUME_SIZE: 1Gi
ZOOKEEPER_STORAGE_CLASS_NAME: gp2

SOLR_VOLUME_SIZE: 2Gi
SOLR_STORAGE_CLASS_NAME: gp2

AUTH_REALM: TEAM19
AUTH_RESOURCE: team19
AUTH_SECRET: 0518f65a-f86d-42e8-ad65-00f46920443d
AUTH_HOST_NAME: sso.computate.org
AUTH_PORT: 443
AUTH_SSL: true
AUTH_TOKEN_URI: "/auth/realms/{{ AUTH_REALM }}/protocol/openid-connect/token"
```

## Run the Ansible automation to deploy the applications to OpenShift

```bash

ansible-playbook --vault-id @prompt -e @~/.local/src/serratus-api-ansible/vaults/$USER-staging/vault ~/.ansible/roles/computate.computate_postgres_openshift/install.yml

ansible-playbook --vault-id @prompt -e @~/.local/src/serratus-api-ansible/vaults/$USER-staging/vault ~/.ansible/roles/computate.computate_zookeeper_openshift/install.yml

ansible-playbook --vault-id @prompt -e @~/.local/src/serratus-api-ansible/vaults/$USER-staging/vault ~/.ansible/roles/computate.computate_solr_openshift/install.yml

ansible-playbook --vault-id @prompt -e @~/.local/src/serratus-api-ansible/vaults/$USER-staging/vault ~/.ansible/roles/computate.computate_project_openshift/install.yml
```

## See the serratus-api application staged here in OpenShift

https://serratus-api.computate.org/
