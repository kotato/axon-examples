# Axon examples
CI: ![](https://travis-ci.org/kotato/axon-examples.svg?branch=master)

## Starting the project locally
In order to start this project locally, you must run mysql first. 
You can choose how you want do it (install it locally and then run it or just run a mysql docker).
Once this is done, then you'll have to create databases manually:

```
CREATE DATABASE IF NOT EXISTS ecommerce_write_model;
CREATE DATABASE IF NOT EXISTS ecommerce_read_model;
``` 

Finally, we are using default mysql user (root) & password (without password). 
If your mysql has a different user & password, just change it at application.yml configuration.

## About the project

If you had any kind of problem running the project locally, you can contact us via github or in the codely.tv course ;)
We'll fix the issue as soon as possible.