package com.thunisoft.orchid;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thunisoft.orchid.bean.pojo.Orchid;
import com.thunisoft.orchid.repository.OrchidRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrchidApplicationTests {

	@Resource
	private OrchidRepository orchidRepository;

	@Test
	public void contextLoads() {
		Orchid orchid = orchidRepository.findByContentAndType("李径桃蹊次第开，穠香百和袭人来。", "兰花类型");
		System.out.println(orchid.getName()+"----"+orchid.getContent());
		String maxDateOrchid = orchidRepository.findMaxDateOrchid();
		System.out.println("----"+maxDateOrchid);
		// 查询并断言查询结果的正确性.
		Assert.assertEquals("幽兰花2", maxDateOrchid);
	}

}
