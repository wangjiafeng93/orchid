package com.thunisoft.orchid;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thunisoft.orchid.mapper.AjxxMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrchidApplicationTests {
	@Autowired
	public AjxxMapper ajxxMapper;

	@Test
	public void contextLoads() {
	}

}
