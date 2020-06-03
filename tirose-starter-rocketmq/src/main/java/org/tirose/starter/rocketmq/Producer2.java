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
public class Producer2 {

	public static void main(String[] args)throws Exception {

		DefaultMQProducer producer = new DefaultMQProducer("xoxogp1");

		producer.setNamesrvAddr("192.168.150.113:9876");


		// �ص�



		producer.start();


		//����
		producer.setRetryTimesWhenSendFailed(2);
		producer.send(new Message());
		producer.setRetryAnotherBrokerWhenNotStoreOK(true);

	//	producer.shutdown();
		System.out.println("�Ѿ�ͣ��");

	}
}
