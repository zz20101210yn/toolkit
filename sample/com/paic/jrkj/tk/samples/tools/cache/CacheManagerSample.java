package com.paic.jrkj.tk.samples.tools.cache;

import com.paic.jrkj.tk.tools.cache.MinTimeCache;
import com.paic.jrkj.tk.tools.cache.CacheManager;

/**
 * <p/>
 *
 * @author <a href="mailto:wangqilianzeg848@pingan.com.cn">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-4 18:01:25 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-4 18:01:25
 */
public class CacheManagerSample {

    public static void main(String[] args){
        @SuppressWarnings("unchecked")
        MinTimeCache<String,String> cache =  CacheManager.getCache(MinTimeCache.class,"id") ;
        cache.put("id","dddd");
        System.out.println(cache.get("id"));
    }

}
