package cloudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "cloudapp.repository", repositoryBaseClass = BaseRepoImpl.class)
public class EntryPoint {

	public static void main(String[] args) {
		System.out.println("EntryPoint.main()");
		SpringApplication.run(EntryPoint.class, args);
	}
}