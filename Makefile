build-EveryNotePersisterFunction:
	rm -rf ./build
	./gradlew clean
	./gradlew nativeCompile
	echo '#!/bin/sh' > ./build/bootstrap
	echo 'set -euo pipefail' >> ./build/bootstrap
	echo 'cd ${LAMBDA_TASK_ROOT:-.}' >> ./build/bootstrap
	echo './application' >> ./build/bootstrap
	chmod 777 ./build/bootstrap
	cp ./build/native/nativeCompile/application $(ARTIFACTS_DIR)
	cp ./build/bootstrap $(ARTIFACTS_DIR)