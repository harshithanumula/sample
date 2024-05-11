Weekly Assessment - 1

1.create user natasha,hari,sara set a common password algebra and hari and natasha are in wheel group.
 
*add users to existing group
 
sudo su -
useradd hary
passwd hary
useradd natasha
passwd natasha
useradd sara
passwd sara
usermod -G wheel hary
usermod -G wheel natasha
cat /etc/group |grep -i wheel
wheel:x:10:ec2-user,hary,natasha
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
2.username jacksparrow passwd is unilog adn jacksparrow should be part of sudolife , he can excute user and password.
 
*add user and give him permissions to add and change password
sudo su -
useradd jack
passwd jack
yum install vim -y
whereis useradd
whereis passwd
vim /etc/sudoers
     jack ALL=(ALL)  /usr/sbin/useradd,/usr/bin/passwd
su - jack
sudo useradd jack
sudo passwd jack
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
3.create a new java file and a new jenkins job.integrete github with jenkins and add maven package to jenkins.
 
*jenkins
 
open github
open aws and create instance and choose the os as redhat
copy the ssh url and connect it with the terminal
open the dev server
sudo su -
yum update -y
yum install git -y
ssh-keygen
cd .ssh/
ll
cat id_rsa.pub
copy the key and paste in the github->setting->ssh and gpg keys->add ssh      key
mkdir /mycode
cd /mycode
git init
vim sample.java
git add simple.java
git commit -m "msg" simple.java
copy the three commands from github
the code will be pushed to the guthub
In jenkins server
connect
sudo su -
yum update -y
yum install git -y
yum install java* -y
yum install wget -y
copy paste the commands to install the jenkins
wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
yum install fontconfig java-17-openjdk
yum install jenkins
rpmquery jenkins
jenkins-2.440.2-1.1.noarch
systemctl start jenkins
systemctl enable jenkins
systemctl status jenkins
edit the ip port of jenkins instance to custom tcp 8080 and anywhere ipv4
copy the public ip address and 8080
login into the jenkins
install maven
manage jenkins -> plugins -> available pulgins -> maven -> Install ->  restart -> login
new build -> project_name -> freestyle -> confiurations -> git -> url ->  main-> save
build now
console output
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
4.create marketing and sales group create four users namely thor, thanos, ironman,ca. The sales group should have user and group permissions and thor and thanos shouldn't access each other files.
 
*create 2 grps add users and give/deny the permissions for users of a particular grp
 
sudo su -
useradd thor
passwd thor
useradd thanos
passwd thanos
useradd im
passwd im
useradd ca
passwd ca
groupadd sales
groupadd mktg
usermod -G sales thanos
usermod -G sales ca
cat /etc/group | grep -i sales
sales:x:1005:thanos,ca
mkdir /salesdata
cd /salesdata/
ll -d /salesdata/
drwxr-xr-x. 2 root root 6 Apr  6 07:23 /salesdata/
chgrp sales /salesdata
chmod 770 /salesdata/
su - thanos
cd /salesdata/
cat > th.txt
this is thanos
ll
exit
su - ca
cd /salesdata/
cat > ca.txt
this is ca
ll
ll -d th.txt
cat th.txt
this is thanos
cat > th.txt
-bash: th.txt: Permission denied
exit
chmod o+t /salesdata/
su - ca
cd /salesdata/
ll
rm -rf th.txt
exit
chmod o+w /salesdata/th.txt
su - ca
cd /salesdata/
cat > th.txt
this is modified
cat th.txt
this is modified
ll
exit

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


Milestone - 1
 
1 .Deploy a EC2 instance using of t2.micro instance in Mumbai region in zone ap-south-1a. Configure web server on it and make it live and attach a 5GB ebs volume where create 10 files like training.txt

Step 1: Go to the aws dashboard
Launch instance -> name -> AWS linux -> t2.micro -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select esg -> add port 8080 -> Include the bash code in advanced details -> launch

(#!/bin/bash
yum install httpd -y
echo "This is mumbai server" >> /var/www/html/index.html
systemctl start httpd
systemctl enable httpd
useradd pooja -p redhat -c "pooja rathod")

Step 2: Go create a new volume
create volume -> volume type(gp2) -> size(5GB) -> availablity zone(1a) -> create volume

Step 3: Once the volume is created attach the volume to the instance
select the volume -> actions -> attach instance -> select the instance -> type(sdb) -> Attach

Step 4: Connect the instance with the terminal

sudo su -
rpmquery httpd
systemctl status httpd

Step 5: Pick the public IP address of the instance and see whether it is reachable or not.
Step 6: Go back to the terminal

lsblk
mkfs tab*2
mkfs.ext4 /dev/xvdb
blkid
df -h
fdisk -l
mkdir /riya
cd /riya
touch training.txt{1..10}
ll
cd
mount /dev/xvdb /riya/
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
2 . My one team is working within Mumabai Indian region and they have some important data in ebs volume and another team is working in singapore region. They also wanted to access same data which is available in Mumbai region. Share it in different region.

Step 1: Create a new instance in Mumbai region
Launch instance -> name -> AWS linux -> t2.micro -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select esg -> add port 8080 -> launch

Step 2: Create a new volume
create -> volume type(gp2) -> size 5Gb -> availability zone (1a) -> create
Select volume -> choose instance -> choose /dev/sdb -> click attach

Step 3: Connect instance to the terminal.

yum update -y
yum install httpd -y
systemctl restart httpd
systemctl enable httpd
systemctl status httpd
lsblk
mkfs(tab*2)
mkfs.ext4 /dev/xvdb
blkid
mkdir /test
mount /dev/xvdb /test/
df -h
cd /test/
touch test.txt{1..10}
ls
cd

Step 4: Create a new snapshot in mumbai region
create -> Select volume -> volume ID -> Description -> create

Step 5: Launch a new instance in Singapore
Launch instance -> name -> AWS linux -> t2.micro -> keys -> deafault Vpc -> subnet(1b) -> auto assignn enable -> select esg -> add port 8080 -> launch

Step 6: Select the snapshot in mumbai region -> Action -> Copy snapshot -> singapore region -> copy

Step 7: Go to singapore region
Select snapshot -> create volume -> gp3 -> 5GB -> create
Select volume -> actions -> attach volume.
(availability zone-which u have selected in instance)-1b
 
Step 8:singapore Connect the terminal

sudo su -
lsblk
mkdir /tech-data
mount /dev/xvdb /tech-data/
df -h
cd /tech-data/
ls
cd

------------------------------------------------------------------------------------------------------------------------------------------------------------
 
3. I have an webserver in Mumbai region where my website is running. I need same server in Singapore region. Migrate these web server from Mumbai to Singapore.

step 1: Create an instance in mumbai region(allow http port 80 in security rules) with a bash code in advanved details.

(
#!/bin/bash
yum install httpd -y
echo "This is mumbai server" >> /var/www/html/index.html
systemctl start httpd
systemctl enable httpd
useradd pooja -p redhat -c "pooja rathod"
)

Step 2: Open the terminal for this instance and check the status of httpd

(
sudo su -
rpmquery httpd
systemctl status httpd
)

step 3: Copy the public ip of the instance and paste in the browser.

step 4: Create an image for this instance

(select the instance--actions--image and templates--create image--name--description--create)

step 5: Now copy AMI to the singapore region

(AMI-- select the image--actions--copy ami--destination region--singapore--copy ami)

step 6: Launch instance using AMI in singapore region

(AMI--select the image--launch instance--my amis--shared with me--select shared ami--allow httpd port 80 in security grp)

step 7: Copy the public ip of the instance and paste in the browser.

------------------------------------------------------------------------------------------------------------------------------------------------------------
 
4Q. Launch an AWS S3 bucket with unique name and upload some objects and this S3 bucket should be reachable on windows host from where I can upload the object in the AWS S3 bucket.

Solution:

Open three tabs -> S3, IAM, EC2

Step 1: Launch a new instance in ec2
launch -> windows -> key pair -> new sg -> launch
port -> 3389(RDP)
Connect -> RDP client -> download desktop file

Step 2: Create S3 bucket
create -> name -> ACLS enabled -> bucket versioning enable -> unblock public access -> I understand -> create
select the bucket -> upload files

Step 3: Go to IAM
create user -> username -> provide -> IAM user -> custom passowrd uncheck user must -> next -> attach policies -> S3full access -> next -> create
open user -> security credentials -> create access key -> tag name -> download csv file

Step 4: Go to instance
connect -> RDP client -> generate password -> upload .pem(key pair) file -> decrypt password -> copy password

Step 5: Open desktop file
paste password -> okay

Step 6: New VM will be opened
Open edge -> tnt windows download -> exe file download -> install
tnt drive install -> I agree -> next -> I'll restart later
Open TNT drive -> add new account -> give access key and secret key -> create account
Add -> folder icone -> existing bucket (any file) -> select and open the bucket -> files will be seen
Add a new file here and go check in the S3 bucket you can see the new file added

------------------------------------------------------------------------------------------------------------------------------------------------------------

5 .Create a custom VPC where you need to create two subnets like private subnet and public subnet. In the public subnet I want to host my webserver where my website is running and private subnet my database is running. Database should not be reachable publicly.


Step 1: Create a VPC with the name mindtree-vpc.
create -> VPC only -> mindtree-vpc -> 10.0.0.0/16 -> No -> create

step 2: Create two subnets, one is public(10.0.0.0/24) and the other is private(10.0.1.0/24) in two different zones using the VPC as mindtree-vpc.
create -> mindtree-vpc -> web-subnet -> N.V (us-east-1a) -> 10.0.0.0/16 -> 10.0.0.0/24 -> name -> web-subnet -> create
create -> mindtree-vpc -> db-subnet -> N.V (us-east-1b) -> 10.0.0.0/16 -> 10.0.1.0/24 -> name -> db-subnet -> create

step 3: Create an ec2 instance web-server using mindtree-vpc and web-subnet with a custom security group having ssh and http along with enabling public ip
Instances -> Launch -> web-server -> AWS -> t2.micro -> Keys -> VPC(mindtree web subnet) -> enable public IP -> create SG -> Allow SSH and port 8080 -> Launch

step 4: Create an internet gateway and attach the vpc
Create -> Mindtree-igw -> create
Click actions -> attach mindtreeVPC -> attach

step 5: Create a route table naming web-rt using the vpc and add internet gateway to the route table(in edit route)
create -> web-rt -> mindtree-vpc -> create
edit route -> 0.0.0.0/0 -> igw -> save

step 6: Now associate web-subnet to the route table.(save associate)
associate the subnet -> web subnet -> save

step 7: Open the terminal and paste the ssh key of the instance web-server.

step 8: Install httpd and create a html file

sudo su -
yum install httpd -y
cd /var/www/html/
echo "This is my webserver" > index.html
ll
cat index.html
This is my webserver
cd
systemctl restart httpd
systemctl enable httpd
systemctl status httpd

step 9: Now copy the public ip of the instance and paste it in the browser.

step 10: Launch another ec2 instance naming database-server using mindtree-vpc and database-subnet with same security group along with ICMP (yuall)security rule.
Intsnaces -> launch -> db-server -> AWS -> t2.micro -> key -> network (VPC) -> subnet(db-subnet) -> public IP disable -> Existing SG -> launch

step 11: Now ping private ip of database-server in the web-server terminal.
ping (private ip of db-server)

Step 12: Go to the key which you have created copy the key.

Step 13: Go to the terminal

yum install vim -y
vim new.pem -> paste the key -> :wq
chmod 400 new.pem
ssh -i new.pem ec2-user@private ip of db
sudo su -

Step 14: Go to dashboard and create a NAT gateway
create -> mindtree-Nat-gateway -> subnet(web) -> connectivity type (public) -> allocate -> create

Step 15: Modify the route table
create route table -> database-rt -> VPC(mindtree-vpc) -> create
edit route -> 0.0.0.0/0 -> from (NAT gateway) -> save changes
subnet association -> edit -> db -> save

Step 16: ping google.com

------------------------------------------------------------------------------------------------------------------------------------------------------------
 
6 .Create two custom VPC one in Mumbai region and another one in Singapore region. So configure VPC peering in btw Mumbai and Singapore.

 
Step 1: Create a VPC with the name mindtree-vpc.
create -> VPC only -> mindtree-vpc -> 10.0.0.0/16 -> No -> create

step 2: Create two subnets, one is public(10.0.0.0/24) and the other is private(10.0.1.0/24) in two different zones using the VPC as mindtree-vpc.
create -> mindtree-vpc -> web-subnet -> N.V (us-east-1a) -> 10.0.0.0/16 -> 10.0.0.0/24 -> name -> web-subnet -> create
create -> mindtree-vpc -> db-subnet -> N.V (us-east-1b) -> 10.0.0.0/16 -> 10.0.1.0/24 -> name -> db-subnet -> create

step 3: Create an ec2 instance web-server using mindtree-vpc and web-subnet with a custom security group having ssh and http along with enabling public ip
Instances -> Launch -> web-server -> AWS -> t2.micro -> Keys -> VPC(mindtree web subnet) -> enable public IP -> create SG -> Allow SSH and port 8080 -> Launch

step 4: Create an internet gateway and attach the vpc
Create -> Mindtree-igw -> create
Click actions -> attach mindtreeVPC -> attach

step 5: Create a route table naming web-rt using the vpc and add internet gateway to the route table(in edit route)
create -> web-rt -> mindtree-vpc -> create
edit route -> 0.0.0.0/0 -> igw -> save

step 6: Now associate web-subnet to the route table.(save associate)
associate the subnet -> web subnet -> save

step 7: Open the terminal and paste the ssh key of the instance web-server.

step 8: Install httpd and create a html file

sudo su -
yum install httpd -y
cd /var/www/html/
echo "This is my webserver" > index.html
ll
cat index.html
This is my webserver
cd
systemctl restart httpd
systemctl enable httpd
systemctl status httpd

step 9: Now copy the public ip of the instance and paste it in the browser.

step 10: Launch another ec2 instance naming database-server using mindtree-vpc and database-subnet with same security group along with ICMP security rule.
Intsnaces -> launch -> db-server -> AWS -> t2.micro -> key -> network (VPC) -> subnet(db-subnet) -> public IP disable -> Existing SG -> launch

step 11: Now ping private ip of database-server in the web-server terminal.
ping private ip of db-server

Step 12: Go to the key which you have created copy the key.

Step 13: Go to the terminal

yum install vim -y
vim new.pem -> paste the key -> :wq
chmod 400 new.pem
ssh -i new.pem ec2-user@private ip of db
sudo su -

Step 14: Go to dashboard and create a NAT gateway
create -> mindtree-Nat-gateway -> subnet(web) -> connectivity type (public) -> allocate -> create

Step 15: Modify the route table
create route table -> database-rt -> VPC(mindtree-vpc) -> create
edit route -> 0.0.0.0/0 -> from (NAT gateway) -> save changes
subnet association -> edit -> db -> save

Step 16: ping google.com

Step 17: Make the same VPC setup in the singapore region with ip address as 20.0.0.0/16

Step 18: Once all the setup is done in both the regions
Go to N.V. region
peering connection -> create -> N.V-to-Singapore -> VPC(mindtree) -> my account -> Another region -> Singapore -> VPCID(singapore) -> create

Step 19: Go to Singapore region
Accept the request from N.V peering request

Step 20: Modify the route tables of both the regions (web-rt)
edit route -> 20.0.0.0/16 -> peering connection -> save changes (In N.V region)
edit route -> 10.0.0.0/16 -> peering connection -> save changes (In singapore region)

Step 21: Now ping public ip of both regions in one another terminals, peering connection for public is established.

Step 22: Modify the route tables of both the regions (db-rt)
edit route -> 20.0.0.0/16 -> peering connection -> save changes (In N.V region)
edit route -> 10.0.0.0/16 -> peering connection -> save changes (In singapore region)

Step 23: Now ping private ip of both regions in one another terminals, peering connection for private is established.

------------------------------------------------------------------------------------------------------------------------------------------------------------

7. Deploy an EC2 instance using with cloud formation in Mumbai region ap-south-1a zone. Instance should be reachable.

Step 1: Open two dashboards (ec2 , cloud formation) mumbai region

Step 2: Create json file (make changes of   ec2 AMI catalog Amazon free id ImageId,(keypair) Keyname,(VPC)subnetId(default)-1a, securitygroup Id)(all defaults0

Step 3: another dashboards >go to cloud formation -> Create stack -> choose existing template -> upload a template file -> upload json file -> enter stack name -> next -> next -> submit

Step 4: Go to ec2 instance and a new instance will be created.

------------------------------------------------------------------------------------------------------------------------------------------------------------
 
8 .We people are working on a common project in a scale region but my servers are in different zones. So I want to share project information with everyone simlutaneously. Configure efs storage should be mount on every server.

step 1: Launch an instance in North Virginia region using aws linux server with http port 80 and nfs port 2049 as security rules.
(Launch instance -> name -> AWS linux -> t2.micro -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select esg -> add port 8080 ->add nfs port 2049-> launch)

step 2: Launch another instance using redhat server with same security grp.
(Launch instance -> name -> redhat -> t2.micro -> keys -> deafault Vpc -> subnet(1b) -> auto assignn enable -> select esg -> add port 8080 ->add nfs port 2049-> launch)

step 3: Open EFS and create file system

step 4: Now delete the file system for remaining regions except (us-1a,us-1b).Change the security group of the file system in both regions.
(network -> manage -> remove the unused zones -> change the sg for the zones)

step 5: Connect the terminal with the redhat server and install nfs.
Connect the terminal with the aws server.

step 7: Create a directory in both terminal and copy the nfs command from efs.

yum install nfs-utils -y
rpmquery nfs-utils

step 8: Now create 10 files in this terminal.

mkdir /tony
cd /tony
touch tony.txt{1..10}

step 9: Connect the terminal with the linux server, create a directory and copy the nfs command

mkdir /tom

step 10: open the directory to view the files created in redhat server.

cd /tom
ls
 
------------------------------------------------------------------------------------------------------------------------------------------------------------

9 .Enable MFA on root account on AWS account and generate access key and secret key for root account.

Step 1: Open IAM or 9security creaditions >ue account )

Step 2: Security Recommendations -> Add MFA

Step 3: Device Name -> Authenticator App  

Step 4: Show QR code -> Scan the QR code using phone Authenticator app -> Add two mfa codes that we get in authenticator app in the phone

step 5: create access key below after adding MFA

(download )
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
10 . Create an IAM role for cloud formation service with administrator full access and create a stack to deploy of JSON code.

Step 1: Open two dashboards (ec2 , cloud formation)

Step 2: Create json file (make changes of <ec2-AMI catalog-redhat ImageId, (ec2)Keyname, (vpc)subnetId, securitygroup Id)

Step 3: Open IAM -> Roles -> create role -> aws service -> cloud formation as use case -> 1st adminstrator full access -> next -> give role name -> create

Step 4: go to cloud formation -> Create stack -> choose existing template -> upload a template file -> upload json file -> enter stack name -> permissions ->select the IAM role -> next -> next -> submit

Step 5: Go to ec2 instance and a new instance will be created.
 
C.json

{
  "Resources": {
    "MyEC2Instance": {
      "Type": "AWS::EC2::Instance",
      "Properties": {
        "InstanceType": "t2.micro",
        "ImageId": "ami-09298640a92b2d12c",
        "KeyName": "sev",
        "SubnetId": "subnet-08ec9a861c7659ac8",
        "SecurityGroupIds": [
          "sg-0c090d32450921317"
        ]
      }
    }
  }
}

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

weekly 2
 
1. Create an Azure virtual machine in the Central region, image as ubuntu and attach 8GB volume.

Step1: Open azure account
Step2: Go to resource group and create resource group by giving the name and select region as Central India amd add tag
Review+create
Create
Step3: Launch a new virtual machine using the created resource group, by adding new disk size of 8GB.
Create new VM.
Name
Security -> standard
Size -> B1
Inbound ports -> 22,80
Next
Disk -> Data disks -> create and attach new disk
Review + create
create
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
2.Pull an image ubuntu from Docker Hub. Deploy web application on 8080 port and application should be reached globally.

Step1: Create an ec2 instance
Launch instance -> name -> linux -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select sg -> launch
Enable ports 22, cutom tcp - 8080
Step2: Connect it to the terminal
sudo su -
yum install docker* -y
systemctl start docker
systemctl enable docker
systemctl status docker
docker pull ubuntu:latest
docker images
docker run -it --name web-server -p 8080:80 ubuntu:latest /bin/bash
apt-get update -y
apt-get install apache2 -y
cd /var/www/html/
echo "Hi this is my web-app" > index.html
ll
cd
service apache2 start
ctrl P + ctrl Q
docker ps -a
docker inspect web-server | less
Shift+G copy ip address
curl http://ip address

Step3: Install docker and pull the ubuntu image from docker hub.
Step4: Run the docker image,install apache2, create a html file and inspect the image.
Step5: Curl the ip address of the image
Step6 : Copy the public ip address of the instance and paste it in the browser using 8080.

 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
3. Deploy an nginx application on k8s cluster and make sure it is available on address of cluster on port 8080.

Step1: Create a new ec2 instance
Launch instance -> name -> linux -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select sg -> launch
Enable ports 22, cutom tcp - 8080, http - 80
Step2: Connect it to the terminal
Step3: Create a new IAM user and role
for user give adminstratorfullaccess
create access key for this user
for roles give administartorfullaccess, IAMfull, EC2full, CloudFormationfull
Step4: Select the instance and go to actions, in security select modify IAM role and add the role.
Step5: In the terminal
sudo su -
yum update -y
aws configure
Install kubectl
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl.sha256"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
chmod +x kubectl
mkdir -p ~/.local/bin
mv ./kubectl /bin/kubectl
kubectl version --client
Install eksctl
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
eksctl version
eksctl create cluster --name my-cluster \
>  --region us-east-1 \
> --node-type t2.small \
Cluster will be created
Create an ecr repository in AWS
create repository -> private -> name -> create -> push commands
docker pull nginx:latest
docker images
create a new index.html file
create a new dockerfile
vim Dockerfile
FROM nginx
COPY index.html /usr/share/nginx/html
EXPOSE 8080
copy the commands from ecr
vim nginix.yml
apiVersion: v1
kind: Service
metadata:
  name: nginx-project
spec:
  type: LoadBalancer
  ports:
    - port: 80
  selector:
    app: nginx-project
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-project
spec:
  replicas: 4
  selector:
    matchLabels:
      app: nginx-project
  template:
    metadata:
      labels:
        app: nginx-project
    spec:
      containers:
        - name: nginx
          image: nginx:1.17.3
          ports:
            - containerPort: 80
kubectl create -f nginix.yml
service/nginx-project created
deployment.apps/nginx-project created
kubectl get pods
kubectl get all
kubectl expose deployment/nginx-project
kubectl get svc nginx-project
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
4. Deploy a web application in K8s pod, Create a replica set if the load increases then increase the count of replica for pod
 
Step1: Create a new ec2 instance
Launch instance -> name -> linux -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select sg -> launch
Enable ports 22, cutom tcp - 8080, http - 80
Step2: Connect it to the terminal
Step3: Create a new IAM user and role
for user give adminstratorfullaccess
create access key for this user
for roles give administartorfullaccess, IAMfull, EC2full, CloudFormationfull
Step4: Select the instance and go to actions, in security select modify IAM role and add the role.
Step5: In the terminal
sudo su -
yum update -y
aws configure
Install kubectl
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl.sha256"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
chmod +x kubectl
mkdir -p ~/.local/bin
mv ./kubectl /bin/kubectl
kubectl version --client
Install eksctl
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
eksctl version
eksctl create cluster --name my-cluster \
>  --region us-east-1 \
> --node-type t2.small \
Cluster will be created
vim nginix.yml
apiVersion: v1
kind: Service
metadata:
  name: nginx-project
spec:
  type: LoadBalancer
  ports:
    - port: 80
  selector:
    app: nginx-project
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-project
spec:
  replicas: 4
  selector:
    matchLabels:
      app: nginx-project
  template:
    metadata:
      labels:
        app: nginx-project
    spec:
      containers:
        - name: nginx
          image: nginx:1.17.3
          ports:
            - containerPort: 80
kubectl create -f nginix.yml
service/nginx-project created
deployment.apps/nginx-project created
kubectl get pods
kubectl get all
kubectl scale deploy --all --replicas=6
kubectl get all
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
5. Launch an ec2 instance in North Virginia webserver running. Create custom image and  launch in Ohio.

Step1: Create an ec2 instance
 
Launch instance -> name -> linux -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select sg -> launch
Enable ports 22, cutom tcp - 8080, Http - 80
Step2: Connect it to the terminal
Step3: Install Httpd
 
yum install httpd -y
systemctl start httpd
systemctl enable httpd
systemctl status httpd

Step4: Go into the html directory and create a html file
 
cd /var/www/html
cat > index.html
Hiiii
Step5: Copy the public ip address of the instance and copy it on to the browser using 80 port.
Step6: Select the instance, go to the actions and create an image.
Step7: Once the image is available, select the image and select copy AMI in actions and select the destination region and copy it.
Step8: Go to the Ohio region and check for the AMI.
Step9: Once the AMI is available launch an instance using the AMI and try to access the public ip address.

 
-----------------------------------------------------------------------------------------------------------------------------------------------------------
 
6. Using of ansible configuration management tool, install httpd package and start and make it enable of service and copy fstab file into temp folder on remote server via playbook.
 
Step1: Launch an instance and connect it to the terminal.
 
Step2: Install ansible in the terminal.
 
yum install ansible-core -y
 
Step3: Configure ansible and change the inventory path.
 
vim /etc/ansible/ansible.cfg
 
inventory = /etc/ansible/hosts
 
Step4: Launch a remote_server instance and connect it to the terminal
 
Step5: Copy the private ip of remote_server and add in the hosts file of ansible.
 
In ansible_server
 
vim /etc/ansible/hosts
(paste the private ip of remote_server)
 
Step6: Generate an ssh key, copy the public key and paste it in the authorized keys of remote_server.
 
In ansible_server

ssh-keygen
cd .ssh/
cat id_rsa.pub
(copy the public key)
 
In remote_server

cd .ssh/
vim authorized_keys
(Paste the key)
 
Step7: Write a Playbook in ansible to install,start and enable httpd package and also to copy the fstab file to tmp folder of remote_server.
 
vim playbook.yml
 
---
- name: Install and configure HTTPD
  hosts: private ip of remote_server # Replace with your server's hostname or IP
  become: true  # Execute tasks with sudo privileges
  tasks:
    - name: Install httpd package
      package:
        name: httpd
        state: present  # Ensure the package is installed
    - name: Start httpd service
      service:
        name: httpd
        state: started  # Ensure the service is started
        enabled: true   # Enable the service to start on boot
    - name: Copy fstab file to /tmp
      copy:
        src: /etc/fstab  # Source file path on the control machine
        dest: /tmp/fstab  # Destination file path on the remote server
 
 
Step8: Run the playbook.
ansible-playbook playbook.yml
 
Step9: Ckeck the status of httpd in remote_server
rpmquery httpd
systemctl status httpd

------------------------------------------------------------------------------------------------------------------------------------------------------------
 
7. Deploy a docker instance and create docker image. Store docker in ecr and achieve it in ecs cluster using ecr image and build a sample java based application.
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
8. Create an ec2 instance in mumbai region and attach a security group where port no 22 and 80 are allowed using terraform.
 
step 1: Launch an ec2 instance and connect to the terminal.
Launch instance -> name -> redhat -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select sg -> launch
step 2: Install awscli and terraform in the terminal.
-> sudo su -
-> yum update -y
awscli
-> yum install unzip -y
-> curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
-> unzip awscliv2.zip
-> sudo ./aws/install
terraform
-> yum install -y yum-utils
-> yum-config-manager --add-repo https://rpm.releases.hashicorp.com/RHEL/hashicorp.repo
-> yum -y install terraform
-> terraform --version
step 3: Create an IAM user and create an access key for the user.
step 4: Configure aws in the terminal by providing the access key of the user.
->aws configue
step 5: Create a directory and create a terraform file in the directory.
 
->mkdir /data
->cd /data
->yum install vim -y
->vim terra-data.tf
step 6: Write code to launch the instance , to attach security group in the terraform file
 
resource "aws_security_group" "test-sg" {
  name        = "test-sg"
  description = "Allow TLS inbound traffic and all outbound traffic"
  tags = {
    Name = "test-sg"
  }
}
resource "aws_vpc_security_group_ingress_rule" "allow_ssh" {
  security_group_id = aws_security_group.test-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 22
  ip_protocol       = "tcp"
  to_port           = 22
}
resource "aws_vpc_security_group_ingress_rule" "allow_http" {
  security_group_id = aws_security_group.test-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 80
  ip_protocol       = "tcp"
  to_port           = 80
}
resource "aws_vpc_security_group_egress_rule" "allow_all_traffic_ipv4" {
  security_group_id = aws_security_group.test-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "-1" # semantically equivalent to all ports
}
###here ending security group code
resource "aws_instance" "outfirst" {
  ami               = "ami-013e83f579886baeb"
  availability_zone = "ap-south-1a"
  instance_type     = "t2.micro"
  security_groups   = ["${aws_security_group.test-sg.name}"]
  key_name          = "terraform"
  #root disk
  root_block_device {
    volume_size           = "25"
    volume_type           = "gp2"
    delete_on_termination = true
  }
  user_data = <<-EOF
        #!/bin/bash
        sudo yum install httpd -y
        sudo systemctl start httpd
        sudo systemctl enable httpd
        echo "<h1>Sample webserver using terraform</h1>" | sudo tee /var/www/html/index.html
  EOF
  tags = {
    Name     = "hello_India"
    Stage    = "testing"
    Location = "India"
  }
}
 
step 6: Initialize the terraform file, format, validate, plan and apply it.
terraform init
terraform fmt
terraform validate
terraform plan
terraform apply
step 7: Check in the mumbai region aws dashboard to see the instance launched.
 
------------------------------------------------------------------------------------------------------------------------------------------------------------
 
9. Configure a cross zone azure load balancer where web-app is exposed on port 80 and configure NAT rule on VM.
 
Step1: Login into the AWS Azure account.
Step2: Create a resource group by giving the name and region.
Step3: Create a VM with the created resource group, give a name, security type as standard, size B1, port 22, 80
Step4: Create a VM with the creates resource group by using the same region but different zones. Assign a name, security type as standard, size B1, ports as 22,80.
In the networking make sure the virtual network you choose is same as that of the first VM.
Step5: Create a load balancer. Select the resource group which you have created, assign a name, standard, public, regional.
Add frontend IP configuration. Assign a name and create new public ip and save
Add backend pool. Assign a name and select virtual network. Next in the Ip configurations select both the VM's and add it and save.
Create + Review.
Create
After the load balancer have got created edit the health probe.
In health probe, assign a name and save
Add load balancing rules, Assign a name, select frontend ip and backend pool,  and assign port 80 as well backend port as 80, select an existing health probe and save.
Step6: Connect the VM to the terminal
ssh -i .\web-key.pem azure@publicip
Step7: In both the terminals use the below commands
sudo su -
apt update -y
apt install apache2 -y
cd /var/www/html
echo "Hi all" > index.html
cat index.html
Hi all
systemctl restart apache2
systemctl enable apache2
systemctl status apache2
Step8: Copy and paste the ip address of load balancer.
 
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Github actions questions

1.Guthub Actions to push file into S3 bucket.

Step 1: Create a new instance connect it to the terminal.
Step 2: Generate the ssh keys and connect it to the github
Step 3: Create a new repository in github and in the terminal create a directory and create a new html file.
Step 4: Push the files on to the github.
Step 5: create new S3 bucket in the aws
Step 6: create an Iam user in the aws (adminstrator, S3, ecr, ecs)
Step 7: create access and secret key for that user.
Step 8: repository settings -> secret and variables -> actions -> add repository secret -> add access key as (AWS_ACCESS_KEY_ID) and secret key as (AWS_SECRET_ACCESS_KEY).
Step 9: in github actions setup a workflow as a yaml file to push into S3 bucket.
name: Deploy to AWS S3
on:
  push:
    branches:
      - main
 
jobs:
  deploy:
    runs-on: ubuntu-latest
 
    steps:
      - name: Checkout
        uses: actions/checkout@v1
 
      - name: Configure AWS Credentials
        uses: aws-actions/configure-aws-credentials@v1
        with:
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
          aws-region: us-east-1
 
      - name: Deploy static site to S3 bucket
        run: aws s3 sync . s3://mynewbucket-512 --delete
(In the above code in the last make sure to replace mynewbucket-512 with your bucket name)

Step 10: After making commit changes -> see in actions whether it is properly deployed or not.
Step 11: Go to s3 bucket and see if these are pushed or not.

------------------------------------------------------------------------------------------------------------------------------------------------------------

2.Github Actions to push file into ecr and to create a cluster,task definitions and service in ecs.

Step 1: Create a new instance connect it to the terminal.
Step 2: Generate the ssh keys and connect it to the github
Step 3: Create a new repository in github and in the terminal create a directory and create a new html file.
Step 4: Push the files on to the github.
Step 6: create an Iam user in the aws (adminstrator, ecr, ecs)
Step 7: create access and secret key for that user.
Step 8: repository settings -> secret and variables -> actions -> add repository secret -> add access key as (AWS_ACCESS_KEY_ID) and secret key as (AWS_SECRET_ACCESS_KEY).
Step 9: Create a Dockerfile in git repository for image.
In repo add new file -> image code -> name Dockerfile.yml

FROM nginx:latest
COPY nginx.conf /etc/nginx/nginx.conf
COPY index.html /usr/share/nginx/html

Step 10: Add new file in git repository -> nginx.conf

server {
  listen 80;
  server_name localhosts;
  location / {
    rppt /usr/share/nginx/html;
    index index.html;
  }
  access_log /var/log/nginx/access.log;
  error_log /var/log/nginx/error.log

Step 11: In aws create a new ecr with private -> name -> tag immutability
Step 12: in github in your repository go to setting -> secrets -> actions -> manage variables -> add -> AWS_REGION -> configure -> add -> AWS_REGION -> us-east-1(your region) -> add


Step 13: go to github and open actions and create new workflow
code as ecr
name: Deploy to Amazon ECS
 
on:
  push:
    branches:
      - main
env:
  AWS_REGION: us-east-1
  ECR_REPOSITORY: new-repo    
 
jobs:
  deploy:
    name: Deploy
    runs-on: ubuntu-latest
    environment: development
 
    steps:
      - name: Checkout
        uses: actions/checkout@v3
 
      - name: Configure AWS credentials
        uses:  aws-actions/configure-aws-credentials@v4
        with:
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
          aws-region: ${{ env.AWS_REGION }}
 
      - name: Login to Amazon ECR
        id: login-ecr
        uses: aws-actions/amazon-ecr-login@v2
 
      - name: Build, tag, and push image to Amazon ECR
        id: build-image
        env:
          ECR_REGISTRY: ${{ steps.login-ecr.outputs.registry }}
          IMAGE_TAG: ${{ github.sha }}
        run: |
          # Build a docker container and
          # push it to ECR so that it can
          # be deployed to ECS.
          docker build -t $ECR_REGISTRY/new-repo:github.sha -f Dockerfile.yml .
          docker push $ECR_REGISTRY/new-repo:github.sha
          echo "image=$ECR_REGISTRY/new-repo:github.sha" >> $GITHUB_OUTPUT

In the above code change the ecr repo name(new-repo) to your ecr repo name

Step 14: image will be pushed to ecr repository
Step 15: copy the image url -> secrets -> new repository secret -> IAMGE_NAME -> url paste
Step 16: write a workflow file to create a ecs cluster,task definition and service edit it along with previous code.

edit ecr workflow file with by adding this
 
- name: Create ECS Cluster
        run: |
          aws ecs create-cluster --cluster-name nginx-cluster
     # Step to register task definition
      - name: Register Task Definition
        run: |
          aws ecs register-task-definition \
            --family nginx-task \
            --container-definitions '[{"name":"new-repo","image":"471112508188.dkr.ecr.us-east-1.amazonaws.com/git-repo","portMappings":[{"containerPort":80}],"memoryReservation":128}]'
     # Step to create ECS service
      - name: Create ECS Service
        run: |
          aws ecs create-service \
            --cluster nginx-cluster \
            --service-name nginx-service \
            --task-definition nginx-task \
            --desired-count 1 \
            --launch-type EC2 \
            --region ${{ env.AWS_REGION }}

In the above code change the name of repo with your ecr repo and image with your ecr repo url

Step 17: Check in AWS ecs for the cluster is created, and the service is deployed.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Terraform question for vpc.

1.Create an ec2 instance and create vpc also in mumbai and attach a security group where port no 22 and 80 are allowed using terraform.
 
 
step 1: Launch an ec2 instance and connect to the terminal.
Launch instance -> name -> redhat -> keys -> deafault Vpc -> subnet(1a) -> auto assignn enable -> select sg -> launch
step 2: Install awscli and terraform in the terminal.
-> sudo su -
-> yum update -y
awscli
-> yum install unzip -y
-> curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
-> unzip awscliv2.zip
-> sudo ./aws/install
terraform
-> yum install -y yum-utils
-> yum-config-manager --add-repo https://rpm.releases.hashicorp.com/RHEL/hashicorp.repo
-> yum -y install terraform
-> terraform --version
step 3: Create an IAM user and create an access key for the user.
step 4: Configure aws in the terminal by providing the access key of the user.
->aws configue
step 5: Create a directory and create a terraform file in the directory.
mkdir /data
cd /data
yum install vim -y
vim terra-data.tf
 
provider "aws" {
  region = "us-east-1"
}
###This is vpc code
resource "aws_vpc" "test-vpc" {
  cidr_block = "10.0.0.0/16"
}
#This is subnet code
resource "aws_subnet" "public-subnet" {
  vpc_id     = aws_vpc.test-vpc.id
  cidr_block = "10.0.0.0/24"
  tags = {
    Name = "Public-subnet"
  }
}
resource "aws_subnet" "private-subnet" {
  vpc_id     = aws_vpc.test-vpc.id
  cidr_block = "10.0.1.0/24"
  tags = {
    Name = "Private-subnet"
  }
}
#internet gateway code
resource "aws_internet_gateway" "test-igw" {
  vpc_id = aws_vpc.test-vpc.id
  tags = {
    Name = "test-igw"
  }
}
#create a public ip for nat gateway
resource "aws_eip" "nat-eip" {
}
###create nat gateway
resource "aws_nat_gateway" "my-ngw" {
  allocation_id = aws_eip.nat-eip.id
  subnet_id     = aws_subnet.public-subnet.id
}
#public route table code
resource "aws_route_table" "public-rt" {
  vpc_id = aws_vpc.test-vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.test-igw.id
  }
  tags = {
    Name = "public-rt"
  }
}
#route table association code
resource "aws_route_table_association" "public-asso" {
  subnet_id      = aws_subnet.public-subnet.id
  route_table_id = aws_route_table.public-rt.id
}
#create private route table
resource "aws_route_table" "private-rt" {
  vpc_id = aws_vpc.test-vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_nat_gateway.my-ngw.id
  }
  tags = {
    Name = "private-rt"
  }
}
#route table association code
resource "aws_route_table_association" "private-asso" {
  subnet_id      = aws_subnet.private-subnet.id
  route_table_id = aws_route_table.private-rt.id
}
#ec2 code
resource "aws_instance" "aishwarya-server" {
  ami             = "ami-07caf09b362be10b8"
  subnet_id       = aws_subnet.public-subnet.id
  instance_type   = "t2.micro"
  security_groups = ["${aws_security_group.test_access.id}"]
  key_name        = "ltimindtree"
  tags = {
    Name     = "test-world"
    Stage    = "testing"
    Location = "chennai"
  }
}
#create an EIP for ec2
resource "aws_eip" "aishwarya-ec2-eip" {
  instance = aws_instance.aishwarya-server.id
}
#ssh keypair code
resource "aws_key_pair" "ltitestkey" {
  key_name   = "ltimindtree"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDTlYZQJqZpAjQ6GfiOVsgdhLhxWLBvY2GwCLHXgRKx5s1hkgnhLESUl+sJokWwrjj2v6gEqTg/K1/o+uIRUvX7fdCVB1QyaH2eGjH+M3YJXte2/9iO1CHBfHyUWt6QnaGF5PCvTfgpr///wpW1Py0F4sDRsLYcxBxrVDUF6jgLY5/NHjHcgHZ/De/pPDKwDjUGr/N+A2D/brz0nsBqcrHWqL2wTMVUYz8rWktTlcRpzw1L4kYsi5HkGVs/xYyIuHYNEOxXJR6SS9goFkPuIAzF+pBT7Q6x8pGkPTgqo4XBgmndCd6rLNttIu5TRTpAnrDdpLHUUgzLyqeVfUFg+TIv37dtXp1V9HXebZ6+hgRFLFa32mrSJOSKtavd1s/bwpWZVT8BE4mqJ/8nIVBkbrAAkNe5a4xuWj0z9hfa+X/MZ0FYoj2aEnBYcOWLrB9A2i7U2vKkHhDgRhyXn5n6xJOYrQR3BllLMpDgeSQWe/lC9AK1sLct2rTmhN+2V3yM1Gk= root@ip-172-31-1-154.ec2.internal"
}
###This is database ec2 code
resource "aws_instance" "database_server" {
  ami             = "ami-07caf09b362be10b8"
  subnet_id       = aws_subnet.private-subnet.id
  instance_type   = "t2.micro"
  security_groups = ["${aws_security_group.test_access.id}"]
  key_name        = "ltimindtree"
  tags = {
    Name     = "db-world"
    Stage    = "stage-base"
    Location = "delhi"
  }
}
resource "aws_security_group" "test_access" {
  name        = "test_access"
  description = "Allow TLS inbound traffic and all outbound traffic"
  vpc_id      = aws_vpc.test-vpc.id
  tags = {
    Name = "test_access"
  }
}
resource "aws_vpc_security_group_ingress_rule" "allow_ssh" {
  security_group_id = aws_security_group.test_access.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 22
  ip_protocol       = "tcp"
  to_port           = 22
}
resource "aws_vpc_security_group_ingress_rule" "allow_http" {
  security_group_id = aws_security_group.test_access.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 80
  ip_protocol       = "tcp"
  to_port           = 80
}
resource "aws_vpc_security_group_egress_rule" "allow_all_traffic_ipv4" {
  security_group_id = aws_security_group.test_access.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "-1" # semantically equivalent to all ports
}
###here ending security group code
 
 
Step 6:
terraform init
terraform fmt
terraform validate
terraform plan
terraform apply

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Docker question

1. Create a docker container and attach a bind volume. Also create a new container with existing bind volume.

->launch an ec2 instance using amazon linux.
 
sudo su -
yum update -y
yum install docker -y
systemctl start docker
systemctl enable docker
systemctl status docker
docker ps -a
docker pull ubuntu:latest
docker pull httpd
docker images
yum install curl -y
docker run -it --name web-one ubuntu:latest /bin/bash

(Now you will enter into web-one container)

apt-get update -y
apt-get install apache2 -y
clr+p+Q
docker attach web-one
cd /var/www/html
echo "this is pooja" > index.html
ll
cd
service apache2 start
ctrl p+ ctrl q
(Now you will enter into root)

curl http://172.17.0.2
cd /var/lib/docker/volumes
ll
cd
docker kill web-one
docker ps -a
docker rm web-one
docker ps -a
docker run -it -v my-volume:/var/www/html --name web-apps ubuntu:latest /bin/bash
(Now you will enter into web-apps container)

apt-get update -y
apt-get install apache2 -y
cd /var/www/html
echo "this is web-apps" > index.html
ll
cd
service apache2 start
clr+p+q
(Now you will enter into root)

curl http://172.17.0.2
docker ps -a
cd /var/lib/docker/volumes
ll
cd my-volume
ll
cd _data
ll
docker stop web-apps
docker ps -a
docker rm web-apps
docker ps -a

(Even though the container is removed data is stored in volume)
cd /var/lib/docker/volumes/my-volume/_data
ls
cd

(Now create a container using the volume which has data)
docker run -it -v my-volume:/var/www/html --name web-sev ubuntu:latest /bin/bash
(Now you will enter into web-sev container)
apt-get update -y
apt-get install apache2 -y
cd /var/www/html
ll
service apache2 start
clr+p+q
(Now you will enter into root)
curl http://172.17.0.2

------------------------------------------------------------------------------------------------------------------------------------------------------------

2. Create a docker container and attach a directory

->launch an ec2 instance using amazon linux. and connect it to the terminal

sudo su -
yum update -y
yum install docker -y
yum install httpd -y
systemctl start docker
systemctl enable docker
systemctl status docker
docker ps -a
docker pull ubuntu:latest
docker pull httpd
docker images
mkdir pooja
ll -d pooja
docker run -it -v pooja:/tmp --name web-three ubuntu:latest /bin/bash
(Now you will enter into web-three container)

cd /tmp
touch pooja.txt{1..10}
ls
ctrl p+q
(Now you will enter into root)

cd /var/lib/docker/volumes
ll
cd pooja
ls
cd _data
ll
pwd
cd /var/lib/docker/volumes/pooja/_data
rm -rf pooja.txt{1..10}
cd
docker attach web-three
(Now you eill enter into web-three container)

cd /tmp
ls
(all files will be removed and is empty)
touch training.txt{1..5}
ls
ctrl p+q
(Now you will enter into root)

cd /var/lib/docker/volumes/pooja/_data
ls
(training.txt files will be reflected here)