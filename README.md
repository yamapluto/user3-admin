<<개인 프로젝트>>
1. ubuntu에서 AWS configure 설정하고
아래 명령어로 클러스터 생성

eksctl create cluster --name user3-Cluster --version 1.15 --nodegroup-name standard-workers --node-type t3.medium --nodes 3 --nodes-min 1 --nodes-max 3
 클러스터 접속
 aws eks --region ap-northeast-1 update-kubeconfig --name user3-Cluster


2. git 생성 후 ms별로 git commit

3. aws ecr repository 생성
aws ecr get-login-password --region ap-northeast-1 | docker login --username AWS --password-stdin 052937454741.dkr.ecr.ap-northeast-1.amazonaws.com

052937454741.dkr.ecr.ap-northeast-2.amazonaws.com

kubectl create deploy user3-admin --image=052937454741.dkr.ecr.ap-northeast-1.amazonaws.com/user3-admin:latest
kubectl expose deploy user3-admin --type="ClusterIP" --port=8080


## KAFKA

토픽 생성 
kubectl -n kafka exec my-kafka-0 -- /usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --topic doremi --create --partitions 1 --replication-factor 1
토픽 조회 
kubectl -n kafka exec my-kafka-0 -- /usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --list
이벤트 수신 
kubectl -n kafka exec -ti my-kafka-0 -- /usr/bin/kafka-console-consumer --bootstrap-server my-kafka:9092 --topic doremi --from-beginning 
kubectl -n kafka exec -ti my-kafka-1 -- /usr/bin/kafka-console-consumer --bootstrap-server my-kafka:9092 --topic doremi


--metrix server 설치해야함

## AUTOSCALE

--buildspec.yml: resource 정보 넣기, readness/liveness 삭제
kubectl autoscale deployment user3-admin --cpu-percent=10 --min=1 --max=5
kubectl get po -l run=user3-admin -w
kubectl get hpa user3-admin -o yaml

watch kubectl get pod

## siege 설치

kubectl apply -f - <<EOF
apiVersion: v1
kind: Pod
metadata:
 name: siege
 namespace: default  
spec:
 containers:
 - name: siege
   image: apexacme/siege-nginx
EOF


## Siege 구동
kubectl exec -it siege --container siege -n default -- /bin/bash


## 명령어 발행
siege -c5 -t60S -v --content-type "application/json" 'http://user3-admin:8080/menus POST {"menuName": "toamto", "menuType": "pizza", "description": "pizzadesc", "price": "123.0"}' 
