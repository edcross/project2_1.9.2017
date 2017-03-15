#!/bin/bash


for f in $(cat sequence4.txt); do


	
	 java -jar makeLabelDescriptor.jar $f /home/ed/Documents/VidriloTags/Sequence1/Vidrilo_Sequence1_GoogleCloudVision



	NPROC=$(($NPROC+1))
    if [ "$NPROC" -ge 3 ]; then
        wait
        NPROC=0
    fi
done

echo "finish process"


