package cn.ionm.tutils2.beans;

import net.sf.morph.Morph;

import java.util.*;

/**
 * Created by 陈朝(chenzhao@rongcapital.cn) on 2016-7-12.
 */
public class BeanBuilder<T> {
    private static final Map<String,String[]> PATHS = new HashMap<String,String[]>();


    public static final Map<String,Object> NULL_OBJ_SOURCE = new HashMap<String,Object>();
    public static final List NULL_LIST_SOURCE = new ArrayList<>();
    Class<T> targetType;
    Object source;
    T target;

    public BeanBuilder(Class<T> targetType){
        this.targetType = targetType;
    }


    public static <T> BeanBuilder<T> instOf(Class<T> type){
        return new BeanBuilder(type);
    }

    public BeanBuilder<T> overwrite(Object source){
        this.target = build();
        Morph.copy(this.target,source);
        return this;
    }

    public BeanBuilder<T> overwrite(Object source,String propStr){
        this.target = build();
        for(String prop:getPaths(propStr)){
            Morph.set(this.target,prop,Morph.get(source,prop));
        }
        return this;
    }

    public BeanBuilder<T> overwriteExcept(Object source,String exceptProperties){
        this.target = build();
        String[] props = Morph.getPropertyNames(this.target);
        for(String prop:props){
            if(prop.equals(exceptProperties)){
                continue;
            }
            Morph.set(this.target,prop,Morph.get(source,prop));
        }
        return this;
    }

    public BeanBuilder<T> set(String path, Object value){
        String[] attrPath = getPaths(path);
        this.target = build();

        if(attrPath!=null){
            if(attrPath.length==1){
                Morph.set(target,attrPath[0],value);
            }else{
                Object parentObj = getPathObject(attrPath,attrPath.length-1);
                Morph.set(parentObj,attrPath[attrPath.length-1],value);
            }
        }
        return this;
    }

    public BeanBuilder<T> add(String pathStr,Object value){
        String[] path = getPaths(pathStr);
        if(path!=null){
            Object parentObj = getPathObject(path,path.length);
            Morph.add(parentObj,value);
        }
        return this;
    }

    private Object getPathObject(String[] attrPath,int depth) {
        try {
            Object parentObj = build();
            for(int i=0;i<depth;i++){
                String attrName = attrPath[i];
                Object tmpObj = Morph.get(parentObj,attrName);
                if(tmpObj==null){
                    Class type = Morph.getType(parentObj,attrName);
                    if(type.isAssignableFrom(List.class)){
                        tmpObj = Morph.convert(type,NULL_LIST_SOURCE);
                    }else if(type.isAssignableFrom(Map.class)){
                        tmpObj = Morph.convert(type,NULL_OBJ_SOURCE);
                    }else{
                        tmpObj = type.newInstance();
                    }
                    Morph.set(parentObj,attrName,tmpObj);
                }
                parentObj = tmpObj;
            }
            return parentObj;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public T build(){
        if(target!=null){
            return target;
        }
        if(source==null){
            return (T)Morph.convert(targetType,NULL_OBJ_SOURCE);
        }
        return (T)Morph.convert(targetType,source);
    }

    private String[] getPaths(String pathString){
        if(pathString==null){
            return null;
        }
        String[] path = PATHS.get(pathString);
        if(path==null){
            path = pathString.split("[\\.,]");
            PATHS.put(pathString,path);
        }
        return path;
    }
}
