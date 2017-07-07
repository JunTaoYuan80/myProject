1、Java提供的序列化反序列化机制，实现Serialize接口
a.反序列化对象跟序列化对象的字段、类路径 不一致，则反序列化失败
b.ObjectOutputStream按照默认方式序列化，这种序列化方式仅仅对对象的非transient的实例变量进行序列化，
	不会序列化对象的transient的实例变量，也不会序列化静态变量

https://developers.google.com/protocol-buffers/docs/javatutorial
2、protobuf协议的序列化反序列化机制
a.反序列化，接受到对方的字节流 生成对应对象，
   即使反序列化对象的字段比序列化对象的字段多，只要不是required类型的字段，也不会报错