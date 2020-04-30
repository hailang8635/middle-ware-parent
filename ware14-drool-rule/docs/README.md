docker run -p 8102:8080 -p 8101:8001 -d --name drools-workbench jboss/drools-workbench-showcase:latest
docker run -p 8103:8080 -d --name kie-server --link drools-workbench:kie_wb jboss/kie-server-showcase:latest


/opt/jboss/.m2/repository/com/smart/demo-smart/1.0.0-SNAPSHOT/

http://112.65.210.206:8101/business-central
http://localhost:8101/business-central
admin/admin