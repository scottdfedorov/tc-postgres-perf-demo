# testcontainers-java-repro

This repo is for illustrating the performance impact of running testcontainers/docker postgres vs native installed postgres
on aarch64 MacOS machines. 


To setup native, you need to install a postgres database and create the environment variables. I have created a helper 
script to do that with brew on macos in the resources folder. 