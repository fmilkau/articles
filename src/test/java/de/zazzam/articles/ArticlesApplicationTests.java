package de.zazzam.articles;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = { "zazzam.articles.data-root=src/test/resources" })
class ArticlesApplicationTests {

	@Test
	void contextLoads() {
	}

}
