# net-prog-dining-hall
Server that imitates a diningh hall in a restaurant. It creates orders and sends them to the [Kitchen](https://github.com/pelm3wka/net-prog-kitchen)

### Prerequisites:
  * JDK 1.8
  * Maven
  * Docker

### To launch the project:
  1. Clone the code;
  2. Create a package:  ``` mvn clean package ```
  3. Build the docker image:  ``` docker build --tag=dining-hall:latest . ```
  4. Launch the docker image: ``` docker run -p 9090:9090 dining-hall:latest ```
  
