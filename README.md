# Reggie-WaiMai

PS：说明这个是对于我二次对黑马瑞吉外卖后端部分进行的二次修改的说明，前端部分采用了vue-cil进行二次开发，可以对学习vue的小伙伴提供一些帮助，全部vue开发，具体请看另一个部分url：https://github.com/cxkbalq/Reggie-Vue-Merchant-Portal



## 1.对于原本黑马瑞吉外卖修改进行修改部分的说明

    采取了主流的jwt登录验证，添加了对前端请求信息的完整性验证，分别对于电脑端安卓端进行
    了分别处理，如果发现信息不完整，会返回过期登录，重新登录获得完整信息，具体代码
    请查看：
    utils/JwtUtils 部分，对其进行了注释。
    
    添加了门店部分的接口，适配对应的前端，以及对于其三种账户进行操作的限制，主要功能，对于
    用户下单在不同店面进行下单操作，进行下单金额的统计，实现了基于springBoot注解
    @Scheduled实现的每日对于当天门店营业额的统计得更新，更加符合商家对于收益的查看，
    具体部分请查看：
    
    utils/DatabaseUpdater 自动更新
    
    controller/MendianController.java 门店部分的接口
    
    controller/OrdersController.java 订单金额的统计

效果图：

![](C:\Users\29256\Pictures\Screenshots\屏幕截图%202023-11-27%20184348.png)

添加了账户权限功能，总共有三种权限

    root账号 ：具有最高权限，可以进行如何操作，相当于现实中的老板       --权限级别1
    
    管理账户：1.可以进行门店得员工得增删改查等                       --权限级别2
             2.可以对自己门店进行下线上线操作
             3.可以对菜品套餐等进行增删改查操作，相当现实中一个门店的管理人员
    
    普通账户：1.只具有查看功能，不具有任何操作功能，                 --权限级别3
               相当于工作人员                               

其他部分进行的修改不进行过多叙述，自行查看，对于数据库的修改请自行执行sql/init.sql查看，这个代码一要对应我的前端，以经不适配黑马原有的前端，会报错

项目体验地址：http://123.60.129.35/

root账户：admin   123456
管理账户：cxkbalq  123456
普通账户：daw 123456

root账户：cs  123456 这个是其他root账号，对应现实中一个系统给多个老板使用

由于这个第一个项目，代码可能存在很多bug，以及书写不规范，请谅解，这个需要慢慢养成

## 2.docker项目部署

##### 1.基本条件：

请你帮保证下面所有文件在同一目录下（项目里全部提供了docker文件里）

/imge           挂载图片数据（直接将我项目下的/imge导入即可，目录下以存在测试图片），

/reggie_waimai-0.0.1-SNAPSHOT.jar

/Dockerfile

/docker-compose.yml

/init.sql    初始化数据库（已有测试数据，如需原始数据库，请自行构建）

docker环境自行安装

如果修改进行端口修改，请自行修改docker-compose.yml文件 

nginx对外端口88：80

mysql对外端口：3308：3306

java_jar对外端口 : 8082: 8080

redis对外端口：6380：6379

##### 2.执行

拉取项目

```
git clone https://github.com/cxkbalq/reggie-waimai.git
```

进入到docker目录（我已经提供了全部文件，理论上不会缺少文件，直接进入即可)

```
cd reggie-waimai/docker
```

启动项目

```
sudo docker compose up -d
```

查看是否运行

```
docker ps -a
```

欧克，完成！！！！
