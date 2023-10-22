package cn.beta.platform.context;


import cn.beta.platform.enums.DataSourceType;

public class DatabaseContextHolder {
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>();
    public static void setDatabaseType(DataSourceType type){
        if(type ==null) throw new NullPointerException();
        contextHolder.set(type);
    }
    public static DataSourceType getDatabaseType(){
        return contextHolder.get() ==null ?DataSourceType.READ:contextHolder.get();
    }
    public static void clear(){
        contextHolder.remove();
    }
}
