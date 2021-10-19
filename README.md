# Setup serratus-api development environment on MacOSX or Linux (Fedora, RHEL, CentOS)

## Install dependencies on Linux

```bash
sudo yum install -y git python3 python3-pip libselinux-python3 maven
```

## Install dependencies on MacOSX

```bash
brew install git python maven gnu-tar
```

# Setup Ansible

## Install python3 dependencies for Ansible

```bash
sudo pip3 install psycopg2-binary ansible
```

## Setup the directory for the project and clone the git repository into it 

```bash
sudo install -d -o $USER -g $USER /usr/local/src/serratus-api
git clone git@github.com:serratus-api/serratus-api.git /usr/local/src/serratus-api
```

## Setup the environment using the requirements.yml file

```bash
ansible-galaxy install -r /usr/local/src/serratus-api/ansible/roles/requirements.yml
```

## Install the 4 required roles using the main ansible playbook

```bash
cd /usr/local/src/serratus-api && ansible-playbook install_project_ansible_playbook.yml -K
 ```
