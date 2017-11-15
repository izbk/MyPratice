#HttpNettyCalculator
netty5的学习示例

#使用方法
直接运行CalcServer
或者打包后运行
mvn package 打包jar文件
运行jar文件，默认开启8080端口

#请求地址

求第n为斐波那契数
http://localhost:8080/fibonacci?n=41

求s到e的累加和
http://localhost:8080/accumulation?s=1&e=100