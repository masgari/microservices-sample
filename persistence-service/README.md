# Persistence service

This service has no dependency to any other micro-service.

Current implementation uses `Hazelcast` as persistence backend, so multiple instances of this micro-service can be started.

To run the server with gradle type `../gradlew run`
To build the app and run multiple instances type `../gradlew iA` and then go to `build/install/persistence-service/bin/`