package org.tirose.starter.rocketmq;

import java.util.ArrayList;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * ��Ϣ������
 * @author һ����
 *
 */
public class Producer {

	public static void main(String[] args)throws Exception {

		TransactionMQProducer producer = new TransactionMQProducer("xoxogp1");

		producer.setNamesrvAddr("192.168.150.113:9876");

		// �ص�

		producer.setTransactionListener(new TransactionListener() {

			public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
				// ִ�� ��������


				System.out.println("=====executeLocalTransaction");
				System.out.println("msg:" + new String(msg.getBody()));
				System.out.println("msg:" + msg.getTransactionId());

				/**
				 *
				 * ���񷽷� д����
				 *
				 * ͬ��ִ��
				 * -----a----
				 * a �ύע����Ϣ()  ;
				 * b д�����ݿ�();
				 * c ���û�() -> ����Ϣ;
				 *
				 * ������Ϣ��Ӧ�ó����ǲ����ʺϷ��Ͷ����ϢҪ��֤ͬʱ�ɹ���ʧ�ܣ�
				 *
				 * ----b----
				 *
				 * ��ȡ��Ϣ
				 * �õ����û�����Ϣ ������
				 *
				 *
				 * ��������һ��commit����ʧ�ܣ�
				 * ҵ������� �쳣�� Ȼ��broker�ȳ�ʱ�ص���� ����ʧ�ܣ����ӵ���������
				 *
				 *
				 * �¶�����������Ϣ ���Կۿ
					��������ɹ����������ۿ� ���������ʧ�ܣ��򲻿ۿ  �������Ϣ�ۿ�ʧ�� ��ع�����   ����������

					mysql ������


				 */
				try {

					// ҵ��
				} catch (Exception e) {
					//
					return LocalTransactionState.ROLLBACK_MESSAGE;
				}
				// ��������ȥ������ ����
				return LocalTransactionState.COMMIT_MESSAGE;
			}

			public LocalTransactionState checkLocalTransaction(MessageExt msg) {
				// Broker �� �ص� ���������

				System.out.println("=====checkLocalTransaction");
				System.out.println("msg:" + new String(msg.getBody()));
				System.out.println("msg:" + msg.getTransactionId());




				// ����ִ�гɹ�
				return LocalTransactionState.COMMIT_MESSAGE;
				// �Ȼ��
		//		return LocalTransactionState.UNKNOW;
				// �ع���Ϣ
		//		return LocalTransactionState.ROLLBACK_MESSAGE;
			}
		});



		producer.start();


		TransactionSendResult sendResult = producer.sendMessageInTransaction(new Message("xxoo003", "���ԣ�����������Ϣ".getBytes()), null);

		System.out.println("sendResult:" + sendResult);

	//	producer.shutdown();
		System.out.println("�Ѿ�ͣ��");

	}
}
