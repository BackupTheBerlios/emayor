/*
 * 
 */
package org.emayor.notification.manager;

import java.util.HashMap;

import org.emayor.notification.interfaces.INotificationProducer;


/**
 * @author Maximilian Schmidt
 *
 * a static repository used to store producer references
 */
public class ProducerRepository {

		/* use a single instance */
		private static ProducerRepository singleton;
		/* a hashmap for the references */
		private HashMap producers;
		
		
		/**
		 * standard constructor
		 * initialising repository as hashmap
		 */
		private ProducerRepository() {
			/* create new hashmap */
			producers = new HashMap();
		}
		
		/**
		 * get an instance - the one and only instance allowed
		 * 
		 * @return the repository instance 
		 */
		public synchronized static ProducerRepository getInstance() {
			if (singleton == null) singleton = new ProducerRepository();
			return singleton;
		}
		
		/**
		 * add a new producer
		 * 
		 * @param prod producer to add
		 * 
		 * @return hashkey of producer 
		 */
		public synchronized Integer add(INotificationProducer prod) {
			Integer hashKey = new Integer(prod.hashCode());
			producers.put(hashKey,prod);
			return hashKey;
		}
		
		/**
		 * remove a producer
		 * 
		 * @param hashKey hashkey/id of producer
		 * 
		 * @return true when removed, false otherwise
		 */
		public synchronized boolean remove(Integer hashKey) {
			if (producers.remove(hashKey) == null) return false;
			return true;
		}
		
		/**
		 * get a producer
		 * 
		 * @param hashKey hashkey/id of producer to get
		 * 
		 * @return producer referenced by hashkey
		 */
		public synchronized INotificationProducer get(Integer hashKey) {
			return (INotificationProducer) producers.get(hashKey);
		}
	
}
