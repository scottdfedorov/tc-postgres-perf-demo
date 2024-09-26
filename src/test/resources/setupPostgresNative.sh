#!/bin/bash
brew install postgresql@15
brew link postgresql@15 --force
brew services start postgresql@15
createuser -s test 2> /dev/null
#DROP EXISTING TO RESET FOR RE-RUNS
dropdb --if-exists test
createdb test
psql -U test -c "CREATE TABLE demo(id serial primary key, uuid text);"
export POSTGRES_NATIVE_URL=jdbc:postgresql://localhost:5432/test
export POSTGRES_NATIVE_USERNAME=test
./mvnw test