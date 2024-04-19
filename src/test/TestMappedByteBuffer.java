package test;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/** 
* <p>Description: �ڴ�ӳ��</p>
* @author <a href="mailto:zhangzhen2@yonghui.cn">zhangzhen2</a>
* @date 2021��2��23��
* @version 1.0
* @since JDK 1.8.0_271  
*/
public class TestMappedByteBuffer {
	public static void main(String[] args)throws Exception {
        int length = 0x00006;//һ��Byteռ1B
        try{
        	FileChannel channel = FileChannel.open(Paths.get("D:\\out.txt"),
                    StandardOpenOption.READ, StandardOpenOption.WRITE);
        	
        	//mmap �ڴ�ӳ��
            MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
            for(int i=0;i<length;i++) {
                mapBuffer.put((byte)'a');
            }
            System.out.println("mapBuffer:"+mapBuffer.toString());
            mapBuffer.flip();
            for(int i = 0;i<3;i++) {
                //������һ�����ʣ����ǲ��ı�positionλ��
                System.out.println(mapBuffer.toString()+":"+mapBuffer.get(i));
            }
            while (mapBuffer.hasRemaining()){
                System.out.println(mapBuffer.toString()+":"+mapBuffer.get());
            }
            //���򻺳���
            
            //sendfile  DMA
            SocketChannel socketChannel = null;
            channel.transferTo(0, 6,socketChannel);
            channel.close();
	    }catch(Exception e){
	    	System.err.print(e);
	    }
    }
}
