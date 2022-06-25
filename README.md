English | [简体中文](./README.zh_CN.md) 
### Project Structure
```txt
Kotlin + SpringBoot + Netty + protobuf + Spring security JWT + slf4j
Kafka as a message bus
```

### Catalog Structure

```txt
src/main/
├──kotlin/**/chatroom
│   ├── config/               // Component Configuration(jwt)
│   ├── message/              // Specific Message Classes(protobuf)
│   ├── protocol/             // Codecs
│   ├── server/               // Service & Business
│   │   ├── handler/          // Message Processor
│   │   ├── message_bus/      // message_bus(Message Synchronization)
│   │   ├── service/          // Business
│   │   ├── session/          // Sessions, room management
│   │   └── ./*.kt            // netty service start + message processor registration
│   ├── util/                 // Toolkit
│   └── Application.kt        // Program Launch Entrance
└── resources/              
    ├── static/               // Static resource files
    ├── templates/            // Page file
    ├── application.yml       // Configuration file
    └── log4j2.xml            // Log Configuration

```

