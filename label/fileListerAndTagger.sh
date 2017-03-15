#!/bin/bash

mvn clean compile assembly:single


for f in $(cat faltantes2.txt); do


	
	 java -cp target/vision-label-1.0-SNAPSHOT-jar-with-dependencies.jar com.google.cloud.vision.samples.label.LabelApp $f 



	NPROC=$(($NPROC+1))
    if [ "$NPROC" -ge 3 ]; then
        wait
        NPROC=0
    fi
done

echo "finish process"


