#!/bin/bash
brew install postgresql@15
createuser -s test 2> /dev/null
#DROP EXISTING TO RESET FOR RE-RUNS
dropdb --if-exists test
createdb test
export POSTGRES_NATIVE_URL=jdbc:postgresql://localhost:5432/test
export POSTGRES_NATIVE_USERNAME=test