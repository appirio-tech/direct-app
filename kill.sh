#!/bin/bash

#
# kill the currently running compiler process on this machine
# creates backups of all compiler logs and puts the backups
# in the backup_logs directory with the date appended.
#

# find the processes

echo kill -9 jboss and backup logs?
read ANSWER
if [[ $ANSWER = 'Y' || $ANSWER = 'y' || $ANSWER = 'yes' || $ANSWER = 'Yes' || $ANSWER = 'YES' ]] 
then

    echo searching for processes....
    sockproc=$(ps -efww | grep "jboss\.Main" |grep $USER | cut -c9-14)

    echo processes $sockproc


    # if they are not blank, kill them
    if [[ -n $sockproc ]]; then
    #  ps -efww |grep "compilerExec.sh" | grep $USER
      echo killing $sockproc ...
      kill -9 $sockproc
      echo "kill -9 ${sockproc}"
    else
      echo "jboss.Main process was not found."
    fi

    #### Now back up all the compiler logs ####
    backupDir="backup_logs"
    
    #if the backup directory does not exist, create it
    if [ ! -d $backupDir ]
    then
      mkdir backup_logs
    fi
    
    #retrieve the date as mm/dd/yy
    dateVar=$(date +%D)
    
    #retrieve the time as hh:mm:ss
    timeVar=$(date +%T)
    
    #format date as mm_dd_yy
    mm=$(expr $dateVar : '\([^/]*\)')
    dd=$(expr $dateVar : '[^/]*/\([^/]*\)')
    yy=$(expr $dateVar : '[^/]*/[^/]*/\([^/]*\)')
    dateVar=$mm'_'$dd'_'$yy':'$timeVar


    declare -a files
    files[0]="gc.log"
    files[1]="nohup.out"
    
    #create backup logs with the date appended to the filenames 
    for i in ${files[@]}
    do
      if [ -f $i ]
      then
        echo backing up $i
        cp $i $i.$dateVar
        mv $i.$dateVar $backupDir
      fi
    done
    
fi



