package com.paic.jrkj.tk.tools.core;

import java.util.HashMap;
import java.util.Map;

public class MinSingletonHolder extends BeanHolder {
	
	private final Map<BeanKey, Object> singletonMap = new HashMap<BeanKey, Object>();
	
	private final long updateInterval =5*60*1000;
	
	private volatile long lstUpdateTime;
	
	public MinSingletonHolder() {
		
	}

	@Override
	public <T> T getBean(Object id, ObjectCreator<T> creator) {
		checkUpdate();
		BeanKey key = new BeanKey(id,creator.getClass().getName());
        T instance = (T) singletonMap.get(key);
        if (instance == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("singleton [" + key + "] init...");
            }
            synchronized (singletonMap) {
                instance = (T)singletonMap.get(key);
                if (instance == null) {
                    instance = creator.create();
                    if(instance!=null){
                        singletonMap.put(key, instance);
                    }
                }
            }
        }
        return instance;
	}
	
	private synchronized void checkUpdate() {
        if (System.currentTimeMillis() - lstUpdateTime >= updateInterval) {
        	singletonMap.clear();
            lstUpdateTime = System.currentTimeMillis();
        }
    }

	
	

	@Override
	public <T> T remove(Object id, ObjectCreator<T> creator) {
		return (T) singletonMap.remove(new BeanKey(id,creator.getClass().getName()));
	}

}
