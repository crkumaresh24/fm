# Prerequestie - MySQL Instal las docker
docker run --name mysql --restart=always -e MYSQL_ROOT_PASSWORD=mysql -p 3306:3306 -d mysql

# Run as local ddocker container - Individual Steps
./gradlew clean build
docker build -t platform/fm .
docker stop platform-fm && docker rm platform-fm
docker run -dit --add-host=host.docker.internal:172.17.0.1 --name platform-fm --restart=always -p 9091:8080 -e "MYSQL_HOST=172.17.0.1" -e "MYSQL_USER=root" -e "MYSQL_PASSWORD=mysql" -e "AUTH_MANAGER_URL=http://172.17.0.1:9090" -v $(pwd)/uploads:/uploads -e "ROOT_PATH=/uploads" platform/fm

# Run as local ddocker container - Single Command
docker stop platform-fm && docker rm platform-fm
./gradlew clean build && docker build -t platform/fm . && docker run -dit --add-host=host.docker.internal:172.17.0.1 --name platform-fm --restart=always -p 9091:8080 -e "MYSQL_HOST=172.17.0.1" -e "MYSQL_USER=root" -e "MYSQL_PASSWORD=mysql" -e "AUTH_MANAGER_URL=http://172.17.0.1:9090" -v $(pwd)/uploads:/uploads -e "ROOT_PATH=/uploads" platform/fm