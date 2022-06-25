[English](./README.md) | 简体中文
### 项目架构
```txt
Kotlin + SpringBoot + Netty + protobuf + Spring security JWT + slf4j
Kafka 做消息总线
```

### 目录结构

```txt
src/main/
├──kotlin/**/chatroom
│   ├── config/               // 配置(目前有jwt)
│   ├── message/              // 具体的消息类(protobuf)
│   ├── protocol/             // 编码解码
│   ├── server/               // 服务与业务
│   │   ├── handler/          // 消息处理器
│   │   ├── message_bus/      // 消息总线(消息同步)
│   │   ├── service/          // 业务处理
│   │   ├── session/          // 会话、房间管理
│   │   └── ./*.kt            // netty服务启动 + 消息处理器注册
│   ├── util/                 // 工具包
│   └── Application.kt        // 程序启动入口
└── resources/              
    ├── static/               // 静态资源文件
    ├── templates/            // 页面文件
    ├── application.yml       // 配置文件
    └── log4j2.xml            // 日志配置

```

