package br.com.luiz.marcadagua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 *  build: 'mvn install dockerfile:build'
 *  push:  'mvn install dockerfile:push'
 *  
 *  buildar e fazer push:  'mvn dockerfile:push'
 *  
 *  RUN: docker run -p 8080:8080 -t luizvilelamarques/marcadagua
 *  
 *  chrome (local docker tool box): http://192.168.99.100:8080/pdf/download
 *  
 *  
 *  
 *  deploy heroku:
 *  
 *  heroku login
 *  heroku container:login
 *  heroku create 
 *  heroku container:push web (build)
 *  heroku container:release web (deploy)
 *  heroku open
 *  
 * @author Luiz
 *
 */
@SpringBootApplication
public class ConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}

}