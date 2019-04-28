package com.zhz.idea.plugin.plus.domain.enums;


public enum ErrorCodeEnums {

    /**
     *
     */
    /** 无异常 */
    success("0000","成功"),
    ;


     ;
     private String code;
     private String desc;
     ErrorCodeEnums(String code,String desc){
         this.desc = desc;
         this.code = code;
     }
     public static ErrorCodeEnums getByCode(String code){
         for (ErrorCodeEnums value : ErrorCodeEnums.values()) {
             if (value.getCode().equals(code)){
                 return value;
             }
         }
         return success;
     }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
