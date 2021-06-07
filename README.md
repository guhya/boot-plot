# Stump

Stump NAVER cloud setting development guide.  
This documentation is intended to be used for developers only.

This is for the homepage of Stump.

### NAVER Cloud URL  
<https://console.ncloud.com/>

## VPC (Virtual Private Cloud)  
________________________________________________________________________________

### Create VPC  
- Ip Address range *10.0.0.0/16* : 10.0.0.0 ~ 10.0.255.255  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpc/1.png) 
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpc/2.png) 

### Create subnets  
- Load Balancer subnets *(10.0.100.0/24)** (Load Balancer)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpc/3.png) 
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpc/subnet/1.png) 

- WEB Server subnets *(10.0.0.0/24)* (Public)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpc/subnet/2.png) 

- WAS Server subnets *(10.0.1.0/24)* (Public)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpc/subnet/3.png) 

- DB Server subnets *(10.0.2.0/24)* (Public)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpc/subnet/4.png) 

### Create ACG
- Create web ACG  
  Allow inbound port **80**  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/acg/1.png) 
  
- Create was ACG  
  Allow inbound port **8088, 8009**
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/acg/2.png) 

## Server 
________________________________________________________________________________

### Create WEB server instance in VPC (web subnet)
- Select Centos 7.8 :
    * Type : g2
	* CPU : 2vCPU
	* RAM : 8GB
	* OS : CentOS 7.8
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/1.png)  
	
- Assign private IP from web subnet
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/3.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/4.png)  

- Set ACG : default, web
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/5.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/6.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/7.png)  

### Create WAS server instance (was subnet)
- Select Centos 7.8 with tomcat instance :
    * Type : g2
	* CPU : 2vCPU
	* RAM : 8GB
	* OS : CentOS 7.8
	* Server image : Tomcat-centos-7.8-64
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/9.png)  

- Assign private IP from was subnet			
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/10.png)  

- Set ACG : default, was  
  Same step as web server above

## Server administration
________________________________________________________________________________
- Get admin login 
  
- Login to server by SSH
    - Default username "root"  
    - Password from console  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/vpc/8.png)  

- Set new password for root
  `passwd`  

- Create new non-root user (ex. stump)
    - Home directory : /home/stump
	- Default group : stump
	- Login shell : bash
	- Username : stump  
	```
	useradd -m -d /home/stump -s /bin/bash stump`  
	```

- Set password for stump  
  `passwd stump`
  
- Grant sudo to stump, by adding stump to wheel group
  The easiest way to grant sudo privileges to a user on CentOS is to add the user to the ¡°wheel¡± group.   
  `usermod -aG wheel stump`  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/12.png)  
  
- Open new SSH session and login to server by using user : stump  

- Test sudo  
  `sudo whoami`  
  If result is "root", then it is success
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/13.png)    

- Disable root login, open file below :  
  `sudo vim /etc/ssh/sshd_config`  
  
- Set `PermitRootLogin no`  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/14.png)  
  Save and exit  
  
- Restart SSHD service  
  `sudo systemctl restart sshd`  
  
- Close root session  

- Test **root** login, should fail  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/15.png)  

### WEB Server install
- Install Apache HTTPD
  `sudo yum install httpd`

- Check if apache is working correctly
  `curl localhost`  

### Firewall management 
- Install firewalld  
  `sudo yum install firewalld`  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/16.png)  

- Enable firewalld  
  `sudo systemctl enable firewalld`  
  
- Start firewalld  
  `sudo systemctl start firewalld`
  
- Check status of firewalld, verify that it is active(running) and loaded  
  `sudo systemctl status firewalld`  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/17.png)  

### Apache administration
- Apache should listen at port **80**  

- Check port it is open  
  `firewall-cmd --list-ports`   

- Open port **80**  
  `sudo firewall-cmd --permanent --add-port=80/tcp`
  
- Add index.html in root directory 
  `sudo vim /var/www/html/index.html`  
  Modify html content and save

- Check if html is rendered correctl
  `curl localhost`  
  
### Install mod_jk for apache load balancing  
-  Change to directory  
  `cd /usr/local`  
  
-  Install compiler
  `sudo yum install httpd-devel gcc`  
  
-  Get source from apache, extract, install   
	```
	sudo wget https://mirror.navercorp.com/apache/tomcat/tomcat-connectors/jk/tomcat-connectors-1.2.48-src.tar.gz  
	sudo tar -xvzf tomcat-connectors-1.2.48-src.tar.gz
	cd /usr/local/tomcat-connectors-1.2.48-src/native
	./configure --with-apxs=/usr/bin/apxs
	sudo make
	sudo make install
	```
  
-  Configure **worker.properties**  
  `sudo vim /etc/httpd/conf/workers.properties`  
  Add following :  
	```
	worker.list=loadbalancer,status

	worker.node1.port=8009
	worker.node1.host=10.0.1.6
	worker.node1.type=ajp13
	worker.node1.socket_keepalive=true
	worker.node1.connection_pool_timeout=300
	worker.node1.socket_timeout=300
	worker.node1.socket_connect_timeout=300
	worker.node1.reply_timeout=30000
	  
	worker.node2.port=8009
	worker.node2.host=10.0.1.7
	worker.node2.type=ajp13
	worker.node2.socket_keepalive=true
	worker.node2.connection_pool_timeout=300
	worker.node2.socket_timeout=300
	worker.node2.socket_connect_timeout=300
	worker.node2.reply_timeout=30000

	worker.loadbalancer.type=lb
	worker.loadbalancer.balance_workers=node1,node2
	worker.loadbalancer.sticky_session=1
	  
	worker.status.type=status
	```
-  Save and quit 

-  Configure **mod_jk.conf**
  `sudo vim /etc/httpd/conf.d/mod_jk.conf`
  Add following :
  ```
	LoadModule jk_module modules/mod_jk.so

	JkWorkersFile conf/workers.properties
	JkLogFile logs/mod_jk.log
	JkLogLevel info
	JkShmFile /var/log/httpd/jk-runtime-status
	JkWatchdogInterval 30
	JkMount /* loadbalancer

	RequestHeader set lb "web1"
  ```
  Makes sure to change value of lb appropriately (web1/web2)
 
- Restart Apache
  `sudo systemctl restart httpd`
  

### Tomcat administration
- Tomcat should listen at port **8088**
  
- Check port it is open  
  `firewall-cmd --list-ports`   
  
- Remove default port **8080, 18080** if open  
  `sudo firewall-cmd --permanent --remove-port=18080/tcp`  
  `sudo firewall-cmd --permanent --remove-port=8080/tcp`  

- Open port **8088** and **8009** 
  `sudo firewall-cmd --permanent --add-port=8088/tcp`  
  `sudo firewall-cmd --permanent --add-port=8009/tcp`  
  
- Reload firewalld and check port 
  ```
  sudo firewall-cmd --reload
  firewall-cmd --list-ports   
  ```

- Check tomcat status, verify that it is active(running) and loaded  
  `sudo systemctl status tomcat`
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/19.png)  
  
- Change listening port of tomcat to port **8088**  
  `sudo vim /opt/tomcat/conf/server.xml`  
    - Modify connector port to **8088**  
    - Enable AJP Connector on port **8009**  
    - Set `jvmRoute` to apache loadbalancer node  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/20.png)  
  
- Restart tomcat  
  `sudo systemctl restart tomcat`  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/21.png)  
  
- Change /opt/tomcat/webapps owner to stump:tomcat  
  `sudo chown -R stump:tomcat /opt/tomcat/webapps`  

- Change /opt/tomcat/webapps folder permission to (770)  
  `sudo chmod 770 /opt/tomcat/webapps`  

- Change /opt/tomcat/logs file and folder owner to stump:tomcat  
  `sudo chown -R stump:tomcat /opt/tomcat/logs`  

- Change /opt/tomcat/logs folder permission to (770)  
  `sudo chmod 770 /opt/tomcat/logs`  

- Change /opt/tomcat/logs files permission to (660)  
  `sudo chmod -R 660 /opt/tomcat/logs/*`  

- Change /var/log/tomcat owner to root:tomcat  
  `sudo chown -R stump:tomcat /var/log/tomcat`  
  
- [Optional] Set environment variable **[dev, test, prod]** and set log location  
    - Create file `sudo vim /opt/tomcat/bin/setenv.sh`   
    - Add `CATALINA_OPTS="$CATALINA_OPTS -Denv=dev"`   
    - Add `CATALINA_OPTS="$CATALINA_OPTS -DserverId=was1"` with correct id  
    - [Optional] Add `CATALINA_OUT="/var/log/tomcat/catalina.out"` ->  If you want to redirect log location   
    - Save file and restart tomcat    
    `sudo systemctl restart tomcat`  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/22.png)  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/23.png)  

- Test tomcat welcome page html is OK  
  `curl localhost:8088`  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/server/24.png)  

## Domain/subdomain
________________________________________________________________________________
- Create main domain  
- Create CDN domain
  
## SSL Certificate
________________________________________________________________________________

### Get external certificate
- For example :  
  <https://app.zerossl.com/certificates>  
  After registration, download certificate files.  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/2.png)  

### Export certificate chain
- Open certificate  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/3.png)  

- Export all chain certificates to file  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/4.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/5.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/6.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/7.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/8.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/9.png)  

### Register certificate in NAVER cloud
- Copy and paste private key (**private.key**)  
- Copy and paste public key (**certificate.crt**)  
- Copy and paste certificate chain (**ZeroSSL.cer** + **Sectigo.cer**)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/10.png)   
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/ssl/11.png)  

## Object Storage 
________________________________________________________________________________

### Create bucket for CDN+
- Create bucket  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/storage/1.png)    
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/storage/2.png)    
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/storage/3.png)    
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/storage/4.png)    
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/storage/5.png)    
 
- Create resources folder for website static files  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/storage/6.png)    

- Upload files and folders to bucket

## CDN+
________________________________________________________________________________
All public static files **(css, js, images)** will be uploaded (manually, java file uplaod) to CDN+

### Create CDN (https)
- Create CDN  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/2.png)  

- Connect to cdn domain using SSL  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/3.png)    

- Connect to object storage  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/4.png)    

- Disable caching for development  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/5.png)    

- Add CORS Header allow origin  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/6.png)    
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/7.png)    
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/8.png)    
	
- Link CNAME record (ex. **cdn.stump.ewideplus.com**) to Ncloud domain (ex. **ddzzkkygefcf6652683.cdn.ntruss.com**)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/9.png)    
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cdn/10.png)    

- Test CDN+ using cdn domain  
  (ex. <https://cdn.stump.ewideplus.com/resources/user/img/pc/common/decoGnb.png>)

## Load Balancer
________________________________________________________________________________

### Create Target Group  

- Set health check IP Address, Port, Ip hash Algorithm and sticky session  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/tg/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/tg/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/tg/3.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/tg/4.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/tg/5.png)  


### Create VPC Load Balancer

- Setup Application Load balancer in HTTP and HTTPS, and set target group
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/3.png)  

- Set listener For HTTPS, user SSL certificate
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/4.png)  

- Add rule to redirect HTTP to HTTPS
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/5.png)  

- Add CNAME record in DNS  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/lb/vpc/6.png)  


## Source Commit
________________________________________________________________________________

### Setup Repository
- Create repository  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/3.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/4.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/5.png)  

- Create develop branch  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/6.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/7.png)  

### Set SSH key
- Login to server
- Generate SSH keypair, accept all default setting (press enter)   
  `ssh-keygen`

- Open public key   
  `cat /home/stump/.ssh/id_rsa.pub`
  
- Copy and paste public key to GIT SSH setting
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/8.png)  

### Set Sub Account login page
- Dashboard
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/9.png)  

### Create Sub Account
- Create sub account [dylee, jhpark, guhya]
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/10.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/11.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/12.png)  

- Click account, add policy
  ```
	  NCP_INFRA_MANAGER
	  NCP_SOURCECOMMIT_ADMIN
	  NCP_SOURCECOMMIT_MANAGER
	  NCP_SOURCECOMMIT_READ
	  NCP_SOURCECOMMIT_WRITE
	  NCP_SOURCECOMMIT_VIEWER
  ```
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/13.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/14.png)  

- Subaccount login console
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/21.png)  

### Clone Repository in Eclipse/STS

- Login to subaccount console, change password in Source Commit  
  <https://www.ncloud.com/nsa/ewidestump>
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/25.png)  

- Clone only develop branch, using new Source Commit pasword  
  <https://devtools.ncloud.com/2667499/stump.git>  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/15.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/16.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/17.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/18.png)  

- Import into project  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/19.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/source/20.png)  


### Commit and push  
- Commit and push only to develop branch 

## Source Build
________________________________________________________________________________
- Set build setting   
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/3.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/4.png)  

- Move build output to bucket  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/5.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/6.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/7.png)  
  
- Build command  
  `mvn clean package -e`
  
- Confirm build command and build path  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/8.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/9.png)  
  
- Build  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/build/10.png)  

## Source Deploy
________________________________________________________________________________
  
- Install agent command in target server :  
  ```
  sudo echo $'NCP_ACCESS_KEY=accesskey\nNCP_SECRET_KEY=secretkey' > /opt/NCP_AUTH_KEY
  sudo wget https://sourcedeploy-agent.apigw.ntruss.com/agent/vpc/download/install
  sudo chmod 755 install
  sudo ./install
  sudo rm -rf install
  
  sudo service sourcedeploy stop
  sudo service sourcedeploy start
  sudo service sourcedeploy status
  ```

- Create deployment
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/3.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/4.png)  

### Setup deployment scenario
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/5.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/6.png)  
  
- Specify before script (as root)  
    - Create dummy file so it will not fail when deleting `touch /opt/tomcat/webapps/ROOT.war`  
    - Create dummy directory `mkdir -p /opt/tomcat/webapps/ROOT`  
    - Remove previously existing directory `rm -rf /opt/tomcat/webapps/ROOT`  
    - Remove previously existing war `rm -rf /opt/tomcat/webbaps/ROOT.war`  
	
- Specify source build and  target directory  
    - Move ROOT.war from object storage `./target/ROOT.war` to target server `/opt/tomcat/webapps`  
	
- Specify after script (as root)  
    - Assign user(read, write) group(read) other(none) - 640 to war file `chmod -R 640 /opt/tomcat/webapps/ROOT.war`  
    - Modify owner of webbaps directory to stump and group to tomcat, so tomcat can read,  
    - explode war, and create ROOT directory automatically `chown -R stump:tomcat /opt/tomcat/webbaps`  
    - Restart tomcat `systemctl restart tomcat`
    - Modify owner of webbaps directory and it's children once more including ROOT, to stump and group to tomcat  
    `chown -r stumop:tomcat /opt/tomcat/webbapps`  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/7.png)  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/8.png)  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/9.png)  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/10.png)  
  
- Install deployment agent as root
  * Get API Key
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/11.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/12.png)  
  
### Setup deployment scenario for batch  
- Specify source build and  target directory  
    - Move batch.jar from object storage `./target/batch.jar` to target server `/home/stump/batch`  
	- Move batch.sh from object storage `./target/batch.sh` to target server `/home/stump/batch`  
	- [Optional] If same properties is used, move application.properties from object storage `./target/application.properties`   
	- to target server `/home/stump/batch`  
	
- Specify after script (as root)  
	- Modify owner of batch directory to stump and group to stump  
	  `chown -R stump:stump /home/stump/batch`  
	- Modify file permission to executable for batch.sh  
	  `chmod u+x /home/stump/batch/batch.sh`  
  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/deploy/vpc/14.png)  

- Deploy
  
## Source Pipeline
________________________________________________________________________________
- Create pipeline
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/pipeline/vpc/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/pipeline/vpc/2.png)  

- Add tasks
  * Build tasks  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/pipeline/vpc/3.png)  
  
  * Deploy tasks  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/pipeline/vpc/4.png)  
  
- Setup task order, and set trigger
  * Build is first order  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/pipeline/vpc/5.png)  
  
  * Deploy is after build  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/pipeline/vpc/6.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/pipeline/vpc/7.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/pipeline/vpc/8.png)  
    
## MySQL Cloud DB
________________________________________________________________________________

### Create DB
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/db/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/db/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/db/3.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/db/4.png)  

### Create User
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/db/5.png)  

### Set ACG
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/db/6.png)  

### Apply public domain
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/db/7.png)  

### Connect using public domain
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/db/8.png)  


### DB Backup

- Install MySQL client 

- Setup backup cron


## NAS
________________________________________________________________________________

### Create NAS
- Create NAS, write down the result (NAS location)
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/nas/1.png)  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/nas/2.png)  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/nas/3.png)  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/nas/4.png)  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/nas/5.png)  

### Mount NAS
- Install NFS Utils  
  `sudo yum install nfs-utils`  

- Enable, start and check status of RPC daemon  
  ```
  sudo systemctl enable rpcbind
  sudo systemctl start rpcbind
  sudo systemctl status rpcbind
  ```
  
- Create mount point  
  `sudo mkdir /mnt/nas`
  
- Mount NAS, adjust NAS location  
  `sudo mount -t nfs -o vers=3 169.254.82.80:/n2667499_wasnas /mnt/nas` 

- Check NAS if it is mounted properly  
  `sudo df -h`  
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/nas/6.png)  

### Automatically mount NAS at startup
- Register in /etc/fstab, add content below  
  ```
  169.254.82.80:/n2667499_wasnas /mnt/nas nfs defaults 0 0
  ```
    ![image](https://cdn.stump.ewideplus.com/resources/html/guide/nas/7.png)  
  
- Reboot and check if NAS is mounted automatically 


## SSL VPN 
________________________________________________________________________________

### Create VPN
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/1.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/2.png)  

### Create VPN User
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/3.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/4.png)  

### Download VPN Agent
  <https://guide.ncloud-docs.com/docs/en/security-5-1-windowsdownload>

### Add VPN to subnet's route table
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/5.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/6.png)  

### Add VPN to default ACG
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/7.png)  

### Connect to VPN  
  Connect to VPN client, enter login information, OTP  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/8.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/9.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/10.png)  

### SSH using Private IP
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/vpn/11.png)  


## KMS (Key Management Service)
________________________________________________________________________________

- Create KMS  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/kms/1.png)  

- Setup algorithm and key rotation  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/kms/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/kms/3.png)  


## CLA (Cloud Log Analytic)
________________________________________________________________________________

### Subscribe and enable product
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cla/1.png)  

### Set log type, path, and agent
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cla/2.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cla/3.png)  

### Analize log, check log in dashboard 
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cla/4.png)  
  ![image](https://cdn.stump.ewideplus.com/resources/html/guide/cla/5.png)  

________________________________________________________________________________
