# testcontainers-java-repro

This repo is for illustrating the performance impact of running testcontainers/docker postgres vs native installed postgres
on aarch64 MacOS machines. 


You can try running this executable with `./src/test/resources/setupPostgresNative.sh` from the root of the project.
(Note: The script needs to be made executable. `chmod 755 ./src/test/resources/setupPostgresNative.sh`)

Full repro steps, if the script doesn't work or you want to handle manually:
* Install Postgres natively to your computer.
* Create a database and user (default for both: `test`), and create the table `demo` as defined in the `init.sql` in the resources folder.
* Set the environment variables for the jdbc url and username, if different than default. 
* Run the command `./mvnw test`

Representative sample:
```sql
14:06:09.833 [main] INFO  org.testcontainers.repro.ReproExampleTest - STARTING RUN AGAINST DOCKER
14:07:56.170 [main] INFO  org.testcontainers.repro.ReproExampleTest - FINISHED RUN AGAINST DOCKER, TOOK 106336 MS
14:07:56.171 [main] INFO  org.testcontainers.repro.ReproExampleTest - STARTING RUN AGAINST NATIVE
14:08:38.334 [main] INFO  org.testcontainers.repro.ReproExampleTest - FINISHED RUN AGAINST NATIVE, TOOK 42163 MS
```