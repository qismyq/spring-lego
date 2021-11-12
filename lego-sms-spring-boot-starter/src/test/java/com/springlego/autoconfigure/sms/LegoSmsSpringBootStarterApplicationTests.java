package com.springlego.autoconfigure.sms;

import com.springlego.autoconfigure.sms.service.ISmsContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LegoSmsSpringBootStarterApplicationTests {

	@Autowired
	private ISmsContentService smsContentService;

	@Test
	void contextLoads() throws Exception{
		smsContentService.send("13007549295",2);
	}

}
