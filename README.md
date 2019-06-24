# Welcome to SpringBootFirst 👋

![version](https://img.shields.io/badge/version-0.99-blue.svg?cacheSeconds=2592000)

> Spring boot and Vue

## Install

```sh
gradlew
```

## Author

👤 **THELOSTSOUL**

* Github: [@thelostsoul5](https://github.com/thelostsoul5)

## Show your support

Give a ⭐️ if this project helped you!

##  Remarks

更改Bean org.apache.ibatis.session.SqlSessionFactory 的构建的时候，要注意不影响Mybatis配置的加载，详见 xyz.thelostsoul.config.MybatisAutoConfig ， 因之前没有注意，导致Mybatis拦截器的JavaConfig一直没生效。