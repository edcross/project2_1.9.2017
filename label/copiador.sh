#!/bin/bash

mvn clean compile assembly:single


for f in $(cat faltantes2.txt); do


	
cp $f /home/ed/Documents/Proyecto_2/label/imgFaltantes/



	NPROC=$(($NPROC+1))
    if [ "$NPROC" -ge 3 ]; then
        wait
        NPROC=0
    fi
done

echo "finish process"


