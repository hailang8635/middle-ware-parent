#!/bin/bash
# eg:
#  sudo docker login registry.cn-shanghai.aliyuncs.com
#  default:0.0.1-zh-SNAPSHOT
#  prod:   0.0.1-zh
#  ./prod_package.sh 0.0.1-SNAPSHOT
#  ./prod_package.sh 0.0.1

package_version=""
docker_path=""

if [ -n "$1" ]
then
  echo $1
else
  package_version="0.0.1-SNAPSHOT"
  docker_path="docs/"
fi

echo "package $package_version .."

mvn clean package -DskipTests
cp target/drool-rule.jar $docker_path
echo "package done"

cd $docker_path
docker build -t registry.cn-shanghai.aliyuncs.com/jrdn/drool-rule:$package_version .
docker push registry.cn-shanghai.aliyuncs.com/jrdn/drool-rule:$package_version
rm -rf drool-rule.jar

echo "docker build done"