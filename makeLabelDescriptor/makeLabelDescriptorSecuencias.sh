#!/bin/bash
cont=5
until [ $cont -lt 1 ];
do

for f in $(cat secuencia$cont.txt); do


	
	 java -jar makeLabelDescriptor.jar $f /home/ed/Documents/VidriloTags/Sequence$cont/Vidrilo_Sequence$cont.GoogleCloudVision



	NPROC=$(($NPROC+1))
    if [ "$NPROC" -ge 3 ]; then
        wait
        NPROC=0
    fi
    
done

     let cont=cont-1 
done

echo "finish process"


