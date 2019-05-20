# Marca dágua

</br>
Spting boot 
</br>

Converte um htm em pdf e insere marcadagua.
</br>

deploy (maven docker): </br>
build image: 'mvn install dockerfile:build'</br>
push image:  'mvn install dockerfile:push'</br>

</br>

run (docker):</br>
run image: docker run -p 8080:8080 -t luizvilelamarques/marcadagua</br>

</br>

access (tool box):</br>
http://192.168.99.100:8080/pdf/download
