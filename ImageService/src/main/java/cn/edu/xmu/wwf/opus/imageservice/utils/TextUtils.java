package cn.edu.xmu.wwf.opus.imageservice.utils;

import java.util.Objects;
import java.util.UUID;

public class TextUtils
{
    // 根据文件名，生成UUID作为新的文件名
    public static String generateFileName(String filename)
    {
        Objects.requireNonNull(filename,"文件名为null");
        int index=filename.lastIndexOf('.');
        if(filename.isBlank() || index==-1)
        {
            throw new IllegalArgumentException("文件名不合法");
        }
        String suffix=filename.substring(index);
        String uuid= UUID.randomUUID().toString().replaceAll("-","");
        return uuid+suffix;
    }

    // 从图片URL中提取Key
    // http://quwf2a7om.hn-bkt.clouddn.com/xxx/11c85acc16f24361ba97999185fb0469.jpeg
    public static String getKey(String imageUrl)
    {
        if(imageUrl==null || imageUrl.trim().isEmpty())
        {
            return null;
        }
        return imageUrl.substring(imageUrl.indexOf(".com")+5);
    }
}

