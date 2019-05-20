# marcadagua


deploy (maven docker): </br>
build image: 'mvn install dockerfile:build'
push image:  'mvn install dockerfile:push'

run (docker):
run image: docker run -p 8080:8080 -t luizvilelamarques/marcadagua

access (tool box):
http://192.168.99.100:8080/pdf/download
