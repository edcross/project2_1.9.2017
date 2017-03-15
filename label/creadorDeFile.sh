#!/bin/bash


for f in $(cat fileFaltantes.txt); do


	
touch /home/ed/Documents/Proyecto_2/label/imgSeq5/$f  



	NPROC=$(($NPROC+1))
    if [ "$NPROC" -ge 3 ]; then
        wait
        NPROC=0
    fi
done

echo "finish process"


