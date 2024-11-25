package com.farm.core.test;

import com.farm.core.inter.domain.Message;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;

public class MessageTest {

	public static void main(String[] args) {
		System.out.println(new Message(TYPE_KEY.comment_know).getId());
		System.out.println(new Message(TYPE_KEY.comment_know).getId());
	}

}
