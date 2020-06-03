package org.tirose.starter.rocketmq;

import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByRandom;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * ��Ϣ������
 * @author һ����
 *
 */
public class Producer4 {

	public static void main(String[] args)throws Exception {

		DefaultMQProducer producer = new DefaultMQProducer("pgp01");

		producer.setNamesrvAddr("192.168.150.113:9876");


		producer.start();


		for (int i = 0; i < 20; i++) {

			Message message = new Message("xxoo007", ("hi!" + i).getBytes());

			producer.send(
					// Ҫ����������Ϣ
					message,
					// queue ѡ���� ���� topic�е��ĸ�queueȥд��Ϣ
					new MessageQueueSelector() {
						// �ֶ� ѡ��һ��queue
						public MessageQueue select(
								// ��ǰtopic �������������queue
								List<MessageQueue> mqs
								,
								// ����Ҫ����������Ϣ
								Message msg

								// ��Ӧ�� send���� ��� args
								, Object arg) {
							// TODO Auto-generated method stub

							// ��̶���һ��queue��д��Ϣ
							MessageQueue queue = mqs.get(0);
							// ѡ�õ�queue
							return queue;
						}
					},
					// �Զ������
					0, 2000);
		}

	//	producer.shutdown();
		System.out.println("�Ѿ�ͣ��");

	}
}
