package com.paic.jrkj.tk.tools.msg.field;

import com.paic.jrkj.tk.util.HexUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 *
 * @author <a href="mailto:zhangzhen405">zhangzhen405</a>
 * @version $Revision: 1.0 $ $Date: 2013-11-12 18:15:36 $
 * @serial 1.0
 * @since JDK1.6.0_27 2013-11-12 18:15:36
 */
public class FieldCatalogy {

    private Field[] fieldArray;
    private Map<String, Field> fieldMap = new HashMap<String, Field>(32);

    public FieldCatalogy() {
    }

    public Field getField(String name) {
        return fieldMap.get(name);
    }

    public Field[] getAllField() {
        return fieldArray;
    }

    public void setField(Field field) {
        if (!fieldMap.containsKey(field.getName())) {
            throw new IllegalArgumentException("field name [" + field.getName() + "] not exist!");
        }
        fieldMap.put(field.getName(), field);
        for (int i = 0; i < fieldArray.length; i++) {
            if (fieldArray[i].getName().equals(field.getName())) {
                fieldArray[i] = field;
                break;
            }
        }
    }

    public void updateField(String name,byte[] data){
        Field field = getField(name);
        if(field==null){
            throw new IllegalArgumentException("field ["+name+"] not found!");
        }
        field.encape(0,data);
    }

    public void addField(Field field) {
        if (fieldMap.containsKey(field.getName())) {
            throw new IllegalArgumentException("field name [" + field.getName() + "] is exist!");
        }
        fieldMap.put(field.getName(), field);
        if (fieldArray == null) {
            fieldArray = new Field[]{field};
            return;
        }
        Field[] newFieldArray = new Field[fieldArray.length + 1];
        System.arraycopy(fieldArray, 0, newFieldArray, 0, fieldArray.length);
        newFieldArray[fieldArray.length] = field;
        this.fieldArray = newFieldArray;
    }

    public void appendField(Field field, int fromIndex){
    	if (fieldMap.containsKey(field.getName())) {
            throw new IllegalArgumentException("field name [" + field.getName() + "] is exist!");
        }else if (fieldArray == null) {
            throw new IllegalArgumentException("field array is empty!");
        }
        fieldMap.put(field.getName(), field);
        if(fromIndex==0){
            Field[] newFieldArray = new Field[fieldArray.length + 1];
            newFieldArray[0] = field;
            System.arraycopy(fieldArray, 0, newFieldArray, 1, fieldArray.length);
            return;
        }
        Field[] newFieldArray = new Field[fieldArray.length + 1];
        System.arraycopy(fieldArray, 0, newFieldArray, 0, fromIndex);
        newFieldArray[fromIndex] = field;
        System.arraycopy(fieldArray, fromIndex, newFieldArray, fromIndex + 1, fieldArray.length - fromIndex);
        this.fieldArray = newFieldArray;
    }
    
    public byte[] getData(int index){
        return fieldArray[index].getData();
    }

    public byte[] getData(String name){
        Field field = getField(name);
        if(field==null){
            throw new IllegalArgumentException("field ["+name+"] not found!");
        }
        return field.getData();
    }

    public byte[] toByteArray(int index){
        return fieldArray[index].toByteArray();
    }

    public byte[] toByteArray(String name){
        Field field = getField(name);
        if(field==null){
            throw new IllegalArgumentException("field ["+name+"] not found!");
        }
        return field.toByteArray();
    }

    public byte[] toByteArray(int fromIndex,int endIndex){
        if(fromIndex<0 || endIndex>fieldArray.length-1){
            throw new ArrayIndexOutOfBoundsException("");
        } else if(fromIndex>endIndex){
            throw new IllegalArgumentException("");
        }
        if(fromIndex==endIndex){
            return toByteArray(fromIndex);
        }
        int length = 0;
        Field field;
        for (int i=fromIndex;i<=endIndex;i++) {
            field = fieldArray[i];
            if (field == null) continue;
            length += field.length();
        }
        byte[] result = new byte[length];
        int index = 0;
        byte[] data;
        for (int i=fromIndex;i<=endIndex;i++) {
            field = fieldArray[i];
            if (field == null) continue;
            data = field.toByteArray();
            System.arraycopy(data, 0, result, index, data.length);
            index += data.length;
        }
        return result;
    }

    public int length(){
        return fieldArray.length;
    }

    public byte[] toByteArray() {
        return toByteArray(0,fieldArray.length-1);
    }

    public void encape(byte[] bisdata) {
        int fromIndex = 0;
        for (int i = 0; i < fieldArray.length; i++) {
            fromIndex = fieldArray[i].encape(fromIndex, bisdata);
        }
    }
    
    public String toJSONString(){
    	StringBuilder builder = new StringBuilder("{");
        String data;
        for(Field field : fieldArray){
            if(field.isLogEnabled()){
                data = field.charset()==null? HexUtil.encode(field.getData()) : new String(field.getData(),field.charset());
                builder.append("\"").append(field.getName()).append("\"");
                builder.append(":");
                builder.append("\"").append(data).append("\"").append(",");
            }
    	}
    	builder.delete(builder.length() - 1, builder.length());
    	builder.append("}");
    	return builder.toString();
    }

    public String toString(){
        return toJSONString();
    }
    
    public String toString(String split) {
        StringBuilder builder = new StringBuilder("{").append(split);
        for (Field field : fieldArray) {
            if(field.isLogEnabled()){
                builder.append(field).append(",").append(split);
            }
        }
        builder.append("}");
        return builder.toString();
    }

}
