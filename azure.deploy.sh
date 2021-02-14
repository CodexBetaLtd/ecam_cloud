#!/bin/bash

WAR_FILE=./build/libs/ECAM.war

echo deploying the artificat $WAR_FILE
curl -X POST -u sajja:BladeMaster1 https://ecam-sajith.scm.azurewebsites.net/api/wardeploy --data-binary @$WAR_FILE
