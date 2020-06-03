package org.tirose.starter.rocketmq;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class Consumer3 {

	public static void main(String[] args)throws Exception {

		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("g02");

		consumer.setNamesrvAddr("192.168.150.113:9876");

		consumer.subscribe("xxoo007", "*");



		/**
		 *
		 *
		 *
		 *
		 * MessageListenerConcurrently   �������� / ������߳�
		 *
		 *
		 */
//		consumer.registerMessageListener(new MessageListenerConcurrently() {
//
//			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//
//				for (MessageExt msg : msgs) {
//
//					System.out.println(new String(msg.getBody()));;
//				}
//				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//			}
//		});


		/*
		 *
		 * MessageListenerOrderly ˳������ ,��һ��queue ����һ���̣߳����queue ������߳�
		 *
		 *
		 */


		//
		// ����������߳���
	//	consumer.setConsumeThreadMax(consumeThreadMax);
		//  ��С�߳���
	//	consumer.setConsumeThreadMin(consumeThreadMin);
		consumer.registerMessageListener(new MessageListenerOrderly() {

			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				for (MessageExt msg : msgs) {

				System.out.println(new String(msg.getBody()) + " Thread:" + Thread.currentThread().getName());;
			}
			return ConsumeOrderlyStatus.SUCCESS;
			}
		});

		consumer.start();
		System.out.println("Consumer g02 start...");
	}
}
