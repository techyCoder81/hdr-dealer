cd ..
./gradlew clean
./gradlew build
cd test
./extract.sh
./test.sh
