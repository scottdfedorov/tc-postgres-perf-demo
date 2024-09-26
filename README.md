# testcontainers-java-repro

This repo is for illustrating the performance impact of running testcontainers/docker postgres vs native installed postgres
on aarch64 MacOS machines. 


You can try running this executable with `./src/test/resources/setupPostgresNative.sh` from the root of the project.
(Note: The script needs to be made executable. `chmod 755 ./src/test/resources/setupPostgresNative.sh`)