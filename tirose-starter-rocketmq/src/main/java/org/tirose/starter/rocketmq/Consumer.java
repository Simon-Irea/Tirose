package org.tirose.starter.rocketmq;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class Consumer {

	public static void main(String[] args)throws Exception {

		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxoocsm");

		consumer.setNamesrvAddr("192.168.150.113:9876");


		/**
		 * a()
		 * c()
		 * d()
		 * b -> �� rocketmq д��һ����Ϣ()
		 * rollback()
		 *
		 *
		 *
		 */

		// ÿ��consumer ��עһ��topic

		// topic ��ע����Ϣ�ĵ�ַ
		// ������ * ��ʾ������
		consumer.subscribe("myTopic002", "*");

		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

				for (MessageExt msg : msgs) {

					System.out.println(new String(msg.getBody()));;
				}
				// Ĭ������� ������Ϣֻ�ᱻ һ��consumer ���ѵ� ��Ե�
				// message ״̬�޸�
				// ack
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		/**
		 * ������Ϣ��ʱ���������ⳬʱ��һ��׼ʱ����ᷢ�͵ڶ����������Ļ���������һ������Ϣ��

		 */

		consumer.setConsumeTimeout(System.currentTimeMillis());
		// ������ ��ͻ�����ȣ�����ô��
		consumer.setMessageModel(MessageModel.CLUSTERING);
		consumer.start();

		// ��Ⱥ -> һ��consumer
		// �㲥

		System.out.println("Consumer 02 start...");
	}
}
