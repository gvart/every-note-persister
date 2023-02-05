build-EveryNotePersisterFunction:
	rm -rf ./build
	./gradlew clean
	./gradlew nativeCompile
	echo '#!/bin/sh\nset -euo pipefail\ncd ${LAMBDA_TASK_ROOT:-.}\n./application' > ./build/bootstrap
	chmod 777 ./build/bootstrap
	cp ./build/native/nativeCompile/application $(ARTIFACTS_DIR)
	cp ./build/bootstrap $(ARTIFACTS_DIR)