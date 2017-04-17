package tech.nodex.tutils2.lang;

/**
 * Created by cz on 2017-3-7.
 */
public class TypeUtils {
    public static <T>T parse(String value,Class<T> clazz) {
        if( String.class.isAssignableFrom( clazz ) ) return (T)value;
        if( Boolean.class.isAssignableFrom( clazz ) || boolean.class.isAssignableFrom( clazz )) return (T)Boolean.valueOf(value);
        if( Byte.class.isAssignableFrom( clazz )|| byte.class.isAssignableFrom( clazz )) return (T)Byte.valueOf( value );
        if( Short.class.isAssignableFrom( clazz )|| short.class.isAssignableFrom( clazz )) return (T)Short.valueOf( value );
        if( Integer.class.isAssignableFrom( clazz )|| int.class.isAssignableFrom( clazz )) return (T)Integer.valueOf( value );
        if( Long.class.isAssignableFrom( clazz )|| long.class.isAssignableFrom( clazz )) return (T)Long.valueOf( value );
        if( Float.class.isAssignableFrom( clazz )|| float.class.isAssignableFrom( clazz )) return (T)Float.valueOf( value );
        if( Double.class.isAssignableFrom( clazz )|| double.class.isAssignableFrom( clazz )) return (T)Double.valueOf( value );
        throw new IllegalArgumentException("只支持转化外基本类型");
    }
}
