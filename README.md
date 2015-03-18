# microservices-sample
Ratpack based microservices sample with two service:

*  User Service: A simple service for adding and connecting users.
*  Persistence Service: For saving entities and finding them by id.

# Building

Build user-service:
```bash
cd user-service
../gradlew shadowJar
```

Build persistence-service:
```bash
cd user-service
../gradlew shadowJar
```


# Quick test
After building the services, run `user-service` and `persistence-service` in two separate terminals:

User-service:
```bash
cd user-service/build/libs/
java -jar user-service-0.0.1-all.jar
```

Persistence-service:
```bash
cd persistence-service/build/libs/
java -jar persistence-service-0.0.1-all.jar
```
Note: Persistence-service must be run *after* user-service to be discovered automatically.

Create first user:
```bash
curl -X POST -H "Content-Type: application/json" -d '{"name": "user1"}' http://localhost:2000/v1/users
# or with httpie
echo '{"name": "user1"}' | http post :2000/v1/users
```

Create second user:
```bash
curl -X POST -H "Content-Type: application/json" -d '{"name": "user2"}' http://localhost:2000/v1/users
#or
echo '{"name": "user2"}' | http post :2000/v1/users
```

Connect to user to each other:
```bash
curl -X POST http://localhost:2000/v1/users/<user1-id>/connect?to=<user2-id>
#or
http post :2000/v1/users/<user1-id>/connect?to<user2-id>
```

Get users connections:
```bash
curl http://localhost:2000/v1/users/<user1-id>/connections
#or
http :2000/v1/users/<user1-id>/connections
```


# Issues

*Hazelcast* nodes can not detect each other on two different hosts.


# Modules

* [user-service](user-service/README.md)
* [persistence-service](persistence-service/README.md)
* [service-discovery](service-discovery/README.md)
* [hazelcast-discovery](hazelcast-discovery/README.md)
* [service-api](service-api/README.md)
* [base-server](base-server/README.md)

# Running tests

Unit tests:

`./gradlew test`

Integration tests:

`./gradlew intT`
